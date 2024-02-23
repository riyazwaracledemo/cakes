package com.waracle.cakes.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakes.dto.CakeDTO;
import com.waracle.cakes.entity.Cake;
import com.waracle.cakes.service.CakesService;

@WebMvcTest(CakesController.class)
public class CakesControllerTest {

	@MockBean
	private CakesService cakesService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	public void non_admin_should_not_be_able_to_add_cake() throws Exception {
		Cake cake = Cake.builder().id(1L).title("New Cake").description("new cake description").image(
				"https://media.istockphoto.com/id/1136810581/photo/birthday-cake-decorated-with-colorful-sprinkles-and-ten-candles.webp?b=1&s=170667a&w=0&k=20&c=OphwD8QZhsghyG5W7P8MzD1uw9Nze38zf6JvdDxcjRU=")
				.build();
		CakeDTO cakeDTO = modelMapper.map(cake, CakeDTO.class);
		when(cakesService.saveCake(cake)).thenReturn(cake);
		mockMvc.perform(post("/addCake").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cakeDTO))).andExpect(status().isForbidden()).andDo(print());
	}

	@Test
	@WithMockUser(username = "user1", roles = { "USER" })
	public void should_get_cake_by_id() throws Exception {
		long id = 1L;
		Cake cake = Cake.builder().id(1L).title("New Cake").description("new cake description").image(
				"https://media.istockphoto.com/id/1136810581/photo/birthday-cake-decorated-with-colorful-sprinkles-and-ten-candles.webp?b=1&s=170667a&w=0&k=20&c=OphwD8QZhsghyG5W7P8MzD1uw9Nze38zf6JvdDxcjRU=")
				.build();
		when(cakesService.getCakeById(id)).thenReturn(Optional.of(cake));
		mockMvc.perform(get("/cakes/{id}", id)).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.title").value(cake.getTitle()))
				.andExpect(jsonPath("$.description").value(cake.getDescription()))
				.andExpect(jsonPath("$.image").value(cake.getImage())).andDo(print());
	}

	@Test
	@WithMockUser(username = "user1", roles = { "USER" })
	public void should_get_all_cakes() throws Exception {

		Cake cake1 = Cake.builder().id(1L).title("New Cake1").description("new cake1 description").image(
				"https://media.istockphoto.com/id/1136810581/photo/birthday-cake-decorated-with-colorful-sprinkles-and-ten-candles.webp?b=1&s=170667a&w=0&k=20&c=OphwD8QZhsghyG5W7P8MzD1uw9Nze38zf6JvdDxcjRU=")
				.build();
		Cake cake2 = Cake.builder().id(2L).title("New Cake2").description("new cake2 description").image(
				"https://media.istockphoto.com/id/1136810581/photo/birthday-cake-decorated-with-colorful-sprinkles-and-ten-candles.webp?b=1&s=170667a&w=0&k=20&c=OphwD8QZhsghyG5W7P8MzD1uw9Nze38zf6JvdDxcjRU=")
				.build();

		when(cakesService.getCakes()).thenReturn(Optional.of(List.of(cake1, cake2)));
		mockMvc.perform(get("/cakes").accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(2));
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	public void non_admin_user_should_not_be_able_to_update_a_cake() throws Exception {

		Cake cake = Cake.builder().id(1L).title("Cake").description("cake description").image("/path/to/new/image1")
				.build();
		CakeDTO cakeDTO = modelMapper.map(cake, CakeDTO.class);
		Cake updatedCake = Cake.builder().id(1L).title("Updated Cake").description("updated cake description").image(
				"https://media.istockphoto.com/id/1136810581/photo/birthday-cake-decorated-with-colorful-sprinkles-and-ten-candles.webp?b=1&s=170667a&w=0&k=20&c=OphwD8QZhsghyG5W7P8MzD1uw9Nze38zf6JvdDxcjRU=")
				.build();

		when(cakesService.updateCake(cake)).thenReturn(updatedCake);
		mockMvc.perform(put("/updateCake").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cakeDTO))).andExpect(status().isForbidden()).andDo(print());
	}

}
