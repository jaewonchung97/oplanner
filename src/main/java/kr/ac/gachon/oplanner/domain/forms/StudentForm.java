package kr.ac.gachon.oplanner.domain.forms;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class StudentForm {
    private String name;
    private int num;
}
