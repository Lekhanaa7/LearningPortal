package com.learningportal.sam.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class user {

    public user() {
		super();
		// TODO Auto-generated constructor stub
	}

	public user(long userId, String email, String name, String role, Set<course> published, Set<enrollment> enrollments,
			Set<favorite> favorites) {
		super();
		this.userId = userId;
		this.email = email;
		this.name = name;
		this.role = role;
		this.published = published;
		this.enrolledCourses = enrollments;
		this.favoriteCourses = favorites;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<course> getPublished() {
		return published;
	}

	public void setPublished(Set<course> published) {
		this.published = published;
	}

	public Set<enrollment> getEnrollments() {
		return enrolledCourses;
	}

	public void setEnrollments(Set<enrollment> enrollments) {
		this.enrolledCourses = enrollments;
	}

	public Set<favorite> getFavorites() {
		return favoriteCourses;
	}

	public void setFavorites(Set<favorite> favorites) {
		this.favoriteCourses = favorites;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    private String name;
    private String role;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<course> published;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<enrollment> enrolledCourses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<favorite> favoriteCourses=new HashSet<>();
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
        name = "enrollment", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
  
    private Set<course> getEnrolledCourses = new HashSet<>();
    

	public List<course> getFavoriteCourses() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<course> getEnrolledCourses() {
		// TODO Auto-generated method stub
		return null;
	}

}
