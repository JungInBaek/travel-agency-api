package com.travel.agency.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.agency.dto.request.MemberCreate;
import com.travel.agency.repository.MemberRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
class ExceptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void clear() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("요청 파라미터 바인딩 예외 발생 테스트")
    void invalidRequestHandlerTest() throws Exception {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .id("")
                .password("")
                .name("")
                .ssn("")
                .tel("")
                .email("baek.naver.com")
                .postcode("ㅁs")
                .address("부산 해운대구 송정동")
                .englishName("한글")
                .build();

        String json = objectMapper.writeValueAsString(memberCreate);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.id").value("아이디를 입력해주세요"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.password").value("비밀번호를 입력해주세요"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.name").value("이름을 입력해주세요"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.ssn").value("주민번호를 입력해주세요"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.tel").value("전화번호를 입력해주세요"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.email").value("이메일 형식을 맞춰주세요."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.postcode").value("숫자만 입력할 수 있습니다"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.englishName").value("영문만 입력할 수 있습니다"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("MemberNotFoundException 예외 발생 테스트")
    void memberNotFoundExceptionTest() throws Exception {
        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/members/{id}", "baek")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("404 NOT_FOUND"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("존재하지 않는 회원입니다"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation").isEmpty())
                .andDo(MockMvcResultHandlers.print());
    }

}