package com.zidio.serviceImpl;

import com.zidio.Entities.*;
import com.zidio.payload.EMailRequest;
import com.zidio.payload.StudentCourseSubscriptionDto;
import com.zidio.repository.*;
import com.zidio.service.EMailService;
import com.zidio.service.StudentCourseSubscriptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentCourseSubscriptionServiceImpl implements StudentCourseSubscriptionService {

    @Autowired 
    private UserRepository userRepo;
    @Autowired 
    private StudentProfileRepository studentRepo;
    @Autowired 
    private CourseRepository courseRepo;
    
    @Autowired
    private StudentCourseSubscriptionRepository subscriptionRepo;
    
    @Autowired 
    private ModelMapper modelMapper;
    
    @Autowired
    private EMailService emailService;

    @Override
    public String subscribeToCourse(Long courseId, String studentEmail) {
        User user = userRepo.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StudentProfile student = studentRepo.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        StudentCourseSubscription subscription = new StudentCourseSubscription();
        subscription.setCourse(course);
        subscription.setStudent(student);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusMonths(course.getDurationMonths()));
        subscription.setActive(true);

        subscriptionRepo.save(subscription);
        
        EMailRequest mail = new EMailRequest();
        mail.setTo(user.getEmail());
        mail.setSubject("Course Subscription Confirmed");
        mail.setMessage("Hello " + user.getName() + ",\n\n" +
        	    " Congratulations! You've successfully subscribed to the course: *" + course.getName() + "*.\n\n" +
        	    " Start Date: " + subscription.getStartDate() + "\n" +
        	    " End Date: " + subscription.getEndDate() + "\n" +
        	    " Course Price: ₹" + course.getPrice() + "\n\n" +
        	    "Happy Learning!\n\n- Zidio Learning Team");
        emailService.sendEmail(mail);

        
        return "Subscribed to course: " + course.getName();
    }

    @Override
    public List<StudentCourseSubscriptionDto> getMySubscriptions(String studentEmail) {
        User user = userRepo.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StudentProfile student = studentRepo.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        return subscriptionRepo.findByStudent(student).stream()
                .map(sub -> {
                    StudentCourseSubscriptionDto dto = new StudentCourseSubscriptionDto();
                    dto.setId(sub.getId());
                    dto.setCourseId(sub.getCourse().getId());
                    dto.setCourseName(sub.getCourse().getName());
                    dto.setStartDate(sub.getStartDate());
                    dto.setEndDate(sub.getEndDate());
                    dto.setActive(sub.isActive());
                    return dto;
                }).collect(Collectors.toList());
    }
    
    
}
