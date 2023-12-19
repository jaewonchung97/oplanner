package kr.ac.gachon.oplanner.domain.forms;

import kr.ac.gachon.oplanner.domain.dbcolumns.LecTime;
import lombok.Data;

@Data
public class TimeTableForm {
    private String lecNum;
    private String lecName;
    private String day;
    private String startTime;
    private String endTime;

    public TimeTableForm(LecTime time) {
        this.lecNum = time.getLecture().getLecNum();
        this.lecName = time.getLecture().getLecName();
        this.day = time.getDay().name();
        this.startTime = time.getStartTime().toString();
        this.endTime = time.getEndTime().toString();
    }
}
