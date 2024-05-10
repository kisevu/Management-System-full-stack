package com.ameda.book.book;/*
*
@author ameda
@project Books
*
*/

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {
    private Integer id;
    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String owner; // full name of user
    private byte [] cover;
    private double rate;
    private boolean archived;
    private boolean shareable;
}
