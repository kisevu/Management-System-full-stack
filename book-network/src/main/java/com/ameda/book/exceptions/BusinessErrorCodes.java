package com.ameda.book.exceptions;/*
*
@author ameda
@project Books
*
*/

import lombok.Getter;


import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum BusinessErrorCodes {

    NO_CODE(0,"No code",NOT_IMPLEMENTED),
    ACCOUNT_LOCKED(302,"user account locked",FORBIDDEN),
    INCORRECT_CURRENT_PASSWORD(300,"current password incorrect",BAD_REQUEST),
    NEW_PASSWORD_DOES_NOT_MATCH(301,"New password does not match",BAD_REQUEST),
    ACCOUNT_DISABLED(303,"User account disabled",FORBIDDEN),
    BAD_CREDENTIALS(304,"Either password or email is incorrect",FORBIDDEN);

    ;
    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatusCode;
    BusinessErrorCodes(int code, String description,HttpStatus httpStatusCode){
        this.code = code;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }
}
