package com.senfiles.version1.Dto;

import java.util.List;

import com.senfiles.version1.Model.RoleModel;

import jakarta.validation.constraints.NotEmpty;

public class Userdto {

    private Long id;

    @NotEmpty(message = "Please enter valid name.")
    private String username;

    @NotEmpty(message = "Please enter valid password.")
    private String password;

    List<RoleModel> roles;

    public Userdto() {
    }

    public Userdto(@NotEmpty(message = "Please enter valid name.") String username,
            @NotEmpty(message = "Please enter valid password.") String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RoleModel> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleModel> roles) {
        this.roles = roles;
    }

}
