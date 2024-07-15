package com.bank.authorization.mapper;

import com.bank.authorization.dto.AuditDto;
import com.bank.authorization.entity.AuditEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AuditMapperTest {

    @Test
    @DisplayName("маппинг в dto")
    void toDtoTest() {
        AuditEntity auditEntity = new AuditEntity(
                123L,
                "USER",
                "CREATE",
                "user1",
                "user2",
                Timestamp.valueOf("2024-03-08 10:00:00"),
                Timestamp.valueOf("2024-03-08 10:05:00"),
                "{\"name\":\"John\"}",
                "{\"name\":\"Mary\"}"
        );

        AuditDto auditDto = new AuditMapperImpl().toDto(auditEntity);

        assertAll(
                () -> assertThat(auditDto).isNotNull(),
                () -> assertThat(auditDto.getId()).isEqualTo(123L),
                () -> assertThat(auditDto.getEntityType()).isEqualTo("USER"),
                () -> assertThat(auditDto.getOperationType()).isEqualTo("CREATE"),
                () -> assertThat(auditDto.getCreatedBy()).isEqualTo("user1"),
                () -> assertThat(auditDto.getModifiedBy()).isEqualTo("user2"),
                () -> assertThat(auditDto.getCreatedAt()).isEqualTo(Timestamp.valueOf("2024-03-08 10:00:00")),
                () -> assertThat(auditDto.getModifiedAt()).isEqualTo(Timestamp.valueOf("2024-03-08 10:05:00")),
                () -> assertThat(auditDto.getNewEntityJson()).isEqualTo("{\"name\":\"John\"}"),
                () -> assertThat(auditDto.getEntityJson()).isEqualTo("{\"name\":\"Mary\"}")
        );
    }
}