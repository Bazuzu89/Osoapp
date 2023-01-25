package service.dto;

import controller.UserController;
import model.Token;
import model.User;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserEntitylAssembler {


    public EntityDto<User> toModel(User entity, Token accessToken, Token refreshToken) {
        EntityDto<User> entityDto = new EntityDto<>();
        entityDto.add(linkTo(methodOn(UserController.class).one(entity.getId())).withSelfRel());
        entityDto.add(linkTo(methodOn(UserController.class).all()).withRel("users"));
        entityDto.setEntity(entity);
        Map<String, Token> rels = new HashMap<>();
        rels.put("access_token", accessToken);
        rels.put("refresh_token", refreshToken);
        entityDto.setRels(rels);
        return entityDto;
    }

    //TODO implement this method
    /* @Override
    public CollectionModel<EntityModel<User>> toCollectionModel(Iterable<? extends User> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    } */
}
