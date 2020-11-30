
package telran.accounting.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	String name;
	String email;
	String avatar;
	String phone;
	boolean blockStatus = false;
	Set<String> roles;
}
