package com.example.controller;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.Perfil;
import com.example.model.Usuario;
import com.example.model.UsuarioPerfil;
import com.example.service.PerfilService;
import com.example.service.UsuarioPerfilService;
import com.example.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	private static final String MSG_SUCESS_INSERT = "Usuario inserido com sucesso.";
	private static final String MSG_SUCESS_UPDATE = "Usuario alterado com sucesso.";
	private static final String MSG_SUCESS_DELETE = "Usuario removido com sucesso.";
	private static final String MSG_ERROR = "Erro.";

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioPerfilService usuarioPerfilService;
	
	@Autowired
	private PerfilService perfilService;

	@GetMapping
	public String index(Model model) {
		List<Usuario> all = usuarioService.findAll();
		model.addAttribute("listUsuario", all);
		return "usuario/index";
	}

	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		if (id != null) {
			Usuario usuario = usuarioService.findOne(id).get();
			model.addAttribute("usuario", usuario);
		}
		return "usuario/show";
	}

	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute Usuario entityUsuario) {
		// model.addAttribute("usuario", entityUsuario);

		return "usuario/form";
	}

	@PostMapping
	public String create(@Valid @ModelAttribute Usuario entity, BindingResult result,
			RedirectAttributes redirectAttributes) {
		Usuario usuario = null;
		try {
			usuario = usuarioService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_INSERT);
		} catch (Exception e) {
			System.out.println("Exception:: exception");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		} catch (Throwable e) {
			System.out.println("Throwable:: exception");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}
		return "redirect:/usuarios/" + usuario.getId();
	}

	@GetMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id) {
		try {
			if (id != null) {
				Usuario entity = usuarioService.findOne(id).get();
				model.addAttribute("usuario", entity);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "usuario/form";
	}

	@GetMapping("/{id}/removeperfil/{perfil}")
	public String removeperfil(Model model, @PathVariable("id") Integer id, @PathVariable("perfil") Integer perfilid,
			RedirectAttributes redirectAttributes) {
		Usuario entity = null;
		try {
			if (id != null && perfilid != null) {
				entity = usuarioService.findOne(id).get();
				UsuarioPerfil up = usuarioPerfilService.findOne(perfilid).get();
				usuarioPerfilService.delete(up);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_UPDATE);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/usuarios/" + entity.getId();
	}
	
	@GetMapping("/{id}/addperfil")
	public String addPerfil(Model model, @PathVariable("id") Integer id) {
		try {
			if (id != null) {
				Usuario entity = usuarioService.findOne(id).get();
				List<Perfil> perfis = perfilService.findExcept(entity.getPerfis()); 
				model.addAttribute("usuario", entity);
				model.addAttribute("perfis", perfis);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "usuario/addperfil";
	}
	
	@PostMapping("/{id}/addperfil")
	public String savePerfil(@Valid @ModelAttribute Perfil entity, BindingResult result,
			RedirectAttributes redirectAttributes, @PathVariable("id") Integer id) {
		Usuario usuario = null;
		try {
			usuario = usuarioService.findOne(id).get();
			UsuarioPerfil up = new UsuarioPerfil();
			Perfil p = perfilService.findOne(((Perfil) entity).getId()).get();
			up.setPerfil(p);
			up.setUsuario(usuario);
			usuarioPerfilService.save(up);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_INSERT);
		} catch (Exception e) {
			System.out.println("Exception:: exception");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		} catch (Throwable e) {
			System.out.println("Throwable:: exception");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}
		return "redirect:/usuarios/" + usuario.getId();
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute Usuario entity, BindingResult result,
			RedirectAttributes redirectAttributes) {
		Usuario usuario = null;
		try {
			usuario = usuarioService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_UPDATE);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/usuarios/" + usuario.getId();
	}

	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			if (id != null) {
				Usuario entity = usuarioService.findOne(id).get();
				usuarioService.delete(entity);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_DELETE);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/usuarios/";
	}
}
