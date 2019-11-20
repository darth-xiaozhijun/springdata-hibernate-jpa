package com.geekshow.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.geekshow.dao.UsersDao;
import com.geekshow.pojo.Users;

@Repository
public class UsersDaoImpl  implements UsersDao {

	@PersistenceContext(name="entityManagerFactory")
	private EntityManager entityManager;
	
	@Override
	public void insertUsers(Users users) {
		this.entityManager.persist(users);
	}

	@Override
	public void updateUsers(Users users) {
		this.entityManager.merge(users);
	}

	@Override
	public void deleteUsers(Users users) {
		Users  u = this.selectUsersById(users.getUserid());
		this.entityManager.remove(u);
	}

	@Override
	public Users selectUsersById(Integer userid) {
		return this.entityManager.find(Users.class, userid);
	}

	/**
	 * HQL
	 */
	@Override
	public List<Users> selectUserByName(String username) {
		return this.entityManager.createQuery(" from Users where username = :abc").setParameter("abc", username).getResultList();
	}

	/**
	 * SQL
	 */
	@Override
	public List<Users> selectUserByNameUseSQL(String username) {
		//在Hibernate JPA中 如果通过？方式来帮顶参数，那么他的查数是从1开始的。而hibernate中是从0开始的。
		return this.entityManager.createNativeQuery("select * from t_users where username = ?", Users.class).setParameter(1, username).getResultList();
	}

	/**
	 * QBC
	 */
	@Override
	public List<Users> selectUserByNameUseCriteria(String username) {
		return null;
	}


}
