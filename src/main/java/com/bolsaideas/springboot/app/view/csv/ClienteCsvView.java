package com.bolsaideas.springboot.app.view.csv;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.bolsaideas.springboot.app.models.entity.Cliente;
import com.bolsaideas.springboot.app.models.service.IClienteService;

@Component("listar.csv")
@SuppressWarnings("unused")
public class ClienteCsvView extends AbstractView {

	public ClienteCsvView() {
		setContentType("text/csv");
	}

	@Autowired
	private IClienteService clienteService;

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment; filename=\"clientes.csv\"");
		response.setContentType(getContentType());

//		Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");

		List<Cliente> clientes = clienteService.findAll();

		ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

		String header[] = { "id", "nombre", "apellido", "email", "createAt" };

		beanWriter.writeHeader(header);

		for (Cliente cliente : clientes)
			beanWriter.write(cliente, header);

		beanWriter.close();

	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

}
