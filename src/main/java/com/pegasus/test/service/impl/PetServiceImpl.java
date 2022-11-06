package com.pegasus.test.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pegasus.test.dto.PetDto;
import com.pegasus.test.dto.PetImageDto;
import com.pegasus.test.dto.PetMapper;
import com.pegasus.test.dto.PetRequest;
import com.pegasus.test.model.Pet;
import com.pegasus.test.repository.PetRepository;
import com.pegasus.test.service.PetService;

@Service
public class PetServiceImpl implements PetService {
	
	@Autowired
	private PetRepository petRepository;
	@Autowired
	private PetMapper petMapper;

	@Override
	public PetDto create(PetRequest petRequest) {
		Pet pet = petMapper.toModel(petRequest);
		return petMapper.toDto(petRepository.save(pet));
	}

	@Override
	public PetDto update(UUID uuid, PetRequest petRequest) {
		Pet p = findById(uuid);
		petMapper.updateModel(petRequest, p);
		return petMapper.toDto(petRepository.save(p));
		
	}

	@Override
	public Page<PetDto> index(Pageable pageable) {
		return petRepository.findAll(pageable).map(petMapper::toDto);
	}

	@Override
	public PetDto show(UUID uuid) {
		Pet p = findById(uuid);
		return petMapper.toFullDto(p);
	}

	@Override
	public void delete(UUID uuid) {
		Pet p = findById(uuid);
		petRepository.deleteById(p.getId());
	}
	
	private  Pet findById(UUID uuid) {
		return petRepository.findById(uuid).orElseThrow(
				() -> new  EntityNotFoundException(
		                "Pet with UUID "+uuid+" does not exist."
			            ));
		
	}

	@Override
	public PetImageDto showImage(UUID uuid) {
		Pet p = findById(uuid);
		return petMapper.toImageDto(p);
	}

	@Override
	public List<PetDto> findByPersonId(UUID personId) {
		return petRepository.findByPersonId(personId).stream().map(petMapper::toDto).collect(Collectors.toList());
	}

}
