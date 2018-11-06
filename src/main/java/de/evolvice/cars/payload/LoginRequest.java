package de.evolvice.cars.payload;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class LoginRequest {
	@NotBlank
	@ApiModelProperty(
			notes = "The username or user email address")
	private String usernameOrEmail;

	@NotBlank
	@ApiModelProperty(
			notes = "The user password")
	private String password;

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
