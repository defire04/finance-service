package com.example.financeservice.model.account.piggy;

import com.example.financeservice.model.account.BaseAccount;
import com.example.financeservice.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "piggy_bank")
public class PiggyBank extends BaseAccount {
    private String name;
    private String purpose;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_owner_id")
    private User owner;

}
