package com.pegasus.test.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.github.javafaker.Faker;
import com.pegasus.test.dto.PetDto;
import com.pegasus.test.dto.PetImageDto;
import com.pegasus.test.dto.PetRequest;
import com.pegasus.test.model.Pet;

public class PetUtil {
	
	public Faker faker = new Faker();
	
	
	public Pet creatModel(Pet p) {
		p.setId(UUID.randomUUID());
		return p;
	}

	public Pet createPet() {
		Pet pet = new Pet();
		pet.setColor(faker.color().name());
		pet.setName(faker.dog().name());
		pet.setBreed(faker.dog().breed());
		pet.setId(UUID.randomUUID());
		pet.setPersonId(UUID.randomUUID());
		pet.setImage(faker.animal().name().getBytes());
		return pet;
	}
	
	public Pet createPetPersonId(UUID personId) {
		Pet pet = new Pet();
		pet.setColor(faker.color().name());
		pet.setName(faker.dog().name());
		pet.setBreed(faker.dog().breed());
		pet.setId(null);
		pet.setPersonId(personId);
		pet.setImage(faker.animal().name().getBytes());
		return pet;
	}
	
	public List<Pet> createPetList(int length) {
		List<Pet> pets = new ArrayList<Pet>();
		for (int i = 0; i < length; i++) {
			pets.add(createPet());
		}
		return pets;
	}

	public Pet toModel(PetRequest petDto) {
		Pet pet = new Pet();
		pet.setColor(petDto.getColor());
		pet.setBreed(petDto.getBreed());
		pet.setName(petDto.getName());
		pet.setPersonId(petDto.getPersonId());
		return pet;
	}

	public PetDto toDto(Pet petDto) {
		return  PetDto.builder()
				.color(petDto.getColor())
		.breed(petDto.getBreed())
		.name(petDto.getName())
		.personId(petDto.getPersonId())
		.id(petDto.getId())
				.build();
	}


	public PetRequest createPetRequest() {
		return  PetRequest.builder()
				.color(faker.color().name())
				.breed(faker.dog().breed())
				.name(faker.dog().name())
				.personId(UUID.randomUUID())
				.imageBase64(faker.dog().name())
				.build();
	}

	public PetImageDto toImageDto(Pet pet) {
		return  PetImageDto.builder()
				.imageBase64(new String(pet.getImage()))
				.build();
	}

}
