package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

@RestController
@RequestMapping("student")

public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping //POST http://localhost:8080/students
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }
    @GetMapping("{id}") //GET http://localhost:8080/students/2
    public Student findStudent(@PathVariable Long id) {
        return studentService.findStudent(id);
    }
    @PutMapping //PUT http://localhost:8080/students
    public Student editStudent(@RequestBody Student student) {
        return studentService.editStudent(student);
    }
    @DeleteMapping("{id}") //DELETE http://localhost:8080/students/2
    public Student delStudent(@PathVariable Long id) {
        return studentService.delStudent(id);
    }
}
