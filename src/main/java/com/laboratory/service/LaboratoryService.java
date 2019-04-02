package com.laboratory.service;

import com.laboratory.exception.BadRequestException;
import com.laboratory.exception.NotFoundException;
import com.laboratory.model.Laboratory;
import com.laboratory.repository.LaboratoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class LaboratoryService {

    private final LaboratoryRepository laboratoryRepository;

    public Collection<Laboratory> findByExam(String exam) {
        return laboratoryRepository.findByExam(exam);
    }

    public Laboratory find(Long laboratoryId) {
        log.info("M=find, finding laboratory with id {}", laboratoryId);
        return laboratoryRepository.findById(laboratoryId).orElseThrow(NotFoundException::new);
    }

    public Page<Laboratory> find(Pageable pageable) {
        return laboratoryRepository.findAll(pageable);
    }

    public Laboratory save(Laboratory laboratory) {
        log.info("M=save, saving laboratory={}", laboratory);
        return laboratoryRepository.save(laboratory);
    }

    public Collection<Laboratory> saveBulk(Set<Laboratory> laboratories) {
        return (Collection<Laboratory>) laboratoryRepository.saveAll(laboratories);
    }

    public void delete(Long laboratoryId) {
        log.info("M=delete, deleting laboratory with id {}", laboratoryId);
        laboratoryRepository.deleteById(laboratoryId);
    }

    public Laboratory update(Long laboratoryId, Laboratory laboratory) {
        final Laboratory lab = laboratoryRepository.findById(laboratoryId)
                .orElseThrow(BadRequestException::new);

        laboratory.setId(lab.getId());

        log.info("M=delete, updating laboratory {}", laboratory);
        return laboratoryRepository.save(laboratory);
    }
}
