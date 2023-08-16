package com.travel.agency.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.travel.agency.domain.Member;
import com.travel.agency.dto.request.MemberCreate;
import com.travel.agency.dto.request.MemberUpdate;
import com.travel.agency.dto.response.MemberResponse;
import com.travel.agency.exception.MemberNotFoundException;
import com.travel.agency.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

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
    void createTest() {
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

        // when
        memberService.create(memberCreate);

        // then
        Member member = memberRepository.findById(memberCreate.getId()).get();

        assertNotNull(member);
        assertEquals(memberCreate.getId(), member.getId());
        assertEquals(EncryptionUtils.hashing(memberCreate.getPassword(), member.getSalt()), member.getPassword());
        assertEquals(memberCreate.getName(), member.getName());
        assertEquals(memberCreate.getSsn(), member.getSsn());
        assertEquals(memberCreate.getTel(), member.getTel());
        assertEquals(memberCreate.getEmail(), member.getEmail());
        assertEquals(memberCreate.getPostcode(), member.getPostcode());
        assertEquals(memberCreate.getAddress(), member.getAddress());
        assertEquals(memberCreate.getEnglishName(), member.getEnglishName());
    }

    @Test
    @DisplayName("회원수정 테스트")
    void editMemberTest() {
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

        // when
        memberService.update(memberUpdate);

        // then
        Member actualMember = memberRepository.findById(memberUpdate.getId()).get();
        assertNotNull(actualMember);
        assertEquals(memberUpdate.getEmail(), actualMember.getEmail());
        assertEquals(memberUpdate.getPostcode(), actualMember.getPostcode());
        assertEquals(memberUpdate.getAddress(), actualMember.getAddress());
        assertEquals(memberUpdate.getEnglishName(), actualMember.getEnglishName());
    }

    @Test
    @DisplayName("회원조회 테스트")
    void fineMemberTest() {
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

        // when
        MemberResponse memberResponse = memberService.get(memberCreate.getId());

        // then
        assertNotNull(memberCreate);
        assertEquals(memberCreate.getId(), memberResponse.getId());
        assertEquals(memberCreate.getName(), memberResponse.getName());
        assertEquals(memberCreate.getSsn(), memberResponse.getSsn());
        assertEquals(memberCreate.getTel(), memberResponse.getTel());
        assertEquals(memberCreate.getEmail(), memberResponse.getEmail());
        assertEquals(memberCreate.getPostcode(), memberResponse.getPostcode());
        assertEquals(memberCreate.getAddress(), memberResponse.getAddress());
        assertEquals(memberCreate.getEnglishName(), memberResponse.getEnglishName());
    }

    @Test
    @DisplayName("회원조회 테스트 - 존재하지 않는 회원")
    void findFailedTest() {
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
        assertThrows(MemberNotFoundException.class, () -> {
            memberService.get("kwon");
        });
    }

    @Test
    @DisplayName("회원삭제 테스트")
    void deleteTest() {
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

        // when
        memberService.delete(memberCreate.getId());

        // then
        assertThrows(MemberNotFoundException.class, () -> {
            memberService.get(memberCreate.getId());
        });
    }

}