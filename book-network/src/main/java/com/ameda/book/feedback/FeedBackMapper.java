package com.ameda.book.feedback;/*
*
@author ameda
@project Books
*
*/

import com.ameda.book.book.Book;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Service
public class FeedBackMapper {
    public FeedBack toFeedback(FeedBackRequest request) {
        return FeedBack.builder()
                .note(Double.valueOf(request.note()))
                .comment(request.comment())
                .book(Book.builder()
                        .id(request.bookId())
                        .archived(false) //not mandatory
                        .shareable(false) //not mandatory
                        .build())
                .build();
    }

    public FeedBackResponse toFeedBackResponse(FeedBack feedBack, Integer id) {
        return  FeedBackResponse.builder()
                .note(String.valueOf(feedBack.getNote()))
                .comment(feedBack.getComment())
                .ownFeedBack(Objects.equals(feedBack.getCreatedBy(),id))
                .build();
    }
}
