package com.bank.account.service;

import com.bank.account.dto.ProfileDto;
import com.bank.account.entity.ProfileEntity;

/**
 * Сервис {@link ProfileEntity} {@link ProfileDto}
 */
public interface ProfileService {

    /**
     * @param id технический идентификатор {@link ProfileEntity}
     * @return {@link ProfileDto}
     */
    ProfileDto getProfileAndSave(Long id);
}
