package com.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.common.ApiResponse;
import com.user.entity.ExchangeRateEntity;
import com.user.service.IExchangeRateService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/exchangeRate")
public class ExchangeRateRestController {
	
	@Autowired
	@Qualifier("exchangeRateService")
	private IExchangeRateService exchangeRateService;

	@PostMapping
	public ResponseEntity<ApiResponse<ExchangeRateEntity>> createExchangeRate(
			@RequestBody ExchangeRateEntity exchangeRate) {
		ExchangeRateEntity savedRate = exchangeRateService.addExchangeRate(exchangeRate);

		ApiResponse<ExchangeRateEntity> response = new ApiResponse<>(HttpStatus.CREATED.value(),
				"Exchange rate created successfully.", savedRate);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ExchangeRateEntity[] getAllUsers() {
		return exchangeRateService.getExchangeRateAry();
	}
	
	@PutMapping
	public ExchangeRateEntity updateExchangeRate(@RequestBody ExchangeRateEntity exchangeRate) {
		return exchangeRateService.updateExchangeRate(exchangeRate);
	}

	@DeleteMapping
	public void deleteExchangeRate(@RequestBody ExchangeRateEntity exchangeRate) {
		exchangeRateService.deleteExchangeRate(exchangeRate);
	}

	@GetMapping("/reflashExchangeRate")
	public Mono<Void> reflashExchangeRate() {
		return exchangeRateService.callApiUpdateExchangeRate();
	}
}