package com.example.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.mapper.UsuarioMapper;
import com.example.model.Usuario;

@Repository
@Transactional
public class AppUsuarioDAO extends JdbcDaoSupport {

	@Autowired
	public AppUsuarioDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public Usuario findUserAccount(String userName) {
		// Select .. from App_User u Where u.User_Name = ?
		String sql = UsuarioMapper.BASE_SQL + " where u.nome = ? ";

		Object[] params = new Object[] { userName };
		UsuarioMapper mapper = new UsuarioMapper();
		try {
			Usuario userInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
			return userInfo;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}