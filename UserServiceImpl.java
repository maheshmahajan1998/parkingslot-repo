package com.capgemini.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.capgemini.exception.DuplicateUserException;
import com.capgemini.exception.InvalidLoginCredintialException;
import com.capgemini.exception.NoSuchUserException;
import com.capgemini.exception.NoSuchVehicleException;
import com.capgemini.model.Login;
import com.capgemini.model.User;
import com.capgemini.repository.LoginDao;
import com.capgemini.repository.UserDao;
import com.capgemini.service.UserService;


@Service("UserService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	LoginDao loginDao;

	@Override
	public boolean registerUser(User user)  {
		userDao.save(user);
		return true;
	}

	@Override
	public ResponseEntity login(Login login) throws InvalidLoginCredintialException {
		try {
			for(Login i : loginDao.findAll()) {
				if(i.getUsername().equals(login.getUsername())) {
					if(i.getPassword().equals(login.getPassword())) {
						return new ResponseEntity("Authentication successfully done!",HttpStatus.OK);
					}
					return new ResponseEntity("Authentication Failed, Check username and password",HttpStatus.NOT_FOUND);
				}
				return new ResponseEntity("Authentication Failed, Check username and password",HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e) {
			throw new InvalidLoginCredintialException("Login details not found! Invalid details");
		}
		return new ResponseEntity("Authentication successfully done!",HttpStatus.OK);
	}

	@Override
	public User findUserProfileById(int userId) throws NoSuchUserException {
		User bean = null;
		try {
			bean = userDao.findById(userId).get();
		}
		catch(Exception e) {
			throw new NoSuchUserException("User details not found!");
		}
		return bean;
	}

	@Override
	public User findUserProfileByEmail(String email) throws NoSuchUserException {
		User bean = null;
		try {
			for(User i :userDao.findAll()) {
				if(i.getEmail().equals(email)) {
					bean = i;
					break;
				}
			}
		}
		catch(Exception e) {
			throw new NoSuchUserException("User details not Found");
		}
		return bean;
	}

	@Override
	public User modifyUserProfile(User user) throws NoSuchUserException {
		User bean = null;
		try {
			bean = userDao.findById(user.getUserId()).get();
		}
		catch(Exception e) {
			throw new NoSuchUserException("User details not found!");
		}
		userDao.save(user);
		return bean;
	}

	@Override
	public boolean deleteUserById(int userId) throws NoSuchUserException {
		User bean = null;
		try {
			bean = userDao.findById(userId).get();
		}
		catch(Exception e) {
			throw new NoSuchUserException("User details not found!");
		}
		userDao.deleteById(userId);
		return true;
	}

	@Override
	public Login changePassword(Login login) throws InvalidLoginCredintialException {
		Login bean = null;
		try {
			bean = loginDao.findById(login.getLoginId()).get();
		}
		catch(Exception e) {
			throw new InvalidLoginCredintialException("Login details not found!");
		}
		loginDao.saveAndFlush(login);
		return bean;
	}

	@Override
	public ResponseEntity forgotPassword(int loginId) throws NoSuchUserException {
		User bean = null;
		try {
			for(User i :userDao.findAll()) {
				if(i.getLogin().getLoginId()==loginId) {
					new ResponseEntity("Password Recovery will be done!",HttpStatus.OK);
				}
			}
		}
		catch(Exception e) {
			throw new NoSuchUserException("User details not Found");
		}
		return new ResponseEntity("User details not Found!",HttpStatus.NOT_FOUND);
		
	}

	@Override
	public ResponseEntity forgotLoginId(String email) throws NoSuchUserException {
		User bean = null;
		try {
			for(User i :userDao.findAll()) {
				if(i.getEmail().equals(email)) {
					new ResponseEntity("Login Id Recovery will be done!",HttpStatus.OK);
				}
			}
		}
		catch(Exception e) {
			throw new NoSuchUserException("User details not Found");
		}
		return new ResponseEntity("User details not Found!",HttpStatus.NOT_FOUND);
		
	}

}
