package hh.swd20.palvelinohjelmointiprojekti.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Tag {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long TagId;
	
	private String nimi;
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy="tagit")
	private List<Elokuva> elokuvat;
	
	
	
	public Tag() {
		super();
	}
		
	public Tag(String nimi) {
		super();
		this.nimi = nimi;
	}

	public Long getTagId() {
		return TagId;
	}

	public void setTagId(Long TagId) {
		this.TagId = TagId;
	}

	public String getNimi() {
		return nimi;
	}

	public List<Elokuva> getElokuvat() {
		return elokuvat;
	}

	public void setElokuvat(List<Elokuva> elokuvat) {
		this.elokuvat = elokuvat;
	}

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	@Override
	public String toString() {
		return "Tag [TagId=" + TagId + ", nimi=" + nimi + "]";
	}

	
	
	
	

}
