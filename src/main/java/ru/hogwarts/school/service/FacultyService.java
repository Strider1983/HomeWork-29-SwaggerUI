package ru.hogwarts.school.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exeptions.FacultyNotFoundExeption;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Collection;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;


    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }
    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundExeption("faculty with such ID not found"));
    }
    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }
    public void delFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getFacultyByColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }
    public Collection<Faculty> getFacultyByName(String name) {
        return facultyRepository.findByNameIgnoreCase(name);
    }
    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    };
}
