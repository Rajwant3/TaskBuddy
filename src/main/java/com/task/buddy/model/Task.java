package com.task.buddy.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id")
	private Long id;

	@NotEmpty(message = "{task.name.not.empty}")
	private String name;

	@NotEmpty(message = "{task.description.not.empty}")
	@Column(length = 1200)
	@Size(max = 1200, message = "{task.description.size}")
	private String description;

	@NotNull(message = "{task.date.not.null}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	private boolean isCompleted;

	private String creatorName;

//	@NotEmpty(message = "{task.priority.not.empty}")
	private int priority = 1;

	@ManyToOne
	@JoinColumn(name = "OWNER_ID")
	private User owner;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public long daysLeftUntilDeadline(LocalDate date) {
		return ChronoUnit.DAYS.between(LocalDate.now(), date);
	}

	public Task() {
	}

	public Task(@NotEmpty String name, @NotEmpty @Size(max = 1200) String description, @NotNull LocalDate date,
			boolean isCompleted, String creatorName, Category category) {
		this.name = name;
		this.description = description;
		this.date = date;
		this.isCompleted = isCompleted;
		this.creatorName = creatorName;
		this.category = category;

	}

	public Task(@NotEmpty String name, @NotEmpty @Size(max = 1200) String description, @NotNull LocalDate date,
			boolean isCompleted, String creatorName, User owner, int priority, Category category) {
		this.name = name;
		this.description = description;
		this.date = date;
		this.isCompleted = isCompleted;
		this.creatorName = creatorName;
		this.owner = owner;
		this.priority = priority;
		this.category = category;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean completed) {
		isCompleted = completed;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Task task = (Task) o;
		return isCompleted == task.isCompleted && Objects.equals(id, task.id) && name.equals(task.name)
				&& description.equals(task.description) && date.equals(task.date)
				&& Objects.equals(creatorName, task.creatorName) && Objects.equals(owner, task.owner);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, description, date, isCompleted, creatorName, owner);
	}
}
