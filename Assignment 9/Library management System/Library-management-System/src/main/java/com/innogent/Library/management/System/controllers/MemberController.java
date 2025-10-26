package com.innogent.Library.management.System.controllers;

import com.innogent.Library.management.System.dtos.RequestMemberDto;
import com.innogent.Library.management.System.dtos.ResponseMemberDto;
import com.innogent.Library.management.System.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseMemberDto createMember(@RequestBody RequestMemberDto requestMemberDto) {
        return memberService.createMember(requestMemberDto);
    }

    @GetMapping
    public List<ResponseMemberDto> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    public ResponseMemberDto getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }

    @PostMapping("/{memberId}/borrow/{bookId}")
    public ResponseMemberDto borrowBook(@PathVariable Long memberId, @PathVariable Long bookId) {
        return memberService.borrowBook(memberId, bookId);
    }

    @PostMapping("/{memberId}/return/{bookId}")
    public void returnBook(@PathVariable Long memberId, @PathVariable Long bookId) {
        memberService.returnBook(memberId, bookId);
    }
}