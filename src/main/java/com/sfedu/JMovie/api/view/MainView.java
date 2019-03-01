package com.sfedu.JMovie.api.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

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
        Button account = new Button("Профиль", VaadinIcon.COG_O.create());
        account.addClickListener(event -> account.getUI().ifPresent(
                ui -> ui.navigate(AccountView.class)));
        pageBottom.add(account);
        pageBottom.setVerticalComponentAlignment(Alignment.BASELINE,
                urlField, addNew, account);
        add(pageBottom);
    }
}
