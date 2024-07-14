package com.bank.account.feign;

import com.bank.account.dto.ProfileDto;
import com.bank.account.entity.ProfileEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign клиент для взаимодействия с микросервисом profile.
 * Используется для чтения Profile по их идентификаторам.
 */
@FeignClient(name = "profile-app", url = "${profile-app.url}")
public interface ProfileClient {

    /**
     * @param id технический идентификатор {@link ProfileEntity}
     * @return {@link ResponseEntity < ProfileDto >}
     */
    @GetMapping("/profile/read/{id}")
    ResponseEntity<ProfileDto> read(@PathVariable("id") Long id);
}
