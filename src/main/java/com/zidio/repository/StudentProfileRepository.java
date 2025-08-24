package com.zidio.repository;

import com.zidio.Entities.StudentProfile;
import com.zidio.Entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    Optional<StudentProfile> findByUserUserId(Long userId);
    Optional<StudentProfile> findByUser(User user);


}



































/*
 * package com.zidio.repository;
 * 
 * import com.zidio.Entities.StudentProfile; import
 * org.springframework.data.jpa.repository.JpaRepository; import
 * org.springframework.stereotype.Service;
 * 
 * import java.util.Optional;
 * 
 * @Service
 * 
 * public interface StudentProfileRepository extends
 * JpaRepository<StudentProfile, Long> { Optional<StudentProfile>
 * findByUserId(Long userId); }
 */



/*
 * package com.zidio.repository;
 * 
 * import java.util.List;
 * 
 * import org.springframework.data.jpa.repository.JpaRepository;
 * 
 * 
 * import com.zidio.Entities.StudentProfile;
 * 
 * 
 * 
 * public interface StudentProfileRepository extends
 * JpaRepository<StudentProfile, Long> {
 * 
 * List<StudentProfile> findAllByUserUserId(long userid);
 * 
 * 
 * }
 */