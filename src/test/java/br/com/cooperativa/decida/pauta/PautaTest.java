package br.com.cooperativa.decida.pauta;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cooperativa.decida.DecidaApplication;
import br.com.cooperativa.decida.controller.form.PautaForm;
import br.com.cooperativa.decida.modelo.Pauta;
import br.com.cooperativa.decida.repository.PautaRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PautaTest {

	    @Autowired
	    private MockMvc mvc;
	    
	    @Autowired
	    private PautaRepository pautaRepository;
	    
	    @Autowired
	    private ObjectMapper objectMapper;
	    
	    int novaPauta(String titulo, String descricao) {
	    	Pauta pauta = new Pauta(titulo, descricao);
	    	pauta = pautaRepository.save(pauta);
	    	
	    	return pauta.getId();
	    }
	    
	    @Test
	    void deveBuscarPautas() throws Exception {
	    	String tituloDaPauta = "Titulo da pauta";
	    	novaPauta(tituloDaPauta, "descricao");
	    	
			mvc.perform(get("/pautas")
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.length()", is(greaterThan(0))));
	    }
	    
	    @Test
	    @WithMockUser(username = "12345678901", password = "1234")
	    void deveInserirPauta() throws Exception {
	    	PautaForm form = new PautaForm("Título cadastrada", "Descrição cadastrada");
	    	
	    	mvc.perform(post("/pautas")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(objectMapper.writeValueAsString(form)))
	    	.andExpect(status().isCreated())
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.titulo", is("Título cadastrada")));	
	    }
	    
	    @Test
	    @WithMockUser(username = "12345678901", password = "1234")
	    void deveDeletarPauta() throws Exception {
	    	int idPauta = novaPauta("Título deletar", "Descricao deletar");
	    	
	    	mvc.perform(delete("/pautas/{id}", idPauta))
	    	.andExpect(status().isOk());
	    }
}
