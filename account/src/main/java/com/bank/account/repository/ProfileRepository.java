package com.bank.account.repository;

import com.bank.account.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для {@link ProfileEntity}
 */
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
}
