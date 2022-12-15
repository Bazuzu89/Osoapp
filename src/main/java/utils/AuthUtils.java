package utils;

import exceptions.EmailConflictException;
import exceptions.NotValidEmailException;
import exceptions.WrongPasswordException;
import model.User;
import model.repository.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class AuthUtils {

    private final UserRepository userRepository;

    final BCryptPasswordEncoder passwordEncoder;

    public AuthUtils(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void checkUniqueEmail(String email) throws EmailConflictException {
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            if (user.getEmail().equals(email)) {
                throw new EmailConflictException();
            }
        }
    }

    public static void validateEmail(String email) throws NotValidEmailException {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new NotValidEmailException();
        }
    }

    public void validatePassword(User user) throws WrongPasswordException {
        if (!passwordEncoder.matches(user.getPassword(), userRepository.findByEmail(user.getEmail()).getPassword())) {
            throw new WrongPasswordException();
        }
    }

    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
