package com.picpaydesafio.repositories;

import com.picpaydesafio.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> { // recebe dois tipos, a entidade da tabela e tipo do id

    Optional<User>findUserByDocument(String document); // método de busa de usuários pelo documento

    Optional<User>findUserById(Long id);

}
