package com.bank.transfer.controller;

import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.PhoneTransferEntity;
import com.bank.transfer.service.PhoneTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для {@link PhoneTransferDto}
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/phone")
public class PhoneTransferController {

    private final PhoneTransferService service;

    /**
     * @param ids список технических идентификаторов {@link PhoneTransferEntity}
     * @return {@link ResponseEntity} c листом {@link PhoneTransferDto}
     */
    @GetMapping("/read/all")
    public ResponseEntity<List<PhoneTransferDto>> readAll(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }

    /**
     * @param id технический идентификатор {@link PhoneTransferEntity}
     * @return {@link ResponseEntity} {@link PhoneTransferDto}
     */
    @GetMapping("/read/{id}")
    public PhoneTransferDto read(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    /**
     * @param phoneTransfer {@link PhoneTransferDto}
     * @return {@link ResponseEntity } {@link PhoneTransferDto}
     */
    @PostMapping("/create")
    public ResponseEntity<PhoneTransferDto> create(@RequestBody PhoneTransferDto phoneTransfer) {
        return ResponseEntity.ok(service.save(phoneTransfer));
    }

    /**
     * @param phoneTransfer {@link PhoneTransferDto}
     * @param id            технический идентификатор {@link PhoneTransferEntity}
     * @return {@link ResponseEntity} {@link PhoneTransferDto}
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<PhoneTransferDto> update(@PathVariable("id") Long id,
                                                   @RequestBody PhoneTransferDto phoneTransfer) {
        return ResponseEntity.ok(service.update(id, phoneTransfer));
    }
}
