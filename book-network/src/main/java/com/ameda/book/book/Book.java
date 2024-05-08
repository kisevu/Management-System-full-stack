package com.ameda.book.book;/*
*
@author ameda
@project Books
*
*/

import com.ameda.book.common.BaseEntity;
import com.ameda.book.feedback.FeedBack;
import com.ameda.book.history.BookTransactionHistory;
import com.ameda.book.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Builder
public class Book extends BaseEntity{
    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String bookCover;
    private boolean archived;
    private boolean shareable;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @OneToMany(mappedBy = "book")
    private List<FeedBack> feedbacks;
    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> histories;



}
