package hh.swd20.palvelinohjelmointiprojekti.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import hh.swd20.palvelinohjelmointiprojekti.domain.*;

@Entity
public class Elokuva {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String nimi;
	private int kesto;
	
	@ManyToOne
	@JsonIgnoreProperties ("elokuvat")
	@JoinColumn(name ="GenreId")
	private Genre genre;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(
		name = "elokuva_tag",
		joinColumns = {@JoinColumn(name="id") },
		inverseJoinColumns = {@JoinColumn(name="TagId")})
	private List<Tag> tagit;
	
	
	
	
	
	public Elokuva() {
		super();
		
		this.tagit = new ArrayList<Tag>();
	}
	
	public Elokuva(String nimi, int kesto, Genre genre, List<Tag> tagit) {
		super();
		this.nimi = nimi;
		this.kesto = kesto;
		this.genre = genre;
		this.tagit = tagit;
	}
	
	public void newTagAdder(String nimi) {
		Tag tag = new Tag("nimi");
		tagit.add(tag);
		
	}
	
	public String printTags() {
		String tagsAsString = "";
		
	
		return tagsAsString;
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNimi() {
		return nimi;
	}
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}
	public int getKesto() {
		return kesto;
	}
	public void setKesto(int kesto) {
		this.kesto = kesto;
	}
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	
	
	public List<Tag> getTagit() {
		return tagit;
	}

	public void setTagit(List<Tag> tagit) {
		this.tagit = tagit;
	}

	public String listTags() {
		String tagLista = ";";
		
		for (Tag tag  : tagit) {
			tagLista += tag;
		}
		
		return tagLista;
	}

	
	
	
}
