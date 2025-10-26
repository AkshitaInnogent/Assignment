package com.innogent.Library.management.System.mappers;

import com.innogent.Library.management.System.dtos.ResponseBookDto;
import com.innogent.Library.management.System.dtos.ResponseMemberDto;
import com.innogent.Library.management.System.entities.Book;
import com.innogent.Library.management.System.entities.Member;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class MemberMapper {

    public ResponseMemberDto convertMemberToMemberDto(Member member) {
        if (member == null) {
            return null; // Return null for null input to handle edge cases
        }

        ResponseMemberDto dto = new ResponseMemberDto();
        dto.setMember_id(member.getMember_id());
        dto.setMember_name(member.getMember_name());
        dto.setMember_email(member.getMember_email());

        Set<ResponseBookDto> borrowedBooks = mapBorrowedBooks(member.getBorrowed_books());
        dto.setBorrowed_books(borrowedBooks);

        return dto;
    }

    private Set<ResponseBookDto> mapBorrowedBooks(Set<Book> books) {
        Set<ResponseBookDto> borrowedBooks = new HashSet<>();
        if (books != null) {
            for (Book book : books) {
                ResponseBookDto bookDto = new ResponseBookDto();
                bookDto.setBook_id(book.getBook_id());
                bookDto.setBook_name(book.getBook_name());
                bookDto.setBook_stock(book.getBook_stock());
                borrowedBooks.add(bookDto);
            }
        }
        return borrowedBooks;
    }
}