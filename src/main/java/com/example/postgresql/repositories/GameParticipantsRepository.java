package com.example.postgresql.repositories;

import com.example.postgresql.entities.GameParticipants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameParticipantsRepository extends JpaRepository<GameParticipants,Long> {
}
