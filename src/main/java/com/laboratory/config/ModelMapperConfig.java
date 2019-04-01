package com.laboratory.config;

import com.laboratory.dto.ExamOutDto;
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

        TypeMap<Exam, ExamOutDto> typeMap = modelMapper.createTypeMap(Exam.class, ExamOutDto.class);
        typeMap.addMappings(mapper -> mapper.map(src -> src.getExamType().getName(), ExamOutDto::setExamType));

        return modelMapper;
    }
}
