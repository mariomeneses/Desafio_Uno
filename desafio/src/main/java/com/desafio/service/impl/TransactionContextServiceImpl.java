package com.desafio.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.stereotype.Service;

import com.desafio.domain.ResponseDTO;
import com.desafio.domain.ResponseWsDTO;
import com.desafio.service.TransactionContextService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class TransactionContextServiceImpl implements TransactionContextService {

	@Override
	public String callPeriodos() {
		String response = null;

		URL url = null;
		try {
			url = new URL("http://127.0.0.1:8080/periodos/api");

			HttpURLConnection conection = (HttpURLConnection) url.openConnection();
			conection.setRequestMethod("GET");
			conection.setRequestProperty("Accept", "application/json");
			int responseCode = conection.getResponseCode();

			if (responseCode == HttpsURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
				String inputLine;
				StringBuilder response2 = new StringBuilder();

				while ((inputLine = in.readLine()) != null) {
					response2.append(inputLine);
				}
				in.close();

				response = response2.toString();

			} else {
				log.error("GET NOT WORKED");
			}
		} catch (IOException e) {
			log.error("Error " + e.getMessage());
		}
		return response;
	}

	@Override
	public ResponseWsDTO parseString(String responseWs) {

		ObjectMapper mapper = new ObjectMapper();

		ResponseWsDTO rsWs = new ResponseWsDTO();
		try {
			rsWs = mapper.readValue(responseWs, ResponseWsDTO.class);
		} catch (JsonParseException e) {
			log.error("JsonParseException " + e.getMessage());
		} catch (JsonMappingException e) {
			log.error("JsonMappingException " + e.getMessage());
		} catch (IOException e) {
			log.error("IOException " + e.getMessage());
		}
		return rsWs;
	}

	@Override
	public ResponseDTO createTransactionContext(ResponseWsDTO request) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		List<LocalDate> listaFechasRs = new ArrayList<>();
		LocalDate localDateInicio = LocalDate.parse(request.getFechaCreacion(), formatter);
		LocalDate localDateFin = LocalDate.parse(request.getFechaFin(), formatter);

		for (String fecha : request.getFechas()) {
			LocalDate fechaParseada = LocalDate.parse(fecha, formatter);
			listaFechasRs.add(fechaParseada);
		}

		List<LocalDate> listaFechas = obtieneFechas(localDateInicio, localDateFin);
		List<String> listaFinal = getListaFinal(listaFechas, listaFechasRs);
		ResponseDTO response = createResponse(request, listaFinal);

		return response;
	}

	private List<LocalDate> obtieneFechas(LocalDate localDateInicio, LocalDate localDateFin) {

		LocalDate fechaFin = localDateFin.plusMonths(1);
		long meses = ChronoUnit.MONTHS.between(localDateInicio, fechaFin);

		List<LocalDate> listaFechas = IntStream.iterate(0, i -> i + 1).limit(meses)
				.mapToObj(i -> localDateInicio.plusMonths(i)).collect(Collectors.toList());

		return listaFechas;
	}

	private List<String> getListaFinal(List<LocalDate> listaEntreFechas, List<LocalDate> listaInicial) {
		List<LocalDate> listaFinal = new ArrayList<>();
		List<String> listString = new ArrayList<>();
		listaEntreFechas.removeIf(listaInicial::contains);
		listaFinal.addAll(listaEntreFechas);

		for (LocalDate fecha : listaFinal) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedString = fecha.format(formatter);

			listString.add(formattedString);
		}

		return listString;
	}

	private ResponseDTO createResponse(ResponseWsDTO request, List<String> listaFinal) {

		ResponseDTO response = new ResponseDTO();

		response.setId(request.getId());
		response.setFechaCreacion(request.getFechaCreacion());
		response.setFechaFin(request.getFechaFin());
		response.setFechas(request.getFechas());
		response.setFechasFaltantes(listaFinal);

		return response;
	}
}
