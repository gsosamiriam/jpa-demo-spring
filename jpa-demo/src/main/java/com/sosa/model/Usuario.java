package com.sosa.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id; 
	private String nombre;
	private String email;
	private String username;
	private String password;
	private Integer estatus;
	private Date fechaRegistro;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="UsuarioPerfil",
			joinColumns = @JoinColumn(name="idUsuario"),
			inverseJoinColumns = @JoinColumn(name="idPerfil")
	)
	private List<Perfil> perfiles;
	
	public void agregar(Perfil tempPerfil) {
		if(perfiles == null) {
			perfiles = new LinkedList<Perfil>();
		}
		perfiles.add(tempPerfil);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getEstatus() {
		return estatus;
	}
	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	
	
	public List<Perfil> getPerfiles() {
		return perfiles;
	}
	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}
	@Override
	public String toString() {
		return "Usuarios [id=" + id + ", nombre=" + nombre + ", email=" + email + ", username=" + username
				+ ", password=" + password + ", estatus=" + estatus + ", fechaRegistro=" + fechaRegistro + "]";
	}
	
	
}
