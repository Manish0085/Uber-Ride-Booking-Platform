package com.uber.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        description = "Standard error response returned when an API request fails"
)
public class ErrorResponseDto {

    @Schema(
            description = "The API endpoint path where the error occurred",
            example = "/api/v1/users/register"
    )
    private String apiPath;

    @Schema(
            description = "The HTTP status code of the error",
            example = "400"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Detailed error message explaining the reason for failure",
            example = "Email already registered"
    )
    private String errorMsg;

    @Schema(
            description = "Timestamp of when the error occurred",
            example = "2025-09-03T12:45:30"
    )
    private LocalDateTime errorTime;

    public ErrorResponseDto(String apiPath, HttpStatus errorCode, String errorMsg, LocalDateTime errorTime) {
        this.apiPath = apiPath;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorTime = errorTime;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(HttpStatus errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public LocalDateTime getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(LocalDateTime errorTime) {
        this.errorTime = errorTime;
    }
}
