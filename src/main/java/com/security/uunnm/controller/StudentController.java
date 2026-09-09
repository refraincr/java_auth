package com.security.uunnm.controller;

import com.security.uunnm.entity.Students;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class StudentController {

    private List<Students> students =  new ArrayList<>(List.of(
            new Students(16L,"相川步",78f),
            new Students(17L,"春奈",100f),
            new Students(18L,"瑟拉",78f)
    ));

    @GetMapping("students")
    public List<Students> students() {
        return students;
    }

    @PostMapping("students")
    public Students students(@RequestBody Students student) {
        students.add(student);
        return student;
    }
}
