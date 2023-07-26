package com.travel.agency.controller;

import com.travel.agency.dto.request.MemberCreate;
import com.travel.agency.dto.request.MemberUpdate;
import com.travel.agency.dto.response.MemberResponse;
import com.travel.agency.service.MemberService;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public void post(@RequestBody @Valid MemberCreate memberCreate) {
        memberService.create(memberCreate);
    }

    @PatchMapping("/members")
    public void update(@RequestBody @Valid MemberUpdate memberUpdate) {
        memberService.update(memberUpdate);
    }

    @GetMapping("/members/{id}/check")
    public Map<String, String> idCheck(@PathVariable @NotBlank(message = "아이디를 입력해주세요") String id) {
        return memberService.duplicateIdCheck(id);
    }

    @GetMapping("/members/{id}")
    public MemberResponse get(@PathVariable @NotBlank(message = "아이디를 입력해주세요") String id) {
        return memberService.get(id);
    }

    @DeleteMapping("/members/{id}")
    public void delete(@PathVariable @NotBlank(message = "아이디를 입력해주세요") String id) {
        memberService.delete(id);
    }

}
