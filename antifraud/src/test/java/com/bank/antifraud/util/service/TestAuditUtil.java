package com.bank.antifraud.util.service;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.entity.AuditEntity;

import java.sql.Timestamp;

/**
 * Класс для создания сущностей {@link AuditEntity}
 *                              {@link AuditDto}
 */
public class TestAuditUtil {

    public static AuditEntity createAuditEntity() {
        return new AuditEntity(
                1L,
                "Account",
                "UPDATE",
                "admin",
                "admin",
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                "{\"id\":1,\"name\":\"New Account Name\",\"balance\":1000}",
                "{\"id\":1,\"name\":\"Old Account Name\",\"balance\":500}");
    }

    public static AuditDto createAuditDto() {
        return new AuditDto(
                1L,
                "Account",
                "UPDATE",
                "admin",
                "admin",
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                "{\"id\":1,\"name\":\"New Account Name\",\"balance\":1000}",
                "{\"id\":1,\"name\":\"Old Account Name\",\"balance\":500}");
    }
}
