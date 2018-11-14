package com.example.repository;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
	
}