package com.laboratory.controller;

import com.laboratory.dto.ExamDto;
import com.laboratory.service.ExamService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;


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

    @PostMapping(value = "/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public Collection<ExamDto> postBulk(@RequestBody Set<ExamDto> exams) {
        return examService.saveBulk(exams);
    }


    @GetMapping(value = "/{examId}")
    @ResponseStatus(HttpStatus.OK)
    public ExamDto get(@PathVariable Long examId) {
        return examService.find(examId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ExamDto> get(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                             @RequestParam(value = "size", defaultValue = "30", required = false) Integer size) {
        return examService.find(PageRequest.of(page, size));
    }

    @PutMapping(value = "/{examId}")
    @ResponseStatus(HttpStatus.OK)
    public ExamDto put(@PathVariable Long examId, @RequestBody ExamDto exam) {
        return examService.update(examId, exam);
    }

    @PutMapping(value = "/bulk")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ExamDto> putBulk(@RequestBody Set<ExamDto> exams) {
        return examService.updateBulk(exams);
    }

    @DeleteMapping(value = "/{examId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long examId) {
        examService.delete(examId);
    }

    @DeleteMapping(value = "/bulk")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBulk(@RequestParam("ids") Set<Long> ids) {
        examService.deleteBulk(ids);
    }
}
