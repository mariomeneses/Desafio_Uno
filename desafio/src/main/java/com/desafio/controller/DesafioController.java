package com.desafio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.domain.ResponseDTO;
import com.desafio.domain.ResponseWsDTO;
import com.desafio.service.TransactionContextService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(path = "/api")
@Api(value = "Desafio nivel 3 ")
public class DesafioController {
	
	  @Autowired
	  private TransactionContextService transactionContextService;
	
	  @ApiOperation(value = "Generate Date")
	  @GetMapping(path = "/desafio", produces = "application/json")
	  public ResponseEntity<ResponseDTO> createTransactionContext() {
		  
		  String responseWs = transactionContextService.callPeriodos();
		  
		  ResponseWsDTO response = transactionContextService.parseString(responseWs);
		  
		  log.info("RequestDTO: "+ responseWs);
		  
		  ResponseDTO responseFinal = transactionContextService.createTransactionContext(response);
		  log.info("ResponseDTO: "+ responseFinal);
	    return new ResponseEntity<>(responseFinal,
	        HttpStatus.CREATED);
	  }

}
