package com.laboratory.controller;


import br.com.six2six.fixturefactory.Fixture;
import com.laboratory.LaboratoryServiceApplicationTests;
import com.laboratory.model.Exam;
import com.laboratory.model.ExamType;
import com.laboratory.model.Laboratory;
import com.laboratory.repository.ExamRepository;
import com.laboratory.repository.ExamTypeRepository;
import com.laboratory.repository.LaboratoryRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LaboratoryControllerTest extends LaboratoryServiceApplicationTests {


    @Autowired
    private LaboratoryRepository laboratoryRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ExamTypeRepository examTypeRepository;

    @Before
    public void before() {
        examTypeRepository.save(ExamType.builder().name("IMAGEM").build());
        examTypeRepository.save(ExamType.builder().name("ANALISE CLINICA").build());
    }

    @After
    public void after() {
        laboratoryRepository.deleteAll();
        examRepository.deleteAll();
        examTypeRepository.deleteAll();
    }

    @Test
    public void testPost() throws Exception {

        final String payload = objectMapper.writeValueAsString(Fixture.from(Laboratory.class).gimme("VALID"));

        mvc.perform(post("/laboratories")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name", Matchers.is("Delboni")))
                .andExpect(jsonPath("$.address.street", Matchers.is("Passagem vila nova")))
                .andExpect(jsonPath("$.address.neighborhood", Matchers.is("Sacramenta")))
                .andExpect(jsonPath("$.address.streetNumber", Matchers.is("56")))
                .andExpect(jsonPath("$.address.postalCode", Matchers.is("66123120")));
    }

    @Test
    public void testGet() throws Exception {

        final Laboratory laboratory = laboratoryRepository.save(Fixture.from(Laboratory.class).gimme("VALID"));

        mvc.perform(get("/laboratories/{laboratoryId}", laboratory.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name", Matchers.is("Delboni")))
                .andExpect(jsonPath("$.address.street", Matchers.is("Passagem vila nova")))
                .andExpect(jsonPath("$.address.neighborhood", Matchers.is("Sacramenta")))
                .andExpect(jsonPath("$.address.streetNumber", Matchers.is("56")))
                .andExpect(jsonPath("$.address.postalCode", Matchers.is("66123120")));
    }

    @Test
    public void testGetNotFound() throws Exception {

        mvc.perform(get("/laboratories/{laboratoryId}", 123455))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPut() throws Exception {

        final Laboratory laboratory = laboratoryRepository.save(Fixture.from(Laboratory.class).gimme("VALID"));
        final String payload = objectMapper.writeValueAsString(Fixture.from(Laboratory.class)
                .gimme("VALID-PUT"));

        mvc.perform(put("/laboratories/{laboratoryId}", laboratory.getId())
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name", Matchers.is("Delboni-PUT")))
                .andExpect(jsonPath("$.address.street", Matchers.is("Passagem vila nova")))
                .andExpect(jsonPath("$.address.neighborhood", Matchers.is("Sacramenta")))
                .andExpect(jsonPath("$.address.streetNumber", Matchers.is("56")))
                .andExpect(jsonPath("$.address.postalCode", Matchers.is("66123120")));
    }

    @Test
    public void testDelete() throws Exception {

        final Laboratory laboratory = laboratoryRepository.save(Fixture.from(Laboratory.class).gimme("VALID"));

        mvc.perform(get("/laboratories/{laboratoryId}", laboratory.getId()))
                .andExpect(status().isOk());

        mvc.perform(delete("/laboratories/{laboratoryId}", laboratory.getId()))
                .andExpect(status().isNoContent());

        mvc.perform(get("/laboratories/{laboratoryId}", laboratory.getId()))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testPostBulk() throws Exception {

        final String payload = objectMapper.writeValueAsString(Fixture.from(Laboratory.class)
                .gimme(2, "VALID", "VALID-2"));

        mvc.perform(post("/laboratories/bulk")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[*].id", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[*].name", Matchers.
                        containsInAnyOrder("Delboni", "Lavoisier")));
    }

    @Test
    public void testPutBulk() throws Exception {

        final String payload = objectMapper.writeValueAsString(Fixture.from(Laboratory.class)
                .gimme(2, "VALID", "VALID-2"));

        mvc.perform(post("/laboratories/bulk")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());

        final Collection<Laboratory> laboratories = (Collection<Laboratory>) laboratoryRepository.findAll();

        final String payloadPut = objectMapper.writeValueAsString(laboratories)
                .replace("Delboni", "Delboni-PUT")
                .replace("Lavoisier", "Lavoisier-PUT");

        mvc.perform(put("/laboratories/bulk")
                .content(payloadPut)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[*].id", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[*].name", Matchers.
                        containsInAnyOrder("Delboni-PUT", "Lavoisier-PUT")));

        Assert.assertEquals(2, ((Collection<Laboratory>) laboratoryRepository.findAll()).size());
    }

    @Test
    public void deleteBulk() throws Exception {

        final String payload = objectMapper.writeValueAsString(Fixture.from(Laboratory.class).gimme(2,
                "VALID", "VALID-2"));

        mvc.perform(post("/laboratories/bulk")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(payload))
                .andExpect(status().isCreated());

        final ArrayList<String> ids = ((ArrayList<Laboratory>) laboratoryRepository.findAll()).stream()
                .map(exam -> String.valueOf(exam.getId())).collect(Collectors.toCollection(ArrayList::new));

        mvc.perform(delete("/laboratories/bulk").param("ids", ids.get(0), ids.get(1)))
                .andExpect(status().isNoContent());

        Assert.assertFalse(laboratoryRepository.findById(Long.valueOf(ids.get(0))).isPresent());
        Assert.assertFalse(laboratoryRepository.findById(Long.valueOf(ids.get(1))).isPresent());
    }

    @Test
    public void testAddExam() throws Exception {

        final ExamType examType = examTypeRepository.findByName("IMAGEM").get();
        final Exam exam = examRepository.save(Exam.builder().name("ULTRASSONOGRAFIA").examType(examType).build());
        final Laboratory laboratory = laboratoryRepository.save(Fixture.from(Laboratory.class).gimme("VALID"));

        mvc.perform(post("/laboratories/{laboratoryId}/exams/{examId}", laboratory.getId(), exam.getId()))
                .andExpect(status().isCreated());
    }


    @Test
    public void testDeleteExam() throws Exception {

        final ExamType examType = examTypeRepository.findByName("IMAGEM").get();
        final Exam exam = examRepository.save(Exam.builder().name("ULTRASSONOGRAFIA").examType(examType).build());
        final Laboratory laboratory = laboratoryRepository.save(Fixture.from(Laboratory.class).gimme("VALID"));

        mvc.perform(post("/laboratories/{laboratoryId}/exams/{examId}", laboratory.getId(), exam.getId()))
                .andExpect(status().isCreated());

        mvc.perform(delete("/laboratories/{laboratoryId}/exams/{examId}", laboratory.getId(), exam.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFindLaboratoryByExam() throws Exception {

        final ExamType examType = examTypeRepository.findByName("IMAGEM").get();
        final Exam exam = examRepository.save(Exam.builder().name("ULTRASSONOGRAFIA").examType(examType).build());
        final Laboratory laboratory = laboratoryRepository.save(Fixture.from(Laboratory.class).gimme("VALID"));


        mvc.perform(post("/laboratories/{laboratoryId}/exams/{examId}", laboratory.getId(), exam.getId()))
                .andExpect(status().isCreated());

        mvc.perform(get("/laboratories/exams").param("exam", exam.getName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[*].id", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.[*].name", Matchers.containsInAnyOrder("Delboni")))
                .andExpect(jsonPath("$.[*].address.street", Matchers.
                        containsInAnyOrder("Passagem vila nova")))
                .andExpect(jsonPath("$.[*].address.neighborhood", Matchers.
                        containsInAnyOrder("Sacramenta")))
                .andExpect(jsonPath("$.[*].address.streetNumber", Matchers.
                        containsInAnyOrder("56")))
                .andExpect(jsonPath("$.[*].address.postalCode", Matchers.
                        containsInAnyOrder("66123120")));
    }
}
