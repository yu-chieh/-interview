package com.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user.entity.ExchangeRateEntity;
import com.user.exception.DataNotFindException;
import com.user.exception.DuplicateEntryException;
import com.user.repository.ExchangeRateRepository;

import reactor.core.publisher.Mono;

@Service
@Transactional(rollbackFor=Exception.class)
public class ExchangeRateService implements IExchangeRateService {
	@Autowired
	private ExchangeRateRepository exchangeRateRepository;

	@Autowired
	@Qualifier("coindeskApiClient")
	private IExchangeRateApiService apiService;

	@Override
	public ExchangeRateEntity addExchangeRate(ExchangeRateEntity exchangeRate) {
		if (exchangeRateRepository.existsById(exchangeRate.getCurrencyCode())) {
			throw new DuplicateEntryException(
					"Exchange rate with currency code '" + exchangeRate.getCurrencyCode() + "' already exists.");
		}
		ExchangeRateEntity addData = exchangeRateRepository.save(exchangeRate);

		return addData;
	}

	@Override
	public ExchangeRateEntity updateExchangeRate(ExchangeRateEntity exchangeRate) {
		if (!exchangeRateRepository.existsById(exchangeRate.getCurrencyCode())) {
			throw new DataNotFindException("Exchange rate not found for update.");
		}
		return exchangeRateRepository.save(exchangeRate);
	}

	@Override
	public void deleteExchangeRate(ExchangeRateEntity exchangeRate) {
		if (!exchangeRateRepository.existsById(exchangeRate.getCurrencyCode())) {
			throw new DataNotFindException("Exchange rate not found for deletion.");
		}

		exchangeRateRepository.deleteById(exchangeRate.getCurrencyCode());
	}

	@Override
	public ExchangeRateEntity[] getExchangeRateAry() {
		List<ExchangeRateEntity> dataList = null;
		try {
			dataList = exchangeRateRepository.findAll();
			if (dataList.isEmpty()) {
				throw new DataNotFindException("No users found.");
			}

			return dataList.toArray(new ExchangeRateEntity[dataList.size()]);
		} finally {
			if (dataList == null) {
			} else {
				dataList.clear();
			}

		}
	}

	@Override
	public Mono<Void> callApiUpdateExchangeRate() {
		return apiService.getExchangeRatesAndUpdateTime().collectList().flatMap(data -> {
			 return Mono.fromCallable(() -> exchangeRateRepository.saveAll(data));
        }).then().onErrorMap(throwable -> {
			return new Exception("Failed to update exchange rates.", throwable);
		});
	}

}
