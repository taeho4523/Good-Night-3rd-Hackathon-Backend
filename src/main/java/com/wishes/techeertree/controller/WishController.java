package com.wishes.techeertree.controller;

import com.wishes.techeertree.entity.Wish;
import com.wishes.techeertree.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wishes")
public class WishController {

    private final WishService wishService;

    @Autowired
    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping("/register")
    public ResponseEntity<Wish> registerWish(@RequestBody Wish wish) {
        try {
            Wish createdWish = wishService.registerWish(wish);
            return new ResponseEntity<>(createdWish, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wish> getWish(@PathVariable Long id) {
        return wishService.findWishById(id)
                .map(wish -> new ResponseEntity<>(wish, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/")
    public ResponseEntity<List<Wish>> getAllWishes() {
        return new ResponseEntity<>(wishService.findAllWishes(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWish(@PathVariable Long id) {
        try {
            wishService.deleteWish(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Wish>> getPendingWishes() {
        return new ResponseEntity<>(wishService.findPendingWishes(), HttpStatus.OK);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Wish> approveWish(@PathVariable Long id) {
        try {
            Wish updatedWish = wishService.approveWish(id);
            return new ResponseEntity<>(updatedWish, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<Wish> rejectWish(@PathVariable Long id) {
        try {
            Wish updatedWish = wishService.rejectWish(id);
            return new ResponseEntity<>(updatedWish, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/approved/{id}")
    public ResponseEntity<Wish> getApprovedWish(@PathVariable Long id) {
        Optional<Wish> wish = wishService.findApprovedWishById(id);
        if (wish.isPresent()) {
            return new ResponseEntity<>(wish.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
