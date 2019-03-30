package com.laboratory.service;

import com.laboratory.dto.ExamDto;
import com.laboratory.exception.BadRequestException;
import com.laboratory.model.Exam;
import com.laboratory.model.ExamType;
import com.laboratory.model.Laboratory;
import com.laboratory.repository.ExamRepository;
import com.laboratory.repository.ExamTypeRepository;
import com.laboratory.repository.LaboratoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class ExamService {

    private final ExamTypeRepository examTypeRepository;

    private final ExamRepository examRepository;

    private final LaboratoryRepository laboratoryRepository;

    public Exam save(ExamDto examDto) {

        final ExamType examType = examTypeRepository.findByName(examDto.getName())
                .orElseThrow(() -> new BadRequestException("the exam type doesn't exists"));
        final Collection<Laboratory> laboratories = laboratoryRepository.findByIdIn(examDto.getLaboratories());
        final Exam exam = Exam.builder().examType(examType).laboratories(laboratories).name(examDto.getName()).build();

        return examRepository.save(exam);
    }
}
