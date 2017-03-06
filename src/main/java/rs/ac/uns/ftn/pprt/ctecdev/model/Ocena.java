package rs.ac.uns.ftn.pprt.ctecdev.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ocena {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String clientID;
	private String companyID;
	private Integer grade;
	
	
	
	public Ocena() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Ocena(String clientID, String companyID, Integer grade) {
		super();
		this.clientID = clientID;
		this.companyID = companyID;
		this.grade = grade;
	}
	
	public Ocena(int id, String clientID, String companyID, Integer grade) {
		super();
		this.id = id;
		this.clientID = clientID;
		this.companyID = companyID;
		this.grade = grade;
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
	
	
}
