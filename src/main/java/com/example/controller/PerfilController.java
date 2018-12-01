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
import com.example.service.PerfilService;

@Controller
@RequestMapping("/perfis")
public class PerfilController {

	private static final String MSG_SUCESS_INSERT = "Perfil inserido com sucesso.";
	private static final String MSG_SUCESS_UPDATE = "Perfil alterado com sucesso.";
	private static final String MSG_SUCESS_DELETE = "Perfil removido com sucesso.";
	private static final String MSG_ERROR = "Erro.";

	@Autowired
	private PerfilService perfilService;
	

	@GetMapping
	public String index(Model model) {
		List<Perfil> all = perfilService.findAll();
		model.addAttribute("listPerfil", all);
		return "perfil/index";
	}
	
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		if (id != null) {
			Perfil perfil= perfilService.findOne(id).get();
			model.addAttribute("perfil", perfil);
		}
		return "perfil/show";
	}

	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute Perfil entityPerfil) {
		// model.addAttribute("perfil", entityPerfil);
		
		return "perfil/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute Perfil entity, BindingResult result, RedirectAttributes redirectAttributes) {
		Perfil perfil = null;
		try {
			perfil = perfilService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_INSERT);
		} catch (Exception e) {
			System.out.println("Exception:: exception");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}catch (Throwable e) {
			System.out.println("Throwable:: exception");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}
		return "redirect:/perfis/" + perfil.getId();
	}
	
	@GetMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id) {
		try {
			if (id != null) {
				Perfil entity = perfilService.findOne(id).get();
				model.addAttribute("perfil", entity);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "perfil/form";
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute Perfil entity, BindingResult result, RedirectAttributes redirectAttributes) {
		Perfil perfil = null;
		try {
			perfil = perfilService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_UPDATE);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/perfis/" + perfil.getId();
	}
	
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			if (id != null) {
				Perfil entity = perfilService.findOne(id).get();
				perfilService.delete(entity);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_DELETE);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/perfis/";
	}
}
