package com.zidio.repository;

import com.zidio.Entities.EmailLog;
import com.zidio.Entities.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {
	 List<EmailLog> findTop10ByRecipientOrderBySentAtDesc(String email);
	 
	

	// Use this (if 'user' is mapped and populated)
	List<EmailLog> findTop10ByUserOrderBySentAtDesc(User user);
}
