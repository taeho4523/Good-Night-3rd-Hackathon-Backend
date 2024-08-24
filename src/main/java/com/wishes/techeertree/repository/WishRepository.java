package com.wishes.techeertree.repository;

import com.wishes.techeertree.entity.Wish;
import com.wishes.techeertree.entity.WishStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
}
