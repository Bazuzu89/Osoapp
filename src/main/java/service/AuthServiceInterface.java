package service;

import exceptions.*;
import model.User;
import service.dto.EntityDto;
import service.dto.RequestUserDto;

import java.io.IOException;

public interface AuthServiceInterface {

    EntityDto<User> register(RequestUserDto requestDto) throws WrongRequestType, NotValidEmailException, EmailConflictException, DAOException, IOException, IOException;
    EntityDto<User> login(RequestUserDto entity) throws NotValidEmailException, DAOException, WrongPasswordException, UserNotFoundException;
    void logout(String accessToken) throws NotAuthorizedException;
}
