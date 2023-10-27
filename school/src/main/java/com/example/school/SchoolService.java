package com.example.school;

import com.example.school.client.StudentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {


    private final SchoolRepository schoolRepository;
    private  final StudentClient studentClient;

    public void saveSchool(School school) {
        schoolRepository.save(school);
    }

    public List<School> findAllSchools() {
        return schoolRepository.findAll();
    }

    public FullSchoolResponse findSchoolsWithStudents(Integer schoolId) {
        System.out.println("\u001B[31m" +"schoolId = " + schoolId + "\u001B[0m");
        var school = schoolRepository
                .findById(schoolId)
                .orElse(
                        School.builder()
                                .name(" not found")
                                .email(" not found")
                                .build()
                );
        System.out.println("\u001B[31m" + "school = " + school.getName() + "\u001B[0m");
        var students = studentClient.foundAllStudentsBySchool(schoolId); // get students from from student microservice
        return FullSchoolResponse.builder()
                .name(school.getName())
                .email(school.getEmail())
                .students(students)
                .build();
    }
}
