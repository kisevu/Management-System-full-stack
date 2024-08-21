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
public class AuthenticationResponse {
    private String token;
}
