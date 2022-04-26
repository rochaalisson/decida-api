package br.com.cooperativa.decida;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.cooperativa.decida.controller.AutenticacaoController;
import br.com.cooperativa.decida.controller.PautaController;
import br.com.cooperativa.decida.controller.VotoController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class DecidaApplicationTests {
	@Autowired
	private PautaController pautaController;
	@Autowired
	private VotoController votoController;
	@Autowired
	private AutenticacaoController autenticacaoController;

	@Test
	void contextLoads() {
		assertNotNull(pautaController);
		assertNotNull(votoController);
		assertNotNull(autenticacaoController);
	}
}
