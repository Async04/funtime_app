package com.example.funtime_app.services;

import com.example.funtime_app.dto.request.PostRateDTO;
import com.example.funtime_app.entity.Post;
import com.example.funtime_app.entity.Rate;
import com.example.funtime_app.entity.User;
import com.example.funtime_app.interfaces.RateServiceInterface;
import com.example.funtime_app.repository.PostRepository;
import com.example.funtime_app.repository.RateRepository;
import com.example.funtime_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RateService implements RateServiceInterface {

    private final RateRepository rateRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    @Override
    public ResponseEntity<?> ratePost(PostRateDTO rateDTO) {
       Optional<Rate> optionalRate = rateRepository.findByRatedByIdAndPostId(rateDTO.getRateUserId(), rateDTO.getPostId());
       if (optionalRate.isPresent()){

           Rate rate = optionalRate.get();
           rate.setMarkValue(rateDTO.getValue());
           rateRepository.save(rate);
           return ResponseEntity.ok("Rate updated!!!");
       }
       else {

           Optional<User> optionalUser  = userRepository.findById(rateDTO.getRateUserId());
           Optional<Post> optionalPost = postRepository.findById(rateDTO.getPostId());
           if (optionalUser.isPresent() && optionalPost.isPresent()){

               Post post = optionalPost.get();
               User user = optionalUser.get();
               Rate rate = Rate.builder()
                       .markValue(rateDTO.getValue())
                       .ratedBy(user)
                       .post(post)
                       .build();

               rateRepository.save(rate);
               return ResponseEntity.ok("Rate saved!!!");
           }
           else {
               return ResponseEntity.badRequest().body("User or Post not found");
           }
       }
    }
}
