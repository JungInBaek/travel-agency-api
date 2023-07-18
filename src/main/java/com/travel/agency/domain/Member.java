package com.travel.agency.domain;

import com.travel.agency.dto.request.MemberCreate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    private String id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ssn;

    @Column(nullable = false)
    private String tel;

    private String email;

    private String postcode;

    private String address;

    private String englishName;

    @Builder
    public Member(String id, String password, String name, String ssn, String tel, String email, String postcode, String address, String englishName) {
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

    public static Member from(MemberCreate memberCreate) {
        return Member.builder()
                .id(memberCreate.getId())
                .password(memberCreate.getPassword())
                .name(memberCreate.getName())
                .ssn(memberCreate.getSsn())
                .tel(memberCreate.getTel())
                .email(memberCreate.getEmail())
                .postcode(memberCreate.getPostcode())
                .address(memberCreate.getAddress())
                .englishName(memberCreate.getEnglishName())
                .build();
    }

}