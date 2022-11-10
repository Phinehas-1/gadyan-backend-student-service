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
        super("This operation failed to save the entity.");
        t.printStackTrace();
    }
}

interface DAO extends CrudRepository<StudentEntity, Long>{  
}
