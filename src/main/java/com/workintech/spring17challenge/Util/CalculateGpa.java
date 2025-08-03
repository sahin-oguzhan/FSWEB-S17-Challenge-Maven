package com.workintech.spring17challenge.Util;

import com.workintech.spring17challenge.exceptions.ApiException;
import com.workintech.spring17challenge.entity.Course;
import com.workintech.spring17challenge.model.HighCourseGpa;
import com.workintech.spring17challenge.model.LowCourseGpa;
import com.workintech.spring17challenge.model.MediumCourseGpa;
import org.springframework.http.HttpStatus;

public class CalculateGpa {
    private Course course;
    private static LowCourseGpa lowCourseGpa = new LowCourseGpa();
    private static MediumCourseGpa mediumCourseGpa = new MediumCourseGpa();
    private static HighCourseGpa highCourseGpa = new HighCourseGpa();


    public static int calculateGpa(Course course) {
        int totalGpa;
        if (course.getCredit() < 0 || course.getCredit() > 4) {
            throw new ApiException("Credit must be between 0 and 4", HttpStatus.BAD_REQUEST);
        } else if (course.getCredit() <= 2) {
            totalGpa = course.getGrade().getCoefficient() * course.getCredit() * lowCourseGpa.getGpa();
        } else if (course.getCredit() == 3) {
            totalGpa = course.getGrade().getCoefficient() * course.getCredit() * mediumCourseGpa.getGpa();
        } else {
            totalGpa = course.getGrade().getCoefficient() * course.getCredit() * highCourseGpa.getGpa();
        }
        return totalGpa;
    }
}
