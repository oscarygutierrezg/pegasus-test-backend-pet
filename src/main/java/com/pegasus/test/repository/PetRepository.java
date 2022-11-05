package com.pegasus.test.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pegasus.test.model.Pet;


@Repository
public interface PetRepository extends JpaRepository<Pet, UUID> {
	

}
