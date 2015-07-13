package com.hw2.spring.cos576.service;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import com.hw2.spring.cos576.dao.StudAccountDao;
import com.hw2.spring.cos576.dao.StudRoleDao;
import com.hw2.spring.cos576.domain.StudAccount;
import com.hw2.spring.cos576.domain.StudRole;

@Service
@Transactional(readOnly = true)
public class StudAccountServiceImpl implements StudAccountService{

	
	
private static final Logger log = LoggerFactory.getLogger(StudAccountServiceImpl.class);
	
	@Inject private StudAccountDao studaccountDao;
	@Inject private StudRoleDao studroleDao;
	
	@Override
	@Transactional(readOnly = false)	


	public boolean registerStudAccount(StudAccount studaccount,
			String password, Errors errors) {
		validateUsername(studaccount.getUsername(), errors);
		boolean valid = !errors.hasErrors();
		
		if (valid) {
			Set<StudRole> studroles = new HashSet<StudRole>();
			studroles.add(studroleDao.findByName("user"));
			studaccount.setStudRoles(studroles);
			studaccountDao.create(studaccount, password);
		}
		
		return valid;
	}


	private void validateUsername(String username, Errors errors) {
		if (studaccountDao.findByUsername(username) != null) {
			log.debug("Validation failed: duplicate username");
			errors.rejectValue("username", "error.duplicate", new String[] { username }, null);
		}
	}
	
}
