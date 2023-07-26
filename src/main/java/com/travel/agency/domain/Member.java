package com.travel.agency.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

//    @Column(columnDefinition = "BINARY(16)")
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
//    private UUID uuid;

    @Id
//    @Column(unique = true)
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
    private Member(String id, String password, String name, String ssn, String tel, String email,
            String postcode, String address, String englishName) {
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

    public MemberEditor.MemberEditorBuilder toMemberEditor() {
        return MemberEditor.builder()
                .email(this.email)
                .postcode(this.postcode)
                .address(this.address)
                .englishName(this.englishName);
    }

    public void edit(MemberEditor memberEditor) {
        this.email = memberEditor.getEmail();
        this.postcode = memberEditor.getPostcode();
        this.address = memberEditor.getAddress();
        this.englishName = memberEditor.getEnglishName();
    }

}