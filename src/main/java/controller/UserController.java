package controller;

import controller.entity.EntityRESTApi;
import model.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
