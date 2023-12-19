package kr.ac.gachon.oplanner.domain.forms;

import kr.ac.gachon.oplanner.domain.enums.ClassificationEnum;
import lombok.Data;

@Data
public class LectureInfo {
    private String lecName;
    private String classification;
    private int credit;

    public LectureInfo(String lecName, ClassificationEnum classificationEnum, int credit){
        this.lecName = lecName;
        this.credit = credit;
        if(classificationEnum == ClassificationEnum.MAJOR_ELECTIVE) this.classification = "Optional";
        else if (classificationEnum==ClassificationEnum.MAJOR_REQUIRED) this.classification = "Required";
    }
}
