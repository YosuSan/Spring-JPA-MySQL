package com.bolsaideas.springboot.app.view.json;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.bolsaideas.springboot.app.models.service.IClienteService;

@Component("listar.json")
public class ClienteListJsonView extends MappingJackson2JsonView {
	
	@Autowired
	private IClienteService clienteService;

	@Override
	protected Object filterModel(Map<String, Object> model) {
		
		model.remove("title");
		model.remove("page");
		model.remove("count");
		model.put("clientes", clienteService.findAll());
		
		return super.filterModel(model);
	}
}
