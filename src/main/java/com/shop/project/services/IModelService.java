package com.shop.project.services;

import java.util.List;

import com.shop.project.model.User;

public interface IModelService<T> {

	public List<T> getList(Integer object, Integer limit);

	public T getOneById(Integer id);

	public T create(T entity);

	public T update(Integer id, T entity);

	public Boolean delete(Integer id);

	public List<T> getListSearch(Integer obj, Integer limit, String search);

	public List<T> getListUser(Integer obj, Integer limit, User user);

	public List<T> getListSearchUser(Integer obj, Integer limit, String search, User user);

	public boolean deleteByUser(Integer obj, Integer limit, User user);
}
