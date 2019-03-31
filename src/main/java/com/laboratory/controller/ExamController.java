package com.laboratory.controller;

import com.laboratory.dto.ExamDto;
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
    public Exam post(@RequestBody ExamDto exam) {
        return examService.save(exam);
    }

    @GetMapping(value = "/{examId}")
    @ResponseStatus(HttpStatus.OK)
    public Exam get(@PathVariable Long examId) {
        return examService.get(examId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Exam> get(Pageable pageable) {
        return examService.get(pageable);
    }

    @PutMapping(value = "/{examId}")
    @ResponseStatus(HttpStatus.OK)
    public Exam put(@PathVariable Long examId, @RequestBody ExamDto exam) {
        return examService.update(examId, exam);
    }

    @DeleteMapping(value = "/{examId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long examId) {
        examService.delete(examId);
    }
}
