package br.com.cooperativa.decida.util;

import java.lang.reflect.Field;

public class ConversorDeObjeto {
	/**
	 * Copia os campos de um objeto para uma instância de uma classe qualquer.
	 * @param <T> Tipo do objeto original
	 * @param <G> Tipo da classe a ser convertida
	 * @param objetoOriginal 	o objeto cujos campos serão copiados
	 * @param classeConvertida	a classe da nova instância convertida
	 * @return	a instância gerada ou, caso ocorra algum erro, null
	 */
	public static <T, G> G converter(T objetoOriginal, Class<G> classeConvertida) {
		try {
			G convertido = classeConvertida.getDeclaredConstructor().newInstance();
			
			for (Field originalField : objetoOriginal.getClass().getDeclaredFields()) {
				originalField.setAccessible(true);
				
				Field convertidoField;
				try {					
					convertidoField = convertido.getClass().getDeclaredField(originalField.getName());
				} catch(NoSuchFieldException exception) {
					continue;
				}
				convertidoField.setAccessible(true);
				
				convertidoField.set(convertido, originalField.get(objetoOriginal));
			}
			return convertido;
		} catch(Exception e) {
			return null;
		}
	}
}
