package com.pegasus.test.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pegasus.test.dto.PetDto;
import com.pegasus.test.dto.PetImageDto;
import com.pegasus.test.dto.PetRequest;
import com.pegasus.test.dto.request.OnCreate;
import com.pegasus.test.service.PetService;

import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "pets")
@RestController
@RequestMapping(value = "/v1/pet")
@CrossOrigin
@Validated
public class PetController {

	@Autowired
	private PetService petService;

	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			) public ResponseEntity<PetDto> createPet(
					@Validated(OnCreate.class) @RequestBody  PetRequest petRequest) {

		PetDto dto = petService.create(petRequest);
		return  ResponseEntity.created(URI.create("/v1/pet/" + dto.getId()))
				.body(dto);
	}

	@PutMapping(
			value = "/{uuid}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<PetDto>  updatePet(@PathVariable(value = "uuid") UUID uuid,
			@Validated @RequestBody  PetRequest petRequest) {
		return  ResponseEntity.ok().body(petService.update(uuid, petRequest));
	}



	@GetMapping(
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Page<PetDto>>  getAllPets(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size
			) {
		return ResponseEntity.ok().body(petService.index(PageRequest.of(page,size)));
	}

	@GetMapping(
			value = "/{uuid}",
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public  ResponseEntity<PetDto>  showPet(
			@PathVariable(value = "uuid") UUID uuid) {
		return  ResponseEntity.ok().body(petService.show(uuid));
	}
	
	@GetMapping(
			value = "/{uuid}/image",
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public  ResponseEntity<PetImageDto>  showImage(
			@PathVariable(value = "uuid") UUID uuid) {
		return  ResponseEntity.ok().body(petService.showImage(uuid));
	}
	
	@DeleteMapping(
			value = "/{uuid}",
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public  ResponseEntity<PetDto>  deletePet(
			@PathVariable(value = "uuid") UUID uuid) {
		petService.delete(uuid);
		return  ResponseEntity.noContent().build();
	}
}
