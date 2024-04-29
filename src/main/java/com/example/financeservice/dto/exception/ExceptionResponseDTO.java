package com.example.financeservice.dto.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponseDTO {

    @JsonProperty("error_msg")
    private String errorMsg;

    @JsonProperty("exception")
    private String exceptionName;

    @JsonProperty("error_status_from_keycloak")
    private HttpStatus errorStatusFromKeycloak;

    @JsonProperty("status")
    private HttpStatus status;
}
