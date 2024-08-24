package com.wishes.techeertree.controller;

import com.wishes.techeertree.entity.Wish;
import com.wishes.techeertree.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
