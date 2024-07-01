package com.bank.account.service;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.mapper.AccountDetailsMapper;
import com.bank.account.repository.AccountDetailsRepository;
import com.bank.account.service.common.ExceptionReturner;
import com.bank.account.util.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountDetailsServiceImplTest {

    private static final String MESSAGE_PREFIX = "Не существующий id = ";

    @Mock
    private AccountDetailsMapper mapper;

    @Mock
    private AccountDetailsRepository repository;

    @Mock
    private ExceptionReturner exceptionReturner;

    @InjectMocks
    private AccountDetailsServiceImpl service;

    @Test
    @DisplayName("Возврщает AccountDetailsDto: успешный запрос по id")
    void findById_shouldReturnAccountDetailsDto_whenExists() {
        AccountDetailsEntity accountDetailsEntity = new DataUtils().createAccountDetailsEntity();
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(accountDetailsEntity));
        when(mapper.toDto(accountDetailsEntity)).thenReturn(accountDetailsDto);

        AccountDetailsDto actualResult = service.findById(1L);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(accountDetailsDto);
        verify(repository).findById(1L);
        verify(mapper).toDto(accountDetailsEntity);
    }

    @Test
    @DisplayName("Должен выбросить исключение EntityNotFoundException: id не существует")
    void findById_shouldThrowException_whenDoesNotExist() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(exceptionReturner.getEntityNotFoundException(anyString()))
                .thenReturn(new EntityNotFoundException(MESSAGE_PREFIX + 1));

        assertThrows(EntityNotFoundException.class, () -> service.findById(1L));

        verify(repository).findById(1L);
        verify(exceptionReturner).getEntityNotFoundException(MESSAGE_PREFIX + 1);
    }

    @Test
    @DisplayName("Должен вернуть список AccountDetailsDto: успешный запрос по списку id")
    void findAllById_shouldReturnAccountDetailsDtoList_whenAllIdExists() {
        List<AccountDetailsEntity> entityList = new DataUtils().createAccountDetailsEntityList();
        List<AccountDetailsDto> dtoList = new DataUtils().createAccountDetailsDtoList();
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(entityList.get(0)));
        when(repository.findById(2L)).thenReturn(Optional.ofNullable(entityList.get(1)));
        when(mapper.toDtoList(entityList)).thenReturn(dtoList);

        List<AccountDetailsDto> resultList = service.findAllById(List.of(1L, 2L));

        assertThat(resultList).isEqualTo(dtoList);
        verify(repository, times(2)).findById(anyLong());
        verify(mapper).toDtoList(entityList);
    }

    @Test
    @DisplayName("Должен выброчить исключение EntityNotFoundException: id не существует")
    void findAllById_shouldThrowException_whenIdDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.of(new AccountDetailsEntity()));
        when(repository.findById(2L)).thenReturn(Optional.empty());
        when(exceptionReturner.getEntityNotFoundException(anyString()))
                .thenReturn(new EntityNotFoundException(MESSAGE_PREFIX + 2));

        assertThrows(EntityNotFoundException.class, () -> service.findAllById(List.of(1L, 2L)));

        verify(repository, times(2)).findById(anyLong());
        verify(exceptionReturner).getEntityNotFoundException(MESSAGE_PREFIX + 2);
    }

    @Test
    @DisplayName("Должен вернуть AccountDetailsDto: успешное сохранение в базу данных")
    void save_shouldReturnAccountDetailsDto_whenSaveSuccess() {
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();
        AccountDetailsEntity accountDetailsEntity = new DataUtils().createAccountDetailsEntity();
        when(mapper.toEntity(accountDetailsDto)).thenReturn(accountDetailsEntity);
        when(repository.save(accountDetailsEntity)).thenReturn(accountDetailsEntity);
        when(mapper.toDto(accountDetailsEntity)).thenReturn(accountDetailsDto);

        AccountDetailsDto actualResult = service.save(accountDetailsDto);

        assertThat(actualResult).isEqualTo(accountDetailsDto);
        verify(mapper).toEntity(accountDetailsDto);
        verify(repository).save(accountDetailsEntity);
        verify(mapper).toDto(accountDetailsEntity);

    }

    @Test
    @DisplayName("Должен выбросить DataIntegrityViolationException: ошибка сохранения в базу данных")
    void save_shouldThrowException_whenRepositorySaveFails() {
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();

        when(mapper.toEntity(any(AccountDetailsDto.class))).thenReturn(new AccountDetailsEntity());
        when(repository.save(any(AccountDetailsEntity.class))).thenThrow(new DataIntegrityViolationException("message"));

        assertThrows(DataIntegrityViolationException.class, () -> service.save(accountDetailsDto));

        verify(mapper).toEntity(any(AccountDetailsDto.class));
        verify(repository).save(any(AccountDetailsEntity.class));
    }

    @Test
    @DisplayName("Должен вернуть AccountDetailsDto: успешное обновление данных")
    void update_shouldReturnAccountDetailsDto_whenUpdateSuccess() {
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();
        AccountDetailsDto updatedDetailsDto = new DataUtils().createUpdatedDetailsDto();
        AccountDetailsEntity preMergerEntity = new DataUtils().createPreMergerAccountDetailsEntity();
        AccountDetailsEntity postMergerEntity = new DataUtils().createPostMergerAccountDetailsEntity();

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(preMergerEntity));
        when(mapper.mergeToEntity(preMergerEntity, accountDetailsDto)).thenReturn(postMergerEntity);
        when((repository.save(postMergerEntity))).thenReturn(postMergerEntity);
        when((mapper.toDto(postMergerEntity))).thenReturn(updatedDetailsDto);

        AccountDetailsDto actualResult = service.update(3L, accountDetailsDto);

        assertThat(actualResult).isEqualTo(updatedDetailsDto);
        verify(repository).findById(3L);
        verify(mapper).mergeToEntity(preMergerEntity, accountDetailsDto);
        verify(repository).save(postMergerEntity);
        verify(mapper).toDto(postMergerEntity);
    }

    @Test
    @DisplayName("Должен выбросить EntityNotFoundException: id не существует")
    void update_shouldThrowException_whenIdDoesNotExist() {
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(exceptionReturner.getEntityNotFoundException(MESSAGE_PREFIX + 1))
                .thenThrow(new EntityNotFoundException(MESSAGE_PREFIX + 1));

        assertThrows(EntityNotFoundException.class, () -> service.update(1L, accountDetailsDto));

        verify(repository).findById(1L);
        verify(exceptionReturner).getEntityNotFoundException(MESSAGE_PREFIX + 1);
    }
}