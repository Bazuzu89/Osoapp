package service.dto;

import model.Token;
import org.json.JSONObject;
import org.springframework.hateoas.EntityModel;

import java.util.Map;

public class EntityDto<T> extends EntityModel<T> {
    T entity;
    Map<String, Token> rels;

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Map<String, Token> getRels() {
        return rels;
    }

    public void setRels(Map<String, Token> rels) {
        this.rels = rels;
    }
}
