package com.pegasus.test.dto;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pegasus.test.dto.request.OnCreate;

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
public class PetRequest {
	
	@NotNull(message = "is mandatory", groups = OnCreate.class)
	@NotEmpty(message = "must not be empty", groups = OnCreate.class)
	protected String name;
	@NotNull(message = "is mandatory", groups = OnCreate.class)
	private UUID personId;
	@NotNull(message = "is mandatory", groups = OnCreate.class)
	@NotEmpty(message = "must not be empty", groups = OnCreate.class)
	private String color;
	@NotNull(message = "is mandatory", groups = OnCreate.class)
	@NotEmpty(message = "must not be empty", groups = OnCreate.class)
	private String breed;
	@NotNull(message = "is mandatory", groups = OnCreate.class)
	@NotEmpty(message = "must not be empty", groups = OnCreate.class)
	private String imageBase64;
	
	
	
}
