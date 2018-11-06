package de.evolvice.cars.payload;

import io.swagger.annotations.ApiModelProperty;

public class UserSummary {
	@ApiModelProperty(notes = "The user id")
    private Long id;
	
	@ApiModelProperty(notes = "The username (user account)")
    private String username;
	
    @ApiModelProperty(notes = "The user name")
    private String name;

    public UserSummary(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
