package com.bolsaideas.springboot.app.view.xml;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.bolsaideas.springboot.app.models.entity.Cliente;
import com.bolsaideas.springboot.app.models.service.IClienteService;

@Component("listar.xml")
@SuppressWarnings({ "unused" })
public class ClienteListXmlView extends MarshallingView {

	@Autowired
	public ClienteListXmlView(Jaxb2Marshaller marshaller) {
		super(marshaller);

	}

	@Autowired
	private IClienteService clienteService;

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

//		Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");
//		model.put("clienteList", new ClienteList(clientes.getContent()));
		List<Cliente> clientes = clienteService.findAll();
		model.put("clienteList", new ClienteList(clientes));

		model.remove("title");
		model.remove("page");
		model.remove("clientes");

		super.renderMergedOutputModel(model, request, response);
	}
}
