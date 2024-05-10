package com.ameda.book.book;/*
*
@author ameda
@project Books
*
*/

import com.ameda.book.book.DTO.BookRequest;
import com.ameda.book.file.FileUtils;
import com.ameda.book.history.BookTransactionHistory;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {

    public Book toBook(BookRequest request) {
        return Book.builder()
                .id(request.id())
                .title(request.title())
                .isbn(request.isbn())
                .authorName(request.authorName())
                .synopsis(request.synopsis())
                .archived(false)
                .shareable(request.shareable())
                .build();
    }

    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .isbn(book.getIsbn())
                .synopsis(book.getSynopsis())
                .rate(book.getRate())
                .archived(book.isArchived())
                .shareable(book.isShareable())
                .owner(book.getOwner().fullName())
                .cover(FileUtils.readFileFromLocation(book.getBookCover()))
                .build();
    }

    public BorrowedBookResponse toBorrowedResponse(BookTransactionHistory transactionHistory){
        return BorrowedBookResponse.builder()
                .id(transactionHistory.getBook().getId())
                .title(transactionHistory.getBook().getTitle())
                .authorName(transactionHistory.getBook().getAuthorName())
                .isbn(transactionHistory.getBook().getIsbn())
                .rate(transactionHistory.getBook().getRate())
                .returned(transactionHistory.isReturned())
                .returnApproved(transactionHistory.isReturnApproved())
                .build();
    }
}
