package com.laboratory.controller;

import com.laboratory.dto.ExamDto;
import com.laboratory.model.Exam;
import com.laboratory.service.ExamService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/exam")
public class ExamController {

    private final ExamService examService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Exam post(ExamDto exam) {
        return examService.save(exam);
    }
}
