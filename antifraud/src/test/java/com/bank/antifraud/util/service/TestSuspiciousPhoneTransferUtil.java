package com.bank.antifraud.util.service;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;

import java.util.List;

/**
 * Класс для создания сущностей {@link SuspiciousPhoneTransferEntity}
 * {@link SuspiciousPhoneTransferDto}
 */
public class TestSuspiciousPhoneTransferUtil {

    public static SuspiciousPhoneTransferEntity createSuspiciousPhoneTransferEntity() {
        return new SuspiciousPhoneTransferEntity(
                1L,
                123456789L,
                true,
                true,
                "Номер телефона заблокирован из-за подозрительной активности",
                "Номер телефона используется для мошенничества");
    }

    public static SuspiciousPhoneTransferEntity createSuspiciousPhoneTransferEntityAfterMerge() {
        return new SuspiciousPhoneTransferEntity(
                1L,
                987654321L,
                false,
                true,
                "Номер телефона используется для рассылки спама",
                "Номер телефона используется для мошенничества"
        );
    }

    public static SuspiciousPhoneTransferDto createSuspiciousPhoneTransferDto() {
        return new SuspiciousPhoneTransferDto(
                1L,
                123456789L,
                true,
                true,
                "Номер телефона заблокирован из-за подозрительной активности",
                "Номер телефона используется для мошенничества");
    }

    public static SuspiciousPhoneTransferDto createSuspiciousPhoneTransferDtoForMerge() {
        return new SuspiciousPhoneTransferDto(
                2L,
                987654321L,
                false,
                true,
                "Номер телефона используется для рассылки спама",
                "Номер телефона используется для мошенничества"
        );
    }

    public static List<SuspiciousPhoneTransferEntity> createListOfSuspiciousPhoneTransferEntities() {
        SuspiciousPhoneTransferEntity firstEntity = new SuspiciousPhoneTransferEntity(
                1L,
                111222333L,
                true,
                false,
                "Номер телефона заблокирован из-за превышения лимита звонков",
                null
        );
        SuspiciousPhoneTransferEntity secondEntity = new SuspiciousPhoneTransferEntity(
                2L,
                444555666L,
                false,
                true,
                null,
                "Номер телефона используется для мошенничества с использованием автоматизированных звонков"
        );

        return List.of(firstEntity, secondEntity);
    }


    public static List<SuspiciousPhoneTransferDto> createListOfSuspiciousPhoneTransferDtos() {

        SuspiciousPhoneTransferDto firstDto = new SuspiciousPhoneTransferDto(
                1L,
                111222333L,
                true,
                false,
                "Номер телефона заблокирован из-за превышения лимита звонков",
                null
        );
        SuspiciousPhoneTransferDto secondDto = new SuspiciousPhoneTransferDto(
                2L,
                444555666L,
                false,
                true,
                null,
                "Номер телефона используется для мошенничества с использованием автоматизированных звонков"
        );

        return List.of(firstDto, secondDto);
    }
}
