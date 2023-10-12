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
@Entity(name="tb_users") //transforma a classe na tabela "tb_users" dentro do h2
public class UserModel {

	@Id //tem que vir do jakarta.persistence.
	@GeneratedValue(generator="UUID") //pede pro UUID gerar automaticamente os valores.
	private UUID id; //usando pra virar a chave primária; é mais seguro que um ID sequencial.
	
	/*@Getter / @Setter*/
	//do lombok -> colocado aqui, só vai fazer getter/setter pra userName e password. Depende de onde coloca.
	private String name;	
	@Column(unique=true)  //pra não permitir valores iguais no atributo
	private String userName;
	private String password;
	
	@CreationTimestamp
	private LocalDateTime createdAt; //pra guardar quando o dado foi criado no BD
	
	//se não mexer em nada, o h2 vai entender que os nomes do atributos serão os nomes das colunas da tabela. 
}