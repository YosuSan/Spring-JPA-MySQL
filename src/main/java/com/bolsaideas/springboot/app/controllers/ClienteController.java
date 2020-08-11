package com.bolsaideas.springboot.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsaideas.springboot.app.models.entity.Cliente;
import com.bolsaideas.springboot.app.models.service.IClienteService;
import com.bolsaideas.springboot.app.models.service.IUploadFileService;
import com.bolsaideas.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes({ "cliente" })
public class ClienteController {

	@Autowired
	@Qualifier("clienteService")
	private IClienteService clienteService;

	@Autowired
	@Qualifier("uploadFileService")
	private IUploadFileService uploadFileService;

	@GetMapping("/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;
		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

//		Cliente cliente = clienteService.findOne(id);
		Cliente cliente = clienteService.fetchByIdWithFacturas(id);
		
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la BBDD");
			return "redirect:/listar";
		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("title", "Detalles del cliente: " + cliente.getNombre());

		return "ver";
	}

	@RequestMapping(value = { "/listar", "/" }, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Cliente> clientes = clienteService.findAll(pageRequest);

		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);

		model.addAttribute("title", "Listado de clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("count", clienteService.count());
		model.addAttribute("page", pageRender);

		return "listar";
	}

	@GetMapping("/form")
	public String crear(Model model) {
		Cliente cliente = new Cliente();

		model.addAttribute("title", "Formulario creacion cliente");
		model.addAttribute("cliente", cliente);

		return "form";
	}

	@GetMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "El ID del cliente no existe en la base de datos");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser 0");
			return "redirect:/listar";
		}

		model.addAttribute("cliente", cliente);
		model.addAttribute("title", "Editar cliente");
		return "form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("title", "Formulario creacion cliente");
			return "form";
		}

		if (cliente.getId() != null && cliente.getFoto() != null && cliente.getFoto().length() > 0)
			uploadFileService.delete(cliente.getFoto());

		if (!foto.isEmpty()) {

			String uniqueFileName = null;
			try {
				uniqueFileName = uploadFileService.copy(foto);
			} catch (IOException e) {
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", "Archivo subido correctamente: '" + foto.getOriginalFilename() + "'");

			cliente.setFoto(uniqueFileName);

		} else
			cliente.setFoto("");

		String mensaje = (cliente.getId() == null) ? "Cliente guardado con éxito" : "Cliente editado con éxito";

		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:listar";
	}

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			Cliente cliente = clienteService.findOne(id);
			clienteService.delete(id);
			flash.addFlashAttribute("success", "Cliente eliminado con éxito");

			if (uploadFileService.delete(cliente.getFoto()))
				flash.addFlashAttribute("info", "Foto del cliente eliminada con éxito");
		}

		return "redirect:/listar";
	}

}
