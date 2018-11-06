package de.evolvice.cars.payload;

import javax.validation.constraints.*;

import io.swagger.annotations.ApiModelProperty;

public class SignUpRequest {
    @NotBlank
    @Size(min = 4, max = 40)
    @ApiModelProperty(notes = "The name of the user")
    private String name;

    @NotBlank
    @Size(min = 3, max = 15)
    @ApiModelProperty(notes = "The username of the user")
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    @ApiModelProperty(notes = "The emai of the user")
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    @ApiModelProperty(notes = "The password of the user")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
