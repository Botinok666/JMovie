package com.sfedu.JMovie.db.repository;

import com.sfedu.JMovie.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Short> {
}
