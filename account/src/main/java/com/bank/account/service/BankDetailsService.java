package com.bank.account.service;

import com.bank.account.dto.BankDetailsDto;
import com.bank.account.entity.BankDetailsEntity;

/**
 * Сервис для {@link BankDetailsEntity} и {@link BankDetailsDto}
 */
public interface BankDetailsService {

    /**
     * @param id технический идентификатор {@link BankDetailsEntity}
     * @return {@link BankDetailsDto}
     */
    BankDetailsDto getBankDetailsAndSave(Long id);
}
