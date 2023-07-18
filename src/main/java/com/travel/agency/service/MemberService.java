package com.travel.agency.service;

import com.travel.agency.domain.Member;
import com.travel.agency.dto.request.MemberCreate;
import com.travel.agency.dto.response.MemberResponse;
import com.travel.agency.exception.MemberNotFoundException;
import com.travel.agency.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void create(MemberCreate memberCreate) {
        Member member = Member.from(memberCreate);
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse get(String id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        return MemberResponse.from(member);
    }

    public void delete(String id) {
        memberRepository.deleteById(id);
    }

}
