package com.bank.account.mapper;

import com.bank.account.dto.BankDetailsDto;
import com.bank.account.entity.BankDetailsEntity;
import org.mapstruct.Mapper;

/**
 * Mapper для {@link BankDetailsEntity} и {@link BankDetailsDto}
 */
@Mapper(componentModel = "spring")
public interface BankDetailsMapper {
    /**
     * @param bankDetailsDto {@link BankDetailsDto}
     * @return {@link BankDetailsEntity}
     */
    BankDetailsEntity toEntity(BankDetailsDto bankDetailsDto);
}
