package com.zidio.repository;

import com.zidio.Entities.JobPortal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobPortalRepository extends JpaRepository<JobPortal, Long> {
    List<JobPortal> findByRecruiterRecruiterId(Long recruiterId);
    Page<JobPortal> findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(String title, String location, Pageable pageable);


}















/*
 * public interface JobPortalRepository extends JpaRepository<JobPortal, Long> {
 * 
 * List<JobPortal> findByRecruiterRecruiterId(Long recruiterId);
 * 
 * Page<JobPortal>
 * findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCase( String title,
 * String location, Pageable pageable ); }
 */


/*
 * package com.zidio.repository;
 * 
 * import com.zidio.Entities.JobPortal; import
 * org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
 * 
 * public interface JobPortalRepository extends JpaRepository<JobPortal, Long> {
 * 
 * // Get jobs posted by a specific recruiter List<JobPortal>
 * findByRecruiterRecruiterId(Long recruiterId); }
 */




/*
 * package com.zidio.repository;
 * 
 * import com.zidio.Entities.JobPortal; import
 * org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
 * 
 * public interface JobPortalRepository extends JpaRepository<JobPortal, Long> {
 * List<JobPortal> findByRecruiterRecruiterId(Long recruiterId); }
 */