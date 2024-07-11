package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import com.bank.antifraud.util.service.TestSuspiciousAccountTransferUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * тест для  {@link SuspiciousAccountTransferMapper}
 */
class SuspiciousAccountTransferMapperImplTest {

    private final SuspiciousAccountTransferMapper mapper = Mappers.getMapper(SuspiciousAccountTransferMapper.class);

    @Test
    @DisplayName("маппинг Entity в Dto")
    void toDtoTest() {
        SuspiciousAccountTransferEntity expectedEntity =
                TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferEntity();

        SuspiciousAccountTransferDto foundDto = mapper.toDto(expectedEntity);

        assertAll("Свойства Dto",
                () -> assertEquals(1L, foundDto.getId()),
                () -> assertEquals(1L, foundDto.getAccountTransferId()),
                () -> assertEquals(false, foundDto.getIsSuspicious()),
                () -> assertNull(foundDto.getSuspiciousReason()),
                () -> assertEquals(true, foundDto.getIsBlocked()),
                () -> assertEquals("Счет отправителя заблокирован", foundDto.getBlockedReason())
        );
    }

    @Test
    @DisplayName("маппинг Entity в Dto, на вход подан null")
    void toDtoNullTest() {
        SuspiciousAccountTransferDto dto = mapper.toDto(null);

        assertNull(dto);
    }

    @Test
    @DisplayName("маппинг Dto в Entity")
    void toEntityTest() {
        SuspiciousAccountTransferDto expectedDto =
                TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferDto();

        SuspiciousAccountTransferEntity foundEntity = mapper.toEntity(expectedDto);

        assertAll("Свойства Entity",
                () -> assertEquals(1L, foundEntity.getAccountTransferId()),
                () -> assertEquals(false, foundEntity.getIsSuspicious()),
                () -> assertNull(foundEntity.getSuspiciousReason()),
                () -> assertEquals(true, foundEntity.getIsBlocked()),
                () -> assertEquals("Счет отправителя заблокирован", foundEntity.getBlockedReason())
        );
    }

    @Test
    @DisplayName("маппинг Dto в Entity, на вход подан null")
    void toEntityNullTest() {
        SuspiciousAccountTransferEntity entity = mapper.toEntity(null);

        assertNull(entity);
    }

    @Test
    @DisplayName("маппинг Entities в Dto's")
    void mergeToListDtoTest() {
        List<SuspiciousAccountTransferEntity> expectedEntities =
                TestSuspiciousAccountTransferUtil.createListOfSuspiciousAccountTransferEntities();

        List<SuspiciousAccountTransferDto> foundDtos = mapper.toListDto(expectedEntities);

        assertAll("Свойства Dto 1",
                () -> assertEquals(1L, foundDtos.get(0).getId()),
                () -> assertEquals(2L, foundDtos.get(0).getAccountTransferId()),
                () -> assertEquals(false, foundDtos.get(0).getIsBlocked()),
                () -> assertEquals(true, foundDtos.get(0).getIsSuspicious()),
                () -> assertNull(foundDtos.get(0).getBlockedReason()),
                () -> assertEquals("На счету обнаружено множество подозрительных транзакций",
                        foundDtos.get(0).getSuspiciousReason())
        );

        assertAll("Свойства Dto 2",
                () -> assertEquals(2L, foundDtos.get(1).getId()),
                () -> assertEquals(3L, foundDtos.get(1).getAccountTransferId()),
                () -> assertEquals(true, foundDtos.get(1).getIsBlocked()),
                () -> assertEquals(true, foundDtos.get(1).getIsSuspicious()),
                () -> assertEquals("Счет получателя заблокирован из-за превышения лимита транзакций",
                        foundDtos.get(1).getBlockedReason()),
                () -> assertEquals("На счету обнаружено множество подозрительных транзакций",
                        foundDtos.get(1).getSuspiciousReason())
        );
    }

    @Test
    @DisplayName("маппинг Entities в Dto's, на вход подан null")
    void mergeToListDtoNullTest() {
        List<SuspiciousAccountTransferDto> foundDtos = mapper.toListDto(null);

        assertNull(foundDtos);
    }


    @Test
    @DisplayName("слияние в Entity")
    void mergeToEntityTest() {
        SuspiciousAccountTransferEntity entityBeforeMerge =
                TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferEntity();

        SuspiciousAccountTransferDto dtoForMerge =
                TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferDtoForMerge();

        SuspiciousAccountTransferEntity foundEntity = mapper.mergeToEntity(dtoForMerge, entityBeforeMerge);

        assertAll("Свойства Entity",
                () -> assertEquals(1L, foundEntity.getId()),
                () -> assertEquals(987654321L, foundEntity.getAccountTransferId()),
                () -> assertEquals(true, foundEntity.getIsBlocked()),
                () -> assertEquals(false, foundEntity.getIsSuspicious()),
                () -> assertEquals("Счет заблокирован", foundEntity.getBlockedReason()),
                () -> assertNull(foundEntity.getSuspiciousReason())
        );
    }

    @Test
    @DisplayName("слияние в Entity, на вход подан null")
    void mergeToEntityNullTest() {
        SuspiciousAccountTransferEntity entityBeforeMerge =
                TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferEntity();

        SuspiciousAccountTransferEntity foundEntity = mapper.mergeToEntity(null, entityBeforeMerge);

        assertEquals(entityBeforeMerge, foundEntity);
    }
}