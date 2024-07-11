package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.entity.AuditEntity;
import com.bank.antifraud.util.service.TestAuditUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * тест для  {@link AuditMapperImpl}
 */
class AuditMapperImplTest {

    private final AuditMapper mapper = new AuditMapperImpl();

    @Test
    @DisplayName("маппинг Entity в Dto")
    void toDtoTest() {
        AuditEntity auditEntity = TestAuditUtil.createAuditEntity();

        AuditDto foundDto = mapper.toDto(auditEntity);

        assertAll("Свойства Dto",
                () -> assertNotNull(foundDto),
                () -> assertEquals(1L, foundDto.getId()),
                () -> assertEquals("Account", foundDto.getEntityType()),
                () -> assertEquals("UPDATE", foundDto.getOperationType()),
                () -> assertEquals("admin", foundDto.getCreatedBy()),
                () -> assertEquals("admin", foundDto.getModifiedBy()),
                () -> assertEquals("{\"id\":1,\"name\":\"New Account Name\",\"balance\":1000}",
                        foundDto.getNewEntityJson()),
                () -> assertEquals("{\"id\":1,\"name\":\"Old Account Name\",\"balance\":500}",
                        foundDto.getEntityJson())
        );
    }

    @Test
    @DisplayName("маппинг Entity в Dto, на вход подан null")
    void toDtoNullTest() {
        AuditDto auditDto = mapper.toDto(null);

        assertNull(auditDto);
    }
}