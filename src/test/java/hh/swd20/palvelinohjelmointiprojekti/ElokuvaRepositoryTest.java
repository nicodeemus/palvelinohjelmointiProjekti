package hh.swd20.palvelinohjelmointiprojekti;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import hh.swd20.palvelinohjelmointiprojekti.domain.Elokuva;
import hh.swd20.palvelinohjelmointiprojekti.domain.ElokuvaRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ElokuvaRepositoryTest {
	
	@Autowired
	private ElokuvaRepository repository;
	
	@Test
	public void createNewElokuva() {
		Elokuva elokuva = new Elokuva();
		repository.save(elokuva);
		assertThat(elokuva.getId()).isNotNull();
	}
	
	@Test
	public void deleteElokuva() {
		Elokuva elokuva = new Elokuva();
		repository.deleteAll();
		
		assertThat(elokuva.getId()).isNull();
	}
	
}
