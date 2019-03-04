package com.sfedu.JMovie.domain.service;

import com.sfedu.JMovie.api.data.MovieData;
import com.sfedu.JMovie.db.RoleType;
import com.sfedu.JMovie.domain.BoolW;
import com.sfedu.JMovie.domain.GetOptions;
import com.sfedu.JMovie.domain.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public interface IMovieService {
    /**
     * Получить все жанры
     * @return Список жанров
     */
    List<GenreDomain> getAllGenres();

    /**
     * Получить все страны
     * @return Список стран
     */
    List<CountryDomain> getAllCountries();

    /**
     * Получить список фильмов
     * @param option Опция для осуществления выборки
     * @param param Параметр для выборки
     * @param page Номер страницы
     * @param hasNext Возвращает, если ли ещё страницы
     * @return Список фильмов, от 0 до 10
     */
    List<MovieDomain> getMovies(GetOptions option,
                                Object param, int page, BoolW hasNext);

    /**
     * Получить фильм по его ID
     * @param id ID фильма
     * @return Фильм с заданным ID
     * @throws NoSuchElementException Если фильм с заданным ID не найден
     */
    MovieDomain getMovieById(Integer id) throws NoSuchElementException;

    /**
     * Определить, если ли фильм в БД
     * @param id ID фильма
     * @return true, если фильм присутствует в БД
     */
    boolean isMovieExists(Integer id);

    /**
     * Заполнить страны, жанры и актёров
     * @param movieData Фильм, для которого нужно заполнить недостающие поля
     * @throws NoSuchElementException Если фильма нет в БД
     */
    void addMissingListsToMovie(MovieData movieData) throws NoSuchElementException;

    /**
     * Получить список из 10 людей, в имени/фамилии которых содержится заданная строка
     * @param name Строка, которая должна входить в имя/фамилию
     * @return Первые 10 людей, соответствующих критерию
     */
    List<PersonDomain> getPersonListByNameContains(String name);

    /**
     * Создать пользователя с заданным логином и пустым паролем
     * @param name Логин
     * @param role Роль
     * @return Созданный пользователь
     * @throws IllegalArgumentException Если такой логин уже занят
     */
    UserDomain createUser(String name, RoleType role) throws IllegalArgumentException;

    /**
     * Изменить пароль пользователя
     * @param id ID пользователя
     * @param pwd Новый пароль
     * @return Обновлённый пользователь
     * @throws NoSuchElementException Если пользователя с таким ID нет
     */
    UserDomain updateUserPwd(Short id, String pwd) throws NoSuchElementException;

    /**
     * Получить список просмотров для заданного фильма и конкретного пользователя
     * @param movieId ID фильма
     * @param userId ID пользователя
     * @return Список просмотров
     */
    List<ViewingDomain> getViewingsByMovieAndUserId(Integer movieId, Short userId);

    /**
     * Сохранить просмотр
     * @param date Дата просмотра
     * @param user_id ID пользователя
     * @param movie_id ID фильма
     * @param ratingUser Рейтинг, который поставил пользователь
     * @return Созданный просмотр
     * @throws NoSuchElementException Если нет фильма или пользователя с заданными ID
     */
    ViewingDomain createViewing(LocalDate date, Short user_id,
                                Integer movie_id, float ratingUser)
            throws NoSuchElementException;

    /**
     * Сохранить фильм в БД
     * @param movieData Все данные о фильме
     * @return Сохранённый фильм
     */
    MovieDomain createMovie(MovieData movieData);
}
