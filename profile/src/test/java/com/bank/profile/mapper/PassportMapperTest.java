package com.bank.profile.mapper;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.util.PassportUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PassportMapperTest {

    private final PassportMapper mapper = Mappers.getMapper(PassportMapper.class);


    @Test
    @DisplayName("маппинг в entity")
    void toEntityTest() {

        PassportDto passportDto = new PassportUtil().createPassportDto();
        PassportEntity passportEntity = new PassportUtil().createPassportEntity();

        PassportEntity actualEntity = mapper.toEntity(passportDto);

        assertThat(actualEntity).isNotNull();

        assertAll(
                () -> assertThat(actualEntity.getSeries()).isEqualTo(passportEntity.getSeries()),
                () -> assertThat(actualEntity.getNumber()).isEqualTo(passportEntity.getNumber()),
                () -> assertThat(actualEntity.getLastName()).isEqualTo(passportEntity.getLastName()),
                () -> assertThat(actualEntity.getFirstName()).isEqualTo(passportEntity.getFirstName()),
                () -> assertThat(actualEntity.getMiddleName()).isEqualTo(passportEntity.getMiddleName()),
                () -> assertThat(actualEntity.getGender()).isEqualTo(passportEntity.getGender()),
                () -> assertThat(actualEntity.getBirthDate()).isEqualTo(passportEntity.getBirthDate()),
                () -> assertThat(actualEntity.getBirthPlace()).isEqualTo(passportEntity.getBirthPlace()),
                () -> assertThat(actualEntity.getIssuedBy()).isEqualTo(passportEntity.getIssuedBy()),
                () -> assertThat(actualEntity.getDateOfIssue()).isEqualTo(passportEntity.getDateOfIssue()),
                () -> assertThat(actualEntity.getDivisionCode()).isEqualTo(passportEntity.getDivisionCode()),

                () -> assertThat(actualEntity.getRegistration().getId()).isEqualTo(passportEntity.getRegistration().getId()),
                () -> assertThat(actualEntity.getRegistration().getCountry()).isEqualTo(passportEntity.getRegistration().getCountry()),
                () -> assertThat(actualEntity.getRegistration().getRegion()).isEqualTo(passportEntity.getRegistration().getRegion()),
                () -> assertThat(actualEntity.getRegistration().getCity()).isEqualTo(passportEntity.getRegistration().getCity()),
                () -> assertThat(actualEntity.getRegistration().getDistrict()).isEqualTo(passportEntity.getRegistration().getDistrict()),
                () -> assertThat(actualEntity.getRegistration().getLocality()).isEqualTo(passportEntity.getRegistration().getLocality()),
                () -> assertThat(actualEntity.getRegistration().getStreet()).isEqualTo(passportEntity.getRegistration().getStreet()),
                () -> assertThat(actualEntity.getRegistration().getHouseNumber()).isEqualTo(passportEntity.getRegistration().getHouseNumber()),
                () -> assertThat(actualEntity.getRegistration().getHouseBlock()).isEqualTo(passportEntity.getRegistration().getHouseBlock()),
                () -> assertThat(actualEntity.getRegistration().getFlatNumber()).isEqualTo(passportEntity.getRegistration().getFlatNumber()),
                () -> assertThat(actualEntity.getRegistration().getIndex()).isEqualTo(passportEntity.getRegistration().getIndex()));
    }

    @Test
    @DisplayName("маппинг в dto")
    void toDtoTest() {
        PassportDto passportDto = new PassportUtil().createPassportDto();
        PassportEntity passportEntity = new PassportUtil().createPassportEntity();

        PassportDto actualDto = mapper.toDto(passportEntity);

        assertThat(actualDto).isEqualTo(passportDto);
    }

    @Test
    @DisplayName("Маппинг списка entity в список dto")
    void toDtoListTest() {
        List<PassportEntity> passportEntityList = new PassportUtil().createPassportEntityList();
        List<PassportDto> passportDtoList = new PassportUtil().createPassportDtoList();

        List<PassportDto> actualDtoList = mapper.toDtoList(passportEntityList);

        assertThat(actualDtoList).isEqualTo(passportDtoList);
    }

    @Test
    @DisplayName("слияние в entity")
    void mergeToEntityTest() {
        PassportEntity preMergerEntity = new PassportUtil().createPreMergerPassportEntity();
        PassportDto passportDto = new PassportUtil().createPassportDto();
        PassportEntity expectedEntity = new PassportUtil().createPostMergerPassportEntity();

        PassportEntity actualEntity = mapper.mergeToEntity(passportDto, preMergerEntity);

        assertThat(actualEntity).isEqualTo(expectedEntity);
    }
}
