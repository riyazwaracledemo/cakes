package com.waracle.cakes.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.waracle.cakes.entity.Cake;

@DataJpaTest
public class CakesRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CakesRepository repository;

	@Test
	public void should_find_no_cakes_if_repository_is_empty() {
		Iterable<Cake> cakes = repository.findAll();
		assertThat(cakes).isEmpty();
	}

	@Test
	public void should_store_a_cake() {
		Cake cake = repository.save(Cake.builder().title("New Cake").description("new cake description")
				.image("path/to/new/image").build());
		assertThat(cake).hasFieldOrPropertyWithValue("title", "New Cake");
		assertThat(cake).hasFieldOrPropertyWithValue("description", "new cake description");
		assertThat(cake).hasFieldOrPropertyWithValue("image", "path/to/new/image");
	}

	@Test
	public void should_find_all_cakes() {
		Cake cake1 = Cake.builder().title("New Cake1").description("new cake1 description").image("path/to/new/image1")
				.build();
		entityManager.persist(cake1);

		Cake cake2 = Cake.builder().title("New Cake2").description("new cake2 description").image("path/to/new/image2")
				.build();
		entityManager.persist(cake2);

		Cake cake3 = Cake.builder().title("New Cake3").description("new cake3 description").image("path/to/new/image3")
				.build();
		entityManager.persist(cake3);

		List<Cake> cakes = repository.findAll();

		assertThat(cakes).hasSize(3).contains(cake1, cake2, cake3);
	}

	@Test
	public void should_find_cake_by_id() {
		Cake cake1 = Cake.builder().title("New Cake1").description("new cake1 description").image("path/to/new/image1")
				.build();
		entityManager.persist(cake1);

		Cake cake2 = Cake.builder().title("New Cake2").description("new cake2 description").image("path/to/new/image2")
				.build();
		entityManager.persist(cake2);

		Cake foundcake = repository.findById(cake2.getId()).get();

		assertThat(foundcake).isEqualTo(cake2);
	}

	@Test
	public void should_update_cake_by_id() {
		Cake cake1 = Cake.builder().title("New Cake1").description("new cake1 description").image("path/to/new/image1")
				.build();
		entityManager.persist(cake1);

		Cake cake2 = Cake.builder().title("New Cake2").description("new cake2 description").image("path/to/new/image2")
				.build();
		entityManager.persist(cake2);

		Cake updatedcake = Cake.builder().title("Updated Cake2").description("updated cake2 description")
				.image("path/to/new/image2").build();

		Cake cake = repository.findById(cake2.getId()).get();
		cake.setTitle(updatedcake.getTitle());
		cake.setDescription(updatedcake.getDescription());
		cake.setImage(updatedcake.getImage());
		repository.save(cake);

		Cake checkcake = repository.findById(cake2.getId()).get();

		assertThat(checkcake.getId()).isEqualTo(cake2.getId());
		assertThat(checkcake.getTitle()).isEqualTo(updatedcake.getTitle());
		assertThat(checkcake.getDescription()).isEqualTo(updatedcake.getDescription());
		assertThat(checkcake.getImage()).isEqualTo(updatedcake.getImage());
	}

	@Test
	public void should_delete_cake_by_id() {
		Cake cake1 = Cake.builder().title("New Cake1").description("new cake1 description").image("path/to/new/image1")
				.build();
		entityManager.persist(cake1);

		Cake cake2 = Cake.builder().title("New Cake2").description("new cake2 description").image("path/to/new/image2")
				.build();
		entityManager.persist(cake2);

		Cake cake3 = Cake.builder().title("New Cake3").description("new cake3 description").image("path/to/new/image3")
				.build();
		entityManager.persist(cake3);

		repository.deleteById(cake2.getId());
		List<Cake> cakes = repository.findAll();
		assertThat(cakes).hasSize(2).contains(cake1, cake3);
	}

}
