package com.pegasus.test.service.impl;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.pegasus.test.dto.PetDto;
import com.pegasus.test.dto.PetImageDto;
import com.pegasus.test.dto.PetMapper;
import com.pegasus.test.dto.PetRequest;
import com.pegasus.test.model.Pet;
import com.pegasus.test.repository.PetRepository;
import com.pegasus.test.util.PetUtil;


@ExtendWith(MockitoExtension.class)
public class PetServiceImplTest {

	@InjectMocks
	private PetServiceImpl petServiceImpl;

	@Mock
	private PetRepository petRepository;
	@Spy
	private PetMapper petMapper;

	private PetUtil petUtil = new PetUtil();


	@Test
	public void test_Create_Should_CreatedPet_When_Invoked() {
		Mockito.when(petRepository.save(Mockito.any(Pet.class))).thenAnswer(p -> petUtil.creatModel((Pet) p.getArguments()[0]));
		Mockito.when(petMapper.toDto(Mockito.any(Pet.class))).thenAnswer(p -> petUtil.toDto((Pet) p.getArguments()[0]));
		Mockito.when(petMapper.toModel(Mockito.any(PetRequest.class))).thenAnswer(p -> petUtil.toModel((PetRequest) p.getArguments()[0]));
		PetRequest petRequest = petUtil.createPetRequest();
		PetDto petDto = petServiceImpl.create(petRequest);

		Assertions.assertNotNull(petDto);
		Assertions.assertNotNull(petDto.getId());

		Mockito.verify(petRepository, Mockito.times(1)).save(Mockito.any());
		Mockito.verify(petMapper, Mockito.times(1)).toDto(Mockito.any());
		Mockito.verify(petMapper, Mockito.times(1)).toModel(Mockito.any());
	}


	@Test
	public void test_Update_Should_UpdatedPet_When_Invoked() {
		Mockito.when(petRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(petUtil.createPet()));
		Mockito.when(petRepository.save(Mockito.any(Pet.class))).thenAnswer(p -> petUtil.creatModel((Pet) p.getArguments()[0]));
		Mockito.when(petMapper.toDto(Mockito.any(Pet.class))).thenAnswer(p -> petUtil.toDto((Pet) p.getArguments()[0]));
		Mockito.doNothing().when(petMapper).updateModel(
				Mockito.any(PetRequest.class),
				Mockito.any(Pet.class));
		PetRequest petRequest = petUtil.createPetRequest();
		PetDto petDto = petServiceImpl.update(UUID.randomUUID(), petRequest);

		Assertions.assertNotNull(petDto);

		Mockito.verify(petRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
		Mockito.verify(petRepository, Mockito.times(1)).save(Mockito.any());
		Mockito.verify(petMapper, Mockito.times(1)).toDto(Mockito.any());
		Mockito.verify(petMapper, Mockito.times(1)).updateModel(Mockito.any(),Mockito.any());
	}

	@Test
	public void test_Delete_Should_DeletedPet_When_Invoked() {
		Mockito.when(petRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(petUtil.createPet()));
		Mockito.doNothing().when(petRepository).deleteById(
				Mockito.any(UUID.class));
		petServiceImpl.delete(UUID.randomUUID());


		Mockito.verify(petRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
		Mockito.verify(petRepository, Mockito.times(1)).deleteById(Mockito.any(UUID.class));
	}

	@Test
	public void test_Show_Should_ReturnPet_When_Invoked() {
		Mockito.when(petRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(petUtil.createPet()));
		Mockito.when(petMapper.toFullDto(Mockito.any(Pet.class))).thenAnswer(p -> petUtil.toDto((Pet) p.getArguments()[0]));
		PetDto petDto = petServiceImpl.show(UUID.randomUUID());

		Assertions.assertNotNull(petDto);
		Assertions.assertNotNull(petDto.getId());

		Mockito.verify(petRepository, Mockito.times(1)).findById(Mockito.any(UUID.class)); 
		Mockito.verify(petMapper, Mockito.times(1)).toFullDto(Mockito.any());
	}

	@Test
	public void test_FindByPersonId_Should_ReturnListPet_When_Invoked() {
		Mockito.when(petRepository.findByPersonId(Mockito.any(UUID.class))).thenReturn(petUtil.createPetList(3));
		Mockito.when(petMapper.toDto(Mockito.any(Pet.class))).thenAnswer(p -> petUtil.toDto((Pet) p.getArguments()[0]));
		List<PetDto> petDto = petServiceImpl.findByPersonId(UUID.randomUUID());

		Assertions.assertNotNull(petDto);
		Assertions.assertEquals(3, petDto.size());

		Mockito.verify(petRepository, Mockito.times(1)).findByPersonId(Mockito.any(UUID.class));
		Mockito.verify(petMapper, Mockito.times(3)).toDto(Mockito.any());
	}


	@Test
	public void test_ShowImage_Should_ReturnImagenPet_When_Invoked() {
		Mockito.when(petRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(petUtil.createPet()));
		Mockito.when(petMapper.toImageDto(Mockito.any(Pet.class))).thenAnswer(p -> petUtil.toImageDto((Pet) p.getArguments()[0]));
		PetImageDto petDto = petServiceImpl.showImage(UUID.randomUUID());

		Assertions.assertNotNull(petDto);
		Assertions.assertNotNull(petDto.getImageBase64());

		Mockito.verify(petRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
		Mockito.verify(petMapper, Mockito.times(1)).toImageDto(Mockito.any());
	}



	@Test
	public void test_Show_Should_ReturnEntityNotFoundException_When_Invoked() {
		Mockito.when(petRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());

		EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
			petServiceImpl.show(UUID.randomUUID());
		});

		Assertions.assertNotNull(exception);
		Assertions.assertNotNull(exception.getMessage());

		Mockito.verify(petRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));

	}

	@Test
	public void test_index_Should_ReturnPagePet_When_Invoked() {
		Page<Pet> page = new PageImpl(petUtil.createPetList(3));

		Mockito.when(petRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
		Mockito.when(petMapper.toDto(Mockito.any(Pet.class))).thenAnswer(p -> petUtil.toDto((Pet) p.getArguments()[0]));
		Page<PetDto> pageDto = petServiceImpl.index(PageRequest.of(0,3));

		Assertions.assertNotNull(pageDto);
		Assertions.assertNotNull(pageDto.getContent());
		Assertions.assertEquals(3, pageDto.getContent().size());

		Mockito.verify(petRepository, Mockito.times(1)).findAll(Mockito.any(Pageable.class));
		Mockito.verify(petMapper, Mockito.times(3)).toDto(Mockito.any());
	}


}
