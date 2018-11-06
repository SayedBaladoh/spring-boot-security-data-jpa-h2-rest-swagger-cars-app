package de.evolvice.cars.payload;

import io.swagger.annotations.ApiModelProperty;

public class ApiResponse {
	@ApiModelProperty(notes = "Is response success")
    private Boolean success;
	
	@ApiModelProperty(notes = "The response message")
    private String message;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
