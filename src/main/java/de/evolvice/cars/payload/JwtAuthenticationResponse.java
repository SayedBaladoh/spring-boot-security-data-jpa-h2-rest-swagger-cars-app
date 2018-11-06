package de.evolvice.cars.payload;

import io.swagger.annotations.ApiModelProperty;

public class JwtAuthenticationResponse {
	
	@ApiModelProperty(notes = "The access token")
    private String accessToken;
	
	@ApiModelProperty(notes = "The token type")
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
