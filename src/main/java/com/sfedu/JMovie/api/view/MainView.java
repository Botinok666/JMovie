package com.sfedu.JMovie.api.view;

import com.sfedu.JMovie.api.data.MovieData;
import com.sfedu.JMovie.api.security.SecurityContextUtils;
import com.sfedu.JMovie.db.RoleType;
import com.sfedu.JMovie.domain.service.IMovieService;
import com.sfedu.JMovie.domain.util.MovieConverter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.security.core.context.SecurityContextHolder;

@Route
public class MainView extends VerticalLayout {
    private IMovieService service;

    private Button addUser;

    public MainView(IMovieService movieService){
        this.service = movieService;
        Grid<MovieData> movieGrid = new Grid<>(MovieData.class);
        movieGrid.setHeightByRows(true);
        movieGrid.setColumns("originalTitle", "localizedTitle", "year", "ratingIMDB");

        Button addNew = new Button("New movie", VaadinIcon.PLUS_CIRCLE.create());
        TextField filter = new TextField();
        filter.setPlaceholder("Filter by original");

        // Hook logic to components
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        //filter.addValueChangeListener(event -> listHellos(event.getValue()));
        HorizontalLayout actions = new HorizontalLayout(filter, addNew);

        Button logout = new Button("Log out", VaadinIcon.EXIT_O.create());
        logout.addClickListener(event ->
                logout.getUI().ifPresent(ui -> {
                    SecurityContextHolder.clearContext();
                    VaadinSession.getCurrent().close();
                    ui.getSession().close();
                    ui.navigate("login/logout");// to redirect user to the login page
        }));

        add(actions, movieGrid);
        if (SecurityContextUtils.hasRole(RoleType.ROLE_ADMIN)) {
            Button addUser = new Button("Add user", VaadinIcon.USER.create());
            addUser.addClickListener(event ->
                    Notification.show("Add user clicked",
                            2000, Notification.Position.BOTTOM_CENTER));
            add(addUser);
        }
        movieGrid.addItemDoubleClickListener(event ->
                movieGrid.getUI().ifPresent(ui ->
                        ui.navigate(String.format("movie/%d", event.getItem().getId()))));
        add(logout);
        movieGrid.setItems(MovieConverter.convertToMovieListDTO(
                movieService.getTenMoviesPaged(0)));
    }
}
