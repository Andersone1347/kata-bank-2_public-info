package com.bank.account.util;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.dto.AuditDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.entity.AuditEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class DataUtils {
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

    public String createTimeStamp() {
        Timestamp timestamp = Timestamp.valueOf("2024-06-26 12:34:56");
        ZonedDateTime utcZonedDateTime = timestamp.toLocalDateTime().atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("UTC"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return utcZonedDateTime.format(formatter) + "+00:00";
    }

    public AccountDetailsDto createAccountDetailsDto() {
        return AccountDetailsDto.builder()
                .id(1L)
                .passportId(1L)
                .accountNumber(1L)
                .bankDetailsId(1L)
                .money(new BigDecimal("1000.50"))
                .negativeBalance(false)
                .profileId(1L)
                .build();
    }

    public AccountDetailsDto createUpdatedDetailsDto() {
        return AccountDetailsDto.builder()
                .id(3L)
                .passportId(3L)
                .accountNumber(3L)
                .bankDetailsId(3L)
                .money(new BigDecimal("1"))
                .negativeBalance(true)
                .profileId(3L)
                .build();
    }

    public AccountDetailsEntity createAccountDetailsEntity() {
        return AccountDetailsEntity.builder()
                .id(1L)
                .passportId(1L)
                .accountNumber(1L)
                .bankDetailsId(1L)
                .money(new BigDecimal("1000.50"))
                .negativeBalance(false)
                .profileId(1L)
                .build();
    }

    public AccountDetailsEntity createPreMergerAccountDetailsEntity() {
        return AccountDetailsEntity.builder()
                .id(3L)
                .passportId(3L)
                .accountNumber(3L)
                .bankDetailsId(3L)
                .money(new BigDecimal("1"))
                .negativeBalance(true)
                .profileId(3L)
                .build();
    }

    public AccountDetailsEntity createPostMergerAccountDetailsEntity() {
        return AccountDetailsEntity.builder()
                .id(3L)
                .passportId(1L)
                .accountNumber(1L)
                .bankDetailsId(1L)
                .money(new BigDecimal("1000.50"))
                .negativeBalance(false)
                .profileId(1L)
                .build();
    }

    public List<AccountDetailsDto> createAccountDetailsDtoList() {
        return Arrays.asList(
                AccountDetailsDto.builder()
                        .id(1L)
                        .passportId(1L)
                        .accountNumber(1L)
                        .bankDetailsId(1L)
                        .money(new BigDecimal("1000.50"))
                        .negativeBalance(false)
                        .profileId(1L)
                        .build(),
                AccountDetailsDto.builder()
                        .id(2L)
                        .passportId(2L)
                        .accountNumber(2L)
                        .bankDetailsId(2L)
                        .money(new BigDecimal("1000.50"))
                        .negativeBalance(false)
                        .profileId(2L)
                        .build()
        );
    }

    public List<AccountDetailsEntity> createAccountDetailsEntityList() {
        return Arrays.asList(
                AccountDetailsEntity.builder()
                        .id(1L)
                        .passportId(1L)
                        .accountNumber(1L)
                        .bankDetailsId(1L)
                        .money(new BigDecimal("1000.50"))
                        .negativeBalance(false)
                        .profileId(1L)
                        .build(),
                AccountDetailsEntity.builder()
                        .id(2L)
                        .passportId(2L)
                        .accountNumber(2L)
                        .bankDetailsId(2L)
                        .money(new BigDecimal("1000.50"))
                        .negativeBalance(false)
                        .profileId(2L)
                        .build()
        );
    }
}
