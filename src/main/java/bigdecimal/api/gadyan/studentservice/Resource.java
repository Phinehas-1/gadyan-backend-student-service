package bigdecimal.api.gadyan.studentservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
}
