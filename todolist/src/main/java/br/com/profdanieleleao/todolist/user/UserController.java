package br.com.profdanieleleao.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired //diz pro spring boot fazer tudo que achar necessário pra acessar a interface criada IUserRepository
	private IUserRepository userRepository;
	
	//informações do usuário vão vir no body da requisição pois está sendo feito um cadastro -> @RequestBody.
	//userModel vem da classe modelo UserModel
	
	@PostMapping("/") //mesma finalidade do @GetMapping, é pra dizer que vai fazer um POST pra criar o cadastro e usa a rota "/users/"
	public ResponseEntity create (@RequestBody UserModel userModel) { //ResponseEntity permite vários retornos e cenários diferentes pra aplicação (respostas)

		//pesquisando pra ver se já existe o usuário antes de inseri-lo
		var user = this.userRepository.findByUsername(userModel.getUsername());
		//caso de insucesso da requisição
		if (user != null) {
			System.out.println("Usuário já cadastrado"); //mensagem de erro
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
			//status code -> código da resposta à requisição
			//bad_request = erro código 400. Está passando um body pra esse status pra enviar a mensagem na hora da requisição (aparecerá no postman com o código 400)  
		}
		
		//******criptografando a senha*********
		//variável pra receber a senha criptografada pelo método da biblioteca BCrypt. 
		//12 é a força da criptografia (está na doc, tem outras); toCharArray() porque o método recebe a senha como char[]
		var passwordHashered = BCrypt.withDefaults().hashToString(12,userModel.getPassword().toCharArray());
		userModel.setPassword(passwordHashered);
		
		//caso de sucesso da requisição
		var userCreated = this.userRepository.save(userModel); //var é só a partir do java 17
		return ResponseEntity.status(HttpStatus.CREATED).body(userCreated); //retorna os dados do objeto criado quando a requisição foi feita -> aparece em ferramentas como postman o resultado	
	}
}