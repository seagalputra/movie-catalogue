package com.seagalputra.moviecatalogue.presenter;

import com.seagalputra.moviecatalogue.adapter.MovieAdapter;
import com.seagalputra.moviecatalogue.model.Movie;
import com.seagalputra.moviecatalogue.view.DetailView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class MovieAdapterTest {

    @Mock
    private MovieAdapter movieAdapter;
    private DetailView detailView;

    @Before
    public void setup() {
        detailView = mock(DetailView.class);
    }

}