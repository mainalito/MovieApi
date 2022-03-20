package com.movies.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import com.movies.models.Movie;
import com.movies.models.MovieInformation;

import reactor.core.publisher.Mono;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/v1/movies")
public class RestController {
	private final String api_url = "https://api.themoviedb.org/3/discover/movie";
	private final String IMGPATH = "https://image.tmdb.org/t/p/w500";
	private final String API_SEARCH = "https://api.themoviedb.org/3/search/movie";
	private final WebClient webclient ;
	private final WebClient searchclient;
	
	@Value("${api.key}")
	private String apiKey;

	public RestController(WebClient.Builder webClientBuilder) {
		this.webclient = webClientBuilder.baseUrl(api_url).build();
		this.searchclient = webClientBuilder.baseUrl(API_SEARCH).build();
	}
	@CrossOrigin
	@GetMapping("/search")
	public ResponseEntity<List<MovieInformation>> singleMovie(@RequestParam String query) {
		Mono<Movie>searchMovieMono=this.searchclient.get().
				uri(uriBuilder->
				uriBuilder.queryParam("api_key", apiKey)
				.queryParam("query", query)
									.build())
				.retrieve().bodyToMono(Movie.class);
				Movie movie = searchMovieMono.block();
		
		
		//fetch movie by title
		List<MovieInformation>SearchMovie= movie != null ? movie.getResults().stream().peek(movieResult -> {

			String posterpath = IMGPATH + movieResult.getPoster_path();
			movieResult.setPoster_path(posterpath);
		}).collect(Collectors.toList()) : null;
		
		return ResponseEntity.status(HttpStatus.OK).body(SearchMovie);
	}
	@CrossOrigin
	@GetMapping("/")
	public ResponseEntity<List<MovieInformation>> fetchMovies() {
		Mono<Movie> movieMono = this.webclient.get().uri(uriBuilder->
		uriBuilder.queryParam("api_key", apiKey)
				.build())
				.retrieve().bodyToMono(Movie.class);
		Movie movie = movieMono.block();
		//fetch all movies
		assert movie != null;
		List<MovieInformation> movies = movie.getResults().stream().peek(movieResult -> {
			String posterpath = IMGPATH + movieResult.getPoster_path();
			movieResult.setPoster_path(posterpath);
		}).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(movies);
	}
	
}
