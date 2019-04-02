package com.laboratory.controller;

import br.com.six2six.fixturefactory.Fixture;
import com.laboratory.LaboratoryServiceApplicationTests;
import com.laboratory.dto.ExamDto;
import com.laboratory.model.Exam;
import com.laboratory.model.ExamType;
import com.laboratory.repository.ExamRepository;
import com.laboratory.repository.ExamTypeRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ExamControllerTest extends LaboratoryServiceApplicationTests {

    @Autowired
    private ExamTypeRepository examTypeRepository;

    @Autowired
    private ExamRepository examRepository;

    @Before
    public void before() {
        examTypeRepository.save(ExamType.builder().name("IMAGEM").build());
        examTypeRepository.save(ExamType.builder().name("ANALISE CLINICA").build());
    }

    @After
    public void after() {
        examRepository.deleteAll();
        examTypeRepository.deleteAll();
    }

    @Test
    public void testPost() throws Exception {

        final String payload = objectMapper.writeValueAsString(Fixture.from(ExamDto.class).gimme("VALID"));

        mvc.perform(post("/exams")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(payload))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name", Matchers.is("ULTRASSONOGRAFIA")))
                .andExpect(jsonPath("$.examType", Matchers.is("IMAGEM")));

    }

    @Test
    public void testGet() throws Exception {

        final ExamType examType = examTypeRepository.findByName("IMAGEM").get();
        final Exam exam = examRepository.save(Exam.builder().name("ULTRASSONOGRAFIA").examType(examType).build());

        mvc.perform(get("/exams/{examId}", exam.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name", Matchers.is("ULTRASSONOGRAFIA")))
                .andExpect(jsonPath("$.examType", Matchers.is("IMAGEM")));
    }

    @Test
    public void testPut() throws Exception {

        final ExamType examType = examTypeRepository.findByName("IMAGEM").get();
        final Exam exam = examRepository.save(Exam.builder().name("ULTRASSONOGRAFIA").examType(examType).build());

        final String payload = objectMapper.writeValueAsString(Fixture.from(ExamDto.class).gimme("VALID-PUT"));

        mvc.perform(put("/exams/{examId}", exam.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(payload))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name", Matchers.is("ULTRASSONOGRAFIA")))
                .andExpect(jsonPath("$.examType", Matchers.is("ANALISE CLINICA")));
    }
}
