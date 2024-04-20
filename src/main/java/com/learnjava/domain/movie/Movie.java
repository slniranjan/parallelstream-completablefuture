package com.learnjava.domain.movie;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
	private MovieInfo movieInfo;
	private List<Review> reviewList;
}
