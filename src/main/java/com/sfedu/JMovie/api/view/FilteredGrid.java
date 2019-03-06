package com.sfedu.JMovie.api.view;

import com.sfedu.JMovie.api.data.CountryData;
import com.sfedu.JMovie.api.data.GenreData;
import com.sfedu.JMovie.api.data.MovieData;
import com.sfedu.JMovie.api.data.PersonData;
import com.sfedu.JMovie.api.security.SecurityContextUtils;
import com.sfedu.JMovie.db.entity.User;
import com.sfedu.JMovie.domain.BoolW;
import com.sfedu.JMovie.domain.GetOptions;
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
import java.util.*;

@SpringComponent
@UIScope
public class FilteredGrid extends VerticalLayout {
    private IMovieService service;
    private Button left, right;
    private final TextField byYearStarts = new TextField("От");
    private final TextField byYearEnds = new TextField("до");
    private final Grid<MovieData> movieGrid =
            new Grid<>(MovieData.class, false);
    private final ComboBox<GetOptions> filterBy =
            new ComboBox<>("Фильтр по");
    private Object param;
    private int currentPage;
    private BoolW hasNext;

    private void getMovies() {
        if (param == null)
            return;
        movieGrid.setItems(MovieConverter.convertToMovieListDTO(
                service.getMovies(filterBy.getValue(), param, currentPage, hasNext)
        ));
    }

    private void yearsSet(){
        try {
            List<Short> period = Arrays.asList(
                    Short.parseShort(byYearStarts.getValue()),
                    Short.parseShort(byYearEnds.getValue())
            );
            if (period.get(0) < period.get(1))
                param = period;
        } catch (Exception e) {
            param = null;
        }
    }

