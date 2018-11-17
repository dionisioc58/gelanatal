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
import com.example.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private ModuleService moduleService; //module service

	// Primeira tela da pagina de Students
	@GetMapping
	public String index(Model model) {
		List<Student> all = studentService.findAll();
		model.addAttribute("listStudent", all);
		model.addAttribute("");
		return "student/index";
	}
	
	// Tela de Show Student
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		if (id != null) {
			Student student = studentService.findOne(id).get();
			model.addAttribute("student", student);
		}
		return "student/show";
	}

	// Tela com Formulario de New Student
	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute Student entityStudent, 
			             @ModelAttribute Module entityModule) {
		// model.addAttribute("student", entityStudent);
		List<Module> all = moduleService.findAll();
		model.addAttribute("modules", all);
		
		return "student/form";
	}
	
	// Processamento do formulario New Student (ou Alter Student) 
	@PostMapping
	public String create(@Valid @ModelAttribute Student entityStudent, 
			             @Valid @ModelAttribute Module entityModule,
			             BindingResult result, RedirectAttributes redirectAttributes) {
		Student student = null;
		String pagina_retorno = "redirect:/students/" ;
	
		try {
			student = studentService.save(entityStudent);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_INSERT);
			pagina_retorno = pagina_retorno + student.getId();
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
				List<Module> all = moduleService.findAll();
				model.addAttribute("modules", all);
				
				Student entity = studentService.findOne(id).get();
				model.addAttribute("student", entity);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "student/form";
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute Student entity, BindingResult result, 
			             RedirectAttributes redirectAttributes) {
		Student student = null;
		try {
			student = studentService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_UPDATE);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/students/" + student.getId();
	}
	
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			if (id != null) {
				Student entity = studentService.findOne(id).get();
				studentService.delete(entity);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_DELETE);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/students/";
	}
	
	private static final String MSG_SUCESS_INSERT = "Student inserted successfully.";
	private static final String MSG_SUCESS_UPDATE = "Student successfully changed.";
	private static final String MSG_SUCESS_DELETE = "Deleted Student successfully.";
	private static final String MSG_ERROR = "Erro na insercao do Student";


}
