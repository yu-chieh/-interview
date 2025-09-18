package com.user.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.user.entity.ExchangeRateEntity;

import reactor.core.publisher.Flux;

@Service
public class CoindeskApiClient implements IExchangeRateApiService {
	private final WebClient webClient;

	@Value("${api.coindesk.url}")
	private String apiUrl;

	public CoindeskApiClient(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.build();
	}

	@Override
	public Flux<ExchangeRateEntity> getExchangeRatesAndUpdateTime() {
		return webClient.get().uri(apiUrl).retrieve().bodyToFlux(ExchangeRateEntity.class).map(rate -> {
			rate.setUpdateTime(LocalDateTime.now());
			return rate;
		});
	}


}
