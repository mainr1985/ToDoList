package br.com.profdanieleleao.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
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
 * */

@Data
@Entity(name="tb_task")
public class TaskModel {
	private UUID id;
	private String description;
	private String title;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	private String priority;	
	private LocalDateTime createdAt;
}
