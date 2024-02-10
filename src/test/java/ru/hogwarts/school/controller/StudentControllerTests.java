package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTests {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private TestRestTemplate restTemplate;
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
    public void testPostStudent() throws Exception {
        Student student = new Student();
        student.setName("TestStudent");
        student.setAge(1000);

        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
                .isNotNull();
    }


}
