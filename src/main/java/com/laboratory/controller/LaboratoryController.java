package com.laboratory.controller;

import com.laboratory.model.Laboratory;
import com.laboratory.service.LaboratoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @PostMapping(value = "/{laboratoryId}/exams/{examId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addLaboratory(@PathVariable Long laboratoryId, @PathVariable Long examId) {
        laboratoryService.addExam(laboratoryId, examId);
    }

    @DeleteMapping(value = "/{laboratoryId}/exams/{examId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLaboratory(@PathVariable Long laboratoryId, @PathVariable Long examId) {
        laboratoryService.deleteExam(laboratoryId, examId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Laboratory> get(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                @RequestParam(value = "size", defaultValue = "30", required = false) Integer size) {
        return laboratoryService.find(PageRequest.of(page, size));
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

    @PutMapping(value = "/bulk")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Laboratory> putBulk(@RequestBody Set<Laboratory> laboratories) {
        return laboratoryService.updateBulk(laboratories);
    }

    @DeleteMapping(value = "/{laboratoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long laboratoryId) {
        laboratoryService.delete(laboratoryId);
    }

    @PostMapping(value = "/bulk")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBulk(@RequestParam("ids") Set<Long> ids) {
        laboratoryService.deleteBulk(ids);
    }

}
