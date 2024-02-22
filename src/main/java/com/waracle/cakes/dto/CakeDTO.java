package com.waracle.cakes.dto;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CakeDTO {
	public Long id;

	@NotNull(message = "title is required.")
	@Size(min = 3, max = 30, message = "title must be from 3 to 30 characters.")
	public String title;

	@NotNull(message = "description is required.")
	@Size(min = 5, max = 100, message = "description must be from 5 to 100 characters.")
	public String description;

	@NotNull(message = "image is required.")
	@URL(message = "image should be a valid url.")
	public String image;
}
