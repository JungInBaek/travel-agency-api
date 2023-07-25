package com.travel.agency.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberUpdate {

    @NotBlank(message = "아이디를 입력해주세요")
    private final String id;

    private final String password;

    @Email(message = "이메일 형식을 맞춰주세요.")
    private final String email;

    private final String postcode;

    private final String address;

    @Pattern(regexp = "^[a-zA-Z]*$", message = "영문만 입력할 수 있습니다.")
    private final String englishName;

    @Builder
    public MemberUpdate(String id, String password, String email, String postcode, String address,
            String englishName) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.postcode = postcode;
        this.address = address;
        this.englishName = englishName;
    }

}
