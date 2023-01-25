package controller.auth;

import exceptions.DAOException;
import exceptions.EmailConflictException;
import org.springframework.http.ResponseEntity;
import service.dto.RequestUserDto;

import java.io.IOException;

public interface AuthRESTApiInterface {

    ResponseEntity logout(String accessToken);
    ResponseEntity register(RequestUserDto userRequestDto) throws IOException;
    ResponseEntity login(RequestUserDto userRequestDto) throws DAOException, EmailConflictException;
}
