package com.movies.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import com.movies.models.Movie;
import com.movies.models.MovieDetails;
import com.movies.models.MovieInformation;
import com.movies.models.OneMovie;
import com.movies.models.Trailer;
import com.movies.models.collection;

import reactor.core.publisher.Mono;

@org.springframework.stereotype.Controller
public class Controller {
	private final String api_url = "https://api.themoviedb.org/3/discover/movie";
	private final String IMGPATH = "https://image.tmdb.org/t/p/w500";
	private final String BACKDROPPATH = "https://image.tmdb.org/t/p/original";
	private final String API_SEARCH = "https://api.themoviedb.org/3/search/movie";
	private final String SINGLE_MOVIE = "https://api.themoviedb.org/3/movie";
	private final String YOUTUBE_API = "https://api.themoviedb.org/3/movie";
	private final String YOUTUBE = "https://www.youtube.com/embed/";
	private final WebClient webclient;
	private final WebClient searchclient;
	private final WebClient movieClient;
	private final WebClient youtube;

	@Value("${api.key}")
	private String apiKey;

	public Controller(WebClient.Builder webClientBuilder) {
		this.webclient = webClientBuilder.baseUrl(api_url).build();
		this.searchclient = webClientBuilder.baseUrl(API_SEARCH).build();
		this.movieClient = webClientBuilder.baseUrl(SINGLE_MOVIE).build();
		this.youtube = webClientBuilder.baseUrl(YOUTUBE_API).build();
	}

	@GetMapping("/movie/{id}")
	public String movie(Model model, @PathVariable("id") String id) {
		OneMovie onemovie = new OneMovie();
		// id = "634649";
		onemovie.setId(id);
		String unique = onemovie.getId();
		Mono<OneMovie> movieMono = this.movieClient.get()
				.uri(uriBuilder -> uriBuilder.path("/" + unique).queryParam("api_key", apiKey).build()).retrieve()
				.bodyToMono(OneMovie.class);
		onemovie = movieMono.block();

		// setting backdrop path
		String backdrop_path = BACKDROPPATH + onemovie.getBackdrop_path();
		assert onemovie != null;
		onemovie.setBackdrop_path(backdrop_path);
		System.out.println("backdrop path " + backdrop_path);
		// fetch poster path
		// collection cl = new collection();
		String poster_path = IMGPATH + onemovie.getPoster_path();
		onemovie.setPoster_path(poster_path);

		model.addAttribute("movie", onemovie);
		return "singleMovie";
	}

	@GetMapping("/")
	public String fetchMovies(Model model, HttpSession session) {
		Mono<Movie> movieMono = this.webclient.get().uri(uriBuilder -> uriBuilder.queryParam("api_key", apiKey).build())
				.retrieve().bodyToMono(Movie.class);
		Movie movie = movieMono.block();
		// fetch all movies
		List<MovieInformation> movies;
		movies = movie != null ? movie.getResults().stream().peek(movieResult -> {
			String posterpath = IMGPATH + movieResult.getPoster_path();
			movieResult.setPoster_path(posterpath);
			session.setAttribute("overview", movieResult.getOverview());
		}).collect(Collectors.toList()) : null;

		model.addAttribute("movieResult", movies);
		return "movies";
	}

	@GetMapping("/search")
	public String searchMovie(Model model, @RequestParam String query) {
		Mono<Movie> searchMovieMono = this.searchclient.get()
				.uri(uriBuilder -> uriBuilder.queryParam("api_key", apiKey).queryParam("query", query).build())
				.retrieve().bodyToMono(Movie.class);
		Movie movie = searchMovieMono.block();

		// fetch movie by title
		List<MovieInformation> SearchMovie = movie != null ? movie.getResults().stream().peek(movieResult -> {

			String posterpath = IMGPATH + movieResult.getPoster_path();
			movieResult.setPoster_path(posterpath);
		}).collect(Collectors.toList()) : null;
		model.addAttribute("searchResult", SearchMovie);
		return "search";
	}

	@GetMapping("/movie/trailer/{id}")
	public String movieYoutube(Model model, @PathVariable("id") String id) {
	
		Trailer trailer = new Trailer();
		// id = "634649";
		trailer.setId(id);
		String uniqueId = trailer.getId();
		Mono<Trailer> TrailerMono = this.youtube.get().uri(uriBuilder -> uriBuilder.path("/" + uniqueId + "/videos")
		.queryParam("api_key", apiKey).queryParam("language", "en-US").build()).retrieve()
		.bodyToMono(Trailer.class);
		trailer = TrailerMono.block();
		
		// fetch all movies trailers
		List<MovieDetails> trailers = trailer.getResults().stream().peek(movieResult -> {
			if (movieResult.getName().matches("Official Trailer") || movieResult.getName().matches("Official Teaser")) {
				String url = YOUTUBE + movieResult.getKey();
				movieResult.setUrl(url);
			}
		}).collect(Collectors.toList());
		// trailers.stream().map(x::getUrl).collect(Collectors.toList());
		String err= "NO OFFICIAL TRAILER FOUND FOR MOVIE ID "+id;
	
		model.addAttribute("trailer",trailer);
		List<MovieDetails> trailerUrl = trailers.stream().filter(x -> x.getUrl() != null).collect(Collectors.toList());
		if(trailerUrl.size()>0){

			model.addAttribute("trailer", trailerUrl );
		}
		else{
			model.addAttribute("error",err);
			return "error";
		}
		
		return "trailer";
	}

}
