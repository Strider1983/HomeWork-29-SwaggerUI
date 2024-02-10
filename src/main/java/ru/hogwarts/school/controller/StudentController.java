package ru.hogwarts.school.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("student")

public class StudentController {


    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    private final StudentService studentService;
    private final AvatarService avatarService;



    @PostMapping //POST http://localhost:8080/student
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }
    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException {
        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }
    @GetMapping("{id}") //GET http://localhost:8080/student/2
    public ResponseEntity<Student> findStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
    @GetMapping("age") //GET http://localhost:8080/student/age?age=20
    public ResponseEntity<Collection<Student>> getStudetsByAge(@RequestParam int age) {
        return ResponseEntity.ok(studentService.getStudetsByAge(age));
    }
    @GetMapping("all")
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
    @GetMapping //GET http://localhost:8080/student?age1=16&age2=24
    public ResponseEntity<Collection<Student>> findByAgeBetween (@RequestParam int age1, @RequestParam int age2) {
        return ResponseEntity.ok(studentService.findByAgeBetween(age1, age2));
    }

    @PutMapping("{id}") //PUT http://localhost:8080/student/1
    public ResponseEntity<Student> editStudent(@PathVariable Long id, @RequestBody Student student) {
        Student editStudent = studentService.editStudent(id, student);
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
