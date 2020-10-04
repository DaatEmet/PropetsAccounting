package telran.accounting;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import telran.accounting.dao.UserRepository;
import telran.accounting.model.User;

@SpringBootApplication
public class PropetsAccountingApplication implements CommandLineRunner {
	
	@Autowired
	UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(PropetsAccountingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
			if(repository.existsById("admin")) {
				User admin = new User();
				String hashPassword = BCrypt.hashpw("admin", BCrypt.gensalt());
				admin.setName("admin");
				admin.setPassword(hashPassword);
				admin.addRole("USER");
				admin.addRole("MODERATOR");
				admin.addRole("ADMINISTRATOR");
				repository.save(admin);
			}
	}

}
