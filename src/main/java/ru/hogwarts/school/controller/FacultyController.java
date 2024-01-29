package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")

public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }
    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }
    @GetMapping("{id}")
    public ResponseEntity<Faculty> findFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }
    @GetMapping
    public ResponseEntity<Collection<Faculty>> findFaculty(@RequestParam (required = false)String color,
                                                           @RequestParam(required = false)String name) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.getFacultyByColor(color));
        }
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(facultyService.getFacultyByName(name));
        }
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }
    @PutMapping
    public Faculty editFaculty(@RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty);
    }
    @DeleteMapping("{id}")
    public ResponseEntity delFaculty(@PathVariable Long id) {
        facultyService.delFaculty(id);
        return ResponseEntity.ok().build();
    }
}
