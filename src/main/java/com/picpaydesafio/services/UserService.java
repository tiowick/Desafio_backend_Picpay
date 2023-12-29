package com.picpaydesafio.services;

import com.picpaydesafio.domain.user.User;
import com.picpaydesafio.domain.user.UserType;
import com.picpaydesafio.dtos.UserDTO;
import com.picpaydesafio.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validatedTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.COMMON){
            throw new Exception("Usuário do tipo lojista não autorizado a realizar transações.");

        }
        if(sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo insuficiente.");
        }
    }

    public User createUser(UserDTO data){
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }
    public List<User> getAllUsers(){
        return this.repository.findAll();
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado."));
    }

    // fazendo a persistencia do usuário no banco de dados
    public void saveUser(User user){
        this.repository.save(user);
    }


}
