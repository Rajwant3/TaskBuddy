package com.task.buddy.model;

import java.util.Objects;

import javax.validation.constraints.Size;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Long id;

	@NotEmpty(message = "{category.name.not.empty}")
	private String name;

	@NotEmpty(message = "{category.description.not.empty}")
	@Column(length = 1200)
	@Size(max = 1200, message = "{category.category.size}")
	private String description;

	private String creatorName;

	public Category() {
	}

	public Category(@NotEmpty String name, @NotEmpty @Size(max = 1200) String description, String creatorName) {
		this.name = name;
		this.description = description;
		this.creatorName = creatorName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Category task = (Category) o;
		return Objects.equals(id, task.id) && name.equals(task.name) && description.equals(task.description)
				&& Objects.equals(creatorName, task.creatorName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, description, creatorName);
	}
}
