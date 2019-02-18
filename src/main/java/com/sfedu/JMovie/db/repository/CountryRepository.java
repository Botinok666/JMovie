package com.sfedu.JMovie.db.repository;

import com.sfedu.JMovie.db.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Short> {
}
