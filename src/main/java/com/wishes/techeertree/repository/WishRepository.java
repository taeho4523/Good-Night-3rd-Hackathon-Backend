package com.wishes.techeertree.repository;

import com.wishes.techeertree.entity.Wish;
import com.wishes.techeertree.entity.WishStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {

    @Query("SELECT w FROM Wish w WHERE w.deletedAt IS NULL")
    List<Wish> findAllActive();

    @Query("SELECT w FROM Wish w WHERE w.id = :id AND w.deletedAt IS NULL")
    Optional<Wish> findActiveById(Long id);

    List<Wish> findByIsConfirm(WishStatus status);  // 이 부분을 추가합니다.
}
