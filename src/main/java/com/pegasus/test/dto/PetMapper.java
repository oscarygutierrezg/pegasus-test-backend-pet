package com.pegasus.test.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.pegasus.test.model.Pet;


@Mapper(
		componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		unmappedTargetPolicy = ReportingPolicy.IGNORE,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
		)
public abstract class PetMapper {
	                
	public abstract PetDto toDto(Pet Pet);
	
	@Mapping(source = "image", target = "imageBase64", qualifiedByName = "bytesToString")  	                
	public abstract PetImageDto toImageDto(Pet Pet);
	
	@Mapping(source = "image", target = "imageBase64", qualifiedByName = "bytesToString")  	                
	public abstract PetDto toFullDto(Pet Pet);

	@Mapping(source = "imageBase64", target = "image", qualifiedByName = "stringToBytes")  
	public abstract Pet toModel(PetRequest Pet);

	public  abstract void updateModel(PetRequest PetRequest, @MappingTarget Pet Pet);

	@Named("bytesToString")
	String bytesToString(byte[] image) {
		return new String(image);
	}
	
	@Named("stringToBytes")
	byte[]  stringToBytes(String imageBase64) {
		return imageBase64.getBytes();
	}

}
