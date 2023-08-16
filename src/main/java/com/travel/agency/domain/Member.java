package com.travel.agency.domain;

import com.travel.agency.dto.request.MemberCreate;
import com.travel.agency.dto.request.MemberUpdate;
import com.travel.agency.service.EncryptionUtils;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Entity
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

    @Column(nullable = false)
    private String salt;

    @Builder
    private Member(String id, String password, String name, String ssn, String tel, String email,
            String postcode, String address, String englishName, String salt) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.ssn = ssn;
        this.tel = tel;
        this.email = email;
        this.postcode = postcode;
        this.address = address;
        this.englishName = englishName;
        this.salt = salt;
    }

    public static Member signup(MemberCreate memberCreate) {
        String salt = EncryptionUtils.createSalt();
        String password = EncryptionUtils.hashing(memberCreate.getPassword(), salt);
        return Member.builder()
                .id(memberCreate.getId())
                .password(password)
                .name(memberCreate.getName())
                .ssn(memberCreate.getSsn())
                .tel(memberCreate.getTel())
                .email(memberCreate.getEmail())
                .postcode(memberCreate.getPostcode())
                .address(memberCreate.getAddress())
                .englishName(memberCreate.getEnglishName())
                .salt(salt)
                .build();
    }

    public void edit(MemberUpdate memberUpdate) {
        this.email = memberUpdate.getEmail();
        this.postcode = memberUpdate.getPostcode();
        this.address = memberUpdate.getAddress();
        this.englishName = memberUpdate.getEnglishName();
    }

}