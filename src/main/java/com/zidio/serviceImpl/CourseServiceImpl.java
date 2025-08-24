package com.zidio.serviceImpl;


import com.zidio.payload.CourseDto;
import com.zidio.repository.CourseRepository;
import com.zidio.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired private CourseRepository courseRepo;
    @Autowired private ModelMapper modelMapper;

    @Override
    public List<CourseDto> getAllCourses() {
        return courseRepo.findAll().stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .collect(Collectors.toList());
    }
}


