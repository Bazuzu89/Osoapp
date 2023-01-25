package controller.entity;

import model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import service.EntityServiceInterface;
import service.dto.RequestUserDto;
import service.dto.ResponseDto;

import java.util.List;

@Component
public class UserRESTApi implements EntityRESTApi {
    EntityServiceInterface<User> userService;

    @Override
    public ResponseEntity findById(int id) {
        //TODO wrap it in responseentity
        return null;
        //return userService.findById(id);
    }

    @Override
    public List<User> findAll() {
        //TODO wrap it in responseentity
        return userService.findAll();
    }

    @Override
    public ResponseDto create(RequestUserDto entity) {
        //TODO unwrap requestDto to User
        return null;
        // return userService.create(entity);
    }

    @Override
    public void delete(RequestUserDto entity) {
        //TODO unwrap requestDto to User

        // userService.delete(entity);
    }
}
