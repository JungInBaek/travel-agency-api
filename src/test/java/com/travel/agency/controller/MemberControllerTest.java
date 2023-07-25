package com.travel.agency.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.agency.domain.Member;
import com.travel.agency.dto.request.MemberCreate;
import com.travel.agency.dto.request.MemberUpdate;
import com.travel.agency.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
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

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
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
    @DisplayName("회원수정 테스트")
    void update() throws Exception {
        // given
        Member member = Member.builder()
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

        memberRepository.save(member);

        MemberUpdate memberUpdate = MemberUpdate.builder()
                .id("baek")
                .password("54321")
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
        Member member = Member.builder()
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

        memberRepository.save(member);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/members/{id}", member.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(member.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(member.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ssn").value(member.getSsn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tel").value(member.getTel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(member.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.postcode").value(member.getPostcode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(member.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.englishName").value(member.getEnglishName()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원삭제 테스트")
    void delete() throws Exception {
        // given
        Member member = Member.builder()
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

        memberRepository.save(member);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.delete("/members/{id}", member.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}