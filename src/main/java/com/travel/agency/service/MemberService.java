package com.travel.agency.service;

import com.travel.agency.domain.Member;
import com.travel.agency.domain.MemberEditor;
import com.travel.agency.dto.request.MemberCreate;
import com.travel.agency.dto.request.MemberUpdate;
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
        Member member = Member.builder()
                .id(memberCreate.getId())
                .password(EncryptionUtils.encryptionSHA256(memberCreate.getPassword()))
                .name(memberCreate.getName())
                .ssn(memberCreate.getSsn())
                .tel(memberCreate.getTel())
                .email(memberCreate.getEmail())
                .postcode(memberCreate.getPostcode())
                .address(memberCreate.getAddress())
                .englishName(memberCreate.getEnglishName())
                .build();
        memberRepository.save(member);
    }

    public void update(MemberUpdate memberUpdate) {
        Member savedMember = memberRepository
                .findById(memberUpdate.getId())
                .orElseThrow(MemberNotFoundException::new);
        MemberEditor.MemberEditorBuilder memberEditorBuilder = savedMember.toMemberEditor();
        MemberEditor memberEditor = memberEditorBuilder
                .password(EncryptionUtils.encryptionSHA256(memberUpdate.getPassword()))
                .email(memberUpdate.getEmail())
                .postcode(memberUpdate.getPostcode())
                .address(memberUpdate.getAddress())
                .englishName(memberUpdate.getEnglishName())
                .build();
        savedMember.edit(memberEditor);
        memberRepository.save(savedMember);
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
