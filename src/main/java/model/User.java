package model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import controller.security.ApplicationUserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static controller.security.ApplicationUserRole.*;

@Schema(description = "User entity")
@Component
@Entity
@Table(name = "users")
@ResponseBody
public class User implements Serializable, UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private int id;

    @Column(name = "email")
    private String username;
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

    private ApplicationUserRole role = ENGINEER;
    @JsonIgnore
    private boolean isEnabled;
    @JsonIgnore
    private boolean isCredentialsNonExpired;
    @JsonIgnore
    private boolean isAccountNonLocked;
    @JsonIgnore
    private boolean isAccountNonExpired;


    public int getId() {
        return id;
    }


    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        //TODO implement Authorities in DB
        return role.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}



