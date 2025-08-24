package com.zidio.repository;

import com.zidio.Entities.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile, Long> {

    //  Used to fetch profile based on the user ID (linked via OneToOne)
    Optional<RecruiterProfile> findByUserUserId(Long userId);
}


