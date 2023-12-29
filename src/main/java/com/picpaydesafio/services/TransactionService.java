package com.picpaydesafio.services;

import com.picpaydesafio.domain.transaction.Transaction;
import com.picpaydesafio.domain.user.User;
import com.picpaydesafio.dtos.TransactionDTO;
import com.picpaydesafio.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository; // simplica a chamada http

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransacion(TransactionDTO trasaction) throws Exception {
        User sender = this.userService.findUserById(trasaction.senderId());
        User receiver = this.userService.findUserById(trasaction.receiverId());
        userService.validatedTransaction(sender, trasaction.value());

         Boolean isAuthorized = this.authorizeTransaction(sender, trasaction.value());
        if(!isAuthorized){
            throw  new Exception("Transação não autorizada.");
        }
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(trasaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimesTamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(trasaction.value()));
        receiver.setBalance(receiver.getBalance().add(trasaction.value()));

        this.repository.save(newTransaction); // this para pegar a instância da classe
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);
        this.notificationService.sendNotification(sender, "Transação realizada com sucesso!");
        this.notificationService.sendNotification(receiver, "Transação recebida com sucesso!");

        return newTransaction;
    }

    public Boolean authorizeTransaction(User sender, BigDecimal value){
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);


        if(authorizationResponse.getStatusCode() == HttpStatus.OK) {
            String message = (String) authorizationResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message); // comparando a string Autorizado com o que vinher na messagem
        }else return false;
    }





}
