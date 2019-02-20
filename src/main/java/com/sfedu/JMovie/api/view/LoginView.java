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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Route
public class LoginView extends VerticalLayout
        implements HasUrlParameter<String>, BeforeEnterObserver {
    @Autowired
    private AuthenticationManager authManager;

    public LoginView(){
        FormLayout content = new FormLayout();
        TextField name = new TextField("Login");
        name.setValue("user");
        PasswordField pwd = new PasswordField("Password");
        pwd.setValue("pass");
        Button send = new Button("Sign in", VaadinIcon.CHECK.create());
        send.addClickListener(event -> {
            send.setEnabled(false);
            if (login(name.getValue(), pwd.getValue())) {
                send.getUI().ifPresent(ui -> ui.navigate(MainView.class));
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
                Authentication fullyAuthenticated =
                        new UsernamePasswordAuthenticationToken(username, password);
                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(fullyAuthenticated);
                return true;
            }
        } catch (BadCredentialsException ex) {
            Notification.show("Incorrect username or password",
                    5000, Notification.Position.TOP_CENTER);
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
                    5000, Notification.Position.MIDDLE);
        }
    }
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Anonymous Authentication is enabled in our Spring Security conf
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            //https://vaadin.com/docs/flow/routing/tutorial-routing-lifecycle.html
            beforeEnterEvent.rerouteTo(MainView.class);
        }
    }
}