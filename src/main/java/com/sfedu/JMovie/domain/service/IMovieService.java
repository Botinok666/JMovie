package com.sfedu.JMovie.domain.service;

import com.sfedu.JMovie.api.data.MovieData;
import com.sfedu.JMovie.db.RoleType;
import com.sfedu.JMovie.domain.BoolW;
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
     * Получить все фильмы
     * @param page Номер страницы (размер страницы 10)
     * @param hasNext Возращает флаг "есть ли следующая страница"
     * @return Список фильмов (10 или менее)
     */
    List<MovieDomain> getAllMovies(int page, BoolW hasNext);

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
     * Получить список фильмов, просмотренных определённым пользователем
     * @param id ID пользователя
     * @param page Номер страницы (размер страницы 10)
     * @param hasNext Возращает флаг "есть ли следующая страница"
     * @return Список фильмов (10 или менее)
     */
    List<MovieDomain> getMovieListByUserId(Short id, int page, BoolW hasNext);

    /**
     * Получить список фильмов, одно из названий которых содержит заданную строку
     * @param title Строка, которая должна входить в одно из названий
     * @param page Номер страницы (размер страницы 10)
     * @param hasNext Возращает флаг "есть ли следующая страница"
     * @return Список фильмов (10 или менее)
     */
    List<MovieDomain> getMovieListByTitleContains(String title, int page, BoolW hasNext);

    /**
     * Получить список фильмов, в описании сюжета которых содержится заданная строка
     * @param story Строка, которая должна входить в описание сюжета
     * @param page Номер страницы (размер страницы 10)
     * @param hasNext Возращает флаг "есть ли следующая страница"
     * @return Список фильмов (10 или менее)
     */
    List<MovieDomain> getMovieListByStorylineContains(String story, int page, BoolW hasNext);

    /**
     * Получить список фильмов заданного жанра
     * @param id ID жанра
     * @param page Номер страницы (размер страницы 10)
     * @param hasNext Возращает флаг "есть ли следующая страница"
     * @return Список фильмов (10 или менее)
     */
    List<MovieDomain> getMovieListByGenreId(Short id, int page, BoolW hasNext);

    /**
     * Получить список фильмов от определённого режиссёра
     * @param id ID режиссёра
     * @param page Номер страницы (размер страницы 10)
     * @param hasNext Возращает флаг "есть ли следующая страница"
     * @return Список фильмов (10 или менее)
     */
    List<MovieDomain> getMovieListByDirectorId(Integer id, int page, BoolW hasNext);

    /**
     * Получить список фильмов, которые снимались в заданной стране
     * @param id ID страны
     * @param page Номер страницы (размер страницы 10)
     * @param hasNext Возращает флаг "есть ли следующая страница"
     * @return Список фильмов (10 или менее)
     */
    List<MovieDomain> getMovieListByCountryId(Short id, int page, BoolW hasNext);

    /**
     * Получить список фильмов, в которых снимался определённый актёр
     * @param id ID актёра
     * @param page Номер страницы (размер страницы 10)
     * @param hasNext Возращает флаг "есть ли следующая страница"
     * @return Список фильмов (10 или менее)
     */
    List<MovieDomain> getMovieListByActorId(Integer id, int page, BoolW hasNext);

    /**
     * Получить список фильмов, вышедших в заданный промежуток времени
     * @param start Начало промежутка (год)
     * @param end Конец промежутка (год)
     * @param page Номер страницы (размер страницы 10)
     * @param hasNext Возращает флаг "есть ли следующая страница"
     * @return Список фильмов (10 или менее)
     */
    List<MovieDomain> getMovieListByYearPeriod(Short start, Short end, int page, BoolW hasNext);

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
