package com.travel.agency.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

@SpringBootTest
@AutoConfigureMockMvc
class ExceptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @AfterEach
    void clear() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("가입요청 파라미터 바인딩 예외 발생 테스트")
    void invalidRequestHandlerTest() throws Exception {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .id("")
                .password("")
                .name("")
                .ssn("")
                .tel("")
                .email("baek.naver.com")
                .postcode("ㅁs1")
                .address("부산 해운대구 송정동")
                .englishName("한글")
                .build();

        String json = objectMapper.writeValueAsString(memberCreate);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.id", "올바르지 않는 형식입니다").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.id", "아이디를 입력해주세요").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.password", "올바르지 않는 형식입니다").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.password", "비밀번호를 입력해주세요").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.name", "올바르지 않는 형식입니다").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.name", "이름을 입력해주세요").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.ssn", "올바르지 않는 형식입니다").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.ssn", "주민번호를 입력해주세요").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.tel", "올바르지 않는 형식입니다").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.tel", "전화번호를 입력해주세요").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.email").value("이메일 형식을 맞춰주세요"))
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

    @Test
    @DisplayName("InvalidMemberIdException 예외 발생 테스트")
    void InvalidMemberIdExceptionTest() throws Exception {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .id("baek1")
                .password("test@123")
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
                .id("baek12")
                .password("test@123")
                .email("kwon@naver.com")
                .postcode("11111")
                .address("부산 중구 남포동")
                .englishName("KYJ")
                .build();
        String json = objectMapper.writeValueAsString(memberUpdate);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400 BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 회원ID값입니다"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation").isEmpty())
                .andDo(MockMvcResultHandlers.print());
    }

}