package com.wishes.techeertree.service;

import com.wishes.techeertree.entity.Comment;
import com.wishes.techeertree.entity.Wish;
import com.wishes.techeertree.repository.CommentRepository;
import com.wishes.techeertree.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
