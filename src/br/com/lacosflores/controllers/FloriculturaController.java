package br.com.lacosflores.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lacosflores.dao.FloriculturaDao;
import br.com.lacosflores.models.Dispositivo;
import br.com.lacosflores.models.Endereco;
import br.com.lacosflores.models.Floricultura;
import br.com.lacosflores.models.Noticias;
import br.com.lacosflores.models.Pedido;
import br.com.lacosflores.models.Usuario;


//apagar
@RestController
public class FloriculturaController{

	@Autowired
	private FloriculturaDao floriculturaDao;
	
	@RequestMapping(value = "/floricultura", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Floricultura> inserir(@RequestBody Floricultura floricultura) {
		//Usuario e Long criados por padrao do parametro inserirUsuario
		try {
			Endereco end = floricultura.getEndereco();
			end.setFloricultura(floricultura);
			
			for (Dispositivo disp : floricultura.getDispositivos()) {
				disp.setFloricultura(floricultura);
			}
			
			for (Usuario usu : floricultura.getUsuarios()) {
				usu.setFloricultura(floricultura);
			}
			
			for (Pedido pedido : floricultura.getPedidos()) {
				pedido.setFloricultura(floricultura);
			}
			
			floriculturaDao.inserir(floricultura);
			
			URI location = new URI("/floricultura/" + floricultura.getId());
			return ResponseEntity.created(location).body(floricultura);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 

	
	@RequestMapping(value = "/floricultura/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> remover(@PathVariable("id") long id) {
		floriculturaDao.remover(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/floricultura", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Floricultura> listar() {
		return floriculturaDao.listar();
	}

	@RequestMapping(value = "/floricultura/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Floricultura consultar(@PathVariable("id") long id) {
		return floriculturaDao.consultar(id);
	}
	
	@RequestMapping(value = "/floricultura/contains/{nomeFantasia}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Floricultura> listar_contains(@PathVariable("nomeFantasia") String nomeFantasia) {
		return floriculturaDao.listar_contains(nomeFantasia);
	}

}