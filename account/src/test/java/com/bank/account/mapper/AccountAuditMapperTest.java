package com.bank.account.mapper;

import com.bank.account.dto.AuditDto;
import com.bank.account.entity.AuditEntity;
import com.bank.account.util.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class AccountAuditMapperTest {
    private final AccountAuditMapper mapper = Mappers.getMapper(AccountAuditMapper.class);
    @Test
    @DisplayName("Должен корректно маппить AuditEntity в AuditDto")
    void toDto() {
        AuditEntity auditEntity = new DataUtils().createAuditEntity();

        AuditDto actualResult = mapper.toDto(auditEntity);

        AuditDto expectedResult = new DataUtils().createAuditDto();
        assertThat(actualResult).isEqualTo(expectedResult);
    }
}