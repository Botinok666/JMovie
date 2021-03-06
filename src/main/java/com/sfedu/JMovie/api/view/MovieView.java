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
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.*;

import java.io.*;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Route
public class MovieView extends VerticalLayout implements HasUrlParameter<String> {
    private Integer movieId;
    private IMovieService service;
    private MovieData movie;

    public MovieView(IMovieService service){
        this.service = service;
    }

    private void fill(boolean isNew){
        //Название, если есть только в одном варианте, выводится без слэша
        final Label movieTitle = new Label(
                movie.getLocalizedTitle().isEmpty() || movie.getOriginalTitle().isEmpty() ?
                        movie.getOriginalTitle() + movie.getLocalizedTitle() :
                        movie.getOriginalTitle() + " / " + movie.getLocalizedTitle()
        );
        movieTitle.getStyle().set("font-family", "helvetica");
        movieTitle.getStyle().set("font-size", "24pt");
        //Если фильм взят из БД, заполним все списки (по умолчанию они пустые)
        if (!isNew)
            service.addMissingListsToMovie(movie);
        //Массив для представления всех характеристик фильма
        final List<List<String>> movieData = new ArrayList<>();
        movieData.add(Arrays.asList("Год:", String.valueOf(movie.getYear())));
        movieData.add(Arrays.asList("Страна:", movie.getCountries()
                        .stream()
                        .map(CountryData::getName)
                        .collect(Collectors.joining(", "))));
        movieData.add(Arrays.asList("Режиссёр:", movie.getDirector().getName()));
        movieData.add(Arrays.asList("Сценарий:", movie.getScreenwriter().getName()));
        movieData.add(Arrays.asList("Слоган:", movie.getTagLine()));
        movieData.add(Arrays.asList("Жанр:", movie.getGenres()
                        .stream()
                        .map(GenreData::getName)
                        .collect(Collectors.joining(", "))));
        movieData.add(Arrays.asList("Продолжительность:", LocalTime.MIN
                        .plusMinutes(movie.getRuntime())
                        .toString()));
        movieData.add(Arrays.asList("В главных ролях:", movie.getActors()
                        .stream()
                        .map(PersonData::getName)
                        .collect(Collectors.joining(", "))));
        movieData.add(Arrays.asList("Сюжет:", movie.getStoryline()));
        movieData.add(Arrays.asList("Рейтинг KP:", String.valueOf(movie.getRatingKP())));
        movieData.add(Arrays.asList("Рейтинг IMDB:", String.valueOf(movie.getRatingIMDB())));
        //Добавим данные о просмотрах, если они были
        if (!isNew && SecurityContextUtils.getUser() != null) {
            final List<ViewingData> viewings = ViewingConverter.convertToViewingListDTO(
                    service.getViewingsByMovieAndUserId(
                            movieId, SecurityContextUtils.getUser().getId())
            );
            if (viewings.size() > 0)
                movieData.add(Arrays.asList("Просмотры: ", viewings
                        .stream()
                        .map(viewingData -> String.format("%s (%.1f)",
                                viewingData.getDate().toString(), viewingData.getRatingUser()))
                        .collect(Collectors.joining(", "))));
        }
        //Создадим вертикальный контейнер для отображения характеристик
        final VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.getStyle().set("font-size", "10pt");
        //Преобразуем все характеристики в горизонтальные контейнеры
        //Тогда характеристики будут представлены в виде двух столбцов
        movieData.stream()
                .map(p -> {
                    Label left = new Label(p.get(0));
                    left.setWidth("25%");
                    left.getStyle().set("text-align", "right");
                    Label right = new Label(p.get(1));
                    right.setWidth("75%");
                    right.getStyle().set("white-space", "normal");
                    HorizontalLayout horizontalLayout = new HorizontalLayout(left, right);
                    horizontalLayout.setWidth("100%");
                    horizontalLayout.setMargin(false);
                    return horizontalLayout;
                })
                .forEach(layout::add);
        //Сначала попробуем загрузить сохранённый постер
        final String pName = movie.getPosterLink().substring(
                movie.getPosterLink().lastIndexOf('/'));
        final Path fName = Paths.get("")
                .toAbsolutePath()
                .resolve("src/main/webapp/frontend/images" + pName
                );
        final Image poster;
        if (new File(fName.toString()).exists()) {
            poster = new Image("frontend/images" + pName,
                    "There should be a local poster");
        } else {
            poster = new Image(movie.getPosterLink(), "There should be a poster");
        }

        //Для картинки нужен отдельный контейнер, чтобы она не растягивалась
        final VerticalLayout posterBox = new VerticalLayout(poster);
        posterBox.setPadding(false);
        posterBox.setWidth("360px");
        //Общий контейнер для картинки и данных о фильме
        final HorizontalLayout posterAndInfo = new HorizontalLayout(posterBox, layout);
        posterAndInfo.setSizeFull();
        //Контейнер для действий с фильмом
        final HorizontalLayout actions = new HorizontalLayout();
        //Инструмент для выбора даты
        final DatePicker datePicker = new DatePicker();
        datePicker.setVisible(!isNew);
        //Поле для ввода пользовательского рейтинга
        final TextField rating = new TextField("Рейтинг");
        rating.setVisible(!isNew);
        rating.setValue(String.format(Locale.US, "%.1f", movie.getRatingIMDB()));
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
        //Если фильм взят из интернета, нужно его сначала сохранить в БД
        final Button save = new Button("Сохранить", VaadinIcon.DISC.create());
        save.setVisible(isNew);
        save.addClickListener(event -> {
           MovieData data = MovieConverter.convertToMovieDTO(service.createMovie(movie));
           if (data.getId().equals(movieId)) {
               save.setVisible(false);
               datePicker.setVisible(true);
               rating.setVisible(true);
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
        }        boolean fromFile = false;
        //Если фильм есть в БД, берём оттуда
        if (service.isMovieExists(movieId)) {
            movie = MovieConverter.convertToMovieDTO(service.getMovieById(movieId));
            fill(false);
        } else {
            //Иначе пытаемся получить данные из интернета
            try {
                movie = KinoPoiskParser.parseURL(
                        new URL("https://www.kinopoisk.ru/film/" + movieId));
                fill(true);
            }
            catch (SocketTimeoutException e1){
                add(new Label("Превышено время ожидания от kinopoisk.ru"));
                return;
            } catch (Exception ex) {
                if (!ex.getMessage().contains("ID")) {
                    add(new Label(ex.getMessage()));
                    return;
                }
                fromFile = true;
            }
        }
        if (fromFile) {
            Label tryUpload = new Label("Не удаётся загрузить страницу. Попробуйте сохранить " +
                    "её в HTML и загрузить файл в поле:");
            MemoryBuffer buffer = new MemoryBuffer();
            Upload fileUp = new Upload(buffer);
            fileUp.setAcceptedFileTypes("text/html");
            fileUp.addSucceededListener(event1 -> {
                try {
                    movie = KinoPoiskParser.parseStream(buffer.getInputStream());
                    tryUpload.setVisible(false);
                    fileUp.setVisible(false);
                    fill(true);
                } catch (NoSuchFieldException e) {
                    add(new Label(e.getMessage()));
                }
            });
            add(tryUpload, fileUp);
        }
    }
}
