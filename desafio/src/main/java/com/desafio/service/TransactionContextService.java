package com.desafio.service;

import com.desafio.domain.ResponseDTO;
import com.desafio.domain.ResponseWsDTO;

public interface TransactionContextService {

	public ResponseDTO createTransactionContext(ResponseWsDTO request);

	public String callPeriodos();

	public ResponseWsDTO parseString(String responseWs);
}
