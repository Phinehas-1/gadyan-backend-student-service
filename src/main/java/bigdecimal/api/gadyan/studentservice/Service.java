package bigdecimal.api.gadyan.studentservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import bigdecimal.api.gadyan.studentservice.domain.Student;
import lombok.Data;

@org.springframework.stereotype.Service
public class Service {
    private final DAO repo;

    public Service(DAO repo) {
        this.repo = repo;
    }

    /**
     * @param student
     * @throws EntityNotSavedException
     */
    public void addStudent(Student student) throws EntityNotSavedException {
        StudentEntity entity = new StudentEntity();
        entity.setStudent_name(student.getStudent_name());
        entity.setStudent_batch_id(student.getStudent_batch_id());
        // TODO check if the batch id exists on the batch service.
        try {
            repo.save(entity);
        } catch (Exception e) {
            throw new EntityNotSavedException(e);
        }
    }

    /**
     * @param student_id
     * @return
     * @throws EntityNotFoundException
     */
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

    public List<Student> getStudentsByBatchId(Long batch_id) throws EntityNotFoundException {
        Student student = new Student();
        try {
            Iterator<StudentEntity> entities = repo.findStudentsByBatchId(batch_id).iterator();
            List<Student> students = new ArrayList<>();
            while (entities.hasNext()) {
                StudentEntity entity = entities.next();
                student.setStudent_batch_id(entity.getStudent_batch_id());
                student.setStudent_id(entity.getStudent_id());
                student.setStudent_name(entity.getStudent_name());
                students.add(student);
            }
            return students;
        } catch (Exception e) {
            throw new EntityNotFoundException(e);
        }
    }

    public List<Student> getAllStudents() throws EntityNotFoundException {
        Student student = new Student();
        try {
            Iterator<StudentEntity> entities = repo.findAll().iterator();
            List<Student> students = new ArrayList<>();
            while (entities.hasNext()) {
                StudentEntity entity = entities.next();
                student.setStudent_batch_id(entity.getStudent_batch_id());
                student.setStudent_id(entity.getStudent_id());
                student.setStudent_name(entity.getStudent_name());
                students.add(student);
            }
            return students;
        } catch (Exception e) {
            throw new EntityNotFoundException(e);
        }
    }

    public void updateStudent(Student student) throws EntityNotUpdatedException {
        StudentEntity entity = new StudentEntity();
        try {
            entity = repo.findById(student.getStudent_id()).orElseThrow();
            entity.setStudent_name(student.getStudent_name());
            entity.setStudent_batch_id(student.getStudent_batch_id());
            repo.save(entity);
        } catch (Exception e) {
            throw new EntityNotUpdatedException(e);
        }
    }

    public void deleteStudentByStudentId(Long student_id) throws EntityNotDeletedException {
        try {
            repo.deleteById(student_id);
        } catch (Exception e) {
            throw new EntityNotDeletedException(e);
        }
    }

    public void deleteStudentsByBatchId(Long batch_id) throws EntityNotDeletedException {
        try {
            repo.deleteStudentsByBatchId(batch_id);
        } catch (Exception e) {
            throw new EntityNotDeletedException(e);
        }
    }

    public void deleteAllStudents() throws EntityNotDeletedException {
        try {
            repo.deleteAll();
        } catch (Exception e) {
            throw new EntityNotDeletedException(e);
        }
    }
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

class EntityNotUpdatedException extends Exception {
    public EntityNotUpdatedException(Throwable t) {
        super("This operation could not update the Student record.");
        t.printStackTrace();
    }
}

class EntityNotDeletedException extends Exception {
    public EntityNotDeletedException(Throwable t) {
        super("This operation could not delete the Student record.");
        t.printStackTrace();
    }
}

@Entity
@Data
@Table(name = "Student")
class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long student_id;
    private String student_name;
    private Long student_batch_id;
}

interface DAO extends CrudRepository<StudentEntity, Long> {

    @Query(value = "select * from Student where student_batch_id = :batch_id", nativeQuery = true)
    public List<StudentEntity> findStudentsByBatchId(@Param("batch_id") Long batch_id);

    @Query(value = "delete from Student where student_batch_id = :batch_id", nativeQuery = true)
    public void deleteStudentsByBatchId(@Param("batch_id") Long batch_id);
}
