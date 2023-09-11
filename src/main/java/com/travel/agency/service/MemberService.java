package com.travel.agency.service;

import com.travel.agency.domain.Member;
import com.travel.agency.dto.request.MemberCreate;
import com.travel.agency.dto.request.MemberUpdate;
import com.travel.agency.dto.response.MemberResponse;
import com.travel.agency.exception.InvalidPasswordException;
import com.travel.agency.exception.MemberNotFoundException;
import com.travel.agency.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void create(MemberCreate memberCreate) {
        Member member = Member.signup(memberCreate);
        memberRepository.save(member);
    }

    public void update(MemberUpdate memberUpdate) {
        String salt = memberRepository.getSalt(memberUpdate.getId())
                .orElseThrow(() -> new RuntimeException(memberUpdate.getId() + " >>> Salt값 없음"));
        String password = EncryptionUtils.hashing(memberUpdate.getPassword(), salt);
        Member savedMember = memberRepository
                .findByIdAndPassword(memberUpdate.getId(), password)
                .orElseThrow(InvalidPasswordException::new);
        savedMember.edit(memberUpdate);
    }

    public Map<String, String> duplicateIdCheck(String id) {
        boolean result = memberRepository.duplicateIdCheck(id).isEmpty();
        return DuplicateIdCheckMessageFactory.getMessage(result);
    }

    @Transactional(readOnly = true)
    public MemberResponse get(String id) {
        Member member = memberRepository
                .findById(id)
                .orElseThrow(MemberNotFoundException::new);
        return MemberResponse.from(member);
    }

    public void delete(String id) {
        memberRepository.deleteById(id);
    }

}
