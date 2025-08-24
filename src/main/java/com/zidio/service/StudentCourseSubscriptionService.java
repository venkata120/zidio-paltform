package com.zidio.service;

import com.zidio.payload.StudentCourseSubscriptionDto;
import java.util.List;

public interface StudentCourseSubscriptionService {
    String subscribeToCourse(Long courseId, String studentEmail);
    List<StudentCourseSubscriptionDto> getMySubscriptions(String studentEmail);
}
