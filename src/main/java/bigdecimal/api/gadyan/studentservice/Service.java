package bigdecimal.api.gadyan.studentservice;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.repository.CrudRepository;

import bigdecimal.api.gadyan.studentservice.domain.Student;
import lombok.Data;

@org.springframework.stereotype.Service
public class Service {
    private final DAO repo;

    public Service(DAO repo) {
        this.repo = repo;
    }

    public void addStudent(Student student) throws EntityNotSavedException {
        StudentEntity entity = new StudentEntity();
        entity.setStudent_name(student.getStudent_name());
        entity.setStudent_batch_id(student.getStudent_batch_id());
        try {
            repo.save(entity);
        } catch (Exception e) {
            throw new EntityNotSavedException(e);
        }
    }

    public Student getStudentByStudentId(Long student_id) throws EntityNotFoundException {
        Student student = new Student();
        try {
            StudentEntity entity = repo.findById(student_id).orElseThrow();
            student.setStudent_batch_id(entity.getStudent_batch_id());
            student.setStudent_id(entity.getStudent_id());
            student.setStudent_name(entity.getStudent_name());
            return student;
        } catch (Exception e) {
            throw new EntityNotFoundException(e);
        }
    }
}

@Entity
@Data
class StudentEntity {
    @Id
    private Long student_id;
    private String student_name;
    private Long student_batch_id;
}

class EntityNotSavedException extends Exception {
    public EntityNotSavedException(Throwable t) {
        super("This operation failed to save the Student record.");
        t.printStackTrace();
    }
}

class EntityNotFoundException extends Exception {
    public EntityNotFoundException(Throwable t) {
        super("This operation could not find the Student record.");
        t.printStackTrace();
    }
}

interface DAO extends CrudRepository<StudentEntity, Long>{  
}
