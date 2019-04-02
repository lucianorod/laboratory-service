package com.laboratory.controller;

import com.laboratory.model.Laboratory;
import com.laboratory.service.LaboratoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/laboratories")
public class LaboratoryController {

    private final LaboratoryService laboratoryService;

    @GetMapping(value = "/exams")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Laboratory> getByExam(@RequestParam(value = "exam") String exam) {
        return laboratoryService.findByExam(exam);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Laboratory> get(Pageable pageable) {
        return laboratoryService.find(pageable);
    }

    @GetMapping(value = "/{laboratoryId}")
    @ResponseStatus(HttpStatus.OK)
    public Laboratory get(@PathVariable Long laboratoryId) {
        return laboratoryService.find(laboratoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Laboratory post(@RequestBody Laboratory laboratory) {
        return laboratoryService.save(laboratory);
    }

    @PostMapping(value = "/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public Collection<Laboratory> postBulk(@RequestBody Set<Laboratory> laboratories) {
        return laboratoryService.saveBulk(laboratories);
    }

    @PutMapping(value = "/{laboratoryId}")
    @ResponseStatus(HttpStatus.OK)
    public Laboratory put(@PathVariable Long laboratoryId, @RequestBody Laboratory laboratory) {
        return laboratoryService.update(laboratoryId, laboratory);
    }

    @DeleteMapping(value = "/{laboratoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long laboratoryId) {
        laboratoryService.delete(laboratoryId);
    }
}
