package com.workintech.spring17challenge.controller;

import com.workintech.spring17challenge.Util.CalculateGpa;
import com.workintech.spring17challenge.Util.CourseValidation;
import com.workintech.spring17challenge.entity.Course;
import com.workintech.spring17challenge.entity.CourseResponse;
import com.workintech.spring17challenge.entity.Grade;
import com.workintech.spring17challenge.exceptions.ApiException;
import com.workintech.spring17challenge.model.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/courses")
public class CourseController {
    private LowCourseGpa lowCourseGpa;
    private MediumCourseGpa mediumCourseGpa;
    private HighCourseGpa highCourseGpa;
    private List<Course> courses;

    @Autowired
    public CourseController(LowCourseGpa lowCourseGpa, MediumCourseGpa mediumCourseGpa, HighCourseGpa highCourseGpa) {
        this.lowCourseGpa = lowCourseGpa;
        this.mediumCourseGpa = mediumCourseGpa;
        this.highCourseGpa = highCourseGpa;

    }

    @PostConstruct
    public void init() {
        courses = new ArrayList<>();
        Course course1 = new Course(1, "MAT", 3, new Grade(5, "Excellent"));
        courses.add(course1);
        Course course2 = new Course(2, "PHY", 4, new Grade(4, "Very Good"));
        courses.add(course2);
        Course course3 = new Course(3, "CHE", 3, new Grade(3, "Good"));
        courses.add(course3);
        Course course4 = new Course(4, "BIO", 2, new Grade(2, "Average"));
        courses.add(course4);
        Course course5 = new Course(5, "ENG", 3, new Grade(1, "Poor"));
        courses.add(course5);
    }

    @GetMapping
    public List<Course> findAll() {
        return courses;
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/{name}")
    public Course findByName(@PathVariable String name) {
        CourseValidation.isNameValid(name);
        for (Course course : courses) {
            if (course.getName().equalsIgnoreCase(name)) {
                return course;
            }
        }
        throw new ApiException("Name couldn't find!" , HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse addCourse(@RequestBody Course course) {
        CourseValidation.validateCourse(course);
        courses.add(course);
        int totalGpa = CalculateGpa.calculateGpa(course);
        return new CourseResponse(course, totalGpa);
    }

    @PutMapping("/{id}")
    public CourseResponse updateCourse(@PathVariable int id, @RequestBody Course course) {
        CourseValidation.isIdValid(id);
        CourseValidation.validateCourse(course);
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getId() == id) {
                courses.set(i, course);
                int totalGpa = CalculateGpa.calculateGpa(course);
                return new CourseResponse(course, totalGpa);
            }
        }
        throw new ApiException("Course not found with id: " + id, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public Course removeCourse(@PathVariable int id) {
        Course toRemove = null;
        for (Course course : courses) {
            if (course.getId() == id) {
                toRemove = course;
                break;
            }
        }
        CourseValidation.validateCourse(toRemove);
        courses.remove(toRemove);
        return toRemove;
    }
}


