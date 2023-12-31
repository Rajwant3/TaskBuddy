package com.task.buddy.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Email(message = "{user.email.not.valid}")
	@NotEmpty(message = "{user.email.not.empty}")
	@Column(unique = true)
	private String email;

	@NotEmpty(message = "{user.name.not.empty}")
	private String name;

	@NotEmpty(message = "{user.password.not.empty}")
	@Length(min = 5, message = "{user.password.length}")
	private String password;

	@Column(columnDefinition = "VARCHAR(255) DEFAULT 'images/user.png'")
	private String photo;

	@OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
	private List<Task> tasksOwned;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;

	private boolean enabled;
//	@Column(name = "verification_code", updatable = false)
//
//	private String verificationCode;
//
//	public String getVerificationCode() {
//		return verificationCode;
//	}
//
//	public void setVerificationCode(String verificationCode) {
//		this.verificationCode = verificationCode;
//	}

	public List<Task> getTasksCompleted() {
		return tasksOwned.stream().filter(Task::isCompleted).collect(Collectors.toList());
	}

	public List<Task> getTasksInProgress() {
		return tasksOwned.stream().filter(task -> !task.isCompleted()).collect(Collectors.toList());
	}

	public boolean isAdmin() {
		String roleName = "ADMIN";
		return roles.stream().map(Role::getRole).anyMatch(roleName::equals);
	}

	public User() {
	}

	public User(@Email @NotEmpty String email, @NotEmpty String name, @NotEmpty @Length(min = 5) String password,
			String photo,boolean enabled) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.photo = photo;
		this.enabled=enabled;
	}

	public User(@Email @NotEmpty String email, @NotEmpty String name, @NotEmpty @Length(min = 5) String password,
			String photo, List<Task> tasksOwned, List<Role> roles) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.photo = photo;
		this.tasksOwned = tasksOwned;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public List<Task> getTasksOwned() {
		return tasksOwned;
	}

	public void setTasksOwned(List<Task> tasksOwned) {
		this.tasksOwned = tasksOwned;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public List<String> getRoleNames() {
		return roles.stream().map(Role::getRole) // Map each Role entity to its role name
				.collect(Collectors.toList()); // Collect the role names into a list
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User) o;
		return Objects.equals(id, user.id) && email.equals(user.email) && name.equals(user.name)
				&& password.equals(user.password) && Objects.equals(photo, user.photo)
				&& Objects.equals(tasksOwned, user.tasksOwned) && Objects.equals(roles, user.roles);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, email, name, password, photo, tasksOwned, roles);
	}
}
