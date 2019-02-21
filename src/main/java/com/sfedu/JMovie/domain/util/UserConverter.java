package com.sfedu.JMovie.domain.util;

import com.sfedu.JMovie.api.data.UserData;
import com.sfedu.JMovie.db.entity.User;
import com.sfedu.JMovie.domain.model.UserDomain;

import java.util.List;
import java.util.stream.Collectors;

public final class UserConverter {
    private UserConverter(){}
    public static UserDomain convertToUserDomain(User user){
        return new UserDomain(user.getId(), user.getName(), user.getPwd());
    }
    public static List<UserDomain> convertToUserDomainList(List<User> users){
        return users.stream()
                .map(UserConverter::convertToUserDomain)
                .collect(Collectors.toList());
    }
    public static UserData convertToUserDTO(UserDomain user){
        return new UserData(user.getId(), user.getName(), user.getPwd());
    }
    public static List<UserData> convertToUserListDTO(List<UserDomain> users){
        return users.stream()
                .map(UserConverter::convertToUserDTO)
                .collect(Collectors.toList());
    }
}
