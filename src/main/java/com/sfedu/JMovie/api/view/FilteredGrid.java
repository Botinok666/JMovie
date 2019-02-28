package com.sfedu.JMovie.api.view;

import com.sfedu.JMovie.api.data.CountryData;
import com.sfedu.JMovie.api.data.GenreData;
import com.sfedu.JMovie.api.data.MovieData;
import com.sfedu.JMovie.api.data.PersonData;
import com.sfedu.JMovie.domain.BoolW;
import com.sfedu.JMovie.domain.service.IMovieService;
import com.sfedu.JMovie.domain.util.CountryConverter;
import com.sfedu.JMovie.domain.util.GenreConverter;
import com.sfedu.JMovie.domain.util.MovieConverter;
import com.sfedu.JMovie.domain.util.PersonConverter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Collections;

@SpringComponent
@UIScope
public class FilteredGrid extends VerticalLayout {
    private IMovieService service;
    private Button left, right;
    private int currentPage;
    private BoolW hasNext;
    private Short start, end;

    public interface PageHandler{
        void onChange();
    }
    private PageHandler pageHandler;
    private void setPageHandler(PageHandler h) {
        //Handler is notified when either left or right is clicked
        pageHandler = h;
    }

    @Autowired
    public FilteredGrid(IMovieService movieService){
        this.service = movieService;
        hasNext = new BoolW(false);
        currentPage = 0;

        TextField byText = new TextField("Название");
        byText.setVisible(false);

        TextField byYearStarts = new TextField("От");
        byYearStarts.setValue("1900");
        byYearStarts.setVisible(false);
        TextField byYearEnds = new TextField("до");
        byYearEnds.setValue(String.valueOf(LocalDate.now().getYear()));
        byYearEnds.setVisible(false);

        ComboBox<CountryData> byCountry = new ComboBox<>("Страна");
        byCountry.setItemLabelGenerator(CountryData::getName);
        byCountry.setAllowCustomValue(false);
        byCountry.setItems(Collections.emptyList());
        byCountry.setVisible(false);

        ComboBox<PersonData> byPerson = new ComboBox<>("Режиссёр");
        byPerson.setItemLabelGenerator(PersonData::getName);
        byPerson.setItems(Collections.emptyList());
        byPerson.addCustomValueSetListener(event ->
                byPerson.setItems(PersonConverter.convertToPersonListDTO(
                    service.getPersonListByNameContains(event.getDetail())
        )));
        byPerson.setVisible(false);

        ComboBox<GenreData> byGenre = new ComboBox<>("Жанр");
        byGenre.setItemLabelGenerator(GenreData::getName);
        byGenre.setItems(Collections.emptyList());
        byGenre.setAllowCustomValue(false);
        byGenre.setVisible(false);

        Button search = new Button("Найти", VaadinIcon.FILTER.create());
        search.setEnabled(false);

        ComboBox<String> filterBy = new ComboBox<>("Фильтр по");
        filterBy.setItems("<нет>", "названию", "году", "стране", "режиссёру",
                "жанру", "актёру", "сюжету");
        filterBy.setValue("<нет>");
        filterBy.setAllowCustomValue(false);
        filterBy.addValueChangeListener(event -> {
            byText.setVisible(false);
            byYearStarts.setVisible(false);
            byYearEnds.setVisible(false);
            byCountry.setVisible(false);
            byPerson.setVisible(false);
            byGenre.setVisible(false);
            if (event.getValue() == null) {
                search.setEnabled(false);
                return;
            }
            switch (event.getValue()){
                case "названию":
                    byText.setLabel("Название");
                    byText.setVisible(true);
                    break;
                case "году":
                    byYearStarts.setVisible(true);
                    byYearEnds.setVisible(true);
                    break;
                case "стране":
                    byCountry.setItems(CountryConverter
                            .convertToCountryListDTO(service.getAllCountries()));
                    byCountry.setVisible(true);
                    break;
                case "режиссёру":
                    byPerson.setLabel("Режиссёр");
                    byPerson.setVisible(true);
                    break;
                case "жанру":
                    byGenre.setItems(GenreConverter
                            .convertToGenreListDTO(service.getAllGenres()));
                    byGenre.setVisible(true);
                    break;
                case "актёру":
                    byPerson.setLabel("Актёр");
                    byPerson.setVisible(true);
                    break;
                case "сюжету":
                    byText.setLabel("Сюжет");
                    byText.setVisible(true);
                    break;
            }
            search.setEnabled(true);
        });
        Grid<MovieData> movieGrid = new Grid<>(MovieData.class, false);
        search.addClickListener(event -> {
            currentPage = 0;
            left.setEnabled(false);
            switch (filterBy.getValue()){
                case "<нет>":
                    setPageHandler(() -> movieGrid.setItems(
                            MovieConverter.convertToMovieListDTO(
                                    service.getTenMoviesPaged(currentPage, hasNext)
                            )));
                    break;
                case "названию":
                    setPageHandler(() -> movieGrid.setItems(
                            MovieConverter.convertToMovieListDTO(
                                    service.getMovieListByTitleContains(
                                            byText.getValue(), currentPage, hasNext)
                            )));
                    break;
                case "году":
                    try {
                        start = Short.parseShort(byYearStarts.getValue());
                        end = Short.parseShort(byYearEnds.getValue());
                    } catch (Exception e) {
                        return;
                    }
                    if (start >= end)
                        return;
                    setPageHandler(() -> movieGrid.setItems(
                            MovieConverter.convertToMovieListDTO(
                                    service.getMovieListByYearPeriod(
                                            start, end, currentPage, hasNext)
                            )));
                    break;
                case "стране":
                    setPageHandler(() -> {
                        if (byCountry.getValue() == null)
                            return;
                        movieGrid.setItems(
                                MovieConverter.convertToMovieListDTO(
                                        service.getMovieListByCountryId(
                                                byCountry.getValue().getId(), currentPage, hasNext)
                                ));
                    });
                    break;
                case "режиссёру":
                    setPageHandler(() -> {
                        if (byPerson.getValue() == null)
                            return;
                        movieGrid.setItems(
                            MovieConverter.convertToMovieListDTO(
                                    service.getMovieListByDirectorId(
                                            byPerson.getValue().getId(), currentPage, hasNext)
                            ));
                    });
                    break;
                case "жанру":
                    setPageHandler(() -> {
                        if (byGenre.getValue() == null)
                            return;
                        movieGrid.setItems(
                            MovieConverter.convertToMovieListDTO(
                                    service.getMovieListByGenreId(
                                            byGenre.getValue().getId(), currentPage, hasNext)
                            ));
                    });
                    break;
                case "актёру":
                    setPageHandler(() ->{
                        if (byPerson.getValue() == null)
                            return;
                        movieGrid.setItems(
                            MovieConverter.convertToMovieListDTO(
                                    service.getMovieListByActorId(
                                            byPerson.getValue().getId(), currentPage, hasNext)
                            ));
                    });
                    break;
                case "сюжету":
                    setPageHandler(() -> movieGrid.setItems(
                            MovieConverter.convertToMovieListDTO(
                                    service.getMovieListByStorylineContains(
                                            byText.getValue(), currentPage, hasNext)
                            )));
                    break;
            }
            pageHandler.onChange();
            right.setEnabled(hasNext.getValue());
        });
        HorizontalLayout actions = new HorizontalLayout(filterBy,
                byText, byYearStarts, byYearEnds, byCountry, byPerson, byGenre, search);
        actions.setVerticalComponentAlignment(Alignment.BASELINE, filterBy,
                byText, byYearStarts, byYearEnds, byCountry, byPerson, byGenre, search);
        add(actions);

        movieGrid.setHeightByRows(true);
        setPageHandler(() -> movieGrid.setItems(
                MovieConverter.convertToMovieListDTO(
                        service.getTenMoviesPaged(currentPage, hasNext)
                )));
        pageHandler.onChange();
        movieGrid.setColumns("originalTitle", "localizedTitle", "year", "ratingIMDB");
        movieGrid.addItemDoubleClickListener(event ->
                movieGrid.getUI().ifPresent(ui ->
                        ui.navigate(String.format("movie/%d", event.getItem().getId()))));
        add(movieGrid);

        left = new Button(VaadinIcon.ARROW_LEFT.create());
        left.setEnabled(false);
        left.addClickListener(event -> {
            currentPage--;
            pageHandler.onChange();
            left.setEnabled(currentPage > 0);
            right.setEnabled(hasNext.getValue());
        });
        right = new Button(VaadinIcon.ARROW_RIGHT.create());
        right.setEnabled(hasNext.getValue());
        right.addClickListener(event -> {
            currentPage++;
            pageHandler.onChange();
            left.setEnabled(currentPage > 0);
            right.setEnabled(hasNext.getValue());
        });
        HorizontalLayout navigation = new HorizontalLayout(left, right);
        add(navigation);
        setHorizontalComponentAlignment(Alignment.CENTER, navigation);
    }
}
