package com.laboratory.controller;

import com.laboratory.dto.ExamInDto;
import com.laboratory.dto.ExamOutDto;
import com.laboratory.model.Exam;
import com.laboratory.service.ExamService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping(value = "/exams")
public class ExamController {

    private final ExamService examService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExamOutDto post(@RequestBody ExamInDto exam) {
        return examService.save(exam);
    }

    @GetMapping(value = "/{examId}")
    @ResponseStatus(HttpStatus.OK)
    public Exam get(@PathVariable Long examId) {
        return examService.find(examId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Exam> get(Pageable pageable) {
        return examService.find(pageable);
    }

    @PutMapping(value = "/{examId}")
    @ResponseStatus(HttpStatus.OK)
    public ExamOutDto put(@PathVariable Long examId, @RequestBody ExamInDto exam) {
        return examService.update(examId, exam);
    }

    @DeleteMapping(value = "/{examId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long examId) {
        examService.delete(examId);
    }
}
