package controller.entity;

import model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import service.dto.RequestDto;
import service.dto.ResponseDto;

import java.util.List;

@Component
public interface EntityRESTApi {

    ResponseEntity findById(int id);
    List<User> findAll();
    ResponseDto<User> create(RequestDto entity);
    void delete(RequestDto entity);

}
