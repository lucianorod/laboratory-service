package com.laboratory.controller;


import br.com.six2six.fixturefactory.Fixture;
import com.laboratory.LaboratoryServiceApplicationTests;
import com.laboratory.dto.LaboratoryDto;
import com.laboratory.model.Laboratory;
import com.laboratory.repository.LaboratoryRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LaboratoryControllerTest extends LaboratoryServiceApplicationTests {


    @Autowired
    private LaboratoryRepository laboratoryRepository;

    @Test
    public void testPost() throws Exception {

        final String payload = objectMapper.writeValueAsString(Fixture.from(LaboratoryDto.class).gimme("VALID"));

        mvc.perform(post("/laboratories")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name", Matchers.is("Flour Laboratory")))
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
                .andExpect(jsonPath("$.name", Matchers.is("Flour Laboratory")))
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
        final String payload = objectMapper.writeValueAsString(Fixture.from(LaboratoryDto.class)
                .gimme("VALID-PUT"));

        mvc.perform(put("/laboratories/{laboratoryId}", laboratory.getId())
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name", Matchers.is("Luciano Laboratory")))
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
}
