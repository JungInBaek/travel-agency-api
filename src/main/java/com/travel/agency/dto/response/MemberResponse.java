package com.travel.agency.dto.response;

import com.travel.agency.domain.Member;
import com.travel.agency.dto.request.MemberCreate;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {

    private final String id;

    private final String password;

    private final String name;

    private final String ssn;

    private final String tel;

    private final String email;

    private final String postcode;

    private final String address;

    private final String englishName;

    @Builder
    public MemberResponse(String id, String password, String name, String ssn, String tel,
            String email, String postcode, String address, String englishName) {
        this.id = id;
        this.password = password;
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
                .password(member.getPassword())
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
