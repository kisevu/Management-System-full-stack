package com.ameda.book.auth.DTO;/*
*
@author ameda
@project Books
*
*/

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
public class AuthRequest {
    @Email(message = "Email is not well formatted")
    @NotEmpty(message = "Email is mandatory")
    @NotNull(message = "Email is mandatory")
    private String email;

    @NotEmpty(message = "Password is mandatory")
    @NotNull(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String password;
}
