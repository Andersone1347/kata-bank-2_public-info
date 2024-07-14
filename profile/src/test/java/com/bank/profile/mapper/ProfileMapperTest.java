package com.bank.profile.mapper;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.util.ProfileUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ProfileMapperTest {

    private final ProfileMapper mapper = Mappers.getMapper(ProfileMapper.class);


    @Test
    @DisplayName("маппинг в entity")
    void toEntityTest() {
        ProfileDto profilesDto = new ProfileUtil().createProfileDto();
        ProfileEntity profileEntity = new ProfileUtil().createProfileEntity();

        ProfileEntity actualEntity = mapper.toEntity(profilesDto);


        assertAll(
                () -> assertThat(actualEntity).isNotNull(),
                () -> assertThat(actualEntity.getId()).isNull(),
                () -> assertThat(actualEntity.getPhoneNumber()).isEqualTo(profileEntity.getPhoneNumber()),
                () -> assertThat(actualEntity.getEmail()).isEqualTo(profileEntity.getEmail()),
                () -> assertThat(actualEntity.getNameOnCard()).isEqualTo(profileEntity.getNameOnCard()),
                () -> assertThat(actualEntity.getInn()).isEqualTo(profileEntity.getInn()),
                () -> assertThat(actualEntity.getSnils()).isEqualTo(profileEntity.getSnils()),

                () -> assertThat(actualEntity.getPassport().getId()).isEqualTo(profileEntity.getPassport().getId()),
                () -> assertThat(actualEntity.getPassport().getNumber()).isEqualTo(profileEntity.getPassport().getNumber()),
                () -> assertThat(actualEntity.getPassport().getLastName()).isEqualTo(profileEntity.getPassport().getLastName()),
                () -> assertThat(actualEntity.getPassport().getFirstName()).isEqualTo(profileEntity.getPassport().getFirstName()),
                () -> assertThat(actualEntity.getPassport().getMiddleName()).isEqualTo(profileEntity.getPassport().getMiddleName()),
                () -> assertThat(actualEntity.getPassport().getGender()).isEqualTo(profileEntity.getPassport().getGender()),
                () -> assertThat(actualEntity.getPassport().getBirthDate()).isEqualTo(profileEntity.getPassport().getBirthDate()),
                () -> assertThat(actualEntity.getPassport().getBirthPlace()).isEqualTo(profileEntity.getPassport().getBirthPlace()),
                () -> assertThat(actualEntity.getPassport().getIssuedBy()).isEqualTo(profileEntity.getPassport().getIssuedBy()),
                () -> assertThat(actualEntity.getPassport().getDateOfIssue()).isEqualTo(profileEntity.getPassport().getDateOfIssue()),
                () -> assertThat(actualEntity.getPassport().getDivisionCode()).isEqualTo(profileEntity.getPassport().getDivisionCode()),
                () -> assertThat(actualEntity.getPassport().getExpirationDate()).isEqualTo(profileEntity.getPassport().getExpirationDate()),

                () -> assertThat(actualEntity.getPassport().getRegistration().getId()).isEqualTo(profileEntity.getPassport().getRegistration().getId()),
                () -> assertThat(actualEntity.getPassport().getRegistration().getCountry()).isEqualTo(profileEntity.getPassport().getRegistration().getCountry()),
                () -> assertThat(actualEntity.getPassport().getRegistration().getRegion()).isEqualTo(profileEntity.getPassport().getRegistration().getRegion()),
                () -> assertThat(actualEntity.getPassport().getRegistration().getCity()).isEqualTo(profileEntity.getPassport().getRegistration().getCity()),
                () -> assertThat(actualEntity.getPassport().getRegistration().getDistrict()).isEqualTo(profileEntity.getPassport().getRegistration().getDistrict()),
                () -> assertThat(actualEntity.getPassport().getRegistration().getLocality()).isEqualTo(profileEntity.getPassport().getRegistration().getLocality()),
                () -> assertThat(actualEntity.getPassport().getRegistration().getStreet()).isEqualTo(profileEntity.getPassport().getRegistration().getStreet()),
                () -> assertThat(actualEntity.getPassport().getRegistration().getHouseNumber()).isEqualTo(profileEntity.getPassport().getRegistration().getHouseNumber()),
                () -> assertThat(actualEntity.getPassport().getRegistration().getHouseBlock()).isEqualTo(profileEntity.getPassport().getRegistration().getHouseBlock()),
                () -> assertThat(actualEntity.getPassport().getRegistration().getFlatNumber()).isEqualTo(profileEntity.getPassport().getRegistration().getFlatNumber()),
                () -> assertThat(actualEntity.getPassport().getRegistration().getIndex()).isEqualTo(profileEntity.getPassport().getRegistration().getIndex()),

                () -> assertThat(actualEntity.getActualRegistration().getId()).isEqualTo(profileEntity.getActualRegistration().getId()),
                () -> assertThat(actualEntity.getActualRegistration().getCountry()).isEqualTo(profileEntity.getActualRegistration().getCountry()),
                () -> assertThat(actualEntity.getActualRegistration().getRegion()).isEqualTo(profileEntity.getActualRegistration().getRegion()),
                () -> assertThat(actualEntity.getActualRegistration().getCity()).isEqualTo(profileEntity.getActualRegistration().getCity()),
                () -> assertThat(actualEntity.getActualRegistration().getDistrict()).isEqualTo(profileEntity.getActualRegistration().getDistrict()),
                () -> assertThat(actualEntity.getActualRegistration().getLocality()).isEqualTo(profileEntity.getActualRegistration().getLocality()),
                () -> assertThat(actualEntity.getActualRegistration().getStreet()).isEqualTo(profileEntity.getActualRegistration().getStreet()),
                () -> assertThat(actualEntity.getActualRegistration().getHouseNumber()).isEqualTo(profileEntity.getActualRegistration().getHouseNumber()),
                () -> assertThat(actualEntity.getActualRegistration().getHouseBlock()).isEqualTo(profileEntity.getActualRegistration().getHouseBlock()),
                () -> assertThat(actualEntity.getActualRegistration().getFlatNumber()).isEqualTo(profileEntity.getActualRegistration().getFlatNumber()),
                () -> assertThat(actualEntity.getActualRegistration().getIndex()).isEqualTo(profileEntity.getActualRegistration().getIndex()));
    }

    @Test
    @DisplayName("маппинг в dto")
    void toDtoTest() {
        ProfileDto profilesDto = new ProfileUtil().createProfileDto();
        ProfileEntity profileEntity = new ProfileUtil().createProfileEntity();

        ProfileDto actualDto = mapper.toDto(profileEntity);

        assertThat(actualDto).isEqualTo(profilesDto);
    }

    @Test
    @DisplayName("Маппинг списка entity в список dto")
    void toDtoListTest() {

        List<ProfileEntity> entityList = new ProfileUtil().createProfileEntityList();
        List<ProfileDto> dtoList = new ProfileUtil().createProfileDtoList();

        List<ProfileDto> actualDtoList = mapper.toDtoList(entityList);

        assertThat(actualDtoList).isEqualTo(dtoList);
    }

    @Test
    @DisplayName("слияние в entity")
    void mergeToEntityTest() {
        ProfileEntity preMergerEntity = new ProfileUtil().createPreMergerProfileEntity();
        ProfileDto profilesDto = new ProfileUtil().createProfileDto();
        ProfileEntity expectedEntity = new ProfileUtil().createPostMergerProfileEntity();

        ProfileEntity actualEntity = mapper.mergeToEntity(profilesDto, preMergerEntity);
        assertThat(actualEntity).isEqualTo(expectedEntity);
    }
}
