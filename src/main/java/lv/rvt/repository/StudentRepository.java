package lv.rvt.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import lv.rvt.entity.Student;


public interface StudentRepository extends Repository<Student, Long> {
    
    public Student save(Student student);

    Optional<Student> findById(long id);
}
