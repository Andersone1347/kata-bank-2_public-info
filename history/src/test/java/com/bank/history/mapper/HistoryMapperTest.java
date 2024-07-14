package com.bank.history.mapper;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import com.bank.history.util.DataHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class HistoryMapperTest {
    private final HistoryMapper mapper = new HistoryMapperImpl();

    @Test
    @DisplayName("маппинг в Entity")
    void toEntityTest() {
        HistoryDto historyDto = DataHistory.createDto();

        HistoryEntity actual = mapper.toEntity(historyDto);

        HistoryEntity expected = DataHistory.createEntity();

        assertAll(
                () -> assertEquals(actual.getAccountAuditId(), expected.getAccountAuditId()),
                () -> assertEquals(actual.getAntiFraudAuditId(), expected.getAntiFraudAuditId()),
                () -> assertEquals(actual.getAuthorizationAuditId(), expected.getAuthorizationAuditId()),
                () -> assertEquals(actual.getProfileAuditId(), expected.getProfileAuditId()),
                () -> assertEquals(actual.getPublicBankInfoAuditId(), expected.getPublicBankInfoAuditId()),
                () -> assertEquals(actual.getTransferAuditId(), expected.getTransferAuditId())
        );
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан null")
    void noEntityNullTest() {
        HistoryDto historyDto = null;

        HistoryEntity actual = mapper.toEntity(historyDto);

        assertNull(actual);
    }

    @Test
    @DisplayName("маппинг в dto")
    void toDtoTest() {

        HistoryEntity historyEntity = DataHistory.createEntity();

        HistoryDto expected = DataHistory.createDto();

        HistoryDto actual = mapper.toDto(historyEntity);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан null")
    void noDtoNullTest() {
        HistoryEntity historyEntity = null;

        HistoryDto actual = mapper.toDto(historyEntity);
        assertNull(actual);
    }

    @Test
    @DisplayName("слияние в Entity")
    void mergeToEntityTest() {
        HistoryEntity historyEntity = DataHistory.preEntityForMerge();
        HistoryDto dto = DataHistory.createDto();

        HistoryEntity actual = mapper.mergeToEntity(dto, historyEntity);

        HistoryEntity expected = DataHistory.postEntityForMerge();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("слияние в Entity, на вход подан null")
    void mergeToEntityNullTest() {
        HistoryEntity historyEntity = DataHistory.preEntityForMerge();
        HistoryDto dto = null;

        HistoryEntity actual = mapper.mergeToEntity(dto, historyEntity);

        HistoryEntity expected = DataHistory.preEntityForMerge();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("маппинг в список dto")
    void toListDtoTest() {
        List<HistoryEntity> entities = DataHistory.listEntity();
        List<HistoryDto> actual = mapper.toListDto(entities);

        List<HistoryDto> expected = DataHistory.listDto();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("маппинг в список dto, на вход подан null")
    void toListDtoNullTest() {
        List<HistoryEntity> entities = null;
        List<HistoryDto> actual = mapper.toListDto(entities);

        assertNull(actual);
    }
}