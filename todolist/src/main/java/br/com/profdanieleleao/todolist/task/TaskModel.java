package br.com.profdanieleleao.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

/*
 * Tarefas tem: 
 * Id 
 * Usuário 
 * Descrição 
 * Título
 * Data de início 
 * Data de término
 *  Prioridade
 *  Id do usuário
 * */

@Data
@Entity(name="tb_tasks")
public class TaskModel {
	
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;
	private String description;
	
	@Column(length=50) //limita a 50 caracteres o campo
	private String title;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	private String priority;	
	private UUID idUser;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	//método para tratamento de exceção de excesso de caracteres passados no título
	public void setTitle(String title) throws Exception {
		if(title.length()>50) {
			throw new Exception("O campo Title deve conter no máximo 50 caracteres.");
		}
		this.title = title;
	}
	
}
