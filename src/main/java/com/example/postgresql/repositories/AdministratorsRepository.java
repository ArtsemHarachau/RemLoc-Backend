package com.example.postgresql.repositories;

import com.example.postgresql.entities.Administrators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorsRepository extends JpaRepository<Administrators,Long> {
}
