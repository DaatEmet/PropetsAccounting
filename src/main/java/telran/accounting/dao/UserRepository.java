package telran.accounting.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.accounting.model.User;

public interface UserRepository extends MongoRepository<User, String> {
	
}
