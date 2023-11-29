package com.task.buddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task.buddy.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

//	List<Category> findByOwnerOrderByDateDesc(User user);
}