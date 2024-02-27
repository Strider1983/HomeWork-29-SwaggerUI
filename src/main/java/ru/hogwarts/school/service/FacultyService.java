package ru.hogwarts.school.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exeptions.FacultyNotFoundExeption;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Collection;

@Service
public class FacultyService {
    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;


    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }
    public Faculty findFaculty(long id) {
        logger.info("Was invoked method for get faculty by id");
        return facultyRepository.findById(id).orElseThrow(() -> {
            logger.error("faculty with id {} not found", id);
            return new FacultyNotFoundExeption("faculty with such ID not found");
        });
    }
    public Faculty editFaculty(Long id, Faculty faculty) {
        logger.info("Was invoked method for edit faculty");
        return facultyRepository.findById(id).map(studentFromDb -> {
            studentFromDb.setName(faculty.getName());
            studentFromDb.setColor(faculty.getColor());
            return facultyRepository.save(studentFromDb);
        }).orElse(null);
    }
    public void delFaculty(long id) {
        logger.info("Was invoked method for delete faculty");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getFacultyByColor(String color) {
        logger.info("Was invoked method for get faculty by color");
        return facultyRepository.findByColorIgnoreCase(color);
    }
    public Collection<Faculty> getFacultyByName(String name) {
        logger.info("Was invoked method for get faculty by name");
        return facultyRepository.findByNameIgnoreCase(name);
    }
    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method for get all faculties");
        return facultyRepository.findAll();
    };
}
