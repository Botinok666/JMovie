package com.sfedu.JMovie.api.view;

import com.sfedu.JMovie.api.data.CountryData;
import com.sfedu.JMovie.api.data.GenreData;
import com.sfedu.JMovie.api.data.MovieData;
import com.sfedu.JMovie.api.data.PersonData;
import com.sfedu.JMovie.domain.service.IMovieService;
import com.sfedu.JMovie.domain.util.MovieConverter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Route
public class MovieView extends VerticalLayout implements HasUrlParameter<String> {
    private Integer movieId;
    private IMovieService service;

    public MovieView(IMovieService service){
        this.service = service;
        removeAll();
    }

    private void fill(){
        MovieData movie = MovieConverter.convertToMovieDTO(service.getMovieById(movieId));
        if (movie == null) {
            add(new Label("This movie isn't in the database yet"));
            return;
        }
        Label movieTitle = new Label(movie.getOriginalTitle() +
                " / " + movie.getLocalizedTitle());
        service.addMissingListsToMovie(movie);
        List<StringPair> movieData = Arrays.asList(
                new StringPair("Год:", String.valueOf(movie.getYear())),
                new StringPair("Страна:", movie.getCountries().stream()
                        .map(CountryData::getName)
                        .collect(Collectors.joining(", "))),
                new StringPair("Режиссёр:", movie.getDirector().getName()),
                new StringPair("Сценарий:", movie.getScreenwriter().getName()),
                new StringPair("Слоган:", movie.getTagLine()),
                new StringPair("Жанр:", movie.getGenres().stream()
                        .map(GenreData::getName)
                        .collect(Collectors.joining(", "))),
                new StringPair("Продолжительность:", LocalTime.MIN
                        .plusMinutes(movie.getRuntime()).toString()),
                new StringPair("В главных ролях:", movie.getActors().stream()
                        .map(PersonData::getName)
                        .collect(Collectors.joining(", "))),
                new StringPair("Сюжет:", movie.getStoryline()),
                new StringPair("Рейтинг KP:", String.valueOf(movie.getRatingKP())),
                new StringPair("Рейтинг IMDB:", String.valueOf(movie.getRatingIMDB()))
        );
        Grid<StringPair> dataGrid = new Grid<>(StringPair.class, false);
        dataGrid.setItems(movieData);
        dataGrid.setHeightByRows(true);
        dataGrid.addColumn(new ComponentRenderer<>(stringPair -> new Label(stringPair.getA())))
                .setFlexGrow(1).setTextAlign(ColumnTextAlign.END);
        dataGrid.addColumn(new ComponentRenderer<>(stringPair -> {
            Label label = new Label(stringPair.getB());
            label.setSizeFull();
            label.getStyle().set("white-space", "normal");
            return label;
        })).setFlexGrow(4).setTextAlign(ColumnTextAlign.START).setVisible(true);
        //dataGrid.addColumn(StringPair::getB);
        Image poster = new Image(movie.getPosterLink(), "There should be a poster");
        VerticalLayout posterBox = new VerticalLayout(poster);
        posterBox.setPadding(false);
        posterBox.setWidth("360px");
        //poster.setHeight("480px");
        HorizontalLayout posterAndInfo = new HorizontalLayout(posterBox, dataGrid);
        posterAndInfo.setSizeFull();
        Button addViewing = new Button("Add viewing", VaadinIcon.PLUS.create());
        DatePicker datePicker = new DatePicker();
        add(movieTitle, posterAndInfo, new HorizontalLayout(addViewing, datePicker));
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        add(new RouterLink("Back to main view", MainView.class));
        if (parameter != null) {
            try {
                movieId = Integer.valueOf(parameter);
            }
            catch (NumberFormatException e) {
                add(new Label("Wrong parameter specified"));
                return;
            }
        }
        fill();
    }

    public static class StringPair {
        private String a;
        private String b;
        public StringPair(String A, String B){
            this.setA(A);
            this.setB(B);
        }

        public String getA() {
            return a;
        }

        public String getB() {
            return b;
        }

        public void setA(String a) {
            this.a = a;
        }

        public void setB(String b) {
            this.b = b;
        }
    }
}
