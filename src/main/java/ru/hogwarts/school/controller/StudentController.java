package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")

public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping //POST http://localhost:8080/student
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }
    @GetMapping("{id}") //GET http://localhost:8080/student/2
    public ResponseEntity<Student> findStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
    @GetMapping("age") //GET http://localhost:8080/student?age=20
    public ResponseEntity<Collection<Student>> getStudetsByAge(@RequestParam int age) {
        return ResponseEntity.ok(studentService.getStudetsByAge(age));
    }
    @GetMapping("all")
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
    @GetMapping
    public ResponseEntity<Collection<Student>> findByAgeBetween (@RequestParam int age1, @RequestParam int age2) {
        return ResponseEntity.ok(studentService.findByAgeBetween(age1, age2));
    }

    @PutMapping //PUT http://localhost:8080/student
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student editStudent = studentService.editStudent(student);
        if (editStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editStudent);
    }
    @DeleteMapping("{id}") //DELETE http://localhost:8080/student/2
    public ResponseEntity delStudent(@PathVariable Long id) {
        studentService.delStudent(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping //DELETE http://localhost:8080/student
    public ResponseEntity delAllStudents() {
        studentService.delAllStudents();
        return ResponseEntity.ok().build();
    }
}
