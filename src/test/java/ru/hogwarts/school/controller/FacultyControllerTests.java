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
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTests {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private FacultyRepository facultyRepository;
    @BeforeEach
    private void clearDataBase() {
        facultyRepository.deleteAll();
    }
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
    public void postFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Puffendor");
        faculty.setColor("Purple");

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.postForEntity("http://localhost:" +  port  + "/faculty", faculty, Faculty.class);

        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Faculty actualFaculty = facultyResponseEntity.getBody();
        assertNotNull(actualFaculty.getId());
        assertEquals(actualFaculty.getName(), faculty.getName());
        assertEquals(actualFaculty.getColor(), faculty.getColor());
    }
    @Test
    public void putFacultyTest() throws Exception {
        Long facultyId = 1L;
        Faculty faculty = new Faculty();
        faculty.setName("Puffendor");
        faculty.setColor("Purple");

        faculty = facultyRepository.save(faculty);

        Faculty putedFaculty = new Faculty();
        putedFaculty.setName("newName");
        putedFaculty.setColor("newColor");

        HttpEntity<Faculty> entity = new HttpEntity<>(putedFaculty);
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" +  port  + "/faculty/" + faculty.getId(),
                HttpMethod.PUT,
                entity,
                Faculty.class);

        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Faculty actualFaculty = facultyResponseEntity.getBody();
        assertNotNull(actualFaculty.getId());
        assertEquals(actualFaculty.getName(), putedFaculty.getName());
        assertEquals(actualFaculty.getColor(), putedFaculty.getColor());
    }
    @Test
    public void getFacultyByIdTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Puffendor");
        faculty.setColor("Purple");
        faculty = facultyRepository.save(faculty);


        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.getForEntity(
                "http://localhost:" +  port  + "/faculty/" + faculty.getId(),
                Faculty.class);

        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Faculty actualFaculty = facultyResponseEntity.getBody();
        assertEquals(actualFaculty.getId(), faculty.getId());
        assertEquals(actualFaculty.getName(), faculty.getName());
        assertEquals(actualFaculty.getColor(), faculty.getColor());
    }
    @Test
    public void delFacultyByIdTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Puffendor");
        faculty.setColor("Purple");
        faculty = facultyRepository.save(faculty);

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" +  port  + "/faculty/" + faculty.getId(),
                HttpMethod.DELETE,
                null,
                Faculty.class);

        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Assertions.assertThat(facultyRepository.findById(faculty.getId())).isNotPresent();

    }

}
