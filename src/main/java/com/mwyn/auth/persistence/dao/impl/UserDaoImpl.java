package com.mwyn.auth.persistence.dao.impl;

import java.util.List;

/*import javax.persistence.Table;
*/
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
/*import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.SessionFactoryUtils;*/
/*import org.springframework.security.core.userdetails.UserDetails;
*//*import org.springframework.security.core.userdetails.UserDetailsService;
*/import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mwyn.auth.persistence.dao.UserDao;
import com.mwyn.auth.persistence.model.User;

@Repository("userDao")
@Transactional
public class UserDaoImpl extends AuthGenericDaoImpl<User, Long> implements UserDao {

	@Autowired
    public UserDaoImpl(@Qualifier("oauthSessionFactory") SessionFactory sessionFactory) {
		super(sessionFactory, User.class);
    }
	
	/**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<User> getUsers() {
        Query qry = getSession().createQuery("from User u order by upper(u.userName)");
        return qry.list();
    }

    /**
     * {@inheritDoc}
     */
    public User saveUser(User user) {
        getSession().saveOrUpdate(user);
        getSession().flush();
        return user;
    }
    
    /**
     * Overridden simply to call the saveUser method. This is happening
     * because saveUser flushes the session and saveObject of BaseDaoHibernate
     * does not.
     *
     * @param user the user to save
     * @return the modified user (with a primary key set if they're new)
     */
    @Override
    public User save(User user) {
        return this.saveUser(user);
    }

    /**
     * {@inheritDoc}
    */
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        List users = getSession().createCriteria(User.class).add(Restrictions.eq("userName", username)).list();
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
            return (User) users.get(0);
        }
    }

    /**
     * {@inheritDoc}
    */
    public String getUserPassword(Long userId) {
        /*JdbcTemplate jdbcTemplate =
                new JdbcTemplate(SessionFactoryUtils.getDataSource(getSessionFactory()));
        Table table = AnnotationUtils.findAnnotation(User.class, Table.class);
        return jdbcTemplate.queryForObject(
                "select password from " + table.name() + " where id=?", String.class, userId);*/
    	return "123456";
    }
}
