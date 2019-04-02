package com.laboratory.config;

import com.laboratory.dto.ExamDto;
import com.laboratory.model.Exam;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        final ModelMapper modelMapper = new ModelMapper();

        TypeMap<Exam, ExamDto> typeMap = modelMapper.createTypeMap(Exam.class, ExamDto.class);
        typeMap.addMappings(mapper -> mapper.map(src -> src.getExamType().getName(), ExamDto::setExamType));
//        typeMap.addMappings(mapper -> mapper.map(src -> src.getLaboratoryExams().stream()
//                .map(LaboratoryExamRepository::getLaboratory).collect(Collectors.toList()), ExamDto::setLaboratories
//        ));

        return modelMapper;
    }
}
