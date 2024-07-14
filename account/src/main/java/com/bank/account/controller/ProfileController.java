package com.bank.account.controller;

import com.bank.account.dto.ProfileDto;
import com.bank.account.entity.ProfileEntity;
import com.bank.account.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для {@link ProfileEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    /**
     * @param id технический идентификатор {@link ProfileEntity}
     * @return {@link ResponseEntity<ProfileDto>}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable Long id) {
        final ProfileDto profileDto = profileService.getProfileAndSave(id);
        return ResponseEntity.ok(profileDto);
    }
}
