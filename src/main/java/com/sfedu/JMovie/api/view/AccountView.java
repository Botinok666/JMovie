package com.sfedu.JMovie.api.view;

import com.sfedu.JMovie.api.security.SecurityContextUtils;
import com.sfedu.JMovie.db.RoleType;
import com.sfedu.JMovie.domain.model.UserDomain;
import com.sfedu.JMovie.domain.service.IMovieService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Route
public class AccountView extends VerticalLayout
        implements HasUrlParameter<String> {

    public AccountView(IMovieService service, PasswordEncoder passwordEncoder){
        add(new RouterLink("Back to main view", MainView.class));
        if (SecurityContextUtils.getUser() == null)
            return;
        PasswordField newPass = new PasswordField("Новый пароль");
        PasswordField passRepeat = new PasswordField("Повторите новый пароль");
        Button change = new Button("Сменить пароль");
        change.addClickListener(event -> {
           if (newPass.getValue().equals(passRepeat.getValue())) {
               service.updateUserPwd(SecurityContextUtils.getUser().getId(),
                       passwordEncoder.encode(passRepeat.getValue()));
               Notification.show("Новый пароль установлен",
                       3000, Notification.Position.TOP_CENTER);
           }
        });
        HorizontalLayout pass = new HorizontalLayout(newPass, passRepeat, change);
        pass.setVerticalComponentAlignment(Alignment.BASELINE, newPass, passRepeat, change);
        add(pass);

        if (SecurityContextUtils.hasRole(RoleType.ROLE_ADMIN)) {
            Button addUser = new Button("Добавить пользователя",
                    VaadinIcon.USER.create());
            TextField name = new TextField("Логин");
            addUser.addClickListener(event ->
                name.getOptionalValue().ifPresent(value -> {
                        if (value.length() > 1) {
                            UserDomain user = service.createUser(value, RoleType.ROLE_USER);
                            service.updateUserPwd(user.getId(),
                                    passwordEncoder.encode(""));
                            Notification.show("Пользователь добавлен",
                                    2000, Notification.Position.BOTTOM_CENTER);
                        } else {
                            Notification.show("Слишком короткий логин",
                                    2000, Notification.Position.BOTTOM_CENTER);
                        }
                    }
                ));
            HorizontalLayout newUser = new HorizontalLayout(name, addUser);
            newUser.setVerticalComponentAlignment(Alignment.BASELINE, name, addUser);
            add(newUser);
        }

        Button logout = new Button("Выйти", VaadinIcon.EXIT_O.create());
        logout.addClickListener(event ->
                logout.getUI().ifPresent(ui -> {
                    SecurityContextHolder.clearContext();
                    VaadinSession.getCurrent().close();
                    ui.getSession().close();
                    ui.navigate("login/logout");// to redirect user to the login page
                }));
        add(logout);
    }
    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if (parameter != null && parameter.contentEquals("emptyPwd")) {
            Notification.show("Пожалуйста, установите пароль на аккаунт!",
                    3000, Notification.Position.TOP_CENTER);
        }
    }
}
