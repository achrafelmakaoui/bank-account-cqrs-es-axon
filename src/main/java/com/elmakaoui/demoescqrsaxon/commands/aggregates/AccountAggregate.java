package com.elmakaoui.demoescqrsaxon.commands.aggregates;

import com.elmakaoui.demoescqrsaxon.commands.commands.AddAccountCommand;
import com.elmakaoui.demoescqrsaxon.enums.AccountStatus;
import com.elmakaoui.demoescqrsaxon.events.AccountCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    private String accountid;
    private double balance;
    private AccountStatus  status;

    // pour axon
    public AccountAggregate() {}

    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        log.info("########## AccountCommand Received ###########");
        if (command.getInitialBalance()<=0) throw new IllegalArgumentException("Initial Balance must be positive");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                AccountStatus.CREATED,
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        log.info("########## AccountCreatedEvent Occurred ###########");
        this.accountid = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.status = event.getStatus();
    }
}
