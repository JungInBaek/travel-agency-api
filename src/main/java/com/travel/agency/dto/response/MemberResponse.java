package com.travel.agency.dto.response;

import com.travel.agency.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {

    private final String id;

    private final String name;

    private final String ssn;

    private final String tel;

    private final String email;

    private final String postcode;

    private final String address;

    private final String englishName;

    @Builder
    public MemberResponse(String id, String name, String ssn, String tel,
            String email, String postcode, String address, String englishName) {
        this.id = id;
        this.name = name;
        this.ssn = ssn;
        this.tel = tel;
        this.email = email;
        this.postcode = postcode;
        this.address = address;
        this.englishName = englishName;
    }

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .ssn(member.getSsn())
                .tel(member.getTel())
                .email(member.getEmail())
                .postcode(member.getPostcode())
                .address(member.getAddress())
                .englishName(member.getEnglishName())
                .build();
    }

}
