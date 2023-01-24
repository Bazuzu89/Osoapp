package service;

import exceptions.*;
import model.User;
import service.dto.EntityDto;
import service.dto.RequestDto;

import java.io.IOException;

public interface AuthServiceInterface {

    EntityDto<User> register(RequestDto requestDto) throws WrongRequestType, NotValidEmailException, EmailConflictException, DAOException, IOException, IOException;
    EntityDto<User> login(RequestDto entity) throws NotValidEmailException, DAOException, WrongPasswordException, UserNotFoundException;
    void logout(String accessToken) throws NotAuthorizedException;
}
