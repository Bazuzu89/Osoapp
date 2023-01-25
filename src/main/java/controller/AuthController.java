package controller;

import controller.auth.AuthRESTApiInterface;
import exceptions.DAOException;
import exceptions.EmailConflictException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.dto.RequestUserDto;
import service.dto.ResponseDto;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class AuthController {
    final
    AuthRESTApiInterface authRESTApi;
    public AuthController(AuthRESTApiInterface authRESTApi) {
        this.authRESTApi = authRESTApi;
    }

    @PostMapping(value = "/auth/registration", consumes = {"application/json"}, produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    ResponseEntity<?> register(@RequestBody RequestUserDto user, HttpServletRequest request) throws IOException {
        user.setIssuer(request.getRequestURL().toString());
        return authRESTApi.register(user);
    }

    @PostMapping("/auth/logout")
    @ApiResponse(responseCode = "200")
    void logout(@RequestHeader(name = "Authorization") String accessToken) throws IOException {
        authRESTApi.logout(accessToken);
    }

    @PostMapping(value = "/auth/login", consumes = {"application/json"}, produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    ResponseEntity<?> login(@RequestBody RequestUserDto user) throws IOException, DAOException, EmailConflictException {
        return authRESTApi.login(user);
    }
}
