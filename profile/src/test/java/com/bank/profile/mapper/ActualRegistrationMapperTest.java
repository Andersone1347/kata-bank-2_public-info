package com.bank.profile.mapper;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.util.ActualRegistrationUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ActualRegistrationMapperTest {

    private final ActualRegistrationMapper mapper = Mappers.getMapper(ActualRegistrationMapper.class);

    @Test
    @DisplayName("маппинг в entity")
    void toEntityTest() {

        ActualRegistrationDto actualRegistrationDto = new ActualRegistrationUtil().createActualRegistrationDto();
        ActualRegistrationEntity actualRegistrationEntity = new ActualRegistrationUtil().createActualRegistrationEntity();

        ActualRegistrationEntity actualEntity = mapper.toEntity(actualRegistrationDto);

        assertAll(
                () -> assertThat(actualEntity).isNotNull(),
                () -> assertThat(actualEntity.getId()).isNull(),
                () -> assertThat(actualEntity.getCountry()).isEqualTo(actualRegistrationEntity.getCountry()),
                () -> assertThat(actualEntity.getRegion()).isEqualTo(actualRegistrationEntity.getRegion()),
                () -> assertThat(actualEntity.getCity()).isEqualTo(actualRegistrationEntity.getCity()),
                () -> assertThat(actualEntity.getDistrict()).isEqualTo(actualRegistrationEntity.getDistrict()),
                () -> assertThat(actualEntity.getLocality()).isEqualTo(actualRegistrationEntity.getLocality()),
                () -> assertThat(actualEntity.getStreet()).isEqualTo(actualRegistrationEntity.getStreet()),
                () -> assertThat(actualEntity.getHouseNumber()).isEqualTo(actualRegistrationEntity.getHouseNumber()),
                () -> assertThat(actualEntity.getHouseBlock()).isEqualTo(actualRegistrationEntity.getHouseBlock()),
                () -> assertThat(actualEntity.getFlatNumber()).isEqualTo(actualRegistrationEntity.getFlatNumber()),
                () -> assertThat(actualEntity.getIndex()).isEqualTo(actualRegistrationEntity.getIndex()));
    }

    @Test
    @DisplayName("маппинг в dto")
    void toDtoTest() {

        ActualRegistrationDto actualRegistrationDto = new ActualRegistrationUtil().createActualRegistrationDto();
        ActualRegistrationEntity actualRegistrationEntity = new ActualRegistrationUtil().createActualRegistrationEntity();

        ActualRegistrationDto actualDto = mapper.toDto(actualRegistrationEntity);

        assertThat(actualDto).isEqualTo(actualRegistrationDto);
    }

    @Test
    @DisplayName("Маппинг списка entity в список dto")
    void toDtoListTest() {
        List<ActualRegistrationEntity> entityList = new ActualRegistrationUtil().createActualRegistrationEntityList();
        List<ActualRegistrationDto> dtoList = new ActualRegistrationUtil().createActualRegistrationDtoList();

        List<ActualRegistrationDto> actualDtoList = mapper.toDtoList(entityList);

        assertThat(actualDtoList).isEqualTo(dtoList);
    }

    @Test
    @DisplayName("слияние в entity")
    void mergeToEntityTest() {
        ActualRegistrationEntity preMergeEntity = new ActualRegistrationUtil().createPreMergerActualRegistrationEntity();
        ActualRegistrationDto actualRegistrationDto = new ActualRegistrationUtil().createActualRegistrationDto();
        ActualRegistrationEntity expectedEntity = new ActualRegistrationUtil().createPostMergerActualRegistrationEntity();

        ActualRegistrationEntity actualEntity = mapper.mergeToEntity(actualRegistrationDto, preMergeEntity);

        assertThat(actualEntity).isEqualTo(expectedEntity);
    }
}
