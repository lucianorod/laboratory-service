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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ExamControllerTest extends LaboratoryServiceApplicationTests {

    @Autowired
    private ExamTypeRepository examTypeRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ModelMapper modelMapper;

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
                .andExpect(jsonPath("$.name", Matchers.is("ULTRASSONOGRAFIA-PUT")))
                .andExpect(jsonPath("$.examType", Matchers.is("IMAGEM")));
    }

    @Test
    public void testDelete() throws Exception {

        final String payload = objectMapper.writeValueAsString(Fixture.from(ExamDto.class).gimme("VALID"));

        mvc.perform(post("/exams")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(payload))
                .andExpect(status().isCreated());

        final Exam exam = examRepository.findAll().iterator().next();

        mvc.perform(delete("/exams/{examId}", exam.getId()))
                .andExpect(status().isNoContent());

        Assert.assertFalse(examRepository.findById(exam.getId()).isPresent());
        Assert.assertEquals(1, ((Collection<Exam>) examRepository.findAll()).size());
    }

    @Test
    public void testPostBulk() throws Exception {

        final String payload = objectMapper.writeValueAsString(Fixture.from(ExamDto.class).gimme(2,
                "VALID", "VALID-2"));

        mvc.perform(post("/exams/bulk")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(payload))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[*].id", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[*].name", Matchers
                        .containsInAnyOrder("ULTRASSONOGRAFIA", "MAMOGRAFIA")))
                .andExpect(jsonPath("$.[*].examType", Matchers
                        .containsInAnyOrder("IMAGEM", "IMAGEM")));

    }

    @Test
    public void testPutBulk() throws Exception {

        final String payload = objectMapper.writeValueAsString(Fixture.from(ExamDto.class).gimme(2,
                "VALID", "VALID-2"));

        mvc.perform(post("/exams/bulk")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(payload))
                .andExpect(status().isCreated());

        final Collection<Exam> exams = (Collection<Exam>) examRepository.findAll();

        final Collection<ExamDto> examsDto = modelMapper.map(exams, new TypeToken<Collection<ExamDto>>() {
        }.getType());

        final String payloadPut = objectMapper.writeValueAsString(examsDto)
                .replace("ULTRASSONOGRAFIA", "ULTRASSONOGRAFIA-PUT")
                .replace("MAMOGRAFIA", "MAMOGRAFIA-PUT");

        mvc.perform(put("/exams/bulk")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(payloadPut))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[*].id", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[*].name", Matchers
                        .containsInAnyOrder("ULTRASSONOGRAFIA-PUT", "MAMOGRAFIA-PUT")))
                .andExpect(jsonPath("$.[*].examType", Matchers
                        .containsInAnyOrder("IMAGEM", "IMAGEM")));

        Assert.assertEquals(2, ((Collection<Exam>) examRepository.findAll()).size());
    }

    @Test
    public void deleteBulk() throws Exception {

        final String payload = objectMapper.writeValueAsString(Fixture.from(ExamDto.class).gimme(2,
                "VALID", "VALID-2"));

        mvc.perform(post("/exams/bulk")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(payload))
                .andExpect(status().isCreated());

        final ArrayList<String> ids = ((ArrayList<Exam>) examRepository.findAll()).stream()
                .map(exam -> String.valueOf(exam.getId())).collect(Collectors.toCollection(ArrayList::new));

        mvc.perform(delete("/exams/bulk").param("ids", ids.get(0), ids.get(1)))
                .andExpect(status().isNoContent());

        Assert.assertFalse(examRepository.findById(Long.valueOf(ids.get(0))).isPresent());
        Assert.assertFalse(examRepository.findById(Long.valueOf(ids.get(1))).isPresent());
    }
}
