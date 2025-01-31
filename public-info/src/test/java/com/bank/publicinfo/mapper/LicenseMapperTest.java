package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import com.bank.publicinfo.entity.LicenseEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

class LicenseMapperTest {

    private final LicenseMapper mapper = Mappers.getMapper(LicenseMapper.class);

    private static LicenseEntity entity;
    private static LicenseDto dto;
    private static BankDetailsEntity bankDetailsEntity;
    private static BankDetailsDto bankDetailsDto;

    @BeforeAll
    static void prepare() {
        entity = new LicenseEntity();
        dto = new LicenseDto();
        bankDetailsEntity = new BankDetailsEntity(
                1L,
                2L,
                3L,
                4L,
                new BigDecimal(5),
                "Test city",
                "Test company",
                "Test name"
        );
        bankDetailsDto = new BankDetailsDto(
                1L,
                2L,
                3L,
                4L,
                new BigDecimal(5),
                "Test city",
                "Test company",
                "Test name"
        );
    }

    @Test
    @DisplayName("Мапинг в entity")
    void toEntityTest() {
        entity.setId(64L);
        entity.setPhotoLicense(new Byte[] {0,1,2,3,4,5});
        entity.setBankDetails(bankDetailsEntity);
        dto.setId(64L);
        dto.setPhotoLicense(new Byte[] {0,1,2,3,4,5});
        dto.setBankDetails(bankDetailsDto);

        LicenseEntity mappedLicenseEntity = mapper.toEntity(dto);
        mappedLicenseEntity.setId(64L);

        assertThat(mappedLicenseEntity).isEqualTo(entity);
    }

    @Test
    @DisplayName("Мапинг в entity, на вход подан null")
    void toEntityNullTest() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("Мапинг в dto")
    void toDtoTest() {
        entity.setId(64L);
        entity.setPhotoLicense(new Byte[] {0,1,2,3,4,5});
        entity.setBankDetails(bankDetailsEntity);
        dto.setId(64L);
        dto.setPhotoLicense(new Byte[] {0,1,2,3,4,5});
        dto.setBankDetails(bankDetailsDto);

        LicenseDto mappedLicenseDto = mapper.toDto(entity);

        assertThat(mappedLicenseDto).isEqualTo(dto);
    }

    @Test
    @DisplayName("Мапинг в dto, на вход подан null")
    void toDtoNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    @DisplayName("Слияние в entity")
    void mergeToEntityTest() {
        entity.setId(64L);
        entity.setPhotoLicense(new Byte[] {0,1,2,3,4,5});
        entity.setBankDetails(bankDetailsEntity);
        dto.setId(99L);
        dto.setPhotoLicense(new Byte[] {14,15,3,18,55,2});
        dto.setBankDetails(bankDetailsDto);
        LicenseEntity expextedMappedLicenseEntity = new LicenseEntity();
        expextedMappedLicenseEntity.setId(entity.getId());
        expextedMappedLicenseEntity.setPhotoLicense(dto.getPhotoLicense());
        expextedMappedLicenseEntity.setBankDetails(entity.getBankDetails());

        LicenseEntity mappedLicenseEntity = mapper.mergeToEntity(dto,entity);

        assertThat(mappedLicenseEntity).isEqualTo(expextedMappedLicenseEntity);
    }

    @Test
    @DisplayName("Мапинг в dto лист")
    void toDtoListTest() {
        LicenseEntity entity1 = new LicenseEntity();
        LicenseEntity entity2 = new LicenseEntity();
        entity.setId(1L);
        entity1.setId(2L);
        entity2.setId(3L);
        List<LicenseEntity> licenseEntityList = List.of(entity, entity1, entity2);
        LicenseDto dto1 = new LicenseDto();
        LicenseDto dto2 = new LicenseDto();
        dto.setId(1L);
        dto1.setId(2L);
        dto2.setId(3L);
        List<LicenseDto> licenseDtoList = List.of(dto, dto1, dto2);

        List<LicenseDto> mappedLicenseDtoList = mapper.toDtoList(licenseEntityList);

        assertThat(mappedLicenseDtoList).isEqualTo(licenseDtoList);
    }

    @Test
    @DisplayName("Мапинг в dto лист, на вход подан null")
    void toDtoListNullTest() {
        assertNull(mapper.toDtoList(null));
    }
}