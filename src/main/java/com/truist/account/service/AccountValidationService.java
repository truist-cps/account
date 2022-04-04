package com.truist.account.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truist.account.model.Account;
import com.truist.account.model.NoSuchAccountException;
import com.truist.account.repository.AccountRepository;


@Service
@Transactional
public class AccountValidationService {

	@Autowired
    private final AccountRepository accountRepository;
	
    public AccountValidationService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

	public Account doValidate(Account account) throws NoSuchAccountException {
		Optional<Account> optionalBankAccount = accountRepository.findById(account.getAccountNumber());
        if(!optionalBankAccount.isPresent()){
            throw new NoSuchAccountException(": "+ account.getAccountNumber());
        }
		return optionalBankAccount.get();
	
	}
}
