package com.laboratory.service;

import com.laboratory.dto.ExamInDto;
import com.laboratory.dto.ExamOutDto;
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
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper;

    public ExamOutDto save(ExamInDto examDto) {

        final ExamType examType = validateExamType(examDto.getExamType());
        final Collection<Laboratory> laboratories = laboratoryRepository.findByIdIn(examDto.getLaboratories());
        final Exam newExam = Exam.builder().examType(examType).laboratories(laboratories)
                .name(examDto.getName()).build();

        log.info("M=save, saving exam={}", newExam);

        final Exam exam = examRepository.save(newExam);
        return modelMapper.map(exam, ExamOutDto.class);
    }

    public Exam find(Long examId) {
        log.info("M=find, finding exam with id {}", examId);
        return examRepository.findById(examId).orElseThrow(NotFoundException::new);
    }

    public Page<Exam> find(Pageable pageable) {
        return examRepository.findAll(pageable);
    }

    public ExamOutDto update(Long examId, ExamInDto examDto) {

        final Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new BadRequestException("exam with id doesn't exists"));
        final ExamType examType = validateExamType(examDto.getExamType());
        final Collection<Laboratory> laboratories = laboratoryRepository.findByIdIn(examDto.getLaboratories());
        final Exam updatedExam = Exam.builder().id(exam.getId()).name(examDto.getName()).examType(examType)
                .laboratories(laboratories).build();

        log.info("M=update, updating exam {}", updatedExam);

        return modelMapper.map(updatedExam, ExamOutDto.class);
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
