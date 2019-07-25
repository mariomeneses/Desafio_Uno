package com.desafio.domain;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ResponseWsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 352401982243716677L;
	private String id;
	private String fechaCreacion;
	private String fechaFin;
	private List<String> fechas;
}
