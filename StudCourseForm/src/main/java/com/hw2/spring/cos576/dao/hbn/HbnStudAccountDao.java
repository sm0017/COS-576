package com.hw2.spring.cos576.dao.hbn;
import javax.inject.Inject;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hw2.spring.cos576.domain.StudAccount;
import com.hw2.spring.cos576.dao.StudAccountDao;

@Repository
public class HbnStudAccountDao extends AbstractHbnDao<StudAccount> implements StudAccountDao 

{

	private static final Logger LOG = LoggerFactory.getLogger(HbnStudAccountDao.class);

	
	private static final String UPDATE_PASSWORD_SQL =
			"update studaccount set password = ? where username = ?";
	
	@Inject private JdbcTemplate jdbcTemplate;
	
	public void create(StudAccount studaccount, String password) {
		// TODO Auto-generated method stub
		
		LOG.debug("Creating studaccount");
		create(studaccount);
		
		LOG.debug("Updating password");
		jdbcTemplate.update(UPDATE_PASSWORD_SQL, password, studaccount.getUsername());
		
		
	}

	public StudAccount findByUsername(String username) {
		// TODO Auto-generated method stub
		Query q = getSession().getNamedQuery("findAccountByUsername");
	
		q.setParameter("username", username);
		return (StudAccount) q.uniqueResult();
	}
	
}
