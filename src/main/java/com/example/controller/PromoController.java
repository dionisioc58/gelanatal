package com.example.controller;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.example.dao.AppUsuarioDAO;
import com.example.model.Curtida;
import com.example.model.Promo;
import com.example.model.Usuario;
import com.example.service.CurtidaService;
import com.example.service.PromoService;
import com.example.service.UsuarioService;

@Controller
@RequestMapping("/promos")
public class PromoController {

	private static final String MSG_SUCESS_INSERT = "Promo inserted successfully.";
	private static final String MSG_SUCESS_UPDATE = "Promo successfully changed.";
	private static final String MSG_SUCESS_DELETE = "Deleted Promo successfully.";
	private static final Object MSG_SUCESS_LIKE   = "Curtida realizada com sucesso.";
	private static final String MSG_ERROR         = "Error.";
	
	@Autowired
	private PromoService promoService;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CurtidaService curtidaService;
	
	@Autowired
	private AppUsuarioDAO appUsuarioDAO;

	@GetMapping
	public String index(Model model) {
		List<Promo> all = promoService.findAll();
		model.addAttribute("listPromo", all);
		return "promo/index";
	}

	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		if (id != null) {
			Promo promo = promoService.findOne(id).get();
			model.addAttribute("promo", promo);
		}
		return "promo/show";
	}

	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute Promo entityPromo, @ModelAttribute Usuario entityUsuario) {
		// model.addAttribute("promo", entityPromo);

		List<Usuario> all = usuarioService.findAll();
		model.addAttribute("usuarios", all);

		return "promo/form";
	}

	@PostMapping
	public String create(@Valid @ModelAttribute Promo entity, @Valid @ModelAttribute Usuario entityUsuario,
			BindingResult result, RedirectAttributes redirectAttributes) {
		Promo promo = null;
		try {
			promo = promoService.save(entity);
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
		return "redirect:/promos/" + promo.getId();
	}

	@GetMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id) {
		try {
			if (id != null) {
				List<Usuario> all = usuarioService.findAll();
				model.addAttribute("usuarios", all);

				Promo entity = promoService.findOne(id).get();
				model.addAttribute("promo", entity);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "promo/form";
	}
	
	@GetMapping("/{id}/like")
	public String curtir(Model model, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			if (id != null) {
				Promo entity = promoService.findOne(id).get();
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				Usuario usuario = appUsuarioDAO.findUserAccount(auth.getName());
				Curtida curtida = new Curtida(entity, usuario);
				curtidaService.save(curtida);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_LIKE);
			}
		} catch (Exception e) {
			System.out.println("Exception:: exception");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR + " " + e.getMessage());
		}
		return "redirect:/promos/";
	}

	@PutMapping
	public String update(@Valid @ModelAttribute Promo entity, BindingResult result,
			RedirectAttributes redirectAttributes) {
		Promo promo = null;
		try {
			promo = promoService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_UPDATE);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/promos/" + promo.getId();
	}

	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			if (id != null) {
				Promo entity = promoService.findOne(id).get();
				promoService.delete(entity);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_DELETE);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/promos/";
	}

}
