package controller.auth;

import exceptions.DAOException;
import exceptions.EmailConflictException;
import exceptions.NotAuthorizedException;
import model.User;
import org.springframework.http.ResponseEntity;
import service.dto.RequestDto;

import java.io.IOException;

public interface AuthRESTApiInterface {

    ResponseEntity logout(String accessToken);
    ResponseEntity register(RequestDto userRequestDto) throws IOException;
    ResponseEntity login(RequestDto userRequestDto) throws DAOException, EmailConflictException;
}
