package org.sobakaisti.core.model;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author jelles
 * Model klasa koja reprezentuje autore (Sobakaiste)
 */
@Entity
@Table(name="AUTHOR")
public class Author {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	
	@Pattern(regexp="[a-zA-Z]{0,20}", message="{validation.warn.pattern}")
	@Size(min=2, max=20, message="{validation.warn.firstName}")
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Pattern(regexp="[a-zA-Z\\s\\.]{0,30}", message="{validation.warn.pattern}")
	@Size(min=2, max=30, message="{validation.warn.lastName}")
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_OF_BIRTH")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Calendar dob;
	
	@Pattern(regexp="[a-zA-Z\\s\\.\\,]{2,50}", message="{validation.warn.pattern}")
	@Column(name="BIRTHPLACE")
	private String birthplace;
	
	@Pattern(regexp="[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})",
			message="{validation.warn.mailPattern}")
	@Column(name="EMAIL")
	private String email;
	@Column(name="WEBSITE")
	private String website;
	@Column(name="SHORT_BIO")
	private String shortBio;
	
	@Pattern(regexp="[a-zA-Z\\s\\.\\,\\/]{2,50}", message="{validation.warn.pattern}")
	@Column(name="PROFESSION")
	private String profession;
	@Column(name="AVATAR_PATH")
	private String avatarPath;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Calendar getDob() {
		return dob;
	}
	public void setDob(Calendar dob) {
		this.dob = dob;
	}
	public String getBirthplace() {
		return birthplace;
	}
	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getShortBio() {
		return shortBio;
	}
	public void setShortBio(String shortBio) {
		this.shortBio = shortBio;
	}
	public String getAvatarPath() {
		return avatarPath;
	}
	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}	
	
	@Override
	public String toString() {
		return "[Autor: "+firstName+" "+lastName+", "+birthplace+", "+email+"]";
	}
}