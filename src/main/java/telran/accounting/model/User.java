package telran.accounting.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "email" })
@Document(collection = "user") 
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6385710609852631794L;
	String name;
	@Id
	String email;
	String password;
	String avatar;
	String phone;
	Boolean blockStatus = false;
	Set<String> roles = new HashSet<>();
	Set<String> favorite = new HashSet<>();
	Set<String> acivity = new HashSet<>();

	public boolean addRole(String role) {
		return roles.add(role);
	}

	public boolean removeRole(String role) {
		return roles.remove(role);
	}
}
