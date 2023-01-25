package controller.auth;

import exceptions.*;
import model.User;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import service.AuthServiceInterface;
import service.dto.EntityDto;
import service.dto.RequestDto;
import service.dto.ResponseDto;
import utils.RequestType;

import java.io.IOException;

@Component
public class AuthRESTApi implements AuthRESTApiInterface {

    AuthServiceInterface authService;

    public AuthRESTApi(AuthServiceInterface authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity logout(String accessToken) {
        try {
            authService.logout(accessToken);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON).body(null);
        } catch (NotAuthorizedException e) {
            ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON).body(null);
        }
        return null;
    }

    @Override
    public ResponseEntity register(RequestDto requestDto) throws IOException {
        try { //TODO refactor local variables names
            String type = requestDto.getType();
            if (!type.equals(RequestType.AUTH.toString())) {
                ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Wrong request type\"}");
            }
            EntityDto<User> entityDto =  authService.register(requestDto);
            ResponseDto<User> respDto = new ResponseDto();
            respDto.setData(entityDto);
            respDto.setType(type);
            return ResponseEntity.created(entityDto.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(respDto);
        } catch (DAOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Internal server error\"}");
        } catch (EmailConflictException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Email is already registered!\"}");
        } catch (NotValidEmailException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Not a valid email!\"}");
        } catch (WrongRequestType e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity login(RequestDto requestDto) throws DAOException {
        try {
            //TODO wrap into ResponseEntity
            String type = requestDto.getType();
            if (!type.equals(RequestType.AUTH.toString())) {
                ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"message\": \"Wrong request type\"}");
            }
            EntityDto<User> entityDto = authService.login(requestDto);
            ResponseDto<User> respDto = new ResponseDto();
            respDto.setData(entityDto);
            respDto.setType(type);
            return ResponseEntity.created(entityDto.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(respDto);
        } catch (NotValidEmailException e) {
            throw new RuntimeException(e);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (WrongPasswordException e) {
            throw new RuntimeException(e);
        }
    }
}
