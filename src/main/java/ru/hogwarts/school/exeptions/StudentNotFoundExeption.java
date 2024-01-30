package ru.hogwarts.school.exeptions;

public class StudentNotFoundExeption extends RuntimeException{


    public StudentNotFoundExeption(String message) {
        super(message);
    }
}

