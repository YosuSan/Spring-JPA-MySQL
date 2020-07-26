package com.bolsaideas.springboot.app.models.service;

import java.util.List;

import com.bolsaideas.springboot.app.models.entity.Cliente;

public interface IClienteService {

	public List<Cliente> findAll();

	public Cliente findOne(Long id);

	public void save(Cliente cliente);

	public void delete(Long id);
	
	public long count();
}
