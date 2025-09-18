package com.user.service;

import com.user.entity.ExchangeRateEntity;

import reactor.core.publisher.Flux;

public interface IExchangeRateApiService {
	Flux<ExchangeRateEntity> getExchangeRatesAndUpdateTime();
}
