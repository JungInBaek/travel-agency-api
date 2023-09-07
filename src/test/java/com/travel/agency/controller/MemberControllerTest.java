package com.travel.agency.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.agency.domain.Member;
import com.travel.agency.dto.request.MemberCreate;
import com.travel.agency.dto.request.MemberUpdate;
import com.travel.agency.repository.MemberRepository;
import com.travel.agency.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;


    @AfterEach
    void clear() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 테스트")
    void create() throws Exception {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .id("baek")
                .password("12345")
                .name("백정인")
                .ssn("960519-1111111")
                .tel("010-1111-2222")
                .email("baek@naver.com")
                .postcode("45910")
                .address("부산 해운대구 송정동")
                .englishName("BJI")
                .build();

        String json = objectMapper.writeValueAsString(memberCreate);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원가입 테스트 - json 필드 공백")
    void create2() throws Exception {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .id("baek")
                .password("12345")
                .name("백정인")
                .ssn("960519-1111111")
                .tel("010-1111-2222")
                .email("")
                .postcode("")
                .address("")
                .englishName("")
                .build();

        String json = objectMapper.writeValueAsString(memberCreate);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andDo(MockMvcResultHandlers.print());
        Member baek = memberRepository.findById("baek").orElseThrow();
        assertEquals("", baek.getEmail());
        assertEquals("", baek.getPostcode());
        assertEquals("", baek.getAddress());
        assertEquals("", baek.getEnglishName());
    }

    @Test
    @DisplayName("회원가입 테스트 - json 필드 누락")
    void create3() throws Exception {
        // given
        String json = "{\"id\": \"baek\", \"password\": \"12345\", \"name\": \"백정인\", \"ssn\": \"960519-1111111\", \"tel\": \"010-1111-2222\"}";
        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        Member baek = memberRepository.findById("baek").orElseThrow();
        assertEquals("", baek.getEmail());
        assertEquals("", baek.getPostcode());
        assertEquals("", baek.getAddress());
        assertEquals("", baek.getEnglishName());
    }

    @Test
    @DisplayName("회원수정 테스트")
    void update() throws Exception {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .id("baek")
                .password("12345")
                .name("백정인")
                .ssn("960519-1111111")
                .tel("010-1111-2222")
                .email("baek@naver.com")
                .postcode("45910")
                .address("부산 해운대구 송정동")
                .englishName("BJI")
                .build();

        memberService.create(memberCreate);

        MemberUpdate memberUpdate = MemberUpdate.builder()
                .id("baek")
                .password("12345")
                .email("kwon@naver.com")
                .postcode("11111")
                .address("부산 중구 남포동")
                .englishName("KYJ")
                .build();

        String json = objectMapper.writeValueAsString(memberUpdate);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/members", memberUpdate.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원조회 테스트")
    void get() throws Exception {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .id("baek")
                .password("12345")
                .name("백정인")
                .ssn("960519-1111111")
                .tel("010-1111-2222")
                .email("baek@naver.com")
                .postcode("45910")
                .address("부산 해운대구 송정동")
                .englishName("BJI")
                .build();

        memberService.create(memberCreate);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/members/{id}", memberCreate.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(memberCreate.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(memberCreate.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ssn").value(memberCreate.getSsn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tel").value(memberCreate.getTel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(memberCreate.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.postcode").value(memberCreate.getPostcode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(memberCreate.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.englishName").value(memberCreate.getEnglishName()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원삭제 테스트")
    void delete() throws Exception {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .id("baek")
                .password("12345")
                .name("백정인")
                .ssn("960519-1111111")
                .tel("010-1111-2222")
                .email("baek@naver.com")
                .postcode("45910")
                .address("부산 해운대구 송정동")
                .englishName("BJI")
                .build();

        memberService.create(memberCreate);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.delete("/members/{id}", memberCreate.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}