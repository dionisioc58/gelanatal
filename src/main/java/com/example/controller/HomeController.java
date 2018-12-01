package com.example.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.utils.WebUtils;

@Controller
public class HomeController implements ErrorController{
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessoNegado(Model model, Principal principal) {
 
        if (principal != null) {
            User usuario = (User) ((Authentication) principal).getPrincipal();
 
            String usuarioInfo = WebUtils.toString(usuario);
 
            model.addAttribute("usuarioInfo", usuarioInfo);
 
            String messagem = "Olá, " + principal.getName() //
                    + "<br> Você não tem permissão para acessar esta página!";
            model.addAttribute("messagem", messagem);
 
        }
 
        return "403Page";
    }

	@RequestMapping("/error")
    public String handleError(Model model, HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        String mensagem = String.format("<html><body><div>Código: <b>%s</b></div>"
                + "<div>Exceção: <b>%s</b></div><body></html>",
                statusCode, exception==null? "N/A": exception.getMessage());
        model.addAttribute("messagem", mensagem);
        return "ops";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
