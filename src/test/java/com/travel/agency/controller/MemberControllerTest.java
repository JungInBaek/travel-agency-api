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
                .id("baek1")
                .password("tE1@Test12")
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
                .id("baek1")
                .password("Test@1234")
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
        Member baek = memberRepository.findById("baek1").orElseThrow();
        assertEquals("", baek.getEmail());
        assertEquals("", baek.getPostcode());
        assertEquals("", baek.getAddress());
        assertEquals("", baek.getEnglishName());
    }

    @Test
    @DisplayName("회원가입 테스트 - json 필드 누락")
    void create3() throws Exception {
        // given
        String json = "{ \"id\":\"baek1\", \"password\":\"Test@123\", \"name\":\"백정인\", \"ssn\":\"960519-1111111\", \"tel\":\"010-1111-2222\" }";

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        Member baek = memberRepository.findById("baek1").orElseThrow();
        assertEquals("", baek.getEmail());
        assertEquals("", baek.getPostcode());
        assertEquals("", baek.getAddress());
        assertEquals("", baek.getEnglishName());
    }

    @Test
    @DisplayName("회원수정 테스트")
    void update1() throws Exception {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .id("baek1")
                .password("Test@123")
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
                .id("baek1")
                .password("Test@123")
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
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원수정 테스트 - 잘못된 id값")
    void update2() throws Exception {
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
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원수정 테스트 - 잘못된 패스워드값")
    void update3() throws Exception {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .id("baek1")
                .password("Test@123")
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
                .id("baek1")
                .password("Test@1234")
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 비밀번호입니다"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation").isEmpty())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원수정 테스트 - 수정 데이터 정규표현식")
    void update4() throws Exception {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .id("baek1")
                .password("Test@123")
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
                .id("baek1")
                .password("Test@123")
                .email("kwon.naver.com")
                .postcode("ttttt")
                .address("부산 중구 남포동")
                .englishName("권용제")
                .build();
        String json = objectMapper.writeValueAsString(memberUpdate);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400 BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 요청입니다"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.email").value("이메일 형식을 맞춰주세요"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.postcode").value("숫자만 입력할 수 있습니다"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.englishName").value("영문만 입력할 수 있습니다"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원수정 테스트 - json 필드 누락")
    void update5() throws Exception {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .id("baek1")
                .password("Test@123")
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
                .id("baek1")
                .password("Test@123")
                .email("kwon@naver.com")
                .postcode("11111")
                .address("부산 중구 남포동")
                .englishName("KYJ")
                .build();

        String json = "{ \"id\": \"baek1\", \"password\": \"Test@123\" }";

        // expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        Member baek1 = memberRepository.findById("baek1").orElseThrow();
        assertEquals("", baek1.getEmail());
        assertEquals("", baek1.getPostcode());
        assertEquals("", baek1.getAddress());
        assertEquals("", baek1.getEnglishName());
    }

    @Test
    @DisplayName("회원수정 테스트 - json 필드 누락")
    void update6() throws Exception {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .id("baek1")
                .password("Test@123")
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
                .id("baek1")
                .password("Test@123")
                .email("kwon@naver.com")
                .postcode("11111")
                .address("부산 중구 남포동")
                .englishName("KYJ")
                .build();

        String json = "{ \"id\":\"baek1\", \"password\":\"Test@123\", \"email\":\"kwon@naver.com\" }";

        // expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        Member baek1 = memberRepository.findById("baek1").orElseThrow();
        assertEquals("kwon@naver.com", baek1.getEmail());
        assertEquals("", baek1.getPostcode());
        assertEquals("", baek1.getAddress());
        assertEquals("", baek1.getEnglishName());
    }

    @Test
    @DisplayName("회원조회 테스트")
    void get1() throws Exception {
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
    @DisplayName("회원조회 테스트")
    void get2() throws Exception {
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
        memberService.delete(memberCreate.getId());

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/members/{id}", memberCreate.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원삭제 테스트")
    void delete1() throws Exception {
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

        // expected
        mockMvc.perform(MockMvcRequestBuilders.delete("/members/{id}", memberCreate.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원삭제 테스트")
    void delete2() throws Exception {
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
        memberService.delete(memberCreate.getId());

        // expected
        mockMvc.perform(MockMvcRequestBuilders.delete("/members/{id}", memberCreate.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("존재하지 않는 회원입니다"))
                .andDo(MockMvcResultHandlers.print());
    }

}