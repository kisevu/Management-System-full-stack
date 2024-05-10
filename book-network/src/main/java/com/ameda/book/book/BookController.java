package com.ameda.book.book;/*
*
@author ameda
@project Books
*
*/

import com.ameda.book.book.DTO.BookRequest;
import com.ameda.book.common.PageResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name="Book")
public class BookController {
    private final BookService bookService;

    @PostMapping("/save")
    public ResponseEntity<Integer> saveBook(@RequestBody @Valid BookRequest request,
                                            Authentication connectedUser){
        return ResponseEntity.ok(bookService.save(request,connectedUser));
    }
    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> findBookById(@PathVariable("bookId") Integer bookId){
        return ResponseEntity.ok(bookService.findById(bookId));
    }
    @GetMapping("all")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            @RequestParam(name = "page",defaultValue = "0", required = false) int page,
            @RequestParam(name = "size",defaultValue = "10", required = false) int size,
            Authentication connectedUser){
        return ResponseEntity.ok(bookService.findAllBooks(page,size,connectedUser));
    }
    @GetMapping("/owner")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
            @RequestParam(name = "page",defaultValue = "0", required = false) int page,
            @RequestParam(name = "size",defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(bookService.findAllBooksByOwner(page,size,connectedUser));
    }
    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
            @RequestParam(name = "page",defaultValue = "0", required = false) int page,
            @RequestParam(name = "size",defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(bookService.findAllBorrowedBooks(page,size,connectedUser));
    }
    @GetMapping("/returned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
            @RequestParam(name = "page",defaultValue = "0", required = false) int page,
            @RequestParam(name = "size",defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(bookService.findAllReturnedBooks(page,size,connectedUser));
    }
    @PatchMapping("/shareable/{bookId}")
    public ResponseEntity<Integer> updateShareableStats(@PathVariable("bookId") Integer bookId,
                                                        Authentication connectedUser){
        return ResponseEntity.ok(bookService.updateShareableStats(bookId,connectedUser));
    }
    @PatchMapping("/archived/{bookId}")
    public ResponseEntity<Integer>  updateArchivedStats(@PathVariable("bookId") Integer bookId,
                                                        Authentication connectedUser){
        return ResponseEntity.ok(bookService.updateArchivedStats(bookId,connectedUser));
    }
    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<Integer> borrowBook(@PathVariable("bookId") Integer bookId,
                                              Authentication connectedUser){
        return ResponseEntity.ok(bookService.borrowBook(bookId,connectedUser));
    }
    @PatchMapping("/borrow/return/{bookId}")
    public ResponseEntity<Integer> returnBorrowedBook(@PathVariable("bookId") Integer bookId,
                                                  Authentication connectedUser){
        return ResponseEntity.ok(bookService.returnBorrowedBook(bookId,connectedUser));
    }

    @PatchMapping("/borrow/return/approve/{bookId}")
    public ResponseEntity<Integer> approveReturnBorrowedBook(@PathVariable("bookId") Integer bookId,
                                                      Authentication connectedUser){
        return ResponseEntity.ok(bookService.approveReturnBorrowedBook(bookId,connectedUser));
    }

    @PostMapping(value = "/cover/{bookId}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadCoverPic(@PathVariable("bookId") Integer bookId,
                                            @Parameter()
                                            @RequestPart("file") MultipartFile file,
                                            Authentication connectedUser
                                            ){
        bookService.uploadCoverPic(bookId,file,connectedUser);
        return ResponseEntity.accepted().build();
    }


}