package com.pegasus.test.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetDto {
	
	private UUID id;
	private UUID personId;
	private String name;
	private String color;
	private String breed;
	@JsonInclude(value  = Include.NON_NULL)
	private String imageBase64;
}
