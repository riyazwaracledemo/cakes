package com.waracle.cakes.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.waracle.cakes.entity.Cake;
import com.waracle.cakes.repository.CakesRepository;

@ExtendWith(MockitoExtension.class)
public class CakesServiceTest {

	@InjectMocks
	private CakesService service;

	@Mock
	private CakesRepository repo;

	@Test
	public void should_save_one_cake() {
		// Arrange
		final var cakeToSave = Cake.builder().title("New Cake").description("new cake description")
				.image("path/to/new/image").build();
		when(repo.save(any(Cake.class))).thenReturn(cakeToSave);

		// Act
		final var actual = service.saveCake(new Cake());

		// Assert
		assertThat(actual).usingRecursiveComparison().isEqualTo(cakeToSave);
		verify(repo, times(1)).save(any(Cake.class));
		verifyNoMoreInteractions(repo);
	}

	@Test
	public void should_find_and_return_one_cake() {
		// Arrange
		final var expectedCake = Cake.builder().title("New Cake").description("new cake description")
				.image("path/to/new/image").build();
		when(repo.findById(anyLong())).thenReturn(Optional.of(expectedCake));

		// Act
		final var actual = service.getCakeById(getRandomLong());

		// Assert
		assertThat(actual).usingRecursiveComparison().isEqualTo(Optional.of(expectedCake));
		verify(repo, times(1)).findById(anyLong());
		verifyNoMoreInteractions(repo);
	}

	@Test
	public void should_find_and_return_all_Cake() {
		// Arrange
		when(repo.findAll()).thenReturn(List.of(new Cake(), new Cake()));

		// Act & Assert
		assertThat(service.getCakes().get().size() == 2);
		verify(repo, times(1)).findAll();
		verifyNoMoreInteractions(repo);
	}

	@Test
	public void should_delete_one_Cake() {
		// Arrange
		doNothing().when(repo).deleteById(anyLong());

		// Act & Assert
		service.deleteCake(getRandomLong());
		verify(repo, times(1)).deleteById(anyLong());
		verifyNoMoreInteractions(repo);
	}

	private long getRandomLong() {
		return new Random().longs(1, 10).findFirst().getAsLong();
	}

}
