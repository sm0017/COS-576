package com.hw2.spring.cos576.dao;

import com.hw2.spring.cos576.domain.StudAccount;
import com.hw2.spring.cos576.dao.Dao;

public interface StudAccountDao extends Dao<StudAccount> {

	
void create(StudAccount studaccount, String password);
	
StudAccount  findByUsername(String username);
	
}
