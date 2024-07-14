package com.bank.profile.mapper;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;
import com.bank.profile.util.AuditUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class AuditMapperTest {

    private final AuditMapper mapper = Mappers.getMapper(AuditMapper.class);


    @Test
    @DisplayName("маппинг в dto")
    void toDtoTest() {
        AuditDto auditDto = new AuditUtil().createAuditDto();
        AuditEntity auditEntity = new AuditUtil().createAuditEntity();

        AuditDto actualDto = mapper.toDto(auditEntity);

        assertThat(actualDto).isEqualTo(auditDto);
    }
}
