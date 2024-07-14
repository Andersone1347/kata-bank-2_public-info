package com.bank.profile.mapper;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.util.RegistrationUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class RegistrationMapperTest {

    private final RegistrationMapper mapper = Mappers.getMapper(RegistrationMapper.class);


    @Test
    @DisplayName("маппинг в entity")
    void toEntityTest() {
        RegistrationDto registrationDto = new RegistrationUtil().createRegistrationDto();
        RegistrationEntity registrationEntity = new RegistrationUtil().createRegistrationEntity();

        RegistrationEntity actualEntity = mapper.toEntity(registrationDto);

        assertAll(
                () -> assertThat(actualEntity).isNotNull(),
                () -> assertThat(actualEntity.getId()).isNull(),
                () -> assertThat(actualEntity.getCountry()).isEqualTo(registrationEntity.getCountry()),
                () -> assertThat(actualEntity.getRegion()).isEqualTo(registrationEntity.getRegion()),
                () -> assertThat(actualEntity.getCity()).isEqualTo(registrationEntity.getCity()),
                () -> assertThat(actualEntity.getDistrict()).isEqualTo(registrationEntity.getDistrict()),
                () -> assertThat(actualEntity.getLocality()).isEqualTo(registrationEntity.getLocality()),
                () -> assertThat(actualEntity.getStreet()).isEqualTo(registrationEntity.getStreet()),
                () -> assertThat(actualEntity.getHouseNumber()).isEqualTo(registrationEntity.getHouseNumber()),
                () -> assertThat(actualEntity.getHouseBlock()).isEqualTo(registrationEntity.getHouseBlock()),
                () -> assertThat(actualEntity.getFlatNumber()).isEqualTo(registrationEntity.getFlatNumber()),
                () -> assertThat(actualEntity.getIndex()).isEqualTo(registrationEntity.getIndex())
        );
    }

    @Test
    @DisplayName("маппинг в dto")
    void toDtoTest() {
        RegistrationDto registrationDto = new RegistrationUtil().createRegistrationDto();
        RegistrationEntity registrationEntity = new RegistrationUtil().createRegistrationEntity();

        RegistrationDto actualDto = mapper.toDto(registrationEntity);

        assertThat(actualDto).isEqualTo(registrationDto);
    }

    @Test
    @DisplayName("Маппинг списка entity в список dto")
    void toDtoListTest() {
        List<RegistrationEntity> entityList = new RegistrationUtil().createRegistrationEntityList();
        List<RegistrationDto> dtoList = new RegistrationUtil().createRegistrationDtoList();

        List<RegistrationDto> actualDtoList = mapper.toDtoList(entityList);

        assertThat(actualDtoList).isEqualTo(dtoList);
    }

    @Test
    @DisplayName("слияние в entity")
    void mergeToEntityTest() {
        RegistrationEntity preMergerEntity = new RegistrationUtil().createPreMergerRegistrationEntity();
        RegistrationDto registrationDto = new RegistrationUtil().createRegistrationDto();
        RegistrationEntity expectedEntity = new RegistrationUtil().createPostMergerRegistrationEntity();

        RegistrationEntity actualEntity = mapper.mergeToEntity(registrationDto, preMergerEntity);

        assertThat(actualEntity).isEqualTo(expectedEntity);
    }
}
