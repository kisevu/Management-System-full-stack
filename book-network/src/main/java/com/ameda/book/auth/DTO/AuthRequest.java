package com.ameda.book.auth.DTO;/*
*
@author ameda
@project Books
*
*/

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    @NotEmpty(message = "Provide email in the correct format i.e kev@gmail")
    @NotBlank(message = "Email should be provided")
    private String email;
    @NotEmpty(message = "Provide password is a mandatory field")
    @NotBlank(message = "Password is marked as a key to access your account")
    @Size(min = 8,message = "Passwords should be 8 characters long and more")
    private String password;
}
