package com.movies.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import com.movies.models.Movie;
import com.movies.models.MovieInformation;

import reactor.core.publisher.Mono;

@org.springframework.stereotype.Controller
public class Controller {
	private final String api_url = "https://api.themoviedb.org/3/discover/movie";
	private final String IMGPATH = "https://image.tmdb.org/t/p/w500";
	private final String API_SEARCH = "https://api.themoviedb.org/3/search/movie";
	private final WebClient webclient;
	private final WebClient searchclient;
	
	@Value("${api.key}")
	private String apiKey;

	public Controller(WebClient.Builder webClientBuilder) {
		this.webclient = webClientBuilder.baseUrl(api_url).build();
		this.searchclient = webClientBuilder.baseUrl(API_SEARCH).build();
	}
	
	@GetMapping("/search")
	public String singleMovie(Model model,@RequestParam String query) {
		Mono<Movie>searchMovieMono=this.searchclient.get().
				uri(uriBuilder->
				uriBuilder.queryParam("api_key", apiKey)
				.queryParam("query", query)
									.build())
				.retrieve().bodyToMono(Movie.class);
				Movie movie = searchMovieMono.block();
		
		
		//fetch movie by title
		List<MovieInformation>SearchMovie= movie.getResults().stream().map(movieResult-> {
			
			String posterpath = IMGPATH + movieResult.getPoster_path();
			movieResult.setPoster_path(posterpath);
			return movieResult;
		}).collect(Collectors.toList());
		model.addAttribute("searchResult",SearchMovie );
		return "search";
	}
	@GetMapping("/movie")
	public String fetchMovies(Model model) {
		Mono<Movie> movieMono = this.webclient.get().uri(uriBuilder->
		uriBuilder.queryParam("api_key", apiKey)
				.build())
				.retrieve().bodyToMono(Movie.class);
		Movie movie = movieMono.block();
		//fetch all movies
		List<MovieInformation> movies = movie.getResults().stream().map(movieResult -> {
			String posterpath = IMGPATH + movieResult.getPoster_path();
			movieResult.setPoster_path(posterpath);
			return movieResult;
		}).collect(Collectors.toList());
		model.addAttribute("movieResult", movies);
		return "movies";
	}


}
