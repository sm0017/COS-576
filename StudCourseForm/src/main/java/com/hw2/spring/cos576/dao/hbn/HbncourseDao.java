package com.hw2.spring.cos576.dao.hbn;
import javax.inject.Inject;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hw2.spring.cos576.domain.CourseAccount;
import com.hw2.spring.cos576.domain.StudAccount;
import com.hw2.spring.cos576.dao.CourseDao;
import com.hw2.spring.cos576.dao.StudAccountDao;

@Repository
public class HbncourseDao extends AbstractHbnCourseDao<CourseAccount> implements CourseDao 

{

	private static final Logger LOG = LoggerFactory.getLogger(HbncourseDao.class);

		
	@Inject private JdbcTemplate jdbcTemplate;
	
	/**public void create(CourseAccount courseAccount) {
		// TODO Auto-generated method stub
		System.out.println("i am in Hbncoursedao:Smita");
		LOG.debug("Creating courseAccount");
		create(courseAccount);
		
			
		
	}*/

	public CourseAccount findByCourseName(String coursename) {
		// TODO Auto-generated method stub
		Query q = getSession().getNamedQuery("findAccountByCoursename");
	
		q.setParameter("coursename", coursename);
		return (CourseAccount) q.uniqueResult();
	}

	
	
}
