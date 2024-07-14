package com.bank.profile.mapper;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.util.AccountDetailsIdUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountDetailsIdMapperTest {


    private final AccountDetailsIdMapper mapper = Mappers.getMapper(AccountDetailsIdMapper.class);

    @Test
    @DisplayName("маппинг в entity")
    void toEntityTest() {

        AccountDetailsIdDto accountDetailsIdDto = new AccountDetailsIdUtil().createAccountDetailsIdDto();
        AccountDetailsIdEntity accountDetailsIdEntity = new AccountDetailsIdUtil().createAccountDetailsIdEntity();

        AccountDetailsIdEntity actualEntity = mapper.toEntity(accountDetailsIdDto);

        assertThat(actualEntity).isNotNull();

        assertThat(actualEntity.getId()).isNull();
        assertThat(actualEntity.getAccountId()).isEqualTo(accountDetailsIdEntity.getAccountId());

        assertThat(actualEntity.getProfile().getId()).isEqualTo(accountDetailsIdEntity.getProfile().getId());
        assertThat(actualEntity.getProfile().getPhoneNumber()).isEqualTo(accountDetailsIdEntity.getProfile().getPhoneNumber());
        assertThat(actualEntity.getProfile().getEmail()).isEqualTo(accountDetailsIdEntity.getProfile().getEmail());
        assertThat(actualEntity.getProfile().getNameOnCard()).isEqualTo(accountDetailsIdEntity.getProfile().getNameOnCard());
        assertThat(actualEntity.getProfile().getInn()).isEqualTo(accountDetailsIdEntity.getProfile().getInn());
        assertThat(actualEntity.getProfile().getSnils()).isEqualTo(accountDetailsIdEntity.getProfile().getSnils());

        assertThat(actualEntity.getProfile().getPassport().getId()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getId());
        assertThat(actualEntity.getProfile().getPassport().getNumber()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getNumber());
        assertThat(actualEntity.getProfile().getPassport().getLastName()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getLastName());
        assertThat(actualEntity.getProfile().getPassport().getFirstName()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getFirstName());
        assertThat(actualEntity.getProfile().getPassport().getMiddleName()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getMiddleName());
        assertThat(actualEntity.getProfile().getPassport().getGender()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getGender());
        assertThat(actualEntity.getProfile().getPassport().getBirthDate()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getBirthDate());
        assertThat(actualEntity.getProfile().getPassport().getBirthPlace()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getBirthPlace());
        assertThat(actualEntity.getProfile().getPassport().getIssuedBy()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getIssuedBy());
        assertThat(actualEntity.getProfile().getPassport().getDateOfIssue()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getDateOfIssue());
        assertThat(actualEntity.getProfile().getPassport().getDivisionCode()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getDivisionCode());
        assertThat(actualEntity.getProfile().getPassport().getExpirationDate()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getExpirationDate());

        assertThat(actualEntity.getProfile().getPassport().getRegistration().getId()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getRegistration().getId());
        assertThat(actualEntity.getProfile().getPassport().getRegistration().getCountry()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getRegistration().getCountry());
        assertThat(actualEntity.getProfile().getPassport().getRegistration().getRegion()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getRegistration().getRegion());
        assertThat(actualEntity.getProfile().getPassport().getRegistration().getCity()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getRegistration().getCity());
        assertThat(actualEntity.getProfile().getPassport().getRegistration().getDistrict()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getRegistration().getDistrict());
        assertThat(actualEntity.getProfile().getPassport().getRegistration().getLocality()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getRegistration().getLocality());
        assertThat(actualEntity.getProfile().getPassport().getRegistration().getStreet()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getRegistration().getStreet());
        assertThat(actualEntity.getProfile().getPassport().getRegistration().getHouseNumber()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getRegistration().getHouseNumber());
        assertThat(actualEntity.getProfile().getPassport().getRegistration().getHouseBlock()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getRegistration().getHouseBlock());
        assertThat(actualEntity.getProfile().getPassport().getRegistration().getFlatNumber()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getRegistration().getFlatNumber());
        assertThat(actualEntity.getProfile().getPassport().getRegistration().getIndex()).isEqualTo(accountDetailsIdEntity.getProfile().getPassport().getRegistration().getIndex());

        assertThat(actualEntity.getProfile().getActualRegistration().getId()).isEqualTo(accountDetailsIdEntity.getProfile().getActualRegistration().getId());
        assertThat(actualEntity.getProfile().getActualRegistration().getCountry()).isEqualTo(accountDetailsIdEntity.getProfile().getActualRegistration().getCountry());
        assertThat(actualEntity.getProfile().getActualRegistration().getRegion()).isEqualTo(accountDetailsIdEntity.getProfile().getActualRegistration().getRegion());
        assertThat(actualEntity.getProfile().getActualRegistration().getCity()).isEqualTo(accountDetailsIdEntity.getProfile().getActualRegistration().getCity());
        assertThat(actualEntity.getProfile().getActualRegistration().getDistrict()).isEqualTo(accountDetailsIdEntity.getProfile().getActualRegistration().getDistrict());
        assertThat(actualEntity.getProfile().getActualRegistration().getLocality()).isEqualTo(accountDetailsIdEntity.getProfile().getActualRegistration().getLocality());
        assertThat(actualEntity.getProfile().getActualRegistration().getStreet()).isEqualTo(accountDetailsIdEntity.getProfile().getActualRegistration().getStreet());
        assertThat(actualEntity.getProfile().getActualRegistration().getHouseNumber()).isEqualTo(accountDetailsIdEntity.getProfile().getActualRegistration().getHouseNumber());
        assertThat(actualEntity.getProfile().getActualRegistration().getHouseBlock()).isEqualTo(accountDetailsIdEntity.getProfile().getActualRegistration().getHouseBlock());
        assertThat(actualEntity.getProfile().getActualRegistration().getFlatNumber()).isEqualTo(accountDetailsIdEntity.getProfile().getActualRegistration().getFlatNumber());
        assertThat(actualEntity.getProfile().getActualRegistration().getIndex()).isEqualTo(accountDetailsIdEntity.getProfile().getActualRegistration().getIndex());

    }

    @Test
    @DisplayName("маппинг в dto")
    void toDtoTest() {
        AccountDetailsIdDto accountDetailsIdDto = new AccountDetailsIdUtil().createAccountDetailsIdDto();
        AccountDetailsIdEntity accountDetailsIdEntity = new AccountDetailsIdUtil().createAccountDetailsIdEntity();

        AccountDetailsIdDto actualDto = mapper.toDto(accountDetailsIdEntity);

        assertThat(actualDto).isEqualTo(accountDetailsIdDto);
    }

    @Test
    @DisplayName("Маппинг списка entity в список dto")
    void toDtoListTest() {
        List<AccountDetailsIdEntity> entityList = new AccountDetailsIdUtil().createAccountDetailsIdEntityList();
        List<AccountDetailsIdDto> DtoList = new AccountDetailsIdUtil().createAccountDetailsIdDtoList();

        List<AccountDetailsIdDto> actualDtoList = mapper.toDtoList(entityList);

        assertThat(actualDtoList).isEqualTo(DtoList);
    }

    @Test
    @DisplayName("слияние в entity")
    void mergeToEntityTest() {
        AccountDetailsIdEntity preMergerEntity = new AccountDetailsIdUtil().createPreMergerAccountDetailsIdEntity();
        AccountDetailsIdDto accountDetailsIdDto = new AccountDetailsIdUtil().createAccountDetailsIdDto();
        AccountDetailsIdEntity expectedEntity = new AccountDetailsIdUtil().createPostMergerAccountDetailsIdEntity();

        AccountDetailsIdEntity actualEntity = mapper.mergeToEntity( accountDetailsIdDto, preMergerEntity);

        assertThat(actualEntity).isEqualTo(expectedEntity);
    }
}
