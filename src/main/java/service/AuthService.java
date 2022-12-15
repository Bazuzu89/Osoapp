package service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import exceptions.*;
import model.Token;
import model.User;
import model.repository.TokenRepository;
import model.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import service.dto.EntityDto;
import service.dto.RequestDto;
import service.dto.UserEntitylAssembler;
import utils.AuthUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

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
        User user = userRepository.findByEmail(s);
        //TODO implement roles into API
        Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public EntityDto<User> register(RequestDto<User> requestDto) throws WrongRequestType, NotValidEmailException, EmailConflictException, DAOException {
        User user = requestDto.getData();
        utils.validateEmail(user.getEmail());
        utils.checkUniqueEmail(user.getEmail());
        Token accessTokenToken;
        Token refreshTokenToken;
        User savedUser;
        String password = utils.encode(user.getPassword());
        user.setPassword(password);
        if ((savedUser = userRepository.save(user)) != null) {
            Algorithm algorithm = Algorithm.HMAC256("dickcheese".getBytes());
            String accessToken = JWT.create()
                    .withSubject(savedUser.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1440*60*100000)) // 24h
                    .withIssuer(requestDto.getIssuer())
                    .sign(algorithm);

            String refreshToken = JWT.create()
                    .withSubject(savedUser.getEmail())
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
    public EntityDto<User> login(RequestDto<User> requestDto) throws NotValidEmailException, DAOException, WrongPasswordException, UserNotFoundException {
        User user = requestDto.getData();
        utils.validateEmail(user.getEmail());
        utils.validatePassword(user);
        Token accessTokenToken;
        Token refreshTokenToken;
        User userFound;
        String password = utils.encode(user.getPassword());
        user.setPassword(password);
        if ((userFound = userRepository.findByEmail(user.getEmail())) != null) {
            Algorithm algorithm = Algorithm.HMAC256("dickcheese".getBytes());
            String accessToken = JWT.create()
                    .withSubject(user.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1440*60*100000)) // 24h
                    .withIssuer(requestDto.getIssuer())
                    .sign(algorithm);

            String refreshToken = JWT.create()
                    .withSubject(user.getEmail())
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
