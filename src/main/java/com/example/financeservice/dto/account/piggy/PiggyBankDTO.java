package com.example.financeservice.dto.account.piggy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class PiggyBankDTO {
    @JsonView(PiggyBankUpdateView.class)
    @JsonProperty("id")
    @Schema(description = "The ID of the piggy bank", example = "1")
    private Long id;

    @JsonProperty("name")
    @JsonView(PiggyBankCreateView.class)
    @Schema(description = "The name of the piggy bank", example = "Vacation Fund")
    private String name;

    @JsonProperty("purpose")
    @JsonView(PiggyBankCreateView.class)

    @Schema(description = "The purpose of the piggy bank", example = "Saving for a vacation")
    private String purpose;

    @JsonProperty("amount")
    @JsonView(PiggyBankAll.class)
    @Schema(description = "The current amount in the piggy bank", example = "100.50")
    private BigDecimal amount;

    @JsonProperty("owner_id")
    @JsonIgnore
    @Schema(hidden = true)
    private Long ownerId;

    public interface PiggyBankCreateView {
    }

    public interface PiggyBankUpdateView extends  PiggyBankCreateView{
    }

    public interface PiggyBankAll extends PiggyBankUpdateView {
    }
}
