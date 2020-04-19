package com.example.ratingservice.persistence;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ratingservice.domain.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long>{
    List<Rating> findRatingsByBookId(Long bookId);
}
