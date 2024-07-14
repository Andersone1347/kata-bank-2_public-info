package com.bank.account.mapper;

import com.bank.account.dto.ProfileDto;
import com.bank.account.entity.ProfileEntity;
import org.mapstruct.Mapper;

/**
 * Mapper для {@link ProfileEntity} и {@link ProfileDto}.
 */
@Mapper(componentModel = "spring")
public interface ProfileMapper {

    /**
     * @param profileDto {@link ProfileDto}
     * @return {@link ProfileEntity}
     */
    ProfileEntity toEntity(ProfileDto profileDto);
}
