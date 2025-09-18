package com.user.service;


import com.user.entity.ExchangeRateEntity;

import reactor.core.publisher.Mono;


public interface IExchangeRateService {	
	
	public ExchangeRateEntity addExchangeRate(ExchangeRateEntity exchangeRate);
	public ExchangeRateEntity updateExchangeRate(ExchangeRateEntity exchangeRate);
	public void deleteExchangeRate(ExchangeRateEntity exchangeRate);
	
	public ExchangeRateEntity[] getExchangeRateAry();
	
	public Mono<Void> callApiUpdateExchangeRate();
}
