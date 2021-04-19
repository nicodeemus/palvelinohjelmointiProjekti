package hh.swd20.palvelinohjelmointiprojekti.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Genre {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long GenreId;
	
	private String nimi;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy ="genre")
	private List<Elokuva> Elokuvat; 
	
	
	
	public Long getGenreId() {
		return GenreId;
	}

	public void setGenreId(Long genreId) {
		GenreId = genreId;
	}

		public List<Elokuva> getElokuvat() {
		return Elokuvat;
	}

	public void setElokuvat(List<Elokuva> elokuvat) {
		Elokuvat = elokuvat; 
	}
	

	public Genre() {
		super();
	}

	public Genre(String nimi) {
		super();
		this.nimi = nimi;
	}

	public Long getId() {
		return GenreId;
	}

	public void setId(Long GenreId) {
		this.GenreId = GenreId;
	}

	public String getNimi() {
		return nimi;
	}

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	@Override
	public String toString() {
		return "Tag [GenreId=" + GenreId + ", nimi=" + nimi + "]";
	}
	
	
	

}
