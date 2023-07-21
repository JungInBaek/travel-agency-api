package com.travel.agency.controller;

import com.travel.agency.dto.request.MemberCreate;
import com.travel.agency.dto.request.MemberUpdate;
import com.travel.agency.dto.response.MemberResponse;
import com.travel.agency.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public void post(@RequestBody @Valid MemberCreate memberCreate) {
        memberService.create(memberCreate);
    }

    @PatchMapping("/members/{id}")
    public void update(@PathVariable String id, @RequestBody MemberUpdate memberUpdate) {
        memberService.update(memberUpdate);
    }

    @GetMapping("/members/{id}")
    public MemberResponse get(@PathVariable String id) {
        return memberService.get(id);
    }

    @DeleteMapping("/members/{id}")
    public void delete(@PathVariable String id) {
        memberService.delete(id);
    }

}
