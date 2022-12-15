package service;

import exceptions.EmailConflictException;
import exceptions.NotValidEmailException;
import exceptions.WrongPasswordException;
import model.User;
import model.repository.TokenRepository;
import model.repository.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import service.dto.UserEntitylAssembler;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService, EntityServiceInterface<User> {

    final UserRepository userRepository;
    final TokenRepository tokenRepository;
    final UserEntitylAssembler assembler;

    final BCryptPasswordEncoder passwordEncoder;


    protected UserService(UserRepository userRepository, TokenRepository tokenRepository, UserEntitylAssembler assembler, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.assembler = assembler;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);
        //TODO implement roles into API
        Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public CollectionModel<EntityModel<User>> findAllUsers() {
        /* List<EntityModel<User>> users = userDAO.findAll().stream()
                .map(assembler :: toModel)
                .collect(Collectors.toList()); */
        return null;
        // CollectionModel.of(users,
               // linkTo(methodOn(UserDAO.class).findAll()).withRel("users"));
    }


    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }


}
