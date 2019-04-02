package com.laboratory.service;

import com.laboratory.dto.ExamDto;
import com.laboratory.exception.BadRequestException;
import com.laboratory.exception.NotFoundException;
import com.laboratory.model.Exam;
import com.laboratory.model.ExamType;
import com.laboratory.model.Laboratory;
import com.laboratory.model.LaboratoryExam;
import com.laboratory.repository.ExamRepository;
import com.laboratory.repository.ExamTypeRepository;
import com.laboratory.repository.LaboratoryExamRepository;
import com.laboratory.repository.LaboratoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ExamService {

    private final ExamTypeRepository examTypeRepository;

    private final ExamRepository examRepository;

    private final LaboratoryRepository laboratoryRepository;

    private final LaboratoryExamRepository laboratoryExamRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public void deleteLaboratory(Long laboratoryId, Long examId) {

        final LaboratoryExam laboratoryExam = laboratoryExamRepository.findByLaboratoryIdAndExamId(laboratoryId, examId)
                .orElseThrow(() -> new BadRequestException("laboratory or exam doesn't exists"));

        laboratoryExamRepository.deleteById(laboratoryExam.getId());
    }

    @Transactional
    public ExamDto addLaboratory(Long laboratoryId, Long examId) {

        final Laboratory laboratory = laboratoryRepository.findById(laboratoryId)
                .orElseThrow(() -> new BadRequestException("laboratory doesn't exists"));
        final Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new BadRequestException("exam doesn't exists"));

        laboratoryExamRepository.save(LaboratoryExam.builder().laboratory(laboratory).exam(exam).build());

        return modelMapper.map(examRepository.findById(examId), ExamDto.class);
    }

    @Transactional
    public ExamDto save(ExamDto examDto) {

        final ExamType examType = validateExamType(examDto.getExamType());
        final Exam newExam = Exam.builder().examType(examType).name(examDto.getName()).build();
        final Exam exam = examRepository.save(newExam);

        log.info("M=save, saving exam={}", newExam);

        return modelMapper.map(exam, ExamDto.class);
    }

    public ExamDto find(Long examId) {

        log.info("M=find, finding exam with id {}", examId);
        final Exam exam = examRepository.findById(examId).orElseThrow(NotFoundException::new);

        return modelMapper.map(exam, ExamDto.class);
    }

    public Page<ExamDto> find(Pageable pageable) {
        final Page<Exam> exams = examRepository.findAll(pageable);
        return new PageImpl<>(exams.get().map(exam -> modelMapper.map(exam, ExamDto.class))
                .collect(Collectors.toList()));
    }

    public ExamDto update(Long examId, ExamDto examDto) {

        final Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new BadRequestException("exam doesn't exists"));
        final ExamType examType = validateExamType(examDto.getExamType());
        final Exam updatedExam = Exam.builder().id(exam.getId()).name(examDto.getName()).examType(examType).build();

        log.info("M=update, updating exam {}", updatedExam);

        return modelMapper.map(updatedExam, ExamDto.class);
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
