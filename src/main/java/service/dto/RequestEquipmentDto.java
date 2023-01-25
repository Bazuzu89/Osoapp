package service.dto;

import model.EquipmentItem;

public class RequestEquipmentDto extends RequestDto {
    EquipmentItem data;

    public EquipmentItem getData() {
        return data;
    }

    public void setData(EquipmentItem data) {
        this.data = data;
    }
}
