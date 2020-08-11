package com.bolsaideas.springboot.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsaideas.springboot.app.models.entity.Cliente;
import com.bolsaideas.springboot.app.models.entity.Factura;
import com.bolsaideas.springboot.app.models.entity.ItemFactura;
import com.bolsaideas.springboot.app.models.entity.Producto;
import com.bolsaideas.springboot.app.models.service.IClienteService;

@Controller
@RequestMapping("factura")
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	@Qualifier("clienteService")
	private IClienteService clienteService;

	private final Logger log = LoggerFactory.getLogger(getClass());

	@GetMapping("ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

//		Factura factura = clienteService.findFacturaById(id);
		
		Factura factura = clienteService.fetchByIdWithClienteWithItemFacturaWithProducto(id);

		if (factura == null) {
			flash.addFlashAttribute("error", "La factura no existe en la BBDD");
			return "redirect:/listar";
		}

		model.addAttribute("factura", factura);
		model.addAttribute("title", "Factura: ".concat(factura.getDescripcion()));

		return "factura/ver";
	}

	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable(value = "clienteId") Long clienteId, Model model, RedirectAttributes flash) {

		Cliente cliente = clienteService.findOne(clienteId);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la BBDD");
			return "redirect:/listar";
		}

		Factura factura = new Factura();
		factura.setCliente(cliente);

		model.addAttribute("title", "Crear Factura");
		model.addAttribute("factura", factura);

		return "factura/form";
	}

	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		return clienteService.findByNombre(term);
	}

	@PostMapping("/form")
	public String guardar(@Valid Factura factura, BindingResult result,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, RedirectAttributes flash,
			SessionStatus status, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("title", "Crear Factura");
			return "factura/form";
		}

		if (itemId == null || itemId.length == 0) {
			model.addAttribute("title", "Crear Factura");
			model.addAttribute("error", "Error: La factura no puede no tener lineas");
			return "factura/form";
		}

		for (int i = 0; i < itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);

			ItemFactura linea = new ItemFactura();
			linea.setProducto(producto);
			linea.setCantidad(cantidad[i]);
			factura.addItemFactura(linea);

			log.info("ID: " + itemId[i]);
			log.info("Cantidad: " + cantidad[i]);
		}
		clienteService.saveFactura(factura);

		status.setComplete();

		flash.addFlashAttribute("success", "Factura creada con exito");

		return "redirect:/ver/".concat(factura.getCliente().getId().toString());
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		
		Factura factura = clienteService.findFacturaById(id);
		
		if (factura == null) {
			flash.addFlashAttribute("error", "La factura no existe en la BBDD");
			return "redirect:/listar";
		} else {
			clienteService.deleteFActura(id);
			flash.addFlashAttribute("success", "Factura eliminada con éxito");
		}
		
		return "redirect:/ver/" + factura.getCliente().getId();
	}

	
	
	
	
	
}
