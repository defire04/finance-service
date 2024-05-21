package com.example.financeservice.dto.auth;

import com.example.financeservice.dto.account.balance.BalanceDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "User Information after Sign-In")
public class SignInUserDTO {

    @Schema(description = "User ID", example = "123")
    @JsonProperty("id")
    private Long id;

    @Schema(description = "Username", example = "jon123")
    @JsonProperty("username")
    private String username;

    @Schema(description = "Timestamp of user creation", example = "1621247891000")
    @JsonProperty("created_timestamp")
    private Long createdTimestamp;

    @Schema(description = "First name", example = "John")
    @JsonProperty("first_name")
    private String firstName;

    @Schema(description = "Last name", example = "Doe")
    @JsonProperty("last_name")
    private String lastName;

    @Schema(description = "Email address", example = "example@example.com")
    @JsonProperty("email")
    private String email;

    @Schema(description = "Currency", example = "USD")
    @JsonProperty("currency")
    private String currency;

    @Schema(description = "Balance Information")
    @JsonProperty("balance")
    private BalanceDTO balance;
}