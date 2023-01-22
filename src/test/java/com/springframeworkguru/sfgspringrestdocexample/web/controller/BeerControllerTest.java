package com.springframeworkguru.sfgspringrestdocexample.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframeworkguru.sfgspringrestdocexample.domain.Beer;
import com.springframeworkguru.sfgspringrestdocexample.repository.BeerRepository;
import com.springframeworkguru.sfgspringrestdocexample.web.model.BeerDto;
import com.springframeworkguru.sfgspringrestdocexample.web.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "com.springframeworkguru.sfgspringrestdocexample.web.mapper")
class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerRepository beerRepository;

    @Test
    void getBeerId() throws Exception {
        given(beerRepository.findById(any())).willReturn(Optional.of(Beer.builder().build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/" + UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void saveNewBeer() throws Exception {
        BeerDto beerDto = getValidBeerDto();
        String beerJson = objectMapper.writeValueAsString(beerDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON)
                .content(beerJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBeerById() throws Exception {
        BeerDto beerDto = getValidBeerDto();
        given(beerRepository.findById(any())).willReturn(Optional.of(Beer.builder().build()));

        String beerJson = objectMapper.writeValueAsString(beerDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/beer/"+UUID.randomUUID().toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(beerJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private BeerDto getValidBeerDto() {
        return BeerDto.builder()
                .name("Nice Ale")
                .style(BeerStyle.ALE)
                .price(BigDecimal.valueOf(9.99))
                .upc(123123123L)
                .build();
    }
}