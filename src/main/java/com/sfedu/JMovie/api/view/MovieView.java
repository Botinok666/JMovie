package com.sfedu.JMovie.api.view;

import com.sfedu.JMovie.api.data.*;
import com.sfedu.JMovie.api.security.SecurityContextUtils;
import com.sfedu.JMovie.api.util.KinoPoiskParser;
import com.sfedu.JMovie.domain.service.IMovieService;
import com.sfedu.JMovie.domain.util.MovieConverter;
import com.sfedu.JMovie.domain.util.ViewingConverter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import org.springframework.security.core.context.SecurityContext;

import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Route
public class MovieView extends VerticalLayout implements HasUrlParameter<String> {
    private Integer movieId;
    private IMovieService service;

    public MovieView(IMovieService service){
        this.service = service;
    }

    private void fill(){
        final MovieData movie;
        boolean isNew = false;
        if (service.isMovieExists(movieId)) {
            movie = MovieConverter.convertToMovieDTO(service.getMovieById(movieId));
        } else {
            try {
                movie = KinoPoiskParser.parseURL(
                        new URL("https://www.kinopoisk.ru/film/" + movieId));
                isNew = true;
            }
            catch (SocketTimeoutException e1){
                add(new Label("Превышено время ожидания от kinopoisk.ru"));
                return;
            } catch (Exception ex) {
                add(new Label(ex.getMessage()));
                return;
            }
        }

        final Label movieTitle = new Label(
                movie.getLocalizedTitle().isEmpty() || movie.getOriginalTitle().isEmpty() ?
                        movie.getOriginalTitle() + movie.getLocalizedTitle() :
                        movie.getOriginalTitle() + " / " + movie.getLocalizedTitle()
        );
        movieTitle.getStyle().set("font-family", "helvetica");
        movieTitle.getStyle().set("font-size", "24pt");
        if (!isNew)
            service.addMissingListsToMovie(movie);
        final List<StringPair> movieData = new ArrayList<>();
        movieData.add(new StringPair("Год:", String.valueOf(movie.getYear())));
        movieData.add(new StringPair("Страна:", movie.getCountries()
                        .stream()
                        .map(CountryData::getName)
                        .collect(Collectors.joining(", "))));
        movieData.add(new StringPair("Режиссёр:", movie.getDirector().getName()));
        movieData.add(new StringPair("Сценарий:", movie.getScreenwriter().getName()));
        movieData.add(new StringPair("Слоган:", movie.getTagLine()));
        movieData.add(new StringPair("Жанр:", movie.getGenres()
                        .stream()
                        .map(GenreData::getName)
                        .collect(Collectors.joining(", "))));
        movieData.add(new StringPair("Продолжительность:", LocalTime.MIN
                        .plusMinutes(movie.getRuntime())
                        .toString()));
        movieData.add(new StringPair("В главных ролях:", movie.getActors()
                        .stream()
                        .map(PersonData::getName)
                        .collect(Collectors.joining(", "))));
        movieData.add(new StringPair("Сюжет:", movie.getStoryline()));
        movieData.add(new StringPair("Рейтинг KP:", String.valueOf(movie.getRatingKP())));
        movieData.add(new StringPair("Рейтинг IMDB:", String.valueOf(movie.getRatingIMDB())));
        //Добавим на страницу данные о просмотрах, если они были
        if (!isNew && SecurityContextUtils.getUser() != null) {
            final List<ViewingData> viewings = ViewingConverter.convertToViewingListDTO(
                    service.getViewingsByMovieAndUserId(
                            movieId, SecurityContextUtils.getUser().getId())
            );
            if (viewings.size() > 0)
                movieData.add(new StringPair("Просмотры: ", viewings
                        .stream()
                        .map(viewingData -> String.format("%s (%.1f)",
                                viewingData.getDate().toString(), viewingData.getRatingUser()))
                        .collect(Collectors.joining(", "))));
        }
        //Создадим вертикальный контейнер, который заполним горизонтальными с данными
        final VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.getStyle().set("font-size", "10pt");
        movieData.stream()
                .map(p -> {
                    Label left = new Label(p.getA());
                    left.setWidth("25%");
                    left.getStyle().set("text-align", "right");
                    Label right = new Label(p.getB());
                    right.setWidth("75%");
                    right.getStyle().set("white-space", "normal");
                    HorizontalLayout horizontalLayout = new HorizontalLayout(left, right);
                    horizontalLayout.setWidth("100%");
                    horizontalLayout.setMargin(false);
                    return horizontalLayout;
                })
                .forEach(layout::add);
        //Для картинки нужен отдельный контейнер, чтобы она не растягивалась
        final Image poster = new Image(movie.getPosterLink(), "There should be a poster");
        final VerticalLayout posterBox = new VerticalLayout(poster);
        posterBox.setPadding(false);
        posterBox.setWidth("360px");
        //Общий контейнер для картинки и данных о фильме
        final HorizontalLayout posterAndInfo = new HorizontalLayout(posterBox, layout);
        posterAndInfo.setSizeFull();
        //Контейнер для действий с фильмом
        final HorizontalLayout actions = new HorizontalLayout();
        final DatePicker datePicker = new DatePicker();
        datePicker.setVisible(!isNew);
        final TextField rating = new TextField("Рейтинг");
        rating.setVisible(!isNew);
        rating.setValue("5.0");
        final Button addViewing = new Button("Добавить просмотр", VaadinIcon.PLUS.create());
        addViewing.setVisible(!isNew);
        addViewing.addClickListener(event ->
            datePicker.getOptionalValue().ifPresent(localDate -> {
                if (localDate.isAfter(LocalDate.now())) {
                    Notification.show("Выберите дату не позднее текущей",
                            2000, Notification.Position.BOTTOM_START);
                } else {
                    if (SecurityContextUtils.getUser() == null) {
                        Notification.show("Ошибка при получении данных о пользователе",
                                2000, Notification.Position.BOTTOM_START);
                        return;
                    }
                    float grade;
                    try {
                        grade = Float.parseFloat(rating.getValue());
                        if (grade > 10 || grade < 0)
                            throw new NumberFormatException();
                    } catch (NumberFormatException e) {
                        Notification.show("Введите оценку от 0.0 до 10.0",
                                2000, Notification.Position.BOTTOM_START);
                        return;
                    }
                    try {
                        service.createViewing(localDate, SecurityContextUtils.getUser().getId(),
                                movieId, grade);
                        Notification.show("Просмотр сохранён!",
                                2000, Notification.Position.BOTTOM_START);
                    } catch (NoSuchElementException e) {
                        Notification.show(e.getMessage(),
                                2000, Notification.Position.BOTTOM_START);
                    }
                }
            })
        );
        final Button save = new Button("Сохранить", VaadinIcon.DISC.create());
        save.setVisible(isNew);
        save.addClickListener(event -> {
           MovieData data = MovieConverter.convertToMovieDTO(service.createMovie(movie));
           if (data.getId().equals(movieId)) {
               save.setVisible(false);
               datePicker.setVisible(true);
               addViewing.setVisible(true);
               Notification.show("Фильм сохранён",
                       2000, Notification.Position.BOTTOM_START);
           } else {
               Notification.show("Что-то пошло не так",
                       2000, Notification.Position.BOTTOM_START);
           }
        });

        actions.add(save, datePicker, rating, addViewing);
        actions.setVerticalComponentAlignment(Alignment.BASELINE,
                datePicker, rating, addViewing);
        add(movieTitle, posterAndInfo, actions);
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
