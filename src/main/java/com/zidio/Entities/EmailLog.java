package com.zidio.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@Table(name = "email_logs")
public class EmailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore 
    private User user;
    
    @Column(nullable = false)
    private String recipient;
    
    @Column(nullable = false)
    private String subject;

    @Column(length = 2000)
    private String message;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime sentAt;
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
    	return subject; 
    	}
    
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
    	return message; 
    	}
    
    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }
    
    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
    
    
}


