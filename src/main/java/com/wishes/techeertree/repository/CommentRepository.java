package com.wishes.techeertree.repository;

import com.wishes.techeertree.entity.Comment;
import com.wishes.techeertree.entity.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 소원에 대한 삭제되지 않은 댓글을 페이지네이션하여 조회
    Page<Comment> findByWishAndDeletedAtIsNull(Wish wish, Pageable pageable);
}
