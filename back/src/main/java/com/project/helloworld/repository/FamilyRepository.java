package com.project.helloworld.repository;

import com.project.helloworld.domain.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<Family,Long> {

}
