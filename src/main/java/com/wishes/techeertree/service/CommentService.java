package com.wishes.techeertree.service;

import com.wishes.techeertree.entity.Comment;
import com.wishes.techeertree.entity.Wish;
import com.wishes.techeertree.repository.CommentRepository;
import com.wishes.techeertree.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final WishRepository wishRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, WishRepository wishRepository) {
        this.commentRepository = commentRepository;
        this.wishRepository = wishRepository;
    }

    public Comment addComment(Long wishId, String content) {
        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 소원을 찾을 수 없습니다."));

        Comment comment = new Comment();
        comment.setWish(wish);
        comment.setContent(content);

        return commentRepository.save(comment);
    }

    public Page<Comment> getCommentsByWish(Long wishId, int page, int size) {
        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 소원을 찾을 수 없습니다."));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        return commentRepository.findByWishAndDeletedAtIsNull(wish, pageable);
    }
}