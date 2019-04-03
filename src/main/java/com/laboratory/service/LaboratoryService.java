package com.laboratory.service;

import com.laboratory.exception.BadRequestException;
import com.laboratory.exception.NotFoundException;
import com.laboratory.model.Exam;
import com.laboratory.model.Laboratory;
import com.laboratory.model.LaboratoryExam;
import com.laboratory.repository.ExamRepository;
import com.laboratory.repository.LaboratoryExamRepository;
import com.laboratory.repository.LaboratoryRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class LaboratoryService {

    private final LaboratoryRepository laboratoryRepository;

    private final LaboratoryExamRepository laboratoryExamRepository;

    private final ExamRepository examRepository;

    public Collection<Laboratory> findByExam(String exam) {
        return laboratoryRepository.findByExam(exam);
    }

    public Laboratory find(Long laboratoryId) {
        log.info("M=find, finding laboratory with id {}", laboratoryId);
        return laboratoryRepository.findById(laboratoryId).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void addExam(Long laboratoryId, Long examId) {

        final Laboratory laboratory = laboratoryRepository.findById(laboratoryId)
                .orElseThrow(() -> new BadRequestException("laboratory doesn't exists"));
        final Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new BadRequestException("exam doesn't exists"));

        final Optional<LaboratoryExam> laboratoryExamOpt = laboratoryExamRepository
                .findByLaboratoryIdAndExamId(laboratory.getId(), exam.getId());

        if (laboratoryExamOpt.isPresent()) {
            final LaboratoryExam laboratoryExam = laboratoryExamOpt.get();
            laboratoryExam.setRemoved(false);
            laboratoryExamRepository.save(laboratoryExam);
        } else {
            laboratoryExamRepository.save(LaboratoryExam.builder().laboratory(laboratory).exam(exam).build());
        }
    }

    @Transactional
    public void deleteExam(Long laboratoryId, Long examId) {

        final LaboratoryExam laboratoryExam = laboratoryExamRepository.findByLaboratoryIdAndExamId(laboratoryId, examId)
                .orElseThrow(() -> new BadRequestException("laboratory or exam doesn't exists"));

        laboratoryExamRepository.deleteById(laboratoryExam.getId());
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

    public Laboratory update(@NonNull Long laboratoryId, Laboratory laboratory) {
        final Laboratory lab = laboratoryRepository.findById(laboratoryId)
                .orElseThrow(BadRequestException::new);

        laboratory.setId(lab.getId());

        log.info("M=delete, updating laboratory {}", laboratory);
        return laboratoryRepository.save(laboratory);
    }

    @Transactional
    public Collection<Laboratory> updateBulk(Set<Laboratory> laboratories) {
        return laboratories.parallelStream().map(laboratory -> update(laboratory.getId(), laboratory))
                .collect(Collectors.toList());
    }

    public void delete(Long laboratoryId) {
        log.info("M=delete, deleting laboratory with id {}", laboratoryId);
        laboratoryRepository.deleteById(laboratoryId);
    }

    public void deleteBulk(Set<Long> ids) {
        ids.forEach(this::delete);
    }
}
