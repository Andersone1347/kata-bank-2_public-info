package com.bank.account.controller;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.service.AccountDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Контроллер для {@link AccountDetailsEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/details")
@Tag(name = "Контроллер банковских счетов")
public class AccountDetailsController {

    private final AccountDetailsService service;

    /**
     * @param id технический идентификатор {@link AccountDetailsEntity}
     * @return {@link ResponseEntity<AccountDetailsDto>}
     */
    @Operation(summary = "Получение данных счета по ID")
    @GetMapping("/{id}")
    public AccountDetailsDto read(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    /**
     * @param accountDetails - сущность для создания в виде {@link AccountDetailsDto}
     * @return {@link ResponseEntity<AccountDetailsDto>}
     */
    @Operation(summary = "Создание банковского счета")
    @PostMapping("/create")
    public ResponseEntity<AccountDetailsDto> create(@RequestBody AccountDetailsDto accountDetails) {
        return ResponseEntity.ok(service.save(accountDetails));
    }

    /**
     * @param accountDetails {@link AccountDetailsDto}
     * @param id             технический идентификатор {@link AccountDetailsEntity}
     * @return {@link ResponseEntity<AccountDetailsDto>}
     */
    @Operation(summary = "Обновление данных банковского счета по ID")
    @PutMapping("/update/{id}")
    public ResponseEntity<AccountDetailsDto> update(@PathVariable Long id,
                                                    @RequestBody AccountDetailsDto accountDetails) {
        return ResponseEntity.ok(service.update(id, accountDetails));
    }

    /**
     * @param ids лист технических идентификаторов {@link AccountDetailsEntity}
     * @return {@link ResponseEntity} c {@link List<AccountDetailsDto>}.
     */
    @Operation(summary = "Получение всех банковских счетов по списку ID")
    @GetMapping("read/all")
    public ResponseEntity<List<AccountDetailsDto>> readAll(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }
}
