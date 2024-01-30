package ru.hogwarts.school.service;

import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exeptions.StudentNotFoundExeption;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service

public class StudentService {


    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }
    public Student findStudent(long id) {
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundExeption("student with such ID not found"));
    }
    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }
    public void delStudent(long id) {
        studentRepository.deleteById(id);
    }
    public void delAllStudents() {
        studentRepository.deleteAll();
    }

    public Collection<Student> getStudetsByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public Collection<Student> findByAgeBetween(int age1, int age2) {
        return studentRepository.findByAgeBetween(age1, age2);
    };
}
