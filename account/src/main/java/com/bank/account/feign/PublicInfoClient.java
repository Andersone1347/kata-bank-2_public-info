package com.bank.account.feign;

import com.bank.account.dto.BankDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign клиент для взаимодействия с микросервисом public-info.
 * Используется для получения BankDetails по их идентификаторам.
 */
@FeignClient(name = "public-info-app", url = "${public-info-app.url}")
public interface PublicInfoClient {

    /**
     * @param id технический идентификатор BankDetailsEntity
     * @return {@link ResponseEntity}, {@link BankDetailsDto} и HttpStatus.OK
     */
    @GetMapping("/bank/details/{id}")
    ResponseEntity<BankDetailsDto> readById(@PathVariable("id") Long id);
}
