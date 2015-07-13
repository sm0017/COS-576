package com.hw2.spring.cos576.dao.hbn;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.hw2.spring.cos576.dao.StudRoleDao;
import com.hw2.spring.cos576.domain.StudRole;

@Repository
public class HbnStudRoleDao extends AbstractHbnDao<StudRole> implements StudRoleDao {

	public StudRole findByName(String name) {
		// TODO Auto-generated method stub
		Query q = getSession().getNamedQuery("findStudByName");
		q.setParameter("name", name);
		return (StudRole) q.uniqueResult();
	}

}
