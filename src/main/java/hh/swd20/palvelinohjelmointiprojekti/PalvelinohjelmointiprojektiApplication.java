package hh.swd20.palvelinohjelmointiprojekti;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.swd20.palvelinohjelmointiprojekti.domain.*;

@SpringBootApplication
public class PalvelinohjelmointiprojektiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PalvelinohjelmointiprojektiApplication.class, args);
	}
	
	
	
		@Bean
		public CommandLineRunner demo(ElokuvaRepository erepository, GenreRepository grepository, TagRepository trepository,UserRepository urepository) {
			return (args) -> {
		
						
				Genre Ga = new Genre("kauhu");
				Genre Gb = new Genre ("romanssi");
				Genre Gc = new Genre ("trilleri");
				
				Tag Ta = new Tag("vanha");
				Tag Tb = new Tag("klassikko");
				
				List<Tag> EaTagList = new ArrayList<>();
				EaTagList.add(Ta);
				EaTagList.add(Tb);
				
				Elokuva Ea = new Elokuva ("Manaaja", 132, Ga, EaTagList);
				
				Tag Tc = new Tag("leonardodicaprio");
				
				List<Tag> EbTagList = new ArrayList<>();
				
				EbTagList.add(Tc);
				EbTagList.add(Tb);
				
				Elokuva Eb = new Elokuva ("Titanic", 210, Gb, EbTagList);
				
				User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
				User user2 = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN");
				
				urepository.save(user1);
				urepository.save(user2);
				
				grepository.save(Ga);
				grepository.save(Gb);
				grepository.save(Gc);
				
				trepository.save(Ta);
				trepository.save(Tb);
				trepository.save(Tc);
				
				erepository.save(Ea);
				erepository.save(Eb);
			
				
				

			};
			
		

	}


}
