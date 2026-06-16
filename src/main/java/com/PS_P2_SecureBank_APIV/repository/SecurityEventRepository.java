package com.PS_P2_SecureBank_APIV.repository;

import com.PS_P2_SecureBank_APIV.entity.SecurityEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityEventRepository extends JpaRepository<SecurityEvent, Long> {
}