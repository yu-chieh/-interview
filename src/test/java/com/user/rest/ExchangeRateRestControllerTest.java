package com.user.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.entity.ExchangeRateEntity;
import com.user.repository.ExchangeRateRepository;
import com.user.service.IExchangeRateApiService;

import reactor.core.publisher.Flux;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class ExchangeRateRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IExchangeRateApiService apiService;

	// 只需要一個 @MockBean 來模擬資料庫
	@MockBean
	private ExchangeRateRepository exchangeRateRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testAddExchangeRate() throws Exception {
		String testCurrencyCode = "test_TWD";
		ExchangeRateEntity newRate = new ExchangeRateEntity(testCurrencyCode, "新台幣", 30.0, LocalDateTime.now());
		String newRateJson = objectMapper.writeValueAsString(newRate);

		// 模擬行為：existsById() 回傳 false，save() 回傳新建立的物件
		when(exchangeRateRepository.existsById(testCurrencyCode)).thenReturn(false);
		when(exchangeRateRepository.save(any(ExchangeRateEntity.class))).thenReturn(newRate);

		mockMvc.perform(post("/api/exchangeRate").contentType(MediaType.APPLICATION_JSON).content(newRateJson))
				.andExpect(status().isCreated());
	}

	@Test
	void testGetExchangeRate() throws Exception {
		String testCurrencyCode = "test_TWD";
		ExchangeRateEntity newRate = new ExchangeRateEntity(testCurrencyCode, "新台幣", 30.0, LocalDateTime.now());

		// 模擬行為：findAll() 回傳一個包含測試資料的可變列表
		List<ExchangeRateEntity> mutableList = new ArrayList<>(Arrays.asList(newRate));
		when(exchangeRateRepository.findAll()).thenReturn(mutableList);

		mockMvc.perform(get("/api/exchangeRate")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.code").value(200))
				.andExpect(jsonPath("$.data[0].currency_code").value(testCurrencyCode));
	}

	@Test
	void testUpdateExchangeRate() throws Exception {
		String testCurrencyCode = "test_TWD";
		ExchangeRateEntity updatedRate = new ExchangeRateEntity(testCurrencyCode, "新台幣 (更新)", 31.0,
				LocalDateTime.now());
		String updatedRateJson = objectMapper.writeValueAsString(updatedRate);

		// 模擬existsById() 回傳 true，save() 回傳更新後的物件
		when(exchangeRateRepository.existsById(testCurrencyCode)).thenReturn(true);
		when(exchangeRateRepository.save(any(ExchangeRateEntity.class))).thenReturn(updatedRate);

		mockMvc.perform(put("/api/exchangeRate").contentType(MediaType.APPLICATION_JSON).content(updatedRateJson)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.code").value(200))
				.andExpect(jsonPath("$.data.currency_name").value("新台幣 (更新)"));
	}

	@Test
	void testDeleteExchangeRate() throws Exception {
		String testCurrencyCode = "test_TWD";
		ExchangeRateEntity rateToDelete = new ExchangeRateEntity(testCurrencyCode, "新台幣", 30.0, LocalDateTime.now());

		// 模擬existsById() 回傳 true
		when(exchangeRateRepository.existsById(testCurrencyCode)).thenReturn(true);

		mockMvc.perform(delete("/api/exchangeRate").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(rateToDelete))).andExpect(status().isOk());
	}

	@Test
	void testReflashExchangeRate() throws Exception {
		// 模擬的 API 回覆
		ExchangeRateEntity mockRate1 = new ExchangeRateEntity("USD", "美元", 30.5, LocalDateTime.now());
		ExchangeRateEntity mockRate2 = new ExchangeRateEntity("EUR", "歐元", 35.0, LocalDateTime.now());
		Flux<ExchangeRateEntity> mockResponse = Flux.just(mockRate1, mockRate2);

		// 1. 模擬 apiService.getExchangeRatesAndUpdateTime() 
		when(apiService.getExchangeRatesAndUpdateTime()).thenReturn(mockResponse);

		when(exchangeRateRepository.saveAll(anyList())).thenReturn(Collections.emptyList());

		// 執行 API 呼叫
		mockMvc.perform(get("/api/exchangeRate/reflashExchangeRate")).andDo(print()).andExpect(status().isOk());
	}
}