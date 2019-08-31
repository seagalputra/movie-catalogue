package com.seagalputra.moviecatalogue.presenter;

import com.seagalputra.moviecatalogue.model.Movie;
import com.seagalputra.moviecatalogue.view.MovieView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class MoviePresenterTest {

    @Mock
    private MoviePresenter moviePresenter;
    private MovieView movieView;

    @Before
    public void setup() {
        movieView = mock(MovieView.class);
        moviePresenter = new MoviePresenter(movieView);
    }

    @Test
    public void getMovieData() {
        Movie movie = new Movie("Aquaman");
        assertEquals("Aquaman", movie.getTitle());
    }
}