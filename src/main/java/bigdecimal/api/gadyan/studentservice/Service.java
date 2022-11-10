package bigdecimal.api.gadyan.studentservice;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.repository.CrudRepository;

import lombok.Data;

@org.springframework.stereotype.Service
public class Service {
    private final DAO repo;

    public Service(DAO repo) {
        this.repo = repo;
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

interface DAO extends CrudRepository<StudentEntity, Long>{  
}
