package com.cimpoint.mes.common.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cimpoint.mes.common.MESConstants;

@Entity
@Table(name="Dictionary")
public class EDictionary implements Serializable {
	private static final long serialVersionUID = 6294158034456116711L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Name", length=50, nullable=false, unique=true)
	private String name;
	
	@Column(name="Description", length=255)
	private String description;
	
	@Column(name="Type", length=20)
	@Enumerated(EnumType.STRING)
	private MESConstants.Dictionary.Type type;
	
	@OneToMany(mappedBy = "dictionary", targetEntity = ELocalizedString.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)  
	private Set<ELocalizedString> localizedStrings;
		
	public EDictionary() {
	}
	
	public EDictionary(String name, String description, Set<ELocalizedString> localedStrings) {
		this.setName(name);
		this.setDescription(description);
		this.setLocalizedStrings(localedStrings);
	}
			
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<ELocalizedString> getLocalizedStrings() {
		return localizedStrings;
	}

	public void setLocalizedStrings(Set<ELocalizedString> localizedStrings) {
		this.localizedStrings = localizedStrings;
	}
	
	public boolean equals(Object obj) {  
		if (obj instanceof EDictionary && obj != null) {
			EDictionary e = (EDictionary) obj;
			if (e.getName().equalsIgnoreCase(this.getName())) {
				return true;
			}
		}
	    return false;
    }

	public MESConstants.Dictionary.Type getType() {
		return type;
	}

	public void setType(MESConstants.Dictionary.Type type) {
		this.type = type;
	}  
}
