package hh.swd20.palvelinohjelmointiprojekti;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import hh.swd20.palvelinohjelmointiprojekti.domain.Tag;
import hh.swd20.palvelinohjelmointiprojekti.domain.ElokuvaRepository;
import hh.swd20.palvelinohjelmointiprojekti.domain.Tag;
import hh.swd20.palvelinohjelmointiprojekti.domain.TagRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TagRepositoryTest {
	
	@Autowired
	private TagRepository repository;
	
	@Test
	public void createNewTag() {
		Tag tag = new Tag();
		repository.save(tag);
		assertThat(tag.getTagId()).isNotNull();
	}
	
	@Test
	public void editTag() {
		Tag tag = new Tag("tag1");
		
		repository.save(tag);
		repository.findById(tag.getTagId());
		tag.setNimi("tag2");
		repository.save(tag);
		
		assertThat(repository.findById(tag.getTagId()).get().getNimi()).isEqualTo("tag2");
	}
	
	@Test
	public void deleteTag() {
		Tag Tag = new Tag();
		repository.deleteAll();
		
		assertThat(Tag.getTagId()).isNull();
	}
	
}
