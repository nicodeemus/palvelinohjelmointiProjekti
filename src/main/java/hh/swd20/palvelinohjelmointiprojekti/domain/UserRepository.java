package hh.swd20.palvelinohjelmointiprojekti.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{
	
	User findByUsername(String username);

}
