package com.bank.authorization.service;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.UserEntity;
import com.bank.authorization.mapper.UserMapper;
import com.bank.authorization.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserMapper mapper;

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findById_shouldReturnUserDto() {
        UserEntity userEntity = new UserEntity(1L, "admin", 100L, "pass");
        UserDto userDto = new UserDto();
        when(repository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        when(mapper.toDTO(userEntity)).thenReturn(userDto);

        UserDto actualDto = userService.findById(userEntity.getId());

        assertEquals(userDto, actualDto);
    }

    @Test
    void findById_shouldThrowEntityNotFoundException_whenUserNotFound() {

        Long userId = 1L;
        when(repository.findById(userId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.findById(userId));
        assertThat(exception.getMessage()).isEqualTo(UserServiceImpl.ENTITY_CONFLICT_MESSAGE + userId);
        verify(repository, times(1)).findById(userId);
        verify(mapper, never()).toDTO(any());
    }

    @Test
    void save_shouldSaveUserAndReturnUserDto() {

        UserDto userDto = new UserDto();
        UserEntity userEntity = new UserEntity(1L, "admin", 100L, "pass");
        when(mapper.toEntity(userDto)).thenReturn(userEntity);
        UserEntity savedUserEntity = new UserEntity();
        when(repository.save(userEntity)).thenReturn(savedUserEntity);
        UserDto savedUserDto = new UserDto();
        when(mapper.toDTO(savedUserEntity)).thenReturn(savedUserDto);

        UserDto actualSavedDto = userService.save(userDto);

        assertThat(actualSavedDto).isEqualTo(savedUserDto);
        verify(mapper, times(1)).toEntity(userDto);
        verify(repository, times(1)).save(userEntity);
        verify(mapper, times(1)).toDTO(savedUserEntity);
    }

    @Test
    void update_shouldUpdateExistingUserAndReturnUserDto() {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        UserEntity existingUserEntity = new UserEntity();
        UserEntity updatedUserEntity = new UserEntity();
        when(repository.findById(userId)).thenReturn(Optional.of(existingUserEntity));
        when(mapper.mergeToEntity(userDto, existingUserEntity)).thenReturn(updatedUserEntity);
        when(repository.save(updatedUserEntity)).thenReturn(updatedUserEntity);
        UserDto updatedUserDto = new UserDto();
        when(mapper.toDTO(updatedUserEntity)).thenReturn(updatedUserDto);

        UserDto result = userService.update(userId, userDto);

        assertThat(result).isEqualTo(updatedUserDto);
        verify(repository, times(1)).findById(userId);
        verify(mapper, times(1)).mergeToEntity(userDto, existingUserEntity);
        verify(repository, times(1)).save(updatedUserEntity);
        verify(mapper, times(1)).toDTO(updatedUserEntity);
    }

    @Test
    void update_shouldThrowEntityNotFoundException_whenUserNotFound() {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        when(repository.findById(userId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.update(userId, userDto));
        assertThat(exception.getMessage()).isEqualTo(UserServiceImpl.ENTITY_CONFLICT_MESSAGE + userId);
        verify(repository, times(1)).findById(userId);
        verify(mapper, never()).mergeToEntity(any(), any());
        verify(repository, never()).save(any());
        verify(mapper, never()).toDTO(any());
    }

    @Test
    void findAllByIds_shouldReturnListOfDtos_whenAllUsersFound() {

        List<Long> ids = List.of(1L, 2L, 3L);
        UserEntity user1 = new UserEntity(1L, "admin", 101L, "pass1");
        UserEntity user2 = new UserEntity(2L, "admin", 102L, "pass2");
        UserEntity user3 = new UserEntity(3L, "admin", 103L, "pass3");
        when(repository.findById(1L)).thenReturn(Optional.of(user1));
        when(repository.findById(2L)).thenReturn(Optional.of(user2));
        when(repository.findById(3L)).thenReturn(Optional.of(user3));
        UserDto dto1 = new UserDto(1L, "admin", "pass1", 101L);
        UserDto dto2 = new UserDto(2L, "admin", "pass2", 102L);
        UserDto dto3 = new UserDto(3L, "admin", "pass3", 103L);
        when(mapper.toDtoList(List.of(user1, user2, user3))).thenReturn(List.of(dto1, dto2, dto3));

        List<UserDto> result = userService.findAllByIds(ids);

        assertEquals(3, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));
        assertEquals(dto3, result.get(2));
        verify(repository, times(3)).findById(anyLong());
        verify(mapper).toDtoList(anyList());
    }

    @Test
    void findAllByIds_shouldThrowEntityNotFoundException_whenUserNotFound() {

        List<Long> ids = List.of(1L, 2L, 3L);
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findAllByIds(ids));
        verify(repository).findById(1L);
        verifyNoInteractions(mapper);
    }
}
