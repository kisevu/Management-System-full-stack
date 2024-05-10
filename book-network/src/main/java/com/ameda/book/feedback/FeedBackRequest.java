package com.ameda.book.feedback;/*
*
@author ameda
@project Books
*
*/

import jakarta.validation.constraints.*;

public record FeedBackRequest(
        @Positive(message = "200")
        @Min(value = 0,message = "201")
        @Min(value = 5,message = "202")
        String note,
        @NotNull(message = "203")
        @NotEmpty(message = "203")
        @NotBlank(message = "203")
        String comment,
        @NotNull(message = "204")
        Integer bookId
) {
}
