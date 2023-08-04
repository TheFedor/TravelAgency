package com.example.travelagency.repositories;

import com.example.travelagency.entities.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends JpaRepository<Clients, String> {

    Clients findByEmailAddress(String emailAddress);
}
