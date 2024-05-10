package com.ameda.book.feedback;/*
*
@author ameda
@project Books
*
*/

import com.ameda.book.book.Book;
import com.ameda.book.book.BookRepository;
import com.ameda.book.common.PageResponse;
import com.ameda.book.exceptions.OperationNotPermittedException;
import com.ameda.book.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedBackService {

    private final BookRepository bookRepository;
    private final FeedBackMapper feedBackMapper;
    private final FeedBackRepository feedBackRepository;


    public Integer saveFeedback(FeedBackRequest request, Authentication connectedUser) {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(()->new EntityNotFoundException("Book was not found with id: "+request.bookId()));

        if(book.isArchived() || !book.isShareable()){
            //if the book is archived or non-shareable then you can't borrow
            throw new OperationNotPermittedException("Cannot give feedback to archived and non-shareable book.");
        }
        // you can borrow
        var user = ((User) connectedUser.getPrincipal());
        if(Objects.equals(book.getOwner().getId(),user.getId())){
            //owner is same connected user...
            throw new OperationNotPermittedException("You cannot give feedback for your own book.");
        }
        FeedBack feedBack = feedBackMapper.toFeedback(request);
        return feedBackRepository.save(feedBack).getId();
    }

    public PageResponse<FeedBackResponse> findAllFeedBacksByBook(Integer bookId, int page, int size,Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page,size);
        var user = ((User) connectedUser.getPrincipal());
        Page<FeedBack> feedBacks = feedBackRepository.findAllByBookId(bookId,pageable);
        List<FeedBackResponse> feedbackResponses = feedBacks.stream()
                .map(f->feedBackMapper.toFeedBackResponse(f,user.getId()))
                .toList();
        return new PageResponse<>(
                feedbackResponses,
                feedBacks.getNumber(),
                feedBacks.getSize(),
                feedBacks.getTotalElements(),
                feedBacks.getTotalPages(),
                feedBacks.isFirst(),
                feedBacks.isLast()
        );
    }
}
