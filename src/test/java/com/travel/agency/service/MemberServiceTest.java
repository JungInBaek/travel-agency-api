package com.travel.agency.service;

import com.travel.agency.domain.Member;
import com.travel.agency.dto.request.MemberCreate;
import com.travel.agency.dto.request.MemberUpdate;
import com.travel.agency.dto.response.MemberResponse;
import com.travel.agency.exception.MemberNotFoundException;
import com.travel.agency.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

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
        assertEquals(EncryptionUtils.encryptionSHA256(memberCreate.getPassword()), member.getPassword());
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

        // when
        memberService.update(memberUpdate);

        // then
        Member actualMember = memberRepository.findById(memberUpdate.getId()).get();
        assertNotNull(actualMember);
        assertEquals(EncryptionUtils.encryptionSHA256(memberUpdate.getPassword()), actualMember.getPassword());
        assertEquals(memberUpdate.getEmail(), actualMember.getEmail());
        assertEquals(memberUpdate.getPostcode(), actualMember.getPostcode());
        assertEquals(memberUpdate.getAddress(), actualMember.getAddress());
        assertEquals(memberUpdate.getEnglishName(), actualMember.getEnglishName());
    }

    @Test
    @DisplayName("회원조회 테스트")
    void fineMemberTest() {
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

        // when
        MemberResponse memberResponse = memberService.get(member.getId());

        // then
        assertNotNull(member);
        assertEquals(member.getId(), memberResponse.getId());
        assertEquals(member.getName(), memberResponse.getName());
        assertEquals(member.getSsn(), memberResponse.getSsn());
        assertEquals(member.getTel(), memberResponse.getTel());
        assertEquals(member.getEmail(), memberResponse.getEmail());
        assertEquals(member.getPostcode(), memberResponse.getPostcode());
        assertEquals(member.getAddress(), memberResponse.getAddress());
        assertEquals(member.getEnglishName(), memberResponse.getEnglishName());
    }

    @Test
    @DisplayName("회원조회 테스트 - 존재하지 않는 회원")
    void findFailedTest() {
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
        assertThrows(MemberNotFoundException.class, () -> {
            memberService.get("kwon");
        });
    }

    @Test
    @DisplayName("회원삭제 테스트")
    void deleteTest() {
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

        // when
        memberService.delete(member.getId());

        // then
        assertThrows(MemberNotFoundException.class, () -> {
            memberService.get(member.getId());
        });
    }

}