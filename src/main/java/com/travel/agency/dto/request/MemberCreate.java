package com.travel.agency.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class MemberCreate {

    @NotBlank(message = "아이디를 입력해주세요")
    @Pattern(regexp = "^[a-z][a-z0-9]{4,11}$", message = "올바르지 않는 형식입니다") // 5~12자 내외
    private final String id;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#])[a-zA-Z0-9!@#]{8,12}", message = "올바르지 않는 형식입니다") // 최소 8~12자, 대문자 1개, 소문자 1개, 숫자 1개, 특수문자 1개 포함
    private final String password;

    @NotBlank(message = "이름을 입력해주세요")
    @Pattern(regexp = "^[가-힣]{1,30}$", message = "올바르지 않는 형식입니다")
    private final String name;

    @NotBlank(message = "주민번호를 입력해주세요")
    @Pattern(regexp = "^[0-9]{6}-[1-4][0-9]{6}$", message = "올바르지 않는 형식입니다")
    private final String ssn;

    @NotBlank(message = "전화번호를 입력해주세요")
    @Pattern(regexp = "^[0-9]{3}-[0-9]{4}-[0-9]{4}$", message = "올바르지 않는 형식입니다")
    private final String tel;

    @Email(message = "이메일 형식을 맞춰주세요")
    private final String email;

    @Pattern(regexp = "^[0-9]{0,5}$", message = "숫자만 입력할 수 있습니다")
    private final String postcode;

    private final String address;

    @Pattern(regexp = "^[a-zA-Z]*$", message = "영문만 입력할 수 있습니다")
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


