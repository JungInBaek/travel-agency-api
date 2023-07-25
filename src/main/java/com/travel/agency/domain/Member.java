package com.travel.agency.domain;

import com.travel.agency.domain.MemberEditor.MemberEditorBuilder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member")
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
    public Member(String id, String password, String name, String ssn, String tel, String email,
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

    public MemberEditorBuilder toMemberEditor() {
        return MemberEditor.builder()
                .password(this.password)
                .email(this.email)
                .postcode(this.postcode)
                .address(this.address)
                .englishName(this.englishName);
    }

    public void edit(MemberEditor memberEditor) {
        this.password = memberEditor.getPassword();
        this.email = memberEditor.getEmail();
        this.postcode = memberEditor.getPostcode();
        this.address = memberEditor.getAddress();
        this.englishName = memberEditor.getEnglishName();
    }

}