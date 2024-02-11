package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTests {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private StudentRepository studentRepository;
    @BeforeEach
    private void clearDataBase() {
        studentRepository.deleteAll();
    }
    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }
    @Test
    public void getAllStudentsTest() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" +  port  + "/student/all", String.class))
                .isNotNull();
    }
    @Test
    public void getAllStudentsByAgeTest() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" +  port  + "/student/age?age=", String.class))
                .isNotNull();
    }
    @Test
    public void getAllStudentsByAgeBetweenTest() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" +  port  + "/student?age1=16&age2=24", String.class))
                .isNotNull();
    }
    @Test
    public void postStudentTest() throws Exception {
        Student student = new Student();
        student.setName("Henry");
        student.setAge(1000);

        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity("http://localhost:" +  port  + "/student", student, Student.class);

        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Student actualStudent = studentResponseEntity.getBody();
        assertNotNull(actualStudent.getId());
        assertEquals(actualStudent.getName(), student.getName());
        assertEquals(actualStudent.getAge(), student.getAge());
    }
    @Test
    public void putStudentTest() throws Exception {
        Student student = new Student();
        student.setName("Henry");
        student.setAge(1000);

        student = studentRepository.save(student);

        Student putedStudent = new Student();
        putedStudent.setName("newName");
        putedStudent.setAge(2000);

        HttpEntity<Student> entity = new HttpEntity<>(putedStudent);
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" +  port  + "/student/" + student.getId(),
                HttpMethod.PUT,
                entity,
                Student.class);

        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Student actualStudent = studentResponseEntity.getBody();
        assertNotNull(actualStudent.getId());
        assertEquals(actualStudent.getName(), putedStudent.getName());
        assertEquals(actualStudent.getAge(), putedStudent.getAge());
    }
    @Test
    public void getStudentByIdTest() throws Exception {
        Student student = new Student();
        student.setName("Henry");
        student.setAge(1000);
        student = studentRepository.save(student);


        ResponseEntity<Student> studentResponseEntity = restTemplate.getForEntity(
                "http://localhost:" +  port  + "/student/" + student.getId(),
                Student.class);

        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Student actualStudent = studentResponseEntity.getBody();
        assertEquals(actualStudent.getId(), student.getId());
        assertEquals(actualStudent.getName(), student.getName());
        assertEquals(actualStudent.getAge(), student.getAge());
    }
    @Test
    public void delStudentByIdTest() throws Exception {
        Student student = new Student();
        student.setName("Henry");
        student.setAge(1000);
        student = studentRepository.save(student);

        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" +  port  + "/student/" + student.getId(),
                HttpMethod.DELETE,
                null,
                Student.class);

        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Assertions.assertThat(studentRepository.findById(student.getId())).isNotPresent();

    }


}
