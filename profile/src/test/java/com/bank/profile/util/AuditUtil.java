package com.bank.profile.util;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;

import java.sql.Timestamp;

public class AuditUtil {
    public AuditDto createAuditDto() {
        return AuditDto.builder()
                .id(1L)
                .entityType("fakeType")
                .operationType("fakeType")
                .createdBy("fakeUser")
                .modifiedBy("fakeUser")
                .createdAt(Timestamp.valueOf("2024-06-26 12:34:56"))
                .modifiedAt(Timestamp.valueOf("2024-06-26 12:34:56"))
                .newEntityJson("fakeJson")
                .entityJson("fakeJson")
                .build();
    }

    public AuditEntity createAuditEntity() {
        return AuditEntity.builder()
                .id(1L)
                .entityType("fakeType")
                .operationType("fakeType")
                .createdBy("fakeUser")
                .modifiedBy("fakeUser")
                .createdAt(Timestamp.valueOf("2024-06-26 12:34:56"))
                .modifiedAt(Timestamp.valueOf("2024-06-26 12:34:56"))
                .newEntityJson("fakeJson")
                .entityJson("fakeJson")
                .build();

    }
}
