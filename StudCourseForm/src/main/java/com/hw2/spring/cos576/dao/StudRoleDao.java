package com.hw2.spring.cos576.dao;

import com.hw2.spring.cos576.domain.StudRole;
import com.hw2.spring.cos576.dao.Dao;

public interface StudRoleDao extends Dao<StudRole> {
	
	StudRole findByName(String name);
}