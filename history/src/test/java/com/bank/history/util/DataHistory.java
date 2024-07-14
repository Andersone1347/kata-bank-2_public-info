package com.bank.history.util;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;

import java.util.List;

public class DataHistory {

    public static HistoryDto createDto() {
        return HistoryDto.builder()
                .id(1L)
                .accountAuditId(1L)
                .antiFraudAuditId(1L)
                .authorizationAuditId(1L)
                .profileAuditId(1L)
                .publicBankInfoAuditId(1L)
                .transferAuditId(1L)
                .build();
    }

    public static HistoryEntity createEntity() {
        return HistoryEntity.builder()
                .id(1L)
                .accountAuditId(1L)
                .antiFraudAuditId(1L)
                .authorizationAuditId(1L)
                .profileAuditId(1L)
                .publicBankInfoAuditId(1L)
                .transferAuditId(1L)
                .build();
    }

    public static HistoryEntity preEntityForMerge() {
        return HistoryEntity.builder()
                .id(2L)
                .accountAuditId(2L)
                .antiFraudAuditId(2L)
                .authorizationAuditId(2L)
                .profileAuditId(2L)
                .publicBankInfoAuditId(2L)
                .transferAuditId(2L)
                .build();
    }

    public static HistoryEntity postEntityForMerge() {
        return HistoryEntity.builder()
                .id(2L)
                .accountAuditId(1L)
                .antiFraudAuditId(1L)
                .authorizationAuditId(1L)
                .profileAuditId(1L)
                .publicBankInfoAuditId(1L)
                .transferAuditId(1L)
                .build();
    }

    public static HistoryDto updateHistoryDto() {
        return HistoryDto.builder()
                .id(2L)
                .accountAuditId(1L)
                .antiFraudAuditId(1L)
                .authorizationAuditId(1L)
                .profileAuditId(1L)
                .publicBankInfoAuditId(1L)
                .transferAuditId(1L)
                .build();
    }

    public static List<HistoryDto> listDto() {
        return List.of(
                HistoryDto.builder()
                        .id(1L)
                        .authorizationAuditId(1L)
                        .publicBankInfoAuditId(1L)
                        .profileAuditId(1L)
                        .antiFraudAuditId(1L)
                        .accountAuditId(1L)
                        .transferAuditId(1L)
                        .build(),
                HistoryDto.builder()
                        .id(2L)
                        .authorizationAuditId(2L)
                        .publicBankInfoAuditId(2L)
                        .profileAuditId(2L)
                        .antiFraudAuditId(2L)
                        .accountAuditId(2L)
                        .transferAuditId(2L)
                        .build()

        );
    }

    public static List<HistoryEntity> listEntity() {
        return List.of(
                HistoryEntity.builder()
                        .id(1L)
                        .authorizationAuditId(1L)
                        .publicBankInfoAuditId(1L)
                        .profileAuditId(1L)
                        .antiFraudAuditId(1L)
                        .accountAuditId(1L)
                        .transferAuditId(1L)
                        .build(),
                HistoryEntity.builder()
                        .id(2L)
                        .authorizationAuditId(2L)
                        .publicBankInfoAuditId(2L)
                        .profileAuditId(2L)
                        .antiFraudAuditId(2L)
                        .accountAuditId(2L)
                        .transferAuditId(2L)
                        .build()

        );
    }
}
