package ru.hogwarts.school.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exeptions.StudentNotFoundExeption;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service

public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);


    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }
    public Student findStudent(long id) {
        logger.debug("requesting student by id: {}", id);
        Student student = studentRepository.findById(id).orElseThrow(() -> {
                logger.error("student with id {} not found", id);
                return new StudentNotFoundExeption("student with such ID not found");
        });
        logger.debug("student by {} is {}", id, student);
        return student;
    }
    public Student editStudent(Long id, Student student) {
        logger.info("Was invoked method for edit student");
        return studentRepository.findById(id).map(studentFromDb -> {
            studentFromDb.setName(student.getName());
            studentFromDb.setAge(student.getAge());
            return studentRepository.save(studentFromDb);
        }).orElse(null);

    }
    public void delStudent(long id) {
        logger.info("Was invoked method for delete student");
        studentRepository.deleteById(id);
    }
    public void delAllStudents() {
        logger.info("Was invoked method for delete students");
        studentRepository.deleteAll();
    }

    public Collection<Student> getStudetsByAge(int age) {
        logger.info("Was invoked method for get students by age");
        return studentRepository.findByAge(age);
    }

    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }
    public List<String> getNamesStartedWithA() {
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(it -> it.startsWith("А"))
                .sorted()
                .collect(Collectors.toList());
    }
    public Double getAverageAge() {
        return studentRepository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average().orElse(0.0);

    }
    public Collection<Student> findByAgeBetween(int age1, int age2) {
        logger.info("Was invoked method for get students by age between");
        return studentRepository.findByAgeBetween(age1, age2);
    }
    public Integer countStudents () {
        logger.info("Was invoked method for count students");
        return studentRepository.countStudents();
    }
    public Integer averageAge () {
        logger.info("Was invoked method for count evegage age");
        return studentRepository.averageAge();
    }
    public Collection<Student> lastStudents() {
        logger.info("Was invoked method for get last five students");
        return studentRepository.lastStudents();
    }

    public void printParallel() {
        List<String> names = studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .toList();

        System.out.println(Thread.currentThread().getName() + ": " + names.get(0));
        System.out.println(Thread.currentThread().getName() + ": " + names.get(1));

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": " + names.get(2));
            System.out.println(Thread.currentThread().getName() + ": " + names.get(3));

        }).start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": " + names.get(4));
            System.out.println(Thread.currentThread().getName() + ": " + names.get(5));

        }).start();


    }

    public Object flag = new Object();

    public void printSynchronized() {
        List<String> names = studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .toList();

        printSynchronizedName(names.get(0));
        printSynchronizedName(names.get(1));

        new Thread(() -> {
            printSynchronizedName(names.get(2));
            printSynchronizedName(names.get(3));

        }).start();

        new Thread(() -> {
            printSynchronizedName(names.get(4));
            printSynchronizedName(names.get(5));

        }).start();
    }
    private synchronized void printSynchronizedName (String name) {
        System.out.println(Thread.currentThread().getName() + ": " + name);
    }

}
