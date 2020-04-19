package com.example.ratingservice.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.ratingservice.domain.Rating;
import com.example.ratingservice.persistence.RatingNotFoundException;
import com.example.ratingservice.persistence.RatingRepository;

import com.google.common.base.Preconditions;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
@Transactional(readOnly = true)
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

 
    @HystrixCommand(commandKey = "ratingsByBookIdFromDB", fallbackMethod = "findCachedRatingsByBookId")
    public List<Rating> findRatingsByBookId(Long bookId) {
        return ratingRepository.findRatingsByBookId(bookId);
    }

 
    @HystrixCommand(commandKey = "ratingsFromDB", fallbackMethod = "findAllCachedRatings")
    public List<Rating> findAllRatings() {
        return ratingRepository.findAll();
    }

 
    @HystrixCommand(commandKey = "ratingsByIdFromDB", fallbackMethod = "findCachedRatingById", ignoreExceptions = { RatingNotFoundException.class })
    public Rating findRatingById(Long ratingId) {
        return Optional.ofNullable(ratingRepository.getOne(ratingId))
            .orElseThrow(() -> new RatingNotFoundException("Rating not found. ID: " + ratingId));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Rating createRating(Rating rating) {
        Rating newRating = new Rating();
        newRating.setBookId(rating.getBookId());
        newRating.setStars(rating.getStars());
        return ratingRepository.save(newRating);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRating(Long ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Rating updateRating(Map<String, String> updates, Long ratingId) {
        final Rating rating = findRatingById(ratingId);
        updates.keySet()
            .forEach(key -> {
                switch (key) {
                case "stars":
                    rating.setStars(Integer.parseInt(updates.get(key)));
                    break;
                }
            });
        return ratingRepository.save(rating);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Rating updateRating(Rating rating, Long ratingId) {
        Preconditions.checkNotNull(rating);
        Preconditions.checkState(rating.getId() == ratingId);
        Preconditions.checkNotNull(ratingRepository.getOne(ratingId));
        return ratingRepository.save(rating);
    }

}
