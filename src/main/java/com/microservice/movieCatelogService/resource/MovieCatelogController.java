package com.microservice.movieCatelogService.resource;

import com.microservice.movieCatelogService.models.CatelogItem;
import com.microservice.movieCatelogService.models.Movie;
import com.microservice.movieCatelogService.models.Rating;
import com.microservice.movieCatelogService.models.UserRatings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catelog")
public class MovieCatelogController{
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WebClient.Builder webClientBuilder;


    @GetMapping("/getMessage")
    public String getMessage(){
        return "Hello world";
    }
    @RequestMapping("/{userId}")
    public List<CatelogItem> getCatelog(@PathVariable("userId") String userId){


       //RestTemplate restTemplate=new RestTemplate();
     // Movie movieObject=  restTemplate.getForObject("http://localhost:8082/movies/123", Movie.class);
    UserRatings ratingList= restTemplate.getForObject("http://localhost:8083/ratingData/users/"+userId,UserRatings.class);


        return ratingList.getUserRating().stream().map(rating->{
            Movie movie= restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class);
            return new CatelogItem(movie.getMovieName(), "super", rating.getRating());

             }).collect(Collectors.toList());

//        return Collections.singletonList(
//                new CatelogItem("titanic","super",4)
//        );

        /* Movie movie= webClientBuilder
        .build()
        .get()
            .uri("http://localhost:8082/movies/"+rating.getMovieId())
            .retrieve()
            .bodyToMono(Movie.class)//asynchronus response is mono<T> ,non blocking
            .block();// convert mon<T> to java object T. synchornous, blocking,watiting the response until complete the execution
                    return new CatelogItem(movie.getMovieName(), "super", rating.getRating());*/

    }
}
