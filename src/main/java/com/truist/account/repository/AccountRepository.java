package com.truist.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truist.account.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

}
