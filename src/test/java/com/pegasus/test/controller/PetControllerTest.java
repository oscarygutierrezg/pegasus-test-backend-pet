package com.pegasus.test.controller;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pegasus.test.PetApplication;
import com.pegasus.test.dto.PetDto;
import com.pegasus.test.dto.PetImageDto;
import com.pegasus.test.dto.PetRequest;
import com.pegasus.test.model.Pet;
import com.pegasus.test.repository.PetRepository;
import com.pegasus.test.util.PetUtil;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PetApplication.class)
public class PetControllerTest {

	private static final String AUTHORIZATION = "Authorization";
	private static final String BASIC = "Basic dGVzdFVzZXI6eENNYms1MDgz";

	@Autowired
	private MockMvc mockMvc;	
	@Autowired
	private PetRepository petRepository;
	@Autowired
	private ObjectMapper objectMapper;
	
	private PetUtil petUtil = new PetUtil();
	
	@Test
	public void test_Create_Should_CreatedPet_When_Invoked() throws JsonProcessingException, Exception {
		PetRequest petRequest = petUtil.createPetRequest();
		
		ResultActions res =    mockMvc.perform(
	            MockMvcRequestBuilders.post("/v1/pet")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(petRequest))
	                .accept(MediaType.APPLICATION_JSON)
	                .header(AUTHORIZATION, BASIC)
	        )
	            .andDo(MockMvcResultHandlers.print())
	            .andExpectAll(
	                    MockMvcResultMatchers.status().isCreated()
	                
	            );
		
		Assertions.assertNotNull(res);
		Assertions.assertNotNull(res.andReturn());
		Assertions.assertNotNull(res.andReturn().getResponse());
		Assertions.assertNotNull(res.andReturn().getResponse().getContentAsString());
		PetDto petDto = objectMapper.readValue(res.andReturn().getResponse().getContentAsString(), PetDto.class);
		Assertions.assertNotNull(petDto.getId());
		
		Pet pet = petRepository.findById(petDto.getId()).get();
		
		Assertions.assertNotNull(pet);
		Assertions.assertTrue(pet.getName().equals(petDto.getName()));
		petRepository.deleteById(petDto.getId());
		
	}
	
	@Test
	public void test_Update_Should_Updateet_When_Invoked() throws JsonProcessingException, Exception {
		Pet pet = new Pet();
		pet.setPersonId(UUID.randomUUID());
		petRepository.save(pet);
		PetRequest petRequest = petUtil.createPetRequest();
		
		ResultActions res =    mockMvc.perform(
	            MockMvcRequestBuilders.put("/v1/pet/"+pet.getId())
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(petRequest))
	                .accept(MediaType.APPLICATION_JSON)
	                .header(AUTHORIZATION, BASIC)
	        )
	            .andDo(MockMvcResultHandlers.print())
	            .andExpectAll(
	                    MockMvcResultMatchers.status().isOk()
	                
	            );
		
		Assertions.assertNotNull(res);
		Assertions.assertNotNull(res.andReturn());
		Assertions.assertNotNull(res.andReturn().getResponse());
		Assertions.assertNotNull(res.andReturn().getResponse().getContentAsString());
		PetDto petDto = objectMapper.readValue(res.andReturn().getResponse().getContentAsString(), PetDto.class);
		Assertions.assertNotNull(petDto.getId());
		
		pet = petRepository.findById(petDto.getId()).get();
		
		Assertions.assertNotNull(pet);
		Assertions.assertTrue(pet.getName().equals(petDto.getName()));
		petRepository.deleteById(petDto.getId());
		
	}
	
	@Test
	public void test_Show_Should_ShowPet_When_Invoked() throws JsonProcessingException, Exception {
		Pet pet = petUtil.createPet();
		pet.setId(null);
		petRepository.save(pet);
		
		
		
		ResultActions res =    mockMvc.perform(
	            MockMvcRequestBuilders.get("/v1/pet/"+pet.getId())
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .header(AUTHORIZATION, BASIC)
	        )
	            .andDo(MockMvcResultHandlers.print())
	            .andExpectAll(
	                    MockMvcResultMatchers.status().isOk()
	                
	            );
		
		Assertions.assertNotNull(res);
		Assertions.assertNotNull(res.andReturn());
		Assertions.assertNotNull(res.andReturn().getResponse());
		Assertions.assertNotNull(res.andReturn().getResponse().getContentAsString());
		PetDto petDto = objectMapper.readValue(res.andReturn().getResponse().getContentAsString(), PetDto.class);
		Assertions.assertTrue(pet.getName().equals(petDto.getName()));
		petRepository.deleteById(petDto.getId());
	}
	
	@Test
	public void test_Show_Should_ShowImagePet_When_Invoked() throws JsonProcessingException, Exception {
		Pet pet = petUtil.createPet();
		pet.setId(null);
		petRepository.save(pet);
		
		
		
		ResultActions res =    mockMvc.perform(
	            MockMvcRequestBuilders.get("/v1/pet/"+pet.getId()+"/image")
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .header(AUTHORIZATION, BASIC)
	        )
	            .andDo(MockMvcResultHandlers.print())
	            .andExpectAll(
	                    MockMvcResultMatchers.status().isOk()
	                
	            );
		
		Assertions.assertNotNull(res);
		Assertions.assertNotNull(res.andReturn());
		Assertions.assertNotNull(res.andReturn().getResponse());
		Assertions.assertNotNull(res.andReturn().getResponse().getContentAsString());
		PetImageDto petDto = objectMapper.readValue(res.andReturn().getResponse().getContentAsString(), PetImageDto.class);
		Assertions.assertNotNull(petDto.getImageBase64());
		petRepository.deleteById(pet.getId());
	}
	
	@Test
	public void test_Index_Should_ShowPagePet_When_Invoked() throws JsonProcessingException, Exception {
		petRepository.save(petUtil.createPet());
		petRepository.save(petUtil.createPet());
		petRepository.save(petUtil.createPet());
		petRepository.save(petUtil.createPet());
		
		mockMvc.perform(
	            MockMvcRequestBuilders.get("/v1/pet")
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .header(AUTHORIZATION, BASIC)
	        )
	            .andDo(MockMvcResultHandlers.print())
	            .andExpectAll(
	                    MockMvcResultMatchers.status().isOk(),
	                    MockMvcResultMatchers.jsonPath("$.totalElements").value(4),
	                    MockMvcResultMatchers.jsonPath("$.totalPages").value(1)
	                    
	                    
	                
	            );
		
	}
	
	@Test
	public void test_Delete_Should_DeletePet_When_Invoked() throws JsonProcessingException, Exception {
		Pet pet = petUtil.createPet();
		pet.setId(null);
		petRepository.save(pet);
		
		mockMvc.perform(
	            MockMvcRequestBuilders.delete("/v1/pet/"+pet.getId())
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .header(AUTHORIZATION, BASIC)
	        )
	            .andDo(MockMvcResultHandlers.print())
	            .andExpectAll(
	                    MockMvcResultMatchers.status().isNoContent()
	                
	            );
	}
	
}


