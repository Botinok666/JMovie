package com.sfedu.JMovie.db.repository;

import com.sfedu.JMovie.db.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Short> {
}
