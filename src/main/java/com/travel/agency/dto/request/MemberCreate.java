package com.travel.agency.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCreate {

    @NotBlank(message = "아이디를 입력해주세요")
    private final String id;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private final String password;

    @NotBlank(message = "이름을 입력해주세요")
    private final String name;

    @NotBlank(message = "주민번호를 입력해주세요")
    private final String ssn;

    @NotBlank(message = "전화번호를 입력해주세요")
    private final String tel;

    private final String email;

    private final String postcode;

    private final String address;

    private final String englishName;

    @Builder
    public MemberCreate(String id, String password, String name, String ssn, String tel,
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

}


