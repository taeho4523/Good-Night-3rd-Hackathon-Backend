package com.wishes.techeertree.service;

import com.wishes.techeertree.entity.Wish;
import com.wishes.techeertree.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;  // 이 부분을 추가합니다
import java.util.Optional;

@Service
public class WishService {

    private final WishRepository wishRepository;

    @Autowired
    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public Wish registerWish(Wish wish) {
        if (wish.getTitle() == null || wish.getContent() == null || wish.getCategory() == null) {
            throw new IllegalArgumentException("제목, 내용, 카테고리는 필수 입력 사항입니다.");
        }
        return wishRepository.save(wish);
    }

    public Optional<Wish> findWishById(Long id) {
        return wishRepository.findActiveById(id);
    }

    public List<Wish> findAllWishes() {
        return wishRepository.findAllActive();
    }

    public void deleteWish(Long id) {
        Optional<Wish> wishOptional = wishRepository.findActiveById(id);
        if (wishOptional.isPresent()) {
            Wish wish = wishOptional.get();
            wish.setDeletedAt(LocalDateTime.now());
            wishRepository.save(wish);
        } else {
            throw new IllegalArgumentException("해당 ID의 소원을 찾을 수 없습니다.");
        }
    }
}
