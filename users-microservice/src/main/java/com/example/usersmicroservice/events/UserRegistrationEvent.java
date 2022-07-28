package com.example.usersmicroservice.events;

import com.example.usersmicroservice.models.CustomUser;
import lombok.Getter;

import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegistrationEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private final CustomUser user;
   public  UserRegistrationEvent(CustomUser user) {
        super(user);
       this.user = user;
   }
}
