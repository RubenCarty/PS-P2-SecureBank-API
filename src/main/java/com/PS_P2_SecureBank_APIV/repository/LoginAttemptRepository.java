package com.PS_P2_SecureBank_APIV.repository;

import com.PS_P2_SecureBank_APIV.entity.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {
}