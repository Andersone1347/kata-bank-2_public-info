package com.bank.authorization.mapper;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    @DisplayName("маппинг в dto")
    void toDTOTest() {
        UserEntity userEntity = new UserEntity(1L, "admin", 100L, "pass");
        UserDto expectedUserDto = new UserDto(userEntity.getId(), userEntity.getRole(),
                userEntity.getPassword(), userEntity.getProfileId());

        UserDto actualUserDto = userMapper.toDTO(userEntity);

        assertAll(
                () -> assertThat(actualUserDto).isNotNull(),
                () -> assertThat(actualUserDto.getId()).isEqualTo(expectedUserDto.getId()),
                () -> assertThat(actualUserDto.getRole()).isEqualTo(expectedUserDto.getRole()),
                () -> assertThat(actualUserDto.getPassword()).isEqualTo(expectedUserDto.getPassword()),
                () -> assertThat(actualUserDto.getProfileId()).isEqualTo(expectedUserDto.getProfileId())
        );
    }

    @Test
    @DisplayName("маппинг в entity")
    void toEntityTest() {
        UserDto userDto = new UserDto(1L, "admin", "pass", 100L);
        UserEntity expectedUserEntity = new UserEntity(userDto.getId(), userDto.getRole(), userDto.getProfileId(),
                userDto.getPassword());

        UserEntity actualUserEntity = userMapper.toEntity(userDto);

        assertAll(
                () -> assertThat(actualUserEntity).isNotNull(),
                () -> assertThat(actualUserEntity.getRole()).isEqualTo(expectedUserEntity.getRole()),
                () -> assertThat(actualUserEntity.getPassword()).isEqualTo(expectedUserEntity.getPassword()),
                () -> assertThat(actualUserEntity.getProfileId()).isEqualTo(expectedUserEntity.getProfileId())
        );
    }

    @Test
    @DisplayName("маппинг пустого листа entity в лист dto")
    void toDtoListIfEntitiesListIsEmptyTest() {
        List<UserEntity> users = new ArrayList<>();
        List<UserDto> actualList = userMapper.toDtoList(users);
        assertEquals(0, actualList.size());
    }

    @Test
    @DisplayName("маппинг листа entity в лист dto")
    void toDtoListTest() {
        UserEntity userEntity = new UserEntity(1L, "admin", 100L, "pass");
        UserDto userDto = new UserDto(userEntity.getId(), userEntity.getRole(),
                userEntity.getPassword(), userEntity.getProfileId());
        List<UserEntity> entitiesList = new ArrayList<>();
        entitiesList.add(userEntity);
        List<UserDto> expectedDtoList = new ArrayList<>();
        expectedDtoList.add(userDto);

        List<UserDto> actualDtoList = userMapper.toDtoList(entitiesList);

        assertAll(
                () -> assertThat(actualDtoList).isNotNull(),
                () -> assertThat(actualDtoList.size()).isEqualTo(expectedDtoList.size()),
                () -> assertThat(actualDtoList.get(0)).isEqualTo(userDto)
        );
    }

    @Test
    @DisplayName("слияние в entity")
    void mergeToEntityTest() {
        UserEntity userEntity = new UserEntity(1L, "admin", 100L, "pass");
        UserDto userDto = new UserDto(99L, "user", "new password", 300L);
        UserEntity expectedUserEntity = new UserEntity(1L, "user", 300L, "new password");

        UserEntity actualUserEntity = userMapper.mergeToEntity(userDto, userEntity);

        assertEquals(expectedUserEntity, actualUserEntity);
    }
}