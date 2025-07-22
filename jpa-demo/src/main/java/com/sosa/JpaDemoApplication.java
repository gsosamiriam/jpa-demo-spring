package com.sosa;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.sosa.model.Categoria;
import com.sosa.model.Perfil;
import com.sosa.model.Usuario;
import com.sosa.model.Vacante;
import com.sosa.repository.CategoriasRepository;
import com.sosa.repository.PerfilesRepository;
import com.sosa.repository.UsuariosRepository;
import com.sosa.repository.VacantesRepository;

@SpringBootApplication
public class JpaDemoApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriasRepository repoCategorias; 
	
	@Autowired
	private VacantesRepository repoVacantes;
	
	@Autowired
	private UsuariosRepository repoUsuarios;
	
	@Autowired 
	private PerfilesRepository repoPerfiles;
	
	
	public static void main(String[] args) {
		SpringApplication.run(JpaDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		buscarVacantesVariosEstatus();
	}
	
	private void buscarVacantesVariosEstatus() {
		String[] estatus = new String[] {"Eliminada","Aprobada"};
		List<Vacante> lista = repoVacantes.findByEstatusIn(estatus);
		System.out.println("Registros encontrados "+ lista.size());
		for(Vacante v: lista) {
			System.out.println(v.getId()+" : "+v.getNombre() +" : "+v.getEstatus());
		}
	}
	
	private void buscarVacantesSalario() {
		List<Vacante> lista = repoVacantes.findBySalarioBetweenOrderBySalarioDesc(7000, 14000);
		System.out.println("Registros encontrados "+ lista.size());
		for(Vacante v: lista) {
			System.out.println(v.getId()+" : "+v.getNombre() +" : "+v.getSalario());
		}
	}
	
	private void buscarVacantesPorDestacadoEstatus() {
		List<Vacante> lista = repoVacantes.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
		System.out.println("Registros encontrados "+ lista.size());
		for(Vacante v: lista) {
			System.out.println(v.getId()+" : "+v.getNombre() +" : "+v.getEstatus()+" : "+v.getDestacado());
		}
	}
	
	private void buscarVacantesPorEstatus() {
		List<Vacante> lista= repoVacantes.findByEstatus("Eliminada");
		System.out.println("Registros encontrados "+ lista.size());
		for(Vacante v: lista) {
			System.out.println(v.getId()+" : "+v.getNombre() +" : "+v.getEstatus());
		}
	}

	public void buscarUsuario() {
		Optional<Usuario> optional = repoUsuarios.findById(50);
		if(optional.isPresent()) {
			Usuario u = optional.get();
			System.out.println("Usuario "+u.getNombre());
			System.out.println("Perfiles Asignados ");
			
			for(Perfil p: u.getPerfiles()) {
				System.out.println(p.getPerfil());
			}
			
		}else {
			System.out.println("Usuario no encontrado");
		}
	}
	
	private void crearUsuarioConDosPerfiles() {
		Usuario user = new Usuario();
		user.setNombre("Iván Tinajerp");
		user.setEmail("Ejemplo@gmail.com");
		user.setFechaRegistro(new Date());
		user.setUsername("itinajeo");
		user.setPassword("12345");
		user.setEstatus(1);
		
		Perfil per1 = new Perfil();
		per1.setId(2);
		
		Perfil per2 = new Perfil();
		per2.setId(3);
		
		user.agregar(per1);
		user.agregar(per2);
		
		repoUsuarios.save(user);
		
	}
	
	//Crear perfiles / roles
	
	private void crearPerfilesAplicacion() {
		repoPerfiles.saveAll(getPerfilesAplicacion());
	}
	
	/*Guardar vacantes*/
	private void guardarVacante() {
		Vacante vacante = new Vacante();
		vacante.setNombre("Profesor de Matematicas");
		vacante.setDescripcion("Escuela primaria solicita profesor para curso de matematicas");
		vacante.setFecha(new Date());
		vacante.setSalario(8500.0);
		vacante.setEstatus("Aprobada");
		vacante.setDestacado(0);
		vacante.setImagen("escuela.png");
		vacante.setDetalles("<h1>Los requisitos para profesor de matematicas</h1>");
		
		Categoria cat = new Categoria();
		cat.setId(15);		
		vacante.setCategoria(cat);
		
		repoVacantes.save(vacante);
	}
	
	private void buscarVacantes() {
		List<Vacante> lista = repoVacantes.findAll();
		for(Vacante v: lista) {
			System.out.println(v.getId()+" "+v.getNombre()+" -> "+v.getCategoria().getNombre());
		}
	}
	
	///
	
	private void buscarTodosPaginacionOrdenados() {
		Page <Categoria> page = repoCategorias.findAll(PageRequest.of(0, 5,Sort.by("nombre")));
		System.out.println("Total de registros: "+page.getTotalElements());
		System.out.println("Total de páginas "+page.getTotalPages());
		
		for(Categoria c: page.getContent()) {
			System.out.println(c.getId() +" "+c.getNombre());
		}
	}
	
	private void buscarTodosPaginacion() {
		//es equivalente al limit pero en el primer parametro indica la pagina y el segundo el numero de registros a mostrar
		Page <Categoria> page = repoCategorias.findAll(PageRequest.of(0, 5));
		System.out.println("Total de registros: "+page.getTotalElements());
		System.out.println("Total de páginas "+page.getTotalPages());
		
		for(Categoria c: page.getContent()) {
			System.out.println(c.getId() +" "+c.getNombre());
		}
	}
	
	private void buscarTodosOrdenados() {
		//List<Categoria> categorias =repo.findAll(Sort.by("nombre"));
		List<Categoria> categorias =repoCategorias.findAll(Sort.by("nombre").descending());
		for(Categoria c: categorias) {
			System.out.println(c.getId() +" "+c.getNombre());
		}
	}
	
	private void borrarTodoEnBloque() {
		
		repoCategorias.deleteAllInBatch();
	}
	
	private void buscarTodosJpa() {
		List<Categoria> categorias =repoCategorias.findAll();
		for(Categoria c: categorias) {
			System.out.println(c.getId() +" "+c.getNombre());
		}
	}
	
	///de aqui para abajo son con la interfaz crudrepository
	private void guardarTodas() {
		
		List<Categoria> categorias = getListaCategoria();
		//como la clase list ya tiene el metodo iterable entonces se puede pasar directo al metodo saveAll
		repoCategorias.saveAll(categorias);
	}
	
	private void existeId() {
		boolean existe = repoCategorias.existsById(50);
		System.out.println("La categoria existe "+existe);
	}
	
	private void buscarTodos() {
		Iterable<Categoria> categorias = repoCategorias.findAll();
		for(Categoria cat: categorias) {
			System.out.println(cat);
		}
	}
	
	private void encontrarporIds() {
		List<Integer> ids = new LinkedList<Integer>();
		ids.add(1);
		ids.add(4);
		ids.add(10);
		repoCategorias.findAllById(ids);
		Iterable<Categoria> categorias = repoCategorias.findAllById(ids);
		
		for(Categoria cat: categorias) {
			System.out.println(cat);
		}
	}
	
	private void eliminarTodos() {
		repoCategorias.deleteAll();
	}
	
	private void conteo() {
		long count = repoCategorias.count();
		System.out.println("Total categorias "+count);
	}
	
	private void modificar() {
		
		Optional<Categoria> optional= repoCategorias.findById(1);
		if(optional.isPresent()) {
			
			Categoria catTmp = optional.get();
			catTmp.setNombre("Ing. de software");
			catTmp.setDescripcion("Desarrollo de sistemas");
			repoCategorias.save(catTmp);
			System.out.println(optional.get());
		}
		else {
			System.out.println("Categoria no encontrada");
		}
			
	}
	
	private void buscarPorId() {
		Optional<Categoria> optional= repoCategorias.findById(3);
		if(optional.isPresent())
			System.out.println(optional.get());
		else
			System.out.println("Categoria no encontrada");
	}
	
	private void guardar() {
		Categoria cat = new Categoria(); 
		cat.setNombre("Finanzas");
		cat.setDescripcion("Trabajos de finanzas y contabilidad");
		repoCategorias.save(cat);
		System.out.println(cat);
	}
	
	private void eliminar() {
		int idCategoria = 2; 
		repoCategorias.deleteById(idCategoria);
	}
	
	private List<Categoria> getListaCategoria(){
		
		List<Categoria> lista = new LinkedList<Categoria>();
		
		//Categoria 1
		
		Categoria cat1 = new Categoria();
		cat1.setNombre("Programador de blockChain");
		cat1.setDescripcion("Trabajos relacionados con Bitcoin y criptomonedas");
		
		Categoria cat2 = new Categoria();
		cat2.setNombre("Soldador / Pintura");
		cat2.setDescripcion("Trabajos relacionados con soldadura, pintura y enderezado");
		
		Categoria cat3 = new Categoria();
		cat3.setNombre("Ingeniero industrial");
		cat3.setDescripcion("Trabajos relacionados con Ingenieria Industrial");
		
		lista.add(cat1);
		lista.add(cat2);
		lista.add(cat3);
		
		return lista;
	}
	
	///Perfiles lista 
	
	private List<Perfil> getPerfilesAplicacion(){
		List<Perfil> lista = new LinkedList<Perfil>();
		Perfil per1 = new Perfil();
		per1.setPerfil("SUPERVISOR");
		
		Perfil per2 = new Perfil();
		per2.setPerfil("ADMINISTRADOR");
		
		Perfil per3 = new Perfil();
		per3.setPerfil("USUARIO");
		
		lista.add(per1);
		lista.add(per2);
		lista.add(per3);
		
		return lista;
	}
}
