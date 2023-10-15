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

import br.com.profdanieleleao.todolist.utils.Utils;
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
	public ResponseEntity update (@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
		
		//se não encontrar com o id, retorne nulo.
		var task = this.taskRepository.findById(id).orElse(null); 
		
		//verificando se a tarefa existe
		if(task == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada");
		}
		
		var idUser = request.getAttribute("idUser");
		
		//verificando se o usuário que quer alterar a tarefa é o dono dela - REVER PORQUE NÃO TÁ ENTRANDO
		/*if (!task.getIdUser().equals(idUser)) {
			System.out.println("to aqui");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não tem autorização para alterar essa tarefa");
		}*/
		Utils.copyNonNullProperties(taskModel,task);		
		var taskUpdated = this.taskRepository.save(task);
		return ResponseEntity.ok().body(taskUpdated);
		
		//OBS.: NÃO DÁ PRA USAR TRY/CATCH DENTRO DESSE MÉTODO FACILMENTE POR CAUSA DO @REQUESTBODY E O QUE ELE FAZ 'POR TRÁS DOS PANOS'
	}
	}