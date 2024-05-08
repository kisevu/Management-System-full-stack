package com.ameda.book.auth;/*
*
@author ameda
@project Books
*
*/

import com.ameda.book.auth.DTO.SignUpRequest;
import com.ameda.book.role.RoleRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("auth")
@Tag(name="Authentication")
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.ACCEPTED)

    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest request) throws MessagingException {
        authenticationService.signUp(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/add/role")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> createRole(@RequestBody RoleRequest request){
        authenticationService.createRole(request);
        return ResponseEntity.accepted().build();
    }
}
