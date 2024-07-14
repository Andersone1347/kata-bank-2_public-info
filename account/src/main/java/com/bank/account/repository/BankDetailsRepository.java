package com.bank.account.repository;

import com.bank.account.entity.BankDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankDetailsRepository extends JpaRepository<BankDetailsEntity, Long> {
}
