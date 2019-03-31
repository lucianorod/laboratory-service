package com.laboratory.service;

import com.laboratory.dto.ExamDto;
import com.laboratory.exception.BadRequestException;
import com.laboratory.exception.NotFoundException;
import com.laboratory.model.Exam;
import com.laboratory.model.ExamType;
import com.laboratory.model.Laboratory;
import com.laboratory.repository.ExamRepository;
import com.laboratory.repository.ExamTypeRepository;
import com.laboratory.repository.LaboratoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@AllArgsConstructor
public class ExamService {

    private final ExamTypeRepository examTypeRepository;

    private final ExamRepository examRepository;

    private final LaboratoryRepository laboratoryRepository;

    public Exam save(ExamDto examDto) {

        final ExamType examType = validateExamType(examDto.getExamType());
        final Collection<Laboratory> laboratories = laboratoryRepository.findByIdIn(examDto.getLaboratories());
        final Exam exam = Exam.builder().examType(examType).laboratories(laboratories).name(examDto.getName()).build();

        return examRepository.save(exam);
    }

    public Exam get(Long examId) {
        log.info("M=get, finding exam with id {}", examId);
        return examRepository.findById(examId).orElseThrow(NotFoundException::new);
    }

    public Page<Exam> get(Pageable pageable) {
        return examRepository.findAll(pageable);
    }

    public Exam update(Long examId, ExamDto examDto) {

        final Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new BadRequestException("exam with id doesn't exists"));
        final ExamType examType = validateExamType(examDto.getExamType());
        final Collection<Laboratory> laboratories = laboratoryRepository.findByIdIn(examDto.getLaboratories());
        final Exam updatedExam = Exam.builder().id(exam.getId()).name(examDto.getName()).examType(examType)
                .laboratories(laboratories).build();

        return examRepository.save(updatedExam);
    }

    public void delete(Long examId) {
        log.info("M=delete, deleting exam with id {}", examId);
        laboratoryRepository.deleteById(examId);
    }

    private ExamType validateExamType(String examType) {
        return examTypeRepository.findByName(examType)
                .orElseThrow(() -> new BadRequestException("exam type doesn't exists"));
    }
}
