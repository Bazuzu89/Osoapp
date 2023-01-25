package controller.entity;

import model.EquipmentItem;
import model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import service.EntityServiceInterface;
import service.dto.RequestUserDto;
import service.dto.ResponseDto;

import java.util.List;

@Component
public class EquipmentItemRESTApi {

    EntityServiceInterface<EquipmentItem> equipmentItemEntityService;

    public ResponseEntity findById(int id) {
        //TODO wrap it in responseentity
        return null;
        // return equipmentItemEntityService.findById(id);
    }


    public List<User> findAll() {
        //TODO wrap it in responseentity
        return null;
//        return equipmentItemEntityService.findAll();
    }


    public ResponseDto create(RequestUserDto entity) {
        //TODO unwrap requestDto to User
        return null;
        //return equipmentItemEntityService.create(entity);
    }


    public void delete(RequestUserDto entity) {
        //TODO unwrap requestDto to User

        //equipmentItemEntityService.delete(entity);
    }
}
