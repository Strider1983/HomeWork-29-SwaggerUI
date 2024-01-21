package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;

public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private long lastId = 0;

    public Student createStudent(Student student) {
        student.getId(++lastId);
        students.put(lastId, student);
        return student;
    }
    public Student findStudent(long id) {
        return students.get(id);
    }
    public Student editStudent(Student student) {
        students.put(student.getId(), student);
        return student;
    }
    public Student delStudent(long id) {
        return students.remove(id);
    }
}
