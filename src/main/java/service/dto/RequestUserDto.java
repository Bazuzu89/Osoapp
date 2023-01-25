package service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import model.User;
import org.springframework.stereotype.Component;

@Component
public class RequestUserDto extends RequestDto {
    User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

}
