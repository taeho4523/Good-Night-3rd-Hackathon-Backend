package com.wishes.techeertree.repository;

import com.wishes.techeertree.entity.Wish;
import com.wishes.techeertree.entity.WishStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import com.wishes.techeertree.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {

    @Query("SELECT w FROM Wish w WHERE w.deletedAt IS NULL")
    List<Wish> findAllActive();

    @Query("SELECT w FROM Wish w WHERE w.id = :id AND w.deletedAt IS NULL")
    Optional<Wish> findActiveById(Long id);

    @Query("SELECT w FROM Wish w WHERE w.deletedAt IS NULL AND w.isConfirm = 'APPROVED' AND w.id = :id")
    Optional<Wish> findApprovedById(Long id);

    List<Wish> findByIsConfirm(WishStatus status);

    Page<Wish> findByIsConfirmAndDeletedAtIsNull(WishStatus isConfirm, Pageable pageable);

    // 키워드와 카테고리를 기반으로 소원을 검색하는 메서드
    @Query("SELECT w FROM Wish w WHERE (w.title LIKE %:keyword% OR w.content LIKE %:keyword%) AND w.category = :category AND w.deletedAt IS NULL")
    Page<Wish> searchByKeywordAndCategory(@Param("keyword") String keyword, @Param("category") Category category, Pageable pageable);

    // 카테고리가 없는 경우 모든 카테고리에서 검색
    @Query("SELECT w FROM Wish w WHERE (w.title LIKE %:keyword% OR w.content LIKE %:keyword%) AND w.deletedAt IS NULL")
    Page<Wish> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
