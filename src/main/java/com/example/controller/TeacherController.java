package com.example.controller;

import com.example.model.Teacher;
import com.example.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
    
    @Autowired
    private TeacherRepository teacherRepository;

    // GET /teachers - Display all teachers (JSP view)
    @GetMapping
    public String getAllTeachers(Model model) {
        List<Teacher> teachers = teacherRepository.findAll();
        model.addAttribute("teachers", teachers);
        return "teachers";
    }

    // GET /teachers/{id} - Get teacher by ID (JSON)
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Integer id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isPresent()) {
            return ResponseEntity.ok(teacher.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /teachers - Create new teacher
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> createTeacher(@RequestParam String name, @RequestParam String subject) {
        if (name == null || name.trim().isEmpty() || subject == null || subject.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Name and subject are required");
        }

        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setSubject(subject);
        
        Teacher savedTeacher = teacherRepository.save(teacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTeacher);
    }

    // PUT /teachers/{id} - Update teacher
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updateTeacher(@PathVariable Integer id, 
                                          @RequestParam String name, 
                                          @RequestParam String subject) {
        if (name == null || name.trim().isEmpty() || subject == null || subject.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Name and subject are required");
        }

        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            teacher.setName(name);
            teacher.setSubject(subject);
            Teacher updatedTeacher = teacherRepository.save(teacher);
            return ResponseEntity.ok(updatedTeacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /teachers/{id} - Delete teacher
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteTeacher(@PathVariable Integer id) {
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

