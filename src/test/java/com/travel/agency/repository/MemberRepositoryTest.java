package com.travel.agency.repository;

import com.travel.agency.domain.Member;
import com.travel.agency.exception.MemberNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원데이터 등록")
    void memberSaveTest() {
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

        // when
        memberRepository.save(member);

        // then
        Member savedMember = memberRepository.findById("baek").get();

        assertThat(savedMember).isNotNull();
        assertEquals(member.getId(), savedMember.getId());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getSsn(), savedMember.getSsn());
        assertEquals(member.getTel(), savedMember.getTel());
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getPostcode(), savedMember.getPostcode());
        assertEquals(member.getAddress(), savedMember.getAddress());
        assertEquals(member.getEnglishName(), savedMember.getEnglishName());
    }

    @Test
    @DisplayName("회원데이터 조회")
    void findMemberTest() {
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
        Member savedMember = memberRepository.findById("baek").get();

        // then
        assertThat(savedMember).isNotNull();
        assertEquals(member.getId(), savedMember.getId());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getSsn(), savedMember.getSsn());
        assertEquals(member.getTel(), savedMember.getTel());
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getPostcode(), savedMember.getPostcode());
        assertEquals(member.getAddress(), savedMember.getAddress());
        assertEquals(member.getEnglishName(), savedMember.getEnglishName());
    }

    @Test
    @DisplayName("아이디로 회원데이터 삭제")
    void deleteByMemberIdTest() {
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
        memberRepository.deleteById(member.getId());

        // then
        assertThrows(MemberNotFoundException.class, () -> {
            memberRepository.findById(member.getId());
        });
    }

    @Test
    @DisplayName("엔티티로 회원데이터 삭제")
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
        memberRepository.delete(member);

        // then
        assertThrows(MemberNotFoundException.class, () -> {
            memberRepository.findById(member.getId());
        });
    }

}