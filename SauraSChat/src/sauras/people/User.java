package sauras.people;

import java.util.ArrayList;

public class User {

	
	//Atributes
	private String ID, userName, email;
	ArrayList<String> groups;
	
	
	//Constructors
	public User() {
		groups = new ArrayList<String>();
	}

	public User(String userName, String email, ArrayList<String> groups) {
		this.ID =ID;
		this.email = email;
		this.userName = userName;
		if(groups != null)this.groups = groups;
		else this.groups = new ArrayList<String>();
	}
	
	
	//Methods
	
	
	
	//Getters & Setters
	public String getID() {
		return ID;
	}


	public void setID(String iD) {
		ID = iD;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
			this.userName = userName;
	}
}
