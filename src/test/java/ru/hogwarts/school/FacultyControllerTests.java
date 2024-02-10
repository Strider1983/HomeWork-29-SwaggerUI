package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTests {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }
    @Test
    public void getAllFacultiesTest() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" +  port  + "/faculty", String.class))
                .isNotNull();
    }
    @Test
    public void getFacultiesByIdTest() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" +  port  + "/faculty/2", String.class))
                .isNotNull();
    }
    @Test
    public void testPostFacultyt() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("TestFaculty");
        faculty.setColor("testColor");

        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, String.class))
                .isNotNull();
    }

}
