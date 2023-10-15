package br.com.profdanieleleao.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.profdanieleleao.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component //sem isso não é implementado o Filter. Component é a classe mais genérica de gerenciamento dentro do spring.
//uma requisição ao ser feita passa primeiro aqui, depois vai pro controller
public class FilterTaskAuth extends OncePerRequestFilter{
	@Autowired
	private IUserRepository userRepository; //NÃO PODE DECLARAR DENTRO DO doFilterInternal, TEM QUE SER AQUI.
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//identificando qual a rota que está sendo acessada, porque só importa /tasks/
		var servletPath = request.getServletPath();
		if (servletPath.startsWith("/tasks/")) {
			//1º passo: pegar a autenticação passada pelo usuário (usu + senha)
			var authorization = request.getHeader("Authorization"); //vem na aba 'Auth' do postman/api dog e é semelhante a um header 
						
			var authEncoded = authorization.substring("Basic".length()).trim(); //trim tira os espaços
			byte[] authDecode = Base64.getDecoder().decode(authEncoded); // authorization vem criptografado em base 64, precisa decodificar.
			var authString = new String (authDecode); 
			
			String[] credentials = authString.split(":"); //divide onde tem ":" pra separar nome de usuário e senha --> [danieleleao,12345]
			String username = credentials[0];
			String password = credentials[1];
			
			//2º passo: validar se usuário existe
			var user = this.userRepository.findByUsername(username);
			if (user == null) {
				response.sendError(401); //erro 401 -> unauthorized
			}
			else {
				//3º passo: validar se senha existe
				var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()); //porque o método findByUsername retorna um objeto do tipo UserModel, então traz a senha também.
				if (passwordVerify.verified) { //método boolean do Bcrypt  
					//ensinando o método a pegar o id do usuário logado pra não precisar que o usuário informe
					request.setAttribute("idUser",user.getId());
					filterChain.doFilter(request, response);
				}			
				else {
					response.sendError(401);
				}
			}
		}	
		else {
			filterChain.doFilter(request, response); //se não for da rota /tasks/, segue em frente.
		}
		}		
}