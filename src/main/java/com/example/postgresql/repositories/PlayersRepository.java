package com.example.postgresql.repositories;

import com.example.postgresql.entities.Players;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public interface PlayersRepository extends JpaRepository<Players,Long> {

//    @Query(value = "insert into players (nickname) VALUES (:name)", nativeQuery = true)
//    @Transactional
//    void insertNickname(@Param("name") String name);
}