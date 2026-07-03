package com.project.Iwrapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Iwrapper.model.AccountUser;
import java.util.List;

public interface AccountRepository extends JpaRepository<AccountUser, Long> {
    List<AccountUser> findByAccountNumber(String accountNumber);

    AccountUser findById(long id);
}
