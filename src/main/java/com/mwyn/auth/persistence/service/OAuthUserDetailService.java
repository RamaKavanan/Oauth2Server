package com.mwyn.auth.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.mwyn.auth.persistence.dao.UserDao;

/**
 * Class provide the authentication process to implementing userDetailservice
 * 
 * @author RamaKavanan
 */

@Configuration
@Component
@Service
public class OAuthUserDetailService implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return (UserDetails) userDao.loadUserByUsername(userName);
    }
} 
