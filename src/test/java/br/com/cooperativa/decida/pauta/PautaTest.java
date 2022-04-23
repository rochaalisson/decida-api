package br.com.cooperativa.decida.pauta;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.cooperativa.decida.DecidaApplication;
import br.com.cooperativa.decida.controller.form.PautaForm;
import br.com.cooperativa.decida.modelo.Pauta;
import br.com.cooperativa.decida.repository.PautaRepository;

@SpringBootTest(webEnvironment = 
		SpringBootTest.WebEnvironment.MOCK,
		classes = DecidaApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PautaTest {

	    @Autowired
	    private MockMvc mvc;
	    
	    @Autowired
	    private PautaRepository pautaRepository;
	    
	    @BeforeEach
	    void setup() {
	    	novaPauta("Título da pauta", "Descricao");
	    }
	    
	    int novaPauta(String titulo, String descricao) {
	    	Pauta pauta = new Pauta();
	    	pauta = pautaRepository.save(pauta);
	    	
	    	return pauta.getId();
	    }
	    
	    @Test
	    void deveBuscarPautas() throws Exception{	    	
			mvc.perform(get("/pautas")
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].titulo", is("Título da pauta")));
	    }
	    
	    @Test
	    void deveInserirPauta() throws Exception {
	    	PautaForm form = new PautaForm("Título cadastrada", "Descrição cadastrada");
	    	
	    	mvc.perform(post("/pautas")
	    			.contentType(MediaType.APPLICATION_JSON))
	    	.andExpect(status().isCreated())
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.titulo", is("Título cadastrada")));	
	    }
	    
	    @Test
	    void deveDeletarPauta() throws Exception {
	    	int idPauta = novaPauta("Título deletar", "Descricao deletar");
	    	
	    	mvc.perform(delete("/pautas/{id}", idPauta)
	    			.contentType(MediaType.APPLICATION_JSON))
	    	.andExpect(status().isOk())
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	    }
}
