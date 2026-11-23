package com.elmakaoui.demoescqrsaxon.commands.aggregates;

import com.elmakaoui.demoescqrsaxon.commands.enums.AccountStatus;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    private String accountid;
    private double balance;
    private AccountStatus  status;
}
