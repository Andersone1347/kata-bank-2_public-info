package com.bank.account.mapper;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.util.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AccountDetailsMapperTest {
    private final AccountDetailsMapper mapper = Mappers.getMapper(AccountDetailsMapper.class);
    @Test
    @DisplayName("Должен корректно маппить AccountDetailsDto в AccountDetailsEntity")
    void toEntity() {
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();
        AccountDetailsEntity expectedEntity = new DataUtils().createAccountDetailsEntity();

        AccountDetailsEntity actualEntity = mapper.toEntity(accountDetailsDto);

        assertThat(actualEntity).isNotNull();
        assertThat(actualEntity.getPassportId()).isEqualTo(expectedEntity.getPassportId());
        assertThat(actualEntity.getAccountNumber()).isEqualTo(expectedEntity.getAccountNumber());
        assertThat(actualEntity.getBankDetailsId()).isEqualTo(expectedEntity.getBankDetailsId());
        assertThat(actualEntity.getMoney()).isEqualTo(expectedEntity.getMoney());
        assertThat(actualEntity.getNegativeBalance()).isEqualTo(expectedEntity.getNegativeBalance());
        assertThat(actualEntity.getProfileId()).isEqualTo(expectedEntity.getProfileId());
        assertThat(actualEntity.getId()).isNull();
    }

    @Test
    @DisplayName("Должен корректно маппить AccountDetailsEntity в AccountDetailsDto")
    void toDto() {
        AccountDetailsEntity accountDetailsEntity = new DataUtils().createAccountDetailsEntity();
        AccountDetailsDto expectedDto = new DataUtils().createAccountDetailsDto();

        AccountDetailsDto actualDto = mapper.toDto(accountDetailsEntity);

        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    @DisplayName("Должен корректно маппить список AccountDetailsEntity в список AccountDetailsDto")
    void toDtoList() {
        List<AccountDetailsEntity> entityList = new DataUtils().createAccountDetailsEntityList();
        List<AccountDetailsDto> expectedDtoList = new DataUtils().createAccountDetailsDtoList();

        List<AccountDetailsDto> actualDtoList = mapper.toDtoList(entityList);

        assertThat(actualDtoList).isEqualTo(expectedDtoList);
    }

    @Test
    @DisplayName("Должен корректно маппить AccountDetailsEntity с AccountDetailsDto")
    void mergeToEntity() {
        AccountDetailsEntity preMergerEntity = new DataUtils().createPreMergerAccountDetailsEntity();
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();
        AccountDetailsEntity expectedEntity = new DataUtils().createPostMergerAccountDetailsEntity();

        AccountDetailsEntity actualEntity = mapper.mergeToEntity(preMergerEntity, accountDetailsDto);

        assertThat(actualEntity).isEqualTo(expectedEntity);
    }
}