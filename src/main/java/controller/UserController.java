package controller;

import controller.entity.EntityRESTApi;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import model.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.dto.RequestDto;
import service.dto.ResponseDto;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class UserController {

    final
    EntityRESTApi entityRESTApi;

    public UserController(EntityRESTApi entityRESTApi) {
        this.entityRESTApi = entityRESTApi;
    }

    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> all() {
        return null;
        //entityRESTApi.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity one(@PathVariable int id) {
        return entityRESTApi.findById(id);
    }


}
