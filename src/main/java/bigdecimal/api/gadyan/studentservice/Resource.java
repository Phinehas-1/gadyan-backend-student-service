package bigdecimal.api.gadyan.studentservice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bigdecimal.api.gadyan.studentservice.domain.Student;

@RestController
@RequestMapping("/api/v1")
public class Resource {
    private ResponseEntity<? extends Object> response;

    private final Service service;

    public Resource(Service service) {
        this.service = service;
    }

    @PostMapping("/addStudent")
    public ResponseEntity<? extends Object> addStudent(@RequestBody Student student) {
        response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (student.getStudent_batch_id() == null || student.getStudent_name() == null
                || student.getStudent_name().trim().isEmpty()) {
            return response;
        }
        try {
            service.addStudent(student);
            response = new ResponseEntity<>(HttpStatus.CREATED);
            return response;
        } catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @GetMapping("/getStudentByStudentId/{student_id}")
    public ResponseEntity<? extends Object> getStudentByStudentId(@PathVariable("student_id") Long student_id) {
        response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (student_id != null) {
            try {
                Student student = service.getStudentByStudentId(student_id);
                response = new ResponseEntity<>(student, HttpStatus.FOUND);
                return response;
            } catch (Exception e) {
                response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                return response;
            }
        }
        return response;
    }

    @GetMapping("/getStudentsByBatchId/{batch_id}")
    public ResponseEntity<? extends Object> getStudentsByBatchId(@PathVariable("batch_id") Long batch_id) {
        response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (batch_id != null) {
            try {
                List<Student> students = service.getStudentsByBatchId(batch_id);
                response = new ResponseEntity<>(students, HttpStatus.FOUND);
                return response;
            } catch (Exception e) {
                response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                return response;
            }
        }
        return response;
    }

    @GetMapping("/getAllStudents")
    public ResponseEntity<? extends Object> getAllStudents() {
        response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try {
            List<Student> students = service.getAllStudents();
            response = new ResponseEntity<>(students, HttpStatus.FOUND);
            return response;
        } catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    @PutMapping("/updateStudent")
    public ResponseEntity<? extends Object> updateStudent(@RequestBody Student student) {
        response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (student.getStudent_batch_id() == null || student.getStudent_name() == null
                || student.getStudent_name().trim().isEmpty()) {
            return response;
        }
        try {
            service.updateStudent(student);
            response = new ResponseEntity<>(HttpStatus.ACCEPTED);
            return response;
        } catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
