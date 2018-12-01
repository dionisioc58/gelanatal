package com.example.service;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dao.AppPerfisDAO;
import com.example.dao.AppUsuarioDAO;
import com.example.model.Usuario;
 
@Service
public class UsuarioDetalhesServiceImpl implements UserDetailsService {
 
    @Autowired
    private AppUsuarioDAO appUsuarioDAO;
 
    @Autowired
    private AppPerfisDAO appPerfilDAO;
 
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Usuario appUsuario = this.appUsuarioDAO.findUserAccount(userName);
 
        if (appUsuario == null) {
            System.out.println("Usuário não encontrado! " + userName);
            throw new UsernameNotFoundException("Usuário " + userName + " não foi encontrado no banco de dados.");
        }
 
        System.out.println("Usuário encontrado: " + appUsuario);
 
        // [ROLE_USER, ROLE_ADMIN,..]
        List<String> perfilNomes = this.appPerfilDAO.getRoleNames(appUsuario.getId());
 
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (perfilNomes != null) {
            for (String role : perfilNomes) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }
 
        UserDetails userDetails = (UserDetails) new User(appUsuario.getNome(), //
        		appUsuario.getSenha(), grantList);
 
        return userDetails;
    }
 
}