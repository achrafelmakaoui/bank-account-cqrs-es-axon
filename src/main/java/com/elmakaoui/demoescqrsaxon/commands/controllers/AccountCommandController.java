package com.elmakaoui.demoescqrsaxon.commands.controllers;

import com.elmakaoui.demoescqrsaxon.commands.commands.AddAccountCommand;
import com.elmakaoui.demoescqrsaxon.commands.dtos.AddNewAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandController {
    private CommandGateway commandGateway;

    public AccountCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/add")
    public CompletableFuture<String> addNewAccount(@RequestBody AddNewAccountRequestDTO request) {
        CompletableFuture<String> response = commandGateway.send(new AddAccountCommand(
                UUID.randomUUID().toString(),
                request.initialBalance(),
                request.currency()
        ));
        return response;
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception) {
        return exception.getMessage();
    }
}
