package com.Jumong.data.repositories;

import com.Jumong.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactUserRepository extends MongoRepository<User, String> {

}
