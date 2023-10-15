package br.com.profdanieleleao.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

	//método para fazer uma cópia dos objetos da tarefa antes do update
	public static void copyNonNullProperties(Object source, Object target) {
		//fazendo uma cópia do array de nulos encontrado no método getNullPropertyNames
		BeanUtils.copyProperties(source, target, getNullPropertyNames(source)); //BeanUtils é uma classe do java que consegue copiar um objeto pra outro
	}	
	
	//método genérico para listar atributos que não foram listados para update pelo usuário -> UPDATE PARCIAL -> pode ser reaproveitado
	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);//BeanWrapper é uma classe do java; interface que permite acessar as propriedades de um objeto
		PropertyDescriptor[] pds = src.getPropertyDescriptors();
		
		Set<String> emptyNames = new HashSet<>();
		
		for(PropertyDescriptor pd: pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null){
				emptyNames.add(pd.getName()); //se for uma propriedade nula passa pra lista - nulo ou não vai vir na requisição 
			}
		}
		
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
}