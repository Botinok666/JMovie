package com.sfedu.JMovie.api.view;

import com.sfedu.JMovie.api.security.SecurityContextUtils;
import com.sfedu.JMovie.db.RoleType;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.security.core.context.SecurityContextHolder;

import java.net.MalformedURLException;
import java.net.URL;

@Route
public class MainView extends VerticalLayout {

    public MainView(FilteredGrid filteredGrid){
        add(filteredGrid);

        HorizontalLayout pageBottom = new HorizontalLayout();
        TextField urlField = new TextField("Фильм на kinopoisk");
        urlField.setPlaceholder("Введите URL фильма");
        Button addNew = new Button("Добавить фильм", VaadinIcon.PLUS_CIRCLE.create());
        addNew.addClickListener(event -> {
            try {
                URL url = new URL(urlField.getValue());
                if (!url.getHost().equals("www.kinopoisk.ru"))
                    throw new MalformedURLException("Поддерживается только kinopoisk.ru");
                String[] path = url.getPath().split("/");
                Integer movieId = Integer.parseInt(path[path.length - 1]);
                urlField.getUI().ifPresent(ui ->
                        ui.navigate(String.format("movie/%d", movieId)));
            } catch (MalformedURLException e) {
                Notification.show("Неверный адрес. " + e.getMessage(),
                        2000, Notification.Position.BOTTOM_START);
            } catch (NumberFormatException e) {
                Notification.show("Введите прямую ссылку на фильм",
                        2000, Notification.Position.BOTTOM_START);
            }
        });
        pageBottom.add(urlField, addNew);
        Button logout = new Button("Выйти", VaadinIcon.EXIT_O.create());
        logout.addClickListener(event ->
                logout.getUI().ifPresent(ui -> {
                    SecurityContextHolder.clearContext();
                    VaadinSession.getCurrent().close();
                    ui.getSession().close();
                    ui.navigate("login/logout");// to redirect user to the login page
                }));
        pageBottom.add(logout);

        if (SecurityContextUtils.hasRole(RoleType.ROLE_ADMIN)) {
            Button addUser = new Button("Add user", VaadinIcon.USER.create());
            addUser.addClickListener(event ->
                    Notification.show("Add user clicked",
                            2000, Notification.Position.BOTTOM_CENTER));
            pageBottom.add(addUser);
        }
        pageBottom.setVerticalComponentAlignment(Alignment.BASELINE,
                urlField, addNew, logout);
        add(pageBottom);
    }
}
