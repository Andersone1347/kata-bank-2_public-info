package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import com.bank.antifraud.util.service.TestSuspiciousCardTransferUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * тест для {@link SuspiciousCardTransferMapperImpl}
 */

class SuspiciousCardTransferMapperImplTest {

    private final SuspiciousCardTransferMapper mapper = Mappers.getMapper(SuspiciousCardTransferMapper.class);

    @Test
    @DisplayName("маппинг Entity в Dto")
    void toDtoTest() {
        SuspiciousCardTransferEntity expectedEntity =
                TestSuspiciousCardTransferUtil.createSuspiciousCardTransferEntity();

        SuspiciousCardTransferDto foundDto = mapper.toDto(expectedEntity);

        assertAll("Свойства Dto",
                () -> assertEquals(1L, foundDto.getId()),
                () -> assertEquals(123456789L, foundDto.getCardTransferId()),
                () -> assertEquals(true, foundDto.getIsSuspicious()),
                () -> assertEquals("На счету обнаружено множество мелких транзакций в течение короткого " +
                        "промежутка времени", foundDto.getSuspiciousReason()),
                () -> assertEquals(true, foundDto.getIsBlocked()),
                () -> assertEquals("Счет заблокирован из-за превышения лимита транзакций",
                        foundDto.getBlockedReason())
        );
    }

    @Test
    @DisplayName("маппинг Entity в Dto, на вход подан null")
    void toDtoNullTest() {
        SuspiciousCardTransferDto dto = mapper.toDto(null);

        assertNull(dto);
    }

    @Test
    @DisplayName("маппинг Dto в Entity")
    void toEntityTest() {
        SuspiciousCardTransferDto expectedDto = TestSuspiciousCardTransferUtil.createSuspiciousCardTransferDto();

        SuspiciousCardTransferEntity foundEntity = mapper.toEntity(expectedDto);

        assertAll("Свойства Entity",
                () -> assertEquals(123456789L, foundEntity.getCardTransferId()),
                () -> assertEquals(true, foundEntity.getIsSuspicious()),
                () -> assertEquals("На счету обнаружено множество мелких транзакций в течение короткого " +
                        "промежутка времени", foundEntity.getSuspiciousReason()),
                () -> assertEquals(true, foundEntity.getIsBlocked()),
                () -> assertEquals("Счет заблокирован из-за превышения лимита транзакций",
                        foundEntity.getBlockedReason())
        );
    }

    @Test
    @DisplayName("маппинг Dto в Entity, на вход подан null")
    void toEntityNullTest() {
        SuspiciousCardTransferEntity entity = mapper.toEntity(null);

        assertNull(entity);
    }


    @Test
    @DisplayName("маппинг Entities в Dto's")
    void mergeToListDtoTest() {
        List<SuspiciousCardTransferEntity> expectedEntities =
                TestSuspiciousCardTransferUtil.createListOfSuspiciousCardTransferEntities();

        List<SuspiciousCardTransferDto> foundDtos = mapper.toListDto(expectedEntities);

        assertAll("Свойства Dto 1",
                () -> assertEquals(1L, foundDtos.get(0).getId()),
                () -> assertEquals(987654321L, foundDtos.get(0).getCardTransferId()),
                () -> assertEquals(false, foundDtos.get(0).getIsBlocked()),
                () -> assertEquals(true, foundDtos.get(0).getIsSuspicious()),
                () -> assertNull(foundDtos.get(0).getBlockedReason()),
                () -> assertEquals("Подозрительная операция", foundDtos.get(0).getSuspiciousReason())
        );

        assertAll("Свойства Dto 2",
                () -> assertEquals(2L, foundDtos.get(1).getId()),
                () -> assertEquals(123456789L, foundDtos.get(1).getCardTransferId()),
                () -> assertEquals(true, foundDtos.get(1).getIsBlocked()),
                () -> assertEquals(true, foundDtos.get(1).getIsSuspicious()),
                () -> assertEquals("Счет заблокирован из-за превышения лимита транзакций",
                        foundDtos.get(1).getBlockedReason()),
                () -> assertEquals("На счету обнаружено множество мелких транзакций в течение короткого " +
                        "промежутка времени", foundDtos.get(1).getSuspiciousReason())
        );
    }

    @Test
    @DisplayName("маппинг Entities в Dto's, на вход подан null")
    void mergeToListDtoNullTest() {
        List<SuspiciousCardTransferDto> foundDtos = mapper.toListDto(null);

        assertNull(foundDtos);
    }

    @Test
    @DisplayName("слияние в Entity")
    void mergeToEntityTest() {
        SuspiciousCardTransferEntity entityBeforeMerge =
                TestSuspiciousCardTransferUtil.createSuspiciousCardTransferEntity();

        SuspiciousCardTransferDto dtoForMerge =
                TestSuspiciousCardTransferUtil.createSuspiciousCardTransferDtoForMerge();

        SuspiciousCardTransferEntity foundEntity = mapper.mergeToEntity(dtoForMerge, entityBeforeMerge);

        assertAll("Свойства Entity",
                () -> assertEquals(1L, foundEntity.getId()),
                () -> assertEquals(987654321L, foundEntity.getCardTransferId()),
                () -> assertEquals(true, foundEntity.getIsBlocked()),
                () -> assertEquals(false, foundEntity.getIsSuspicious()),
                () -> assertEquals("Счет заблокирован", foundEntity.getBlockedReason()),
                () -> assertNull(foundEntity.getSuspiciousReason())
        );
    }

    @Test
    @DisplayName("слияние в Entity, на вход подан null")
    void mergeToEntityNullTest() {
        SuspiciousCardTransferEntity entityBeforeMerge =
                TestSuspiciousCardTransferUtil.createSuspiciousCardTransferEntity();

        SuspiciousCardTransferEntity foundEntity = mapper.mergeToEntity(null, entityBeforeMerge);

        assertEquals(entityBeforeMerge, foundEntity);
    }
}