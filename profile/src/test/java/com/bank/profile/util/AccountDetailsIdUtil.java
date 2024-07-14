package com.bank.profile.util;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsIdEntity;

import java.util.Arrays;
import java.util.List;

public class AccountDetailsIdUtil {

    public AccountDetailsIdDto createAccountDetailsIdDto() {
        return AccountDetailsIdDto.builder()
                .id(1L)
                .accountId(1L)
                .profile(new ProfileUtil().createProfileDto())
                .build();
    }


    public AccountDetailsIdEntity createAccountDetailsIdEntity() {
        return AccountDetailsIdEntity.builder()
                .id(1L)
                .accountId(1L)
                .profile(new ProfileUtil().createProfileEntity())
                .build();
    }

    public List<AccountDetailsIdDto> createAccountDetailsIdDtoList() {
        return Arrays.asList(
                AccountDetailsIdDto.builder()
                        .id(1L)
                        .accountId(1L)
                        .profile(new ProfileUtil().createProfileDto())
                        .build(),
                AccountDetailsIdDto.builder()
                        .id(2L)
                        .accountId(1L)
                        .profile(new ProfileUtil().createProfileDto())
                        .build());
    }

    public List<AccountDetailsIdEntity> createAccountDetailsIdEntityList() {
        return Arrays.asList(
                AccountDetailsIdEntity.builder()
                        .id(1L)
                        .accountId(1L)
                        .profile(new ProfileUtil().createProfileEntity())
                        .build(),
                AccountDetailsIdEntity.builder()
                        .id(2L)
                        .accountId(1L)
                        .profile(new ProfileUtil().createProfileEntity())
                        .build());
    }


    public AccountDetailsIdEntity createPreMergerAccountDetailsIdEntity() {
        return AccountDetailsIdEntity.builder()
                .id(3L)
                .accountId(3L)
                .profile(new ProfileUtil().createProfileEntity())
                .build();
    }

    public AccountDetailsIdEntity createPostMergerAccountDetailsIdEntity() {
        return AccountDetailsIdEntity.builder()
                .id(3L)
                .accountId(1L)
                .profile(new ProfileUtil().createProfileEntity())
                .build();
    }


   public AccountDetailsIdDto createUpdatedAccountDetailsIdDto(){
       return AccountDetailsIdDto.builder()
               .id(3L)
               .accountId(3L)
               .profile(new ProfileUtil().createProfileDto())
               .build();
   }




}
