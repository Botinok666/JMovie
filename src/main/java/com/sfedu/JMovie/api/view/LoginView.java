package com.sfedu.JMovie.api.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Route
public class LoginView extends VerticalLayout
        implements HasUrlParameter<String>, BeforeEnterObserver {
    @Autowired
    private AuthenticationManager authManager;

    public LoginView(){
        FormLayout content = new FormLayout();
        TextField name = new TextField("Login");
        name.setValue("admin");
        PasswordField pwd = new PasswordField("Password");
        pwd.setValue("pass");
        Button send = new Button("Sign in", VaadinIcon.SIGN_IN.create());
        send.addClickListener(event -> {
            send.setEnabled(false);
            if (login(name.getValue(), pwd.getValue())) {
                send.getUI().ifPresent(ui -> {
                    if (pwd.getValue().isEmpty())
                        ui.navigate("account/emptyPwd");
                    else
                        ui.navigate(MainView.class);
                });
            } else {
                send.setEnabled(true);
            }
        });
        content.add(name, pwd, send);
        content.setSizeUndefined();
        add(content);
        setHorizontalComponentAlignment(Alignment.CENTER, content);
    }

    private boolean login(String username, String password) {
        try {
            Authentication authenticate = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            if (authenticate.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authenticate);
                return true;
            }
        } catch (BadCredentialsException ex) {
            Notification.show("Incorrect username or password",
                    3000, Notification.Position.TOP_CENTER);
        } catch (Exception ex) {
            Notification.show("An unexpected error occurred",
                    3000, Notification.Position.MIDDLE);
        }
        return false;
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if (parameter != null && parameter.contentEquals("logout")) {
            Notification.show("You have been successfully logged out",
                    3000, Notification.Position.MIDDLE);
        }
    }
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        //Если на страницу login пытается зайти авторизованный пользователь
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken)) {
            //Перенаправим его на главную
            beforeEnterEvent.rerouteTo(MainView.class);
        }
    }
}