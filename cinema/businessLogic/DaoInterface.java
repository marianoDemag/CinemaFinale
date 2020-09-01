package com.azienda.cinema.businessLogic;

import java.util.List;

public interface DaoInterface <T> {

	public void create(T object);
	public List<T> retrieve();
	public void update(T object);
	public void delete(T object);
	
}
