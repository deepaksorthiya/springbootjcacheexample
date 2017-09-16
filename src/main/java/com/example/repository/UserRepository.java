package com.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.model.UserModel;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Long> {

}
