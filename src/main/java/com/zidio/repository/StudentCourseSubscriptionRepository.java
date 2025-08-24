package com.zidio.repository;

import com.zidio.Entities.StudentCourseSubscription;
import com.zidio.Entities.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentCourseSubscriptionRepository extends JpaRepository<StudentCourseSubscription, Long> {
    List<StudentCourseSubscription> findByStudent(StudentProfile student);
}


