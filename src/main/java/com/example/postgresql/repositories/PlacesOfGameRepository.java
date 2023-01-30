package com.example.postgresql.repositories;

import com.example.postgresql.entities.CityGames;
import com.example.postgresql.entities.PlacesOfGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacesOfGameRepository extends JpaRepository<PlacesOfGame, String> {
}
