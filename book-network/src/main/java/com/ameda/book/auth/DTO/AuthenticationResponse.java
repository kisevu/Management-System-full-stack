package com.ameda.book.auth.DTO;/*
*
@author ameda
@project Books
*
*/

import lombok.*;

@Getter
@Setter
@Builder
public class AuthResponse {
    private String token;
}
