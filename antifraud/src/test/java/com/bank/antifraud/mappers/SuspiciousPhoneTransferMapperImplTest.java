package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import com.bank.antifraud.util.service.TestSuspiciousPhoneTransferUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Тест для {@link SuspiciousPhoneTransferMapper}
 */

class SuspiciousPhoneTransferMapperImplTest {

    private final SuspiciousPhoneTransferMapper mapper = Mappers.getMapper(SuspiciousPhoneTransferMapper.class);

    @Test
    @DisplayName("маппинг Entity в Dto")
    void toDtoTest() {
        SuspiciousPhoneTransferEntity expectedEntity =
                TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferEntity();

        SuspiciousPhoneTransferDto foundDto = mapper.toDto(expectedEntity);

        assertAll("Свойства Dto",
                () -> assertEquals(1L, foundDto.getId()),
                () -> assertEquals(123456789L, foundDto.getPhoneTransferId()),
                () -> assertEquals(true, foundDto.getIsSuspicious()),
                () -> assertEquals("Номер телефона используется для мошенничества",
                        foundDto.getSuspiciousReason()),
                () -> assertEquals(true, foundDto.getIsBlocked()),
                () -> assertEquals("Номер телефона заблокирован из-за подозрительной активности",
                        foundDto.getBlockedReason())
        );
    }

    @Test
    @DisplayName("маппинг Entity в Dto, на вход подан null")
    void toDtoNullTest() {
        SuspiciousPhoneTransferDto dto = mapper.toDto(null);

        assertNull(dto);
    }


    @Test
    @DisplayName("маппинг Dto в Entity")
    void toEntityTest() {
        SuspiciousPhoneTransferDto expectedDto = TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferDto();

        SuspiciousPhoneTransferEntity foundEntity = mapper.toEntity(expectedDto);

        assertAll("Свойства Entity",
                () -> assertEquals(123456789L, foundEntity.getPhoneTransferId()),
                () -> assertEquals(true, foundEntity.getIsSuspicious()),
                () -> assertEquals("Номер телефона используется для мошенничества",
                        foundEntity.getSuspiciousReason()),
                () -> assertEquals(true, foundEntity.getIsBlocked()),
                () -> assertEquals("Номер телефона заблокирован из-за подозрительной активности",
                        foundEntity.getBlockedReason())
        );
    }

    @Test
    @DisplayName("маппинг Dto в Entity, на вход подан null")
    void toEntityNullTest() {
        SuspiciousPhoneTransferEntity entity = mapper.toEntity(null);

        assertNull(entity);
    }

    @Test
    @DisplayName("маппинг Entities в Dto's")
    void mergeToListDtoTest() {
        List<SuspiciousPhoneTransferEntity> expectedEntities =
                TestSuspiciousPhoneTransferUtil.createListOfSuspiciousPhoneTransferEntities();

        List<SuspiciousPhoneTransferDto> foundDtos = mapper.toListDto(expectedEntities);

        assertAll("Свойства Dto 1",
                () -> assertEquals(1L, foundDtos.get(0).getId()),
                () -> assertEquals(111222333L, foundDtos.get(0).getPhoneTransferId()),
                () -> assertEquals(true, foundDtos.get(0).getIsBlocked()),
                () -> assertEquals(false, foundDtos.get(0).getIsSuspicious()),
                () -> assertEquals("Номер телефона заблокирован из-за превышения лимита звонков",
                        foundDtos.get(0).getBlockedReason()),
                () -> assertNull(foundDtos.get(0).getSuspiciousReason())
        );

        assertAll("Свойства Dto 2",
                () -> assertEquals(2L, foundDtos.get(1).getId()),
                () -> assertEquals(444555666L, foundDtos.get(1).getPhoneTransferId()),
                () -> assertEquals(false, foundDtos.get(1).getIsBlocked()),
                () -> assertEquals(true, foundDtos.get(1).getIsSuspicious()),
                () -> assertNull(foundDtos.get(1).getBlockedReason()),
                () -> assertEquals("Номер телефона используется для мошенничества с использованием " +
                        "автоматизированных звонков", foundDtos.get(1).getSuspiciousReason())
        );
    }

    @Test
    @DisplayName("маппинг Entities в Dto's, на вход подан null")
    void mergeToListDtoNullTest() {
        List<SuspiciousPhoneTransferDto> foundDtos = mapper.toListDto(null);

        assertNull(foundDtos);
    }

    @Test
    @DisplayName("слияние в Entity")
    void mergeToEntityTest() {
        SuspiciousPhoneTransferEntity entityBeforeMerge =
                TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferEntity();

        SuspiciousPhoneTransferDto dtoForMerge =
                TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferDtoForMerge();

        SuspiciousPhoneTransferEntity foundEntity = mapper.mergeToEntity(dtoForMerge, entityBeforeMerge);

        assertAll("Свойства Entity",
                () -> assertEquals(1L, foundEntity.getId()),
                () -> assertEquals(987654321L, foundEntity.getPhoneTransferId()),
                () -> assertEquals(false, foundEntity.getIsBlocked()),
                () -> assertEquals(true, foundEntity.getIsSuspicious()),
                () -> assertEquals("Номер телефона используется для рассылки спама",
                        foundEntity.getBlockedReason()),
                () -> assertEquals("Номер телефона используется для мошенничества",
                        foundEntity.getSuspiciousReason())
        );
    }

    @Test
    @DisplayName("слияние в Entity, на вход подан null")
    void mergeToEntityNullTest() {
        SuspiciousPhoneTransferEntity entityBeforeMerge =
                TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferEntity();

        SuspiciousPhoneTransferEntity foundEntity = mapper.mergeToEntity(null, entityBeforeMerge);

        assertEquals(entityBeforeMerge, foundEntity);
    }
}