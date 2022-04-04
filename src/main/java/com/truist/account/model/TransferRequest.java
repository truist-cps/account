package com.truist.account.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class TransferRequest {
	public TransferRequest(TransferRequest transactionRequest) {
		this.creditAccount = transactionRequest.getCreditAccount();
		this.debitAccount = transactionRequest.getDebitAccount();
		this.payment = transactionRequest.getPayment();
		this.notifyEmail = transactionRequest.getNotifyEmail();
	}
	private Account debitAccount;
    private Account creditAccount;
    private Payment payment;
    private String notifyEmail;
}
