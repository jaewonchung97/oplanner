package kr.ac.gachon.oplanner.service;

import kr.ac.gachon.oplanner.domain.dbcolumns.LecTime;
import kr.ac.gachon.oplanner.domain.enums.DayEnum;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class TimeTableConstraint {
    private static final long delayedHour = 1;
    private static final long maxLecHour = 7;

    public static boolean checkConstraint(List<LecTime> tableTimes, Set<DayEnum> exceptDays) {
        Set<DayEnum> checkDays = new HashSet<>(exceptDays);
        DayEnum curDay = null;
        long curDayLecTime = 0;
        Time curEnd = null;
        for (LecTime lecTime : tableTimes) {
            // 9시 수업
            if (lecTime.getStartTime().getTime() == new Time(9,0,0).getTime()) {
                return false;
            }

            if (lecTime.getDay() != curDay) {
//                 하루 최대 수업 시간
                if (curDayLecTime > 0){
                    long timeStandard = maxLecHour * 60 * 60 * 1000;
                    if(curDayLecTime > timeStandard) {
                        return false;
                    }
                }

                curDay = lecTime.getDay();
                curEnd = lecTime.getEndTime();
                curDayLecTime = lecTime.getEndTime().getTime() - lecTime.getStartTime().getTime();
                checkDays.remove(curDay);
                if (checkDays.isEmpty()) {
                    return false;
                }

                continue;
            }

            // 시간 겹침 제거
            if (lecTime.getStartTime().compareTo(curEnd) < 0) return false;

            // 긴 공강 제거
            long timeDiff = delayedHour * 60 * 60 * 1000 + 30 * 60 * 1000;
            if (lecTime.getStartTime().getTime() - curEnd.getTime() > timeDiff) {
                return false;
            }

            curEnd = lecTime.getEndTime();
            curDayLecTime += lecTime.getEndTime().getTime() - lecTime.getStartTime().getTime();
        }
        return true;
    }

}
