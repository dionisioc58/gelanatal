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

import com.example.model.Module;
import com.example.model.Principal;
import com.example.service.PrincipalService;

@Controller
@RequestMapping("/principals")
public class PrincipalController {
	
	@Autowired
	private PrincipalService principalService;
	
	// Primeira tela da pagina de Principals
	@GetMapping
	public String index(Model model) {
		List<Principal> all = principalService.findAll();
		model.addAttribute("listPrincipal", all);
		model.addAttribute("");
		return "principal/index";
	}
	
	// Tela de Show Principal
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		if (id != null) {
			Principal principal = principalService.findOne(id).get();
			model.addAttribute("principal", principal);
		}
		return "principal/show";
	}

	// Tela com Formulario de New Principal
	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute Principal entityPrincipal, 
			             @ModelAttribute Module entityModule) {
		return "principal/form";
	}
	
	// Processamento do formulario New Principal (ou Alter Principal) 
	@PostMapping
	public String create(@Valid @ModelAttribute Principal entityPrincipal, 
			             @Valid @ModelAttribute Module entityModule,
			             BindingResult result, RedirectAttributes redirectAttributes) {
		Principal principal = null;
		String pagina_retorno = "redirect:/principals/" ;
	
		try {
			principal = principalService.save(entityPrincipal);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_INSERT);
			pagina_retorno = pagina_retorno + principal.getId();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}catch (Throwable e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}
		
		return pagina_retorno;
	}
	
	@GetMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id) {
		
		try {
			if (id != null) {
				Principal entity = principalService.findOne(id).get();
				model.addAttribute("principal", entity);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "principal/form";
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute Principal entity, BindingResult result, 
			             RedirectAttributes redirectAttributes) {
		Principal principal = null;
		try {
			principal = principalService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_UPDATE);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/principals/" + principal.getId();
	}
	
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			if (id != null) {
				Principal entity = principalService.findOne(id).get();
				principalService.delete(entity);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_DELETE);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/principals/";
	}
	
	private static final String MSG_SUCESS_INSERT = "Principal inserted successfully.";
	private static final String MSG_SUCESS_UPDATE = "Principal successfully changed.";
	private static final String MSG_SUCESS_DELETE = "Deleted Principal successfully.";
	private static final String MSG_ERROR = "Erro na insercao do Principal";
}
