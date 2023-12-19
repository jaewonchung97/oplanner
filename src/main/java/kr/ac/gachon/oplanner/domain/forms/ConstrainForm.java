package kr.ac.gachon.oplanner.domain.forms;

import kr.ac.gachon.oplanner.domain.enums.DayEnum;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class ConstrainForm {
    private Set<DayEnum> exceptDays;
    private int numOfLectures;
    private List<String> lecNames;

    public ConstrainForm(List<String> exceptDays, int credits, List<String> lecNames){
        this.exceptDays = new HashSet<>();
        for (String exceptDay : exceptDays) {
            this.exceptDays.add(DayEnum.valueOf(exceptDay));
        }
        this.numOfLectures = credits / 3;
        this.lecNames = lecNames;
    }
}
