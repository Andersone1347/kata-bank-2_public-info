package com.bank.account.service;

import com.bank.account.dto.BankDetailsDto;
import com.bank.account.entity.BankDetailsEntity;
import com.bank.account.feign.PublicInfoClient;
import com.bank.account.mapper.BankDetailsMapper;
import com.bank.account.repository.BankDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

/**
 * Реализация {@link BankDetailsService}
 */
@Service
@RequiredArgsConstructor
public class BankDetailsServiceImpl implements BankDetailsService {
    private final static String MESSAGE = "Информации о банке не найдено с id ";

    private final BankDetailsRepository bankDetailsRepository;
    private final BankDetailsMapper mapper;
    private final PublicInfoClient publicInfoClient;

    /**
     * @param id технический идентификатор {@link BankDetailsEntity}
     * @return {@link BankDetailsDto}
     */
    @Override
    @Transactional
    public BankDetailsDto getBankDetailsAndSave(Long id) {
        final BankDetailsDto bankDetailsDto = publicInfoClient.readById(id).getBody();

        if (bankDetailsDto == null) {
            throw new EntityNotFoundException(MESSAGE + id);
        }

        final BankDetailsEntity bankDetailsEntity = mapper.toEntity(bankDetailsDto);
        bankDetailsRepository.save(bankDetailsEntity);

        return bankDetailsDto;
    }
}
