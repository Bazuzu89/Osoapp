package model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
@Component
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "usertoken")
    private String userToken;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "is_active")
    private boolean isActive;

    public Token() {
    }

    public Token(String userToken, int usersId) {
        this.userId = usersId;
        this.userToken = userToken;
    }

    public Token(String userToken, int usersId, boolean isActive) {
        this.userToken = userToken;
        this.userId = usersId;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getUserToken() {
        return userToken;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}
