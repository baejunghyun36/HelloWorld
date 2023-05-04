package com.project.helloworld.repository;

import com.project.helloworld.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUserSeq(Long userSeq);
    Optional<User> findByPhoneNumber(String phoneNumber);
    boolean deleteByUserSeq(Long userSeq);
    boolean existsByEmail(String email);
    @Modifying
    @Query("UPDATE User u SET u.total = :total WHERE u.userSeq = :userSeq")
    void updateTotalByUserSeq(Long userSeq, int total);
}
