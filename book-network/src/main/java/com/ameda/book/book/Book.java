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

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book extends BaseEntity{
    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String bookCover; //file path of upload
    private boolean archived;
    private boolean shareable;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @OneToMany(mappedBy = "book")
    private List<FeedBack> feedbacks;
    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> histories;

    @Transient
    public double getRate(){
        if(feedbacks == null || feedbacks.isEmpty()){
            return 0.0;
        }
        var rate = this.feedbacks.stream()
                .mapToDouble(FeedBack::getNote)
                .average()
                .orElse(0.0);

        //rounding off
        double roundedUpRate = Math.round(rate*10.0)/10.0;
        return roundedUpRate;
    }

}
