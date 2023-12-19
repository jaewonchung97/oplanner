package kr.ac.gachon.oplanner.service;

import kr.ac.gachon.oplanner.domain.dbcolumns.LecTime;
import kr.ac.gachon.oplanner.domain.dbcolumns.Lecture;
import kr.ac.gachon.oplanner.domain.forms.ConstrainForm;
import kr.ac.gachon.oplanner.repository.interfaces.LecTimeRepository;
import kr.ac.gachon.oplanner.repository.interfaces.LectureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RecommendService {

    private final LectureRepository lectureRepository;
    private final LecTimeRepository lecTimeRepository;

    public RecommendService(LectureRepository lectureRepository, LecTimeRepository lecTimeRepository) {
        this.lectureRepository = lectureRepository;
        this.lecTimeRepository = lecTimeRepository;
    }

    private static <T> List<List<T>> generateCombinations(List<T> elements, int r) {
        List<List<T>> combinations = new ArrayList<>();
        generateCombinationsHelper(elements, combinations, new ArrayList<>(), 0, r);
        return combinations;
    }

    private static <T> void generateCombinationsHelper(List<T> elements, List<List<T>> combinations,
                                                       List<T> current, int start, int r) {
        if (r == 0) {
            combinations.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < elements.size(); i++) {
            current.add(elements.get(i));
            generateCombinationsHelper(elements, combinations, current, i + 1, r - 1);
            current.remove(current.size() - 1);
        }
    }

    public List<List<Lecture>> getAllPosTimeTables(ConstrainForm constrainForm) {
        ArrayList<List<Lecture>> result = new ArrayList<>();

        List<List<String>> lecNameCombs = generateCombinations(constrainForm.getLecNames(), constrainForm.getNumOfLectures());
        for (List<String> lecNameComb : lecNameCombs) {
            List<List<Lecture>> posTimeTables = getPosTimeTables(lecNameComb, constrainForm);
            if (!posTimeTables.isEmpty()) result.addAll(posTimeTables);
        }
        return result;
    }

    private List<List<Lecture>> getPosTimeTables(List<String> lecNameComb, ConstrainForm constrainForm) {
        List<List<Lecture>> selLec = new ArrayList<>();
        for (String lecName : lecNameComb) {
            selLec.add(lectureRepository.getLecturesByName(lecName));
        }

        List<List<Lecture>> posTimeTables = new ArrayList<>();

        List<Lecture> lectures = selLec.get(0);
        for (Lecture lecture : lectures) {
            List<Lecture> timeTable = new ArrayList<>();
            timeTable.add(lecture);
            posTimeTables.add(timeTable);
        }

        for (int i = 1; i < selLec.size(); i++) {
            if (posTimeTables.isEmpty()) return posTimeTables;
            lectures = selLec.get(i);
            List<List<Lecture>> tempTimeTables = new ArrayList<>(posTimeTables);
            posTimeTables.clear();
            for (List<Lecture> tempPosTimeTable : tempTimeTables) {
                for (Lecture lecture : lectures) {
                    List<Lecture> tempTimeTable = new ArrayList<>(tempPosTimeTable);
                    tempTimeTable.add(lecture);
                    List<LecTime> times = lecTimeRepository.getAllLecTimes(tempTimeTable);
                    if (TimeTableConstraint.checkConstraint(times, constrainForm.getExceptDays()))
                        posTimeTables.add(tempTimeTable);
                }
            }
        }
        return posTimeTables;
    }
}
