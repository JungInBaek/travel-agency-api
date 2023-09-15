package com.travel.agency.domain;

import com.travel.agency.dto.request.MemberCreate;
import com.travel.agency.dto.request.MemberUpdate;
import com.travel.agency.service.EncryptionUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
        String email = memberUpdate.getEmail();
        String postcode = memberUpdate.getPostcode();
        String address = memberUpdate.getAddress();
        String englishName = memberUpdate.getEnglishName();
        this.email = (email != null) ? email : "";
        this.postcode = (postcode != null) ? postcode : "";
        this.address = (address != null) ? address : "";
        this.englishName = (englishName != null) ? englishName : "";
    }

    public static MemberBuilder builder() {
        return new MemberBuilder();
    }

    public static class MemberBuilder {
        private String id;
        private String password;
        private String name;
        private String ssn;
        private String tel;
        private String email;
        private String postcode;
        private String address;
        private String englishName;
        private String salt;

        MemberBuilder() {
        }

        public MemberBuilder id(final String id) {
            this.id = id;
            return this;
        }

        public MemberBuilder password(final String password) {
            this.password = password;
            return this;
        }

        public MemberBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public MemberBuilder ssn(final String ssn) {
            this.ssn = ssn;
            return this;
        }

        public MemberBuilder tel(final String tel) {
            this.tel = tel;
            return this;
        }

        public MemberBuilder email(final String email) {
            this.email = (email != null) ? email : "";
            return this;
        }

        public MemberBuilder postcode(final String postcode) {
            this.postcode = (postcode != null) ? postcode : "";
            return this;
        }

        public MemberBuilder address(final String address) {
            this.address = (address != null) ? address : "";
            return this;
        }

        public MemberBuilder englishName(final String englishName) {
            this.englishName = (englishName != null) ? englishName : "";
            return this;
        }

        public MemberBuilder salt(final String salt) {
            this.salt = salt;
            return this;
        }

        public Member build() {
            return new Member(this.id, this.password, this.name, this.ssn, this.tel, this.email, this.postcode, this.address, this.englishName, this.salt);
        }

        public String toString() {
            return "Member.MemberBuilder(id=" + this.id + ", password=" + this.password + ", name=" + this.name + ", ssn=" + this.ssn + ", tel=" + this.tel + ", email=" + this.email + ", postcode=" + this.postcode + ", address=" + this.address + ", englishName=" + this.englishName + ", salt=" + this.salt + ")";
        }
    }

}