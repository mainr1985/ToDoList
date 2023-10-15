package br.com.profdanieleleao.todolist.task;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private ITaskRepository taskRepository;
	
	@PostMapping("/") 
	//HttpServletRequest é passado como parâmetro porque é ele que sabe a id do usuário
	public ResponseEntity create (@RequestBody TaskModel taskModel, HttpServletRequest request) {
		var idUser = request.getAttribute("idUser");

		//adicionando validação de horas/data
		//var currentDate = LocalDateTime.now();
		/*if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de início/término inválida");
		}*/
		/*if(taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de início não pode ser maior que a data de término");
		}*/
			
		taskModel.setIdUser((UUID) idUser); //fazendo cast pra transformar o valor recebido em UUID.
		var task = this.taskRepository.save(taskModel);
		return ResponseEntity.status(HttpStatus.OK).body(task);
	}
	
	//método para listar as tarefas salvas pelo usuário
	@GetMapping("/") //pra trazer tudo que seja relacionado ao usuário
	public List<TaskModel> list(HttpServletRequest request) {
		var idUser = request.getAttribute("idUser"); //pegando o id 
		var tasks = this.taskRepository.findByIdUser((UUID) idUser);
		return tasks;
	}
	
	//método para fazer update nas tarefas
	//http://localhost:8080/tasks/id-da-tarefa
	@PutMapping("/{id}")
	public TaskModel update (@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
		var idUser = request.getAttribute("idUser"); //pegando o id 
		taskModel.setIdUser((UUID) idUser);
		taskModel.setId(id);		
		return this.taskRepository.save(taskModel);
	}	
}