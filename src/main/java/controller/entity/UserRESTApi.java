package controller.entity;

import model.User;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import service.EntityServiceInterface;
import service.dto.EntityDto;
import service.dto.RequestUserDto;
import service.dto.ResponseDto;
import service.dto.UserEntitylAssembler;
import utils.RequestType;

import java.util.List;

@Component
public class UserRESTApi implements EntityRESTApi {
    EntityServiceInterface<UserDetails> userService;
    private UserEntitylAssembler assembler;

    public UserRESTApi(EntityServiceInterface<UserDetails> userService, UserEntitylAssembler assembler) {
        this.userService = userService;
        this.assembler = assembler;
    }

    @Override
    public ResponseEntity findById(int id) {
        //TODO wrap it in responseentity
        EntityDto<User> user = assembler.toModel((User) userService.findById(id));
        ResponseDto<User> respDto = new ResponseDto();
        respDto.setData(user);
        respDto.setType(RequestType.USERS.name());
        ResponseEntity responseEntity = ResponseEntity.created(user.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(respDto);
        return responseEntity;
    }

    @Override
    public List<User> findAll() {
        //TODO wrap it in responseentity
//        return userService.findAll();
        return null;
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
