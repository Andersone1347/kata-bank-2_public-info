package com.bank.antifraud.util.service;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;

import java.util.List;

/**
 * Класс для создания сущностей {@link SuspiciousAccountTransferEntity}
 * {@link SuspiciousAccountTransferDto}
 */
public class TestSuspiciousAccountTransferUtil {
    public static SuspiciousAccountTransferEntity createSuspiciousAccountTransferEntity() {
        return new SuspiciousAccountTransferEntity
                (1L, 1L, true, false, "Счет отправителя заблокирован",
                        null);
    }

    public static SuspiciousAccountTransferEntity createSuspiciousAccountTransferEntityAfterMerge() {

        return new SuspiciousAccountTransferEntity(
                1L,
                987654321L,
                true,
                false,
                "Счет заблокирован",
                null);
    }


    public static SuspiciousAccountTransferDto createSuspiciousAccountTransferDto() {
        return new SuspiciousAccountTransferDto(
                1L,
                1L,
                true,
                false,
                "Счет отправителя заблокирован",
                null);
    }

    public static SuspiciousAccountTransferDto createSuspiciousAccountTransferDtoForMerge() {

        return new SuspiciousAccountTransferDto(
                2L,
                987654321L,
                true,
                false,
                "Счет заблокирован",
                null);
    }

    public static List<SuspiciousAccountTransferEntity> createListOfSuspiciousAccountTransferEntities() {
        SuspiciousAccountTransferEntity firstEntity = new SuspiciousAccountTransferEntity(
                1L,
                2L,
                false,
                true,
                null,
                "На счету обнаружено множество подозрительных транзакций"
        );
        SuspiciousAccountTransferEntity secondEntity = new SuspiciousAccountTransferEntity(
                2L,
                3L,
                true,
                true,
                "Счет получателя заблокирован из-за превышения лимита транзакций",
                "На счету обнаружено множество подозрительных транзакций"
        );

        return List.of(firstEntity, secondEntity);
    }


    public static List<SuspiciousAccountTransferDto> createListOfSuspiciousAccountTransferDtos() {

        SuspiciousAccountTransferDto firstDto = new SuspiciousAccountTransferDto(
                1L,
                2L,
                false,
                true,
                null,
                "На счету обнаружено множество подозрительных транзакций"
        );
        SuspiciousAccountTransferDto secondDto = new SuspiciousAccountTransferDto(
                2L,
                3L,
                true,
                true,
                "Счет получателя заблокирован из-за превышения лимита транзакций",
                "На счету обнаружено множество подозрительных транзакций"
        );

        return List.of(firstDto, secondDto);
    }
}
