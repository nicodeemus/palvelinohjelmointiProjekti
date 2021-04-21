package hh.swd20.palvelinohjelmointiprojekti.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.metrics.StartupStep.Tags;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import hh.swd20.palvelinohjelmointiprojekti.domain.Elokuva;
import hh.swd20.palvelinohjelmointiprojekti.domain.ElokuvaRepository;
import hh.swd20.palvelinohjelmointiprojekti.domain.GenreRepository;
import hh.swd20.palvelinohjelmointiprojekti.domain.Tag;
import hh.swd20.palvelinohjelmointiprojekti.domain.TagRepository;


@Controller
public class ElokuvaController {
	
	@Autowired
	private ElokuvaRepository erepository;
	
	@Autowired
	private GenreRepository grepository;
	
	@Autowired
	private TagRepository trepository;
	
	@RequestMapping(value = {"/elokuvalista", "/",""})
	public String Elokuvalist (Model model) {
		model.addAttribute("elokuvat", erepository.findAll());
		return "elokuvalista";
	}  
	
	@RequestMapping(value = "/lisaaelokuva")
	public String LisaaElokuva (Model model) {
		Elokuva elokuva = new Elokuva();
		elokuva.getTagit().add(new Tag());
		model.addAttribute("elokuva", elokuva);
		model.addAttribute("genret", grepository.findAll());
		
		
		return "elokuvaLisays";
	}
	

	
	@RequestMapping(value = "/login")
	public String login() {		
		return "login";
	}  
	
	@RequestMapping(value ="/edit/{id}", method = RequestMethod.GET)
	public String editElokuva(@PathVariable("id") Long elokuvaId, Model model) {
		
		Elokuva elokuva =  erepository.findById(elokuvaId).get();
		
		model.addAttribute("elokuva", elokuva);
		model.addAttribute("genret", grepository.findAll());
	
		
		
		return "elokuvaMuokkaus";
	}
	
	@RequestMapping(value="/elokuvatTagilla/{id}", method = RequestMethod.GET)
	public String elokuvatTagilla(@PathVariable("id") Long tagId, Model model) {
		
		model.addAttribute("tag", trepository.findById(tagId).get());
	
		
		
		return "elokuvatTagilla";
	}
	
	@RequestMapping(value="/elokuvatGenrella/{id}", method = RequestMethod.GET)
	public String elokuvatGenrella(@PathVariable("id") Long genreId, Model model) {
		
		model.addAttribute("genre", grepository.findById(genreId).get());
	
		
		
		return "elokuvatGenrella";
	}
	

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteElokuva(@PathVariable("id") Long Id, Model model) {
		erepository.deleteById(Id);
		return "redirect:../elokuvalista";
	}
	
	//funktio rivin lisäämiseen tageille
	@RequestMapping(value = "/addrow", method = RequestMethod.POST)
	public String addnewrow(@ModelAttribute Elokuva elokuva, Model model) {
		
		elokuva.getTagit().add(new Tag());
		model.addAttribute("elokuva", elokuva);
		model.addAttribute("genret", grepository.findAll());
		
		if(elokuva.getTagit().get(0).getTagId()==null) {
			return "elokuvaLisays";
		}else {
			return "elokuvaMuokkaus";
		}
		
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String saveElokuva(@ModelAttribute Elokuva elokuva) {
		
		
		
		//Tarkastaa ettei samoja tageja
		for(int i = 0; i<elokuva.getTagit().size(); i++) {
			elokuva.getTagit().get(i).setNimi(elokuva.getTagit().get(i).getNimi().toLowerCase().replaceAll("\\s+", ""));
			for(int j= i+1; j<elokuva.getTagit().size(); j++) {
				elokuva.getTagit().get(j).setNimi(elokuva.getTagit().get(j).getNimi().toLowerCase().replaceAll("\\s+", ""));
				if(elokuva.getTagit().get(i).getNimi().equals(elokuva.getTagit().get(j).getNimi())) {
					elokuva.getTagit().remove(j);
					j--;
				}
			}
		}
		
		boolean wasMerged = false;	
				
		Iterable<Tag> kaikkiTagit = trepository.findAll();
		List<Tag>  elokuvaTagit = elokuva.getTagit();
		
		//poistaa isot kirjaimet ja välit, yhdistää uudet tagit vanhoihin tai tallentaa uudet
		for(int i = 0; i<elokuvaTagit.size(); i++) {

			if(elokuvaTagit.get(i).getNimi().isEmpty()) {
				elokuvaTagit.remove(i);
				continue;
			}
			
			for(Tag tag : kaikkiTagit) {
			wasMerged=false;
				
				if(elokuvaTagit.get(i).getNimi().equals(tag.getNimi())){
					elokuvaTagit.get(i).setTagId(tag.getTagId());
					wasMerged = true;
					break;
				}
					
			}
			if(!wasMerged) {
			trepository.save(elokuvaTagit.get(i));	
			}
		
		}
		
		erepository.save(elokuva);
		return "redirect:elokuvalista";
	}
	
		
		@RequestMapping(value="/saveEdit", method = RequestMethod.POST)
		public String saveEditedElokuva(@ModelAttribute Elokuva elokuva) {
			
			//Tarkastaa ettei samoja tageja
			for(int i = 0; i<elokuva.getTagit().size(); i++) {
				elokuva.getTagit().get(i).setNimi(elokuva.getTagit().get(i).getNimi().toLowerCase().replaceAll("\\s+", ""));
				for(int j= i+1; j<elokuva.getTagit().size(); j++) {
					elokuva.getTagit().get(j).setNimi(elokuva.getTagit().get(j).getNimi().toLowerCase().replaceAll("\\s+", ""));
					if(elokuva.getTagit().get(i).getNimi()==elokuva.getTagit().get(j).getNimi()) {
						elokuva.getTagit().remove(j);
						j--;
					}
				}
			}
			
			boolean wasMerged = false;
			boolean TagNameWasAltered = false;
			
			
			//hdistää uudet tagit vanhoihin tai tallentaa uudet
			Iterable<Tag> kaikkiTagit = trepository.findAll();
			List<Tag>  elokuvaTagit = elokuva.getTagit();
		
			for(int i = 0; i<elokuvaTagit.size(); i++) {
				
				if(elokuvaTagit.get(i).getNimi().isEmpty()) {
					elokuvaTagit.remove(i);
					continue;
				}

				for(Tag tag : kaikkiTagit) {
					System.out.println(elokuvaTagit.get(i));
				wasMerged=false;
					
					if(elokuvaTagit.get(i).getNimi().equals(tag.getNimi())&&
							tag.getTagId().equals(elokuvaTagit.get(i).getTagId())){
							elokuvaTagit.get(i).setTagId(tag.getTagId());
							wasMerged = true;
							break;
					
					}else if(elokuvaTagit.get(i).getNimi().equals(tag.getNimi())&&
					!(tag.getTagId().equals(elokuvaTagit.get(i).getTagId()))) {
						elokuvaTagit.get(i).setTagId(tag.getTagId());
						break;
						
					
						
					
					}else if(!(elokuvaTagit.get(i).getNimi().equals(tag.getNimi()))&&
						tag.getTagId().equals(elokuvaTagit.get(i).getTagId())) {
						elokuvaTagit.get(i).setTagId(null);
						TagNameWasAltered = true;
						break;
						
					}
						
					}
				

				if(!wasMerged || TagNameWasAltered) {
						trepository.save(elokuvaTagit.get(i));	
				}
			
			}
		
		
		erepository.save(elokuva);
		return "redirect:elokuvalista";
	}
	

}
