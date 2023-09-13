package com.travel.agency.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class MemberUpdate {

    @NotBlank(message = "아이디를 입력해주세요")
    @Pattern(regexp = "^[a-z][a-z0-9]{4,11}$", message = "올바르지 않는 형식입니다") // 5~12자 내외
    private final String id;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "[([a-z].+)([A-z].+)([0-9].+)([!@#].+)]{8,12}", message = "올바르지 않는 형식입니다") // 최소 8~12자, 대문자 1개, 소문자 1개, 숫자 1개, 특수문자 1개 포함
    private final String password;

    @Email(message = "이메일 형식을 맞춰주세요")
    private final String email;

    @Pattern(regexp = "^[0-9]{0,5}$", message = "숫자만 입력할 수 있습니다")
    private final String postcode;

    private final String address;

    @Pattern(regexp = "^[a-zA-Z]*$", message = "영문만 입력할 수 있습니다")
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
