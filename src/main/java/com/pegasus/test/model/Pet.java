package com.pegasus.test.model;

import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "pet")
@EntityListeners(AuditingEntityListener.class)

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
	@Type(type = "uuid-char")	
	private UUID id;
	@Column(name = "person_id", nullable = false, columnDefinition = "VARCHAR(36)")
	@Type(type = "uuid-char")	
	private UUID personId;
	private String name;
	private String color;
	private String breed;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] image;
	
}
