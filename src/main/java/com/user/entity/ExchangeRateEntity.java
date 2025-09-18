package com.user.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "exchange_rate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateEntity {
	@Id
	@Column(name = "currency_code", unique = true, nullable = false)
	@JsonProperty("currency_code")
	private String currencyCode; 

	@Column(name = "currency_name", nullable = true)
	@JsonProperty("currency_name")
	private String currencyName; 

	@Column(name = "rate", nullable = false)
	@JsonProperty("rate")
	private double rate; 

	@Column(name = "update_time", nullable = false)
	@JsonProperty("update_time")
	private LocalDateTime updateTime; 
}