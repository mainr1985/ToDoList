package br.com.profdanieleleao.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired //o spring vai gerenciar instanciações e o que mais for necessário pra usar o userRepository (gerencia o ciclo de vida)
	private IUserRepository userRepository; //chamando a interface que foi criada
	
	//informações do usuário vão vir no body da requisição pois está sendo feito um cadastro -> @RequestBody.
	//userModel vem da classe modelo UserModel
	
	@PostMapping("/") //mesma finalidade do @GetMapping, é pra dizer que vai fazer um POST pra criar o cadastro e usa a rota "/users/"
	public UserModel create (@RequestBody UserModel userModel) {
		//this.userRepository.findBy(null, null)
		var userCreated = this.userRepository.save(userModel);
		return userCreated;
	}
}
