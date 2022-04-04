package com.truist.account.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.truist.account.model.NoSuchAccountException;
import com.truist.account.model.ResponseModel;
import com.truist.account.model.TransferRequest;
import com.truist.account.service.AccountValidationService;

@RestController
@RequestMapping("/api/v1/account")
public class MainController {
	
	private final AccountValidationService accountValidationService;
	private final Outbound outbound;

	public MainController(AccountValidationService accountValidationService, Outbound outbound) {
		super();
		this.accountValidationService = accountValidationService;
		this.outbound = outbound;
	}
	
    @PostMapping("/fund-transfer")
    private ResponseEntity<?> initTransfer(@RequestBody TransferRequest transactionRequest) {
    	ResponseModel responseModel=new ResponseModel();
		try {
			accountValidationService.doValidate(transactionRequest.getDebitAccount());
		}catch (NoSuchAccountException e) {
			throw new ResponseStatusException(
			          HttpStatus.NOT_FOUND, "Account not found",e);
		}catch (Exception e) {
			throw new ResponseStatusException(
			          HttpStatus.INTERNAL_SERVER_ERROR, "Something happend!!..",e);
		}
		
		try {
			accountValidationService.doValidate(transactionRequest.getCreditAccount());
			responseModel.setData(transactionRequest);
	    	responseModel.setMessage("Success");
		}catch (NoSuchAccountException e) {
			throw new ResponseStatusException(
			          HttpStatus.NOT_FOUND, "Account not found",e);
		}catch (Exception e) {
			throw new ResponseStatusException(
			          HttpStatus.INTERNAL_SERVER_ERROR, "Something happend!!..",e);
		}
		this.outbound.sendMessage(transactionRequest);
		
		return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
    }
}
