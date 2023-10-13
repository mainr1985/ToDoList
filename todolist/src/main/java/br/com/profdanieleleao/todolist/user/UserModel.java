package br.com.profdanieleleao.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data //annotation do Lombok pra automaticamente adicionar os getters/setters dos atributos (não dá pra ver, mas estão lá).
@Entity(name = "tb_users")
public class UserModel {

	/*@Getter / @Setter*/
	//do lombok -> colocado aqui, só vai fazer getter/setter pra userName e password. Depende de onde coloca.
	private String name;	
	
	@Column (unique=true) //pra não poder repetir nome de usuário
	private String username;
	private String password;
	 
	//campo para chave primária de geração automática pelo jpa
	@Id //tem que ser da jakarta persistence
	@GeneratedValue(generator="UUID")
	private UUID id; //mais seguro que um campo com auto numeração
	
	@CreationTimestamp
	private LocalDateTime createdAt;
}