package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Collection;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private long lastId = 0;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++lastId);
        faculties.put(lastId, faculty);
        return faculty;
    }
    public Faculty findFaculty(long id) {
        return faculties.get(id);
    }
    public Faculty editFaculty(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }
    public Faculty delFaculty(long id) {
        return faculties.remove(id);
    }

    public Collection<Faculty> getFacultyByColor(String color) {
        return faculties.values()
                .stream()
                .filter(faculty -> color.equals(faculty.getColor()))
                .collect(Collectors.toList());
    }
}
