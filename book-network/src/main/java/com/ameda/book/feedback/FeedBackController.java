package com.ameda.book.feedback;/*
*
@author ameda
@project Books
*
*/

import com.ameda.book.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feedbacks")
@RequiredArgsConstructor
@Tag(name="Feedback")
public class FeedBackController {
    private final FeedBackService feedBackService;

    @PostMapping
    public ResponseEntity<Integer> saveFeedback(
            @Valid @RequestBody FeedBackRequest request,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(feedBackService.saveFeedback(request,connectedUser));
    }
    @GetMapping("/book/{bookId}")
    public ResponseEntity<PageResponse<FeedBackResponse>> findAllFeedBacksByBook(
            @PathVariable("bookId") Integer bookId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser){
        return ResponseEntity.ok(
                feedBackService.findAllFeedBacksByBook(bookId,page,size,connectedUser)
        );
    }
}
