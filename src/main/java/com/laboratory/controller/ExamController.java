package com.laboratory.controller;

import com.laboratory.dto.ExamDto;
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
    public ExamDto post(@RequestBody ExamDto exam) {
        return examService.save(exam);
    }

    @GetMapping(value = "/{examId}")
    @ResponseStatus(HttpStatus.OK)
    public ExamDto get(@PathVariable Long examId) {
        return examService.find(examId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ExamDto> get(Pageable pageable) {
        return examService.find(pageable);
    }

    @PutMapping(value = "/{examId}")
    @ResponseStatus(HttpStatus.OK)
    public ExamDto put(@PathVariable Long examId, @RequestBody ExamDto exam) {
        return examService.update(examId, exam);
    }

    @DeleteMapping(value = "/{examId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long examId) {
        examService.delete(examId);
    }

    @PostMapping(value = "/{examId}/laboratories/{laboratoryId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ExamDto addLaboratory(@PathVariable Long laboratoryId, @PathVariable Long examId) {
        return examService.addLaboratory(laboratoryId, examId);
    }

    @DeleteMapping(value = "/{examId}/laboratories/{laboratoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLaboratory(@PathVariable Long laboratoryId, @PathVariable Long examId) {
        examService.deleteLaboratory(laboratoryId, examId);
    }
}
