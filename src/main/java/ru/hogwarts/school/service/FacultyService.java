package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.Map;

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
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }
    public Faculty delFaculty(long id) {
        return faculties.remove(id);
    }

}
