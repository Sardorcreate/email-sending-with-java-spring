package com.company.dto;

import com.company.enums.Role;
import com.company.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Integer id;
    private String name;
    private String surname;
    private Role role;
    private Status status;
    private String login;
    private String password;
    private String email;
    private String jwt;
    private Boolean visible;
}
