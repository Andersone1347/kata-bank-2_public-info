package com.bank.history.dto;

import com.bank.history.entity.HistoryEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;


/**
 * Dto для {@link HistoryEntity}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryDto {
    Long id;
    Long transferAuditId;
    Long profileAuditId;
    Long accountAuditId;
    Long antiFraudAuditId;
    Long publicBankInfoAuditId;
    Long authorizationAuditId;
}
