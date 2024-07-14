package com.bank.account.controller;

import com.bank.account.dto.BankDetailsDto;
import com.bank.account.entity.BankDetailsEntity;
import com.bank.account.service.BankDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для {@link BankDetailsDto}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bank/details")
public class BankDetailsController {

    private final BankDetailsService bankDetailsService;

    /**
     * @param id технический идентификатор {@link BankDetailsEntity}
     * @return {@link ResponseEntity}, {@link BankDetailsDto} и HttpStatus.OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<BankDetailsDto> readById(@PathVariable Long id) {

        final BankDetailsDto bankDetailsDto = bankDetailsService.getBankDetailsAndSave(id);
        return ResponseEntity.ok(bankDetailsDto);
    }
}
