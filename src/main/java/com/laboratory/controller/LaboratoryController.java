package com.laboratory.controller;

import com.laboratory.dto.LaboratoryDto;
import com.laboratory.model.Laboratory;
import com.laboratory.service.LaboratoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/laboratory")
public class LaboratoryController {

    private final LaboratoryService laboratoryService;

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
    public Laboratory post(@RequestBody LaboratoryDto laboratoryDto) {
        return laboratoryService.save(laboratoryDto);
    }

    @PutMapping(value = "/{laboratoryId}")
    @ResponseStatus(HttpStatus.OK)
    public Laboratory put(@PathVariable Long laboratoryId, @RequestBody LaboratoryDto laboratoryDto) {
        return laboratoryService.update(laboratoryId, laboratoryDto);
    }

    @DeleteMapping(value = "/{laboratoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long laboratoryId) {
        laboratoryService.delete(laboratoryId);
    }
}
