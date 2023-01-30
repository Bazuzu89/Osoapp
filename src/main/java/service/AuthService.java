package service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import exceptions.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import model.Token;
import model.User;
import model.repository.TokenRepository;
import model.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import service.dto.EntityDto;
import service.dto.RequestUserDto;
import service.dto.UserEntitylAssembler;
import utils.AuthUtils;

import java.io.IOException;
import java.security.Key;
import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class AuthService implements AuthServiceInterface, UserDetailsService {

    final UserRepository userRepository;
    final TokenRepository tokenRepository;
    final UserEntitylAssembler assembler;
    final BCryptPasswordEncoder passwordEncoder;
    final AuthUtils utils;

    public AuthService(UserRepository userRepository,
                       TokenRepository tokenRepository,
                       UserEntitylAssembler assembler,
                       BCryptPasswordEncoder passwordEncoder,
                       AuthUtils utils) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.assembler = assembler;
        this.passwordEncoder = passwordEncoder;
        this.utils = utils;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }

    @Override
    public EntityDto<User> register(RequestUserDto requestDto) throws IOException, WrongRequestType, NotValidEmailException, EmailConflictException, DAOException {
        User user = requestDto.getData();
        utils.validateEmail(user.getUsername());
        utils.checkUniqueEmail(user.getUsername());
        Token accessTokenToken;
        Token refreshTokenToken;
        User savedUser;
        String password = utils.encode(user.getPassword());
        user.setPassword(password);
        if ((savedUser = userRepository.save(user)) != null) {
            Algorithm algorithm = Algorithm.HMAC256("dickcheesedickcheesedickcheesedickcheesedickcheesedickcheese".getBytes());


            String accessToken = JWT.create()
                    .withSubject(savedUser.getUsername())
                    .withClaim("authorities", savedUser.getAuthorities().stream()
                            .map(authority -> authority.getAuthority())
                            .collect(Collectors.toList()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1440*60*100000)) // 24h
                    .withIssuer(requestDto.getIssuer())
                    .sign(algorithm);

            String refreshToken = JWT.create()
                    .withSubject(savedUser.getUsername())
                    .withClaim("authorities", savedUser.getAuthorities().stream()
                            .map(authority -> authority.getAuthority())
                            .collect(Collectors.toList()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + 20160*60*100000)) // 2 weeks
                    .withIssuer(requestDto.getIssuer())
                    .sign(algorithm);

            int userId = savedUser.getId();
            accessTokenToken = new Token("Bearer " + accessToken, userId, true);
            refreshTokenToken = new Token("Bearer " + refreshToken, userId, true);
            if (tokenRepository.save(accessTokenToken) == null || tokenRepository.save(refreshTokenToken) == null) {
                userRepository.delete(user);
                tokenRepository.delete(accessTokenToken);
                tokenRepository.delete(refreshTokenToken);
                throw new DAOException();
            }
        } else {
            throw new DAOException();
        }
        EntityDto<User> entityDto = assembler.toModel(savedUser, accessTokenToken, refreshTokenToken);
        return entityDto;
    }

    @Override
    public EntityDto<User> login(RequestUserDto requestDto) throws NotValidEmailException, DAOException, WrongPasswordException, UserNotFoundException {
        User user = requestDto.getData();
        utils.validateEmail(user.getUsername());
        utils.validatePassword(user);
        Token accessTokenToken;
        Token refreshTokenToken;
        User userFound;
        String password = utils.encode(user.getPassword());
        user.setPassword(password);
        if ((userFound = (User) userRepository.findByUsername(user.getUsername())) != null) {
            String key = "dickcheesedickcheesedickcheesedickcheesedickcheesedickcheese";
            Algorithm algorithm = Algorithm.HMAC256(key.getBytes());


            String accessToken = JWT.create()
                    .withSubject(user.getUsername())
                    .withClaim("authorities", userFound.getAuthorities().stream()
                            .map(authority -> authority.getAuthority())
                            .collect(Collectors.toList()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1440*60*100000)) // 24h
                    .withIssuer(requestDto.getIssuer())
                    .sign(algorithm);

            String refreshToken = JWT.create()
                    .withSubject(user.getUsername())
                    .withClaim("authorities", userFound.getAuthorities().stream()
                            .map(authority -> authority.getAuthority())
                            .collect(Collectors.toList()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + 20160*60*100000)) // 2 weeks
                    .withIssuer(requestDto.getIssuer())
                    .sign(algorithm);

            int userId = userFound.getId();
            accessTokenToken = new Token("Bearer " + accessToken, userId, true);
            refreshTokenToken = new Token("Bearer " + refreshToken, userId, true);
            if (tokenRepository.save(accessTokenToken) == null || tokenRepository.save(refreshTokenToken) == null) {
                tokenRepository.delete(accessTokenToken);
                tokenRepository.delete(refreshTokenToken);
                throw new DAOException();
            }
        } else {
            throw new UserNotFoundException();
        }
        EntityDto<User> entityDto = assembler.toModel(user, accessTokenToken, refreshTokenToken);
        return entityDto;
    }

    @Override
    public void logout(String accessToken) throws NotAuthorizedException {
        Token token = tokenRepository.findTokenByUserToken(accessToken);
        token.setIsActive(false);
        tokenRepository.save(token);
    }
}
