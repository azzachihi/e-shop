package com.shop.project.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shop.project.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

	@Query("SELECT u FROM User u WHERE u.username = :name")
	public User findByUsername(String name);

	@Query("SELECT u FROM User u ORDER BY u.id ASC")
	public List<User> getListUserPage(Pageable page);

	@Query("SELECT u FROM User u WHERE u.username = :username ORDER BY u.id ASC")
	public List<User> findByUsername(Pageable page, String username);

	@Query("SELECT u FROM User u WHERE u.id = :id")
	public List<User> findByUser(Pageable page, Integer id);

	@Query("SELECT u FROM User u WHERE u.userRole = :role ORDER BY u.id ASC")
	public List<User> findByRole(Pageable page, User.UserRole role);

}
