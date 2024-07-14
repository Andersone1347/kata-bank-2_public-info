package com.bank.profile.dto;

import com.bank.profile.entity.AccountDetailsIdEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


import java.io.Serializable;

/**
 * ДТО для сущности {@link AccountDetailsIdEntity}
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailsIdDto implements Serializable {
    private Long id;
    private Long accountId;
    private ProfileDto profile;
}
