package com.bank.account.service;

import com.bank.account.dto.ProfileDto;
import com.bank.account.entity.ProfileEntity;
import com.bank.account.feign.ProfileClient;
import com.bank.account.mapper.ProfileMapper;
import com.bank.account.repository.ProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

/**
 * Реализация для {@link ProfileService}
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileServiceImp implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper mapper;
    private final ProfileClient profileClient;

    /**
     * @param id технический идентификатор {@link ProfileEntity}
     * @return {@link ProfileDto}
     */
    @Override
    @Transactional
    public ProfileDto getProfileAndSave(Long id) {
        final ProfileDto profileDto = profileClient.read(id).getBody();

        if (profileDto == null) {
            throw new EntityNotFoundException("Profile with id " + id + " not found");
        }

        final ProfileEntity profileEntity = mapper.toEntity(profileDto);
        profileRepository.save(profileEntity);

        return profileDto;
    }
}
