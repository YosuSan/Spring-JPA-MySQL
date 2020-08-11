package com.bolsaideas.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsaideas.springboot.app.models.entity.Cliente;
import com.bolsaideas.springboot.app.models.entity.Factura;
import com.bolsaideas.springboot.app.models.entity.Producto;

public interface IClienteService {

	public List<Cliente> findAll();

	public Page<Cliente> findAll(Pageable pageable);

	public Cliente findOne(Long id);
	
	public Cliente fetchByIdWithFacturas(Long id);

	public void save(Cliente cliente);

	public void delete(Long id);

	public long count();
	
	public List<Producto> findByNombre(String term);
	
	public void saveFactura(Factura factura);
	
	public Producto findProductoById(Long id);
	
	public Factura findFacturaById(Long id);
	
	public void deleteFActura(Long id);
	
	public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);
}
