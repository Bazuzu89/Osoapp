package service;

import model.User;
import model.repository.TokenRepository;
import model.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import service.dto.UserEntitylAssembler;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
public class UserService implements UserDetailsService, EntityServiceInterface<UserDetails> {

    final UserRepository userRepository;
    final TokenRepository tokenRepository;
    final UserEntitylAssembler assembler;

    final BCryptPasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, TokenRepository tokenRepository, UserEntitylAssembler assembler, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.assembler = assembler;
        this.passwordEncoder = passwordEncoder;
    }




    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByUsername(s);
        return user;
    }


    @Override
    public UserDetails findById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<UserDetails> findAll() {
        return userRepository.findAll().stream()
                .map(user -> (UserDetails) user)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails create(UserDetails entity) {
        return userRepository.save((User) entity);
    }

    @Override
    public void delete(UserDetails entity) {
        userRepository.delete((User) entity);
    }

    @Override
    public UserDetails update(UserDetails entity) {
        return userRepository.save((User) entity);
    }

}
