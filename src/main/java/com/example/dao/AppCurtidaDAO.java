package com.example.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Curtida;

@Repository
@Transactional
public class AppCurtidaDAO extends JdbcDaoSupport {

	@Autowired
	public AppCurtidaDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public Boolean existPromoUser(Curtida curtida) {
		String sql = "select count(*) from curtidas c where c.promo_id = ? and c.usuario_id = ?";
		Object params[] = new Object[] { curtida.getPromo().getId(), curtida.getUsuario().getId() };
		try {
			int qtd = this.getJdbcTemplate().queryForObject(sql, params, Integer.class);
			if (qtd > 0)
				return true;
			else
				return false;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}
}