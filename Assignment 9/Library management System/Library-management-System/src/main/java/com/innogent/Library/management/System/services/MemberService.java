package com.innogent.Library.management.System.services;

import com.innogent.Library.management.System.dtos.RequestMemberDto;
import com.innogent.Library.management.System.dtos.ResponseMemberDto;
import com.innogent.Library.management.System.entities.Book;
import com.innogent.Library.management.System.entities.Member;
import com.innogent.Library.management.System.mappers.MemberMapper;
import com.innogent.Library.management.System.repositories.BookRepository;
import com.innogent.Library.management.System.repositories.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final MemberMapper memberMapper;

    public ResponseMemberDto createMember(RequestMemberDto requestMemberDto) {
        Member member = new Member();
        member.setMember_name(requestMemberDto.getMember_name());
        member.setMember_email(requestMemberDto.getMember_email());
        Member savedMember = memberRepository.save(member);
        return memberMapper.convertMemberToMemberDto(savedMember);
    }

    public List<ResponseMemberDto> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        List<ResponseMemberDto> memberDtos = new ArrayList<>();
        for (Member member : members) {
            memberDtos.add(memberMapper.convertMemberToMemberDto(member));
        }
        return memberDtos;
    }

    public ResponseMemberDto getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member with ID " + id + " not found"));
        return memberMapper.convertMemberToMemberDto(member);
    }

    @Transactional
    public ResponseMemberDto borrowBook(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member with ID " + memberId + " not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + bookId + " not found"));

        if (book.getBook_stock() < 1) {
            throw new IllegalStateException("Book stock is insufficient for borrowing");
        }

        book.setBook_stock(book.getBook_stock() - 1);

        member.getBorrowed_books().add(book);
        book.getMembers().add(member);

        memberRepository.save(member);
        bookRepository.save(book);

        return memberMapper.convertMemberToMemberDto(member);
    }

    public void returnBook(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member with ID " + memberId + " not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + bookId + " not found"));

        if (member.getBorrowed_books().contains(book)) {
            member.getBorrowed_books().remove(book);
            book.getMembers().remove(member);
            book.setBook_stock(book.getBook_stock() + 1); 

            memberRepository.save(member);
            bookRepository.save(book);
        }
    }
}