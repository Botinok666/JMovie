package com.sfedu.JMovie.db.repository;

import com.sfedu.JMovie.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Short> {
    User findByName(String name);
}
