package com.bank.antifraud.util.service;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;

import java.util.List;

/**
 * Класс для создания сущностей {@link SuspiciousCardTransferEntity}
 * {@link SuspiciousCardTransferDto}
 */
public class TestSuspiciousCardTransferUtil {

    public static SuspiciousCardTransferEntity createSuspiciousCardTransferEntity() {

        return new SuspiciousCardTransferEntity(
                1L,
                123456789L,
                true,
                true,
                "Счет заблокирован из-за превышения лимита транзакций",
                "На счету обнаружено множество мелких транзакций в течение короткого промежутка времени");
    }

    public static SuspiciousCardTransferEntity createSuspiciousCardTransferEntityAfterMerge() {

        return new SuspiciousCardTransferEntity(
                1L,
                987654321L,
                true,
                false,
                "Счет заблокирован",
                null);
    }

    public static SuspiciousCardTransferDto createSuspiciousCardTransferDto() {

        return new SuspiciousCardTransferDto(
                1L,
                123456789L,
                true,
                true,
                "Счет заблокирован из-за превышения лимита транзакций",
                "На счету обнаружено множество мелких транзакций в течение короткого промежутка времени");
    }

    public static SuspiciousCardTransferDto createSuspiciousCardTransferDtoForMerge() {

        return new SuspiciousCardTransferDto(
                2L,
                987654321L,
                true,
                false,
                "Счет заблокирован",
                null);
    }

    public static List<SuspiciousCardTransferEntity> createListOfSuspiciousCardTransferEntities() {

        SuspiciousCardTransferEntity firstEntity =
                new SuspiciousCardTransferEntity(
                        1L,
                        987654321L,
                        false,
                        true,
                        null,
                        "Подозрительная операция");

        SuspiciousCardTransferEntity secondEntity =
                new SuspiciousCardTransferEntity(
                        2L,
                        123456789L,
                        true,
                        true,
                        "Счет заблокирован из-за превышения лимита транзакций",
                        "На счету обнаружено множество мелких транзакций в течение короткого промежутка времени");

        return List.of(firstEntity, secondEntity);
    }

    public static List<SuspiciousCardTransferDto> createListOfSuspiciousCardTransferDtos() {

        SuspiciousCardTransferDto firstEntity =
                new SuspiciousCardTransferDto(
                        1L,
                        987654321L,
                        false,
                        true,
                        null,
                        "Подозрительная операция");

        SuspiciousCardTransferDto secondEntity =
                new SuspiciousCardTransferDto(
                        2L,
                        123456789L,
                        true,
                        true,
                        "Счет заблокирован из-за превышения лимита транзакций",
                        "На счету обнаружено множество мелких транзакций в течение короткого промежутка времени");

        return List.of(firstEntity, secondEntity);
    }
}
