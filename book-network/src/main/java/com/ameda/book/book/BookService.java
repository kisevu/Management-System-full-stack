package com.ameda.book.book;/*
*
@author ameda
@project Books
*
*/

import com.ameda.book.book.DTO.BookRequest;
import com.ameda.book.common.PageResponse;
import com.ameda.book.exceptions.OperationNotPermittedException;
import com.ameda.book.file.FileStorageService;
import com.ameda.book.history.BookTransactionHistory;
import com.ameda.book.history.BookTransactionHistoryRepository;
import com.ameda.book.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor

public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final BookTransactionHistoryRepository transactionHistoryRepository;
    private final FileStorageService fileStorageService;
    public Integer save(BookRequest request, Authentication connectedUser) {
//        var user = ((User) connectedUser.getPrincipal());
        //principal is the connected user also we implemented the Principal interface for User Entity
        Book book = bookMapper.toBook(request);
        return  bookRepository.save(book).getId();
    }

    public BookResponse findById(Integer bookId) {
        return  bookRepository.findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow(()->new EntityNotFoundException("No book found with id: "+bookId));
    }


    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser) {
//        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable, connectedUser.getName());
        List<BookResponse> booksResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                booksResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }
    public PageResponse<BookResponse> findBooks(int page, int size, Authentication connectedUser){
//       User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAll(pageable);
        List<BookResponse> bookResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponse
        );
    }


    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {
//        var user = ((User) connectedUser.getPrincipal());
        Pageable  pageable = PageRequest.of(page,size, Sort.by("createdDate")
                .descending());
        Page<Book> books = bookRepository.findAll(BookSpec.withOwnerId(connectedUser.getName()),pageable);

        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {
//        var user = ((User) connectedUser.getPrincipal());
        Pageable  pageable = PageRequest.of(page,size, Sort.by("createdDate")
                .descending());
        Page<BookTransactionHistory> borrowedBooks = transactionHistoryRepository.findAllBorrowedBooks(pageable, connectedUser.getName());
        List<BorrowedBookResponse> borrowedResponse = borrowedBooks.stream()
                .map(bookMapper::toBorrowedResponse)
                .toList();
        return new PageResponse<>(
                borrowedResponse,
                borrowedBooks.getNumber(),
                borrowedBooks.getSize(),
                borrowedBooks.getTotalElements(),
                borrowedBooks.getTotalPages(),
                borrowedBooks.isFirst(),
                borrowedBooks.isLast()
        );
    }

    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser) {
//        var user = ((User) connectedUser.getPrincipal());
        Pageable  pageable = PageRequest.of(page,size, Sort.by("createdDate")
                .descending());
        Page<BookTransactionHistory> borrowedBooks = transactionHistoryRepository.findAllReturnedBooks(pageable, connectedUser.getName());
        List<BorrowedBookResponse> borrowedResponse = borrowedBooks.stream()
                .map(bookMapper::toBorrowedResponse)
                .toList();
        return new PageResponse<>(
                borrowedResponse,
                borrowedBooks.getNumber(),
                borrowedBooks.getSize(),
                borrowedBooks.getTotalElements(),
                borrowedBooks.getTotalPages(),
                borrowedBooks.isFirst(),
                borrowedBooks.isLast()
        );
    }

    public Integer updateShareableStats(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new EntityNotFoundException("no book found with passed id: "+bookId));
//        var user = ((User) connectedUser.getPrincipal());
        //only owner can update the book
        if(!Objects.equals(book.getCreatedBy(),connectedUser.getName())){
            throw new OperationNotPermittedException("Sorry you are not the owner of the book, man!!!");
        }
        book.setShareable(!book.isShareable());
        bookRepository.save(book);
        return bookId;
    }

    public Integer updateArchivedStats(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new EntityNotFoundException("no book found with passed id: "+bookId));
//        var user = ((User) connectedUser.getPrincipal());
        //only owner can update the book
        if(!Objects.equals(book.getCreatedBy(),connectedUser.getName())){
            throw new OperationNotPermittedException("Sorry you are not the owner of the book, man!!!");
        }
        book.setArchived(!book.isArchived());
        bookRepository.save(book);
        return bookId;
    }

    public Integer borrowBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new EntityNotFoundException("Book was not found with id: "+bookId));
        if(book.isArchived() || !book.isShareable()){
            //if the book is archived or non-shareable then you can't borrow
            throw new OperationNotPermittedException("requested book cannot be borrowed is archived or not shareable");
        }
        // you can borrow
//        var user = ((User) connectedUser.getPrincipal());
        if(Objects.equals(book.getCreatedBy(),connectedUser.getName())){
            //owner is same connected user...
            throw new OperationNotPermittedException("You cannot borrow from yourself");
        }
        final boolean isBorrowed = transactionHistoryRepository.isAlreadyBorrowedByUser(bookId,connectedUser.getName());
        if(isBorrowed){
            throw new OperationNotPermittedException("Requested book is borrowed already");
        }
        // the book is available

        BookTransactionHistory bookTransactionHistory = BookTransactionHistory.builder()
                .userId(connectedUser.getName())
                .book(book)
                .returned(false)
                .returnApproved(false)
                .build();
        return transactionHistoryRepository.save(bookTransactionHistory).getId();
    }

    public Integer returnBorrowedBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new EntityNotFoundException("Book was not found with id: "+bookId));

        if(book.isArchived() || !book.isShareable()){
            //if the book is archived or non-shareable then you can't borrow
            throw new OperationNotPermittedException("requested book cannot be borrowed is archived or not shareable");
        }
        // you can borrow
//        var user = ((User) connectedUser.getPrincipal());
        if(Objects.equals(book.getCreatedBy(),connectedUser.getName())){
            //owner is same connected user...
            throw new OperationNotPermittedException("You cannot borrow from or return book to yourself");
        }
        BookTransactionHistory bookTransactionHistory = transactionHistoryRepository.findByBookIdAndUserId(bookId, connectedUser.getName())
                .orElseThrow(()-> new OperationNotPermittedException("You did not borrow this book"));
        bookTransactionHistory.setReturned(true);
       return transactionHistoryRepository.save(bookTransactionHistory).getId();
    }

    public Integer approveReturnBorrowedBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new EntityNotFoundException("Book was not found with id: "+bookId));

        if(book.isArchived() || !book.isShareable()){
            //if the book is archived or non-shareable then you can't borrow
            throw new OperationNotPermittedException("requested book cannot be borrowed is archived or not shareable");
        }
        // you can borrow
//        var user = ((User) connectedUser.getPrincipal());
        if(!Objects.equals(book.getCreatedBy(),connectedUser.getName())){
            //owner is same connected user...
            throw new OperationNotPermittedException("You cannot return a book that you don't own");
        }
        BookTransactionHistory bookTransactionHistory = transactionHistoryRepository.findByBookIdAndOwnerId(bookId, connectedUser.getName())
                .orElseThrow(()-> new OperationNotPermittedException("Book is not returned yet, you cannot approve its return"));
        bookTransactionHistory.setReturnApproved(true);
        return  transactionHistoryRepository.save(bookTransactionHistory).getId();
    }

    public void uploadCoverPic(Integer bookId, MultipartFile file, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new EntityNotFoundException("Book was not found with id: "+bookId));
//        var user = ((User) connectedUser.getPrincipal());
        var bookCover = fileStorageService.saveFile(file,connectedUser.getName());
        book.setBookCover(bookCover);
        bookRepository.save(book);
    }
}