    @Autowired
    public FilteredGrid(IMovieService movieService){
        this.service = movieService;
        hasNext = new BoolW(false);
        currentPage = 0;
        //Для фильтрации по названию или сюжету
        final TextField byText = new TextField("Название");
        byText.setVisible(false);
        byText.addValueChangeListener(event -> param = event.getValue());
        //Для фильтрации по временному промежутку
        byYearStarts.setValue("1900");
        byYearStarts.addValueChangeListener(event -> yearsSet());
        byYearStarts.setVisible(false);
        byYearEnds.setValue(String.valueOf(LocalDate.now().getYear()));
        byYearEnds.addValueChangeListener(event -> yearsSet());
        byYearEnds.setVisible(false);
        //Для фильтрации по стране
        final ComboBox<CountryData> byCountry = new ComboBox<>("Страна");
        byCountry.setItemLabelGenerator(CountryData::getName);
        byCountry.setAllowCustomValue(false);
        byCountry.setItems(Collections.emptyList());
        byCountry.addValueChangeListener(event ->
                Optional.ofNullable(event.getValue())
                        .ifPresent(countryData -> param = countryData.getId()));
        byCountry.setVisible(false);
        //Для фильтрации по режиссёру или актёру
        final ComboBox<PersonData> byPerson = new ComboBox<>("Режиссёр");
        byPerson.setItemLabelGenerator(PersonData::getName);
        byPerson.setItems(Collections.emptyList());
        byPerson.addCustomValueSetListener(event ->
                byPerson.setItems(PersonConverter.convertToPersonListDTO(
                    service.getPersonListByNameContains(event.getDetail())
        )));
        byPerson.addValueChangeListener(event ->
                Optional.ofNullable(event.getValue())
                        .ifPresent(personData -> param = personData.getId()));
        byPerson.setVisible(false);
        //Для фильтрации по жанру
        final ComboBox<GenreData> byGenre = new ComboBox<>("Жанр");
        byGenre.setItemLabelGenerator(GenreData::getName);
        byGenre.setItems(Collections.emptyList());
        byGenre.setAllowCustomValue(false);
        byGenre.addValueChangeListener(event ->
                Optional.ofNullable(event.getValue())
                        .ifPresent(genreData -> param = genreData.getId()));
        byGenre.setVisible(false);

        final Button search = new Button("Найти", VaadinIcon.FILTER.create());
        search.setEnabled(false);
        //Список доступных фильтров
        filterBy.setItemLabelGenerator(GetOptions::getName);
        filterBy.setItems(new ArrayList<>(EnumSet.allOf(GetOptions.class)));
        filterBy.setValue(GetOptions.GetAll);
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
                case GetAll:
                    param = new Object();
                    break;
                case GetByTitle:
                    byText.setLabel("Название");
                    byText.setVisible(true);
                    break;
                case GetByYearPeriod:
                    byYearStarts.setVisible(true);
                    byYearEnds.setVisible(true);
                    break;
                case GetByCountry:
                    byCountry.setItems(CountryConverter
                            .convertToCountryListDTO(service.getAllCountries()));
                    byCountry.setVisible(true);
                    break;
                case GetByDirector:
                    byPerson.setLabel("Режиссёр");
                    byPerson.setVisible(true);
                    break;
                case GetByGenre:
                    byGenre.setItems(GenreConverter
                            .convertToGenreListDTO(service.getAllGenres()));
                    byGenre.setVisible(true);
                    break;
                case GetByActor:
                    byPerson.setLabel("Актёр");
                    byPerson.setVisible(true);
                    break;
                case GetByStoryline:
                    byText.setLabel("Сюжет");
                    byText.setVisible(true);
                    break;
                case GetByUser:
                    param = Optional.ofNullable(SecurityContextUtils.getUser())
                            .map(User::getId)
                            .orElse(null);
                    break;
            }
            search.setEnabled(true);
        });
        search.addClickListener(event -> {
            currentPage = 0;
            left.setEnabled(false);
            //Вызовем сразу обработчик, чтобы заполнить первую страницу
            getMovies();
            //Кнопка "вправо" будет активна, если есть следующая страница
            //Это можно определить после выполнения предыдущей строки кода
            right.setEnabled(hasNext.getValue());
        });
        final HorizontalLayout actions = new HorizontalLayout(filterBy,
                byText, byYearStarts, byYearEnds, byCountry, byPerson, byGenre, search);
        actions.setVerticalComponentAlignment(Alignment.BASELINE, filterBy,
                byText, byYearStarts, byYearEnds, byCountry, byPerson, byGenre, search);
        add(actions);

        movieGrid.setHeightByRows(true);
        param = new Object();
        getMovies();
        movieGrid.addColumn(MovieData::getOriginalTitle).setHeader("Название").setFlexGrow(6);
        movieGrid.addColumn(MovieData::getLocalizedTitle).setHeader("Локализация").setFlexGrow(6);
        movieGrid.addColumn(MovieData::getYear).setHeader("Год").setFlexGrow(1);
        movieGrid.addColumn(movieData -> movieData.getDirector().getName()).setHeader("Режиссёр").setFlexGrow(4);
        movieGrid.addColumn(MovieData::getRatingIMDB).setHeader("IMDB").setFlexGrow(1);
        movieGrid.addItemDoubleClickListener(event ->
                movieGrid.getUI().ifPresent(ui ->
                        ui.navigate(String.format("movie/%d", event.getItem().getId()))));
        add(movieGrid);
        //Кнопки для навигации между страницами с результатами
        //Определяется, можно ли двигаться влево/вправо
        left = new Button(VaadinIcon.ARROW_LEFT.create());
        left.setEnabled(false);
        left.addClickListener(event -> {
            currentPage--;
            getMovies();
            left.setEnabled(currentPage > 0);
            right.setEnabled(hasNext.getValue());
        });
        right = new Button(VaadinIcon.ARROW_RIGHT.create());
        right.setEnabled(hasNext.getValue());
        right.addClickListener(event -> {
            currentPage++;
            getMovies();
            left.setEnabled(currentPage > 0);
            right.setEnabled(hasNext.getValue());
        });
        final HorizontalLayout navigation = new HorizontalLayout(left, right);
        add(navigation);
        setHorizontalComponentAlignment(Alignment.CENTER, navigation);
    }
}
