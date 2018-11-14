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
import com.example.model.Student;
import com.example.service.ModuleService;

@Controller
@RequestMapping("/modules")
public class ModuleController {

	private static final String MSG_SUCESS_INSERT = "Module inserted successfully.";
	private static final String MSG_SUCESS_UPDATE = "Module successfully changed.";
	private static final String MSG_SUCESS_DELETE = "Deleted Module successfully.";
	private static final String MSG_ERROR = "Error.";

	@Autowired
	private ModuleService moduleService;
	

	@GetMapping
	public String index(Model model) {
		List<Module> all = moduleService.findAll();
		model.addAttribute("listModule", all);
		return "module/index";
	}
	
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		if (id != null) {
			Module module = moduleService.findOne(id).get();
			model.addAttribute("module", module);
		}
		return "module/show";
	}

	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute Module entityModule, @ModelAttribute Student entityStudent) {
		// model.addAttribute("module", entityModule);
		
		return "module/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute Module entity, BindingResult result, RedirectAttributes redirectAttributes) {
		Module module = null;
		try {
			module = moduleService.save(entity);
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
		return "redirect:/modules/" + module.getId();
	}
	
	@GetMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id) {
		try {
			if (id != null) {
				Module entity = moduleService.findOne(id).get();
				model.addAttribute("module", entity);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "module/form";
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute Module entity, BindingResult result, RedirectAttributes redirectAttributes) {
		Module module = null;
		try {
			module = moduleService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_UPDATE);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/modules/" + module.getId();
	}
	
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			if (id != null) {
				Module entity = moduleService.findOne(id).get();
				moduleService.delete(entity);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_DELETE);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/modules/";
	}

}
