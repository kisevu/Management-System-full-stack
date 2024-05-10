package com.ameda.book.feedback;/*
*
@author ameda
@project Books
*
*/

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedBackResponse {
    private String note;
    private String comment;
    private boolean ownFeedBack;
}
