package br.com.profdanieleleao.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {

	//criando o método findByUsername
	UserModel findByUsername(String username);
	
}
