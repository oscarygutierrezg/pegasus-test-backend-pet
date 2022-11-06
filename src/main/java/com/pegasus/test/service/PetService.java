package com.pegasus.test.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; 
import com.pegasus.test.dto.PetDto;
import com.pegasus.test.dto.PetImageDto;
import com.pegasus.test.dto.PetRequest;


public interface PetService {
	PetDto create(PetRequest petRequest);

	PetDto update(UUID uuid, PetRequest petRequest);

	Page<PetDto> index(Pageable pageable);
	
	List<PetDto> findByPersonId(UUID personId);

	PetDto show(UUID uuid);
	
	PetImageDto showImage(UUID uuid);
	
	void delete(UUID uuid);


}
