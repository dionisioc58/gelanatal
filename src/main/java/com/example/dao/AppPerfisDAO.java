package com.example.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AppPerfisDAO extends JdbcDaoSupport {

	@Autowired
	public AppPerfisDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public List<String> getRoleNames(Integer integer) {
		String sql = "Select p.nome " //
				+ " from usuarios_perfis up, perfis p " //
				+ " where up.perfil_id = p.id and up.usuario_id = ? ";

		Object[] params = new Object[] { integer };

		List<String> roles = this.getJdbcTemplate().queryForList(sql, params, String.class);

		return roles;
	}

}