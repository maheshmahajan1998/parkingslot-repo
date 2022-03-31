package com.capgemini.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.dao.UserServiceImpl;
import com.capgemini.exception.DuplicateParkingFloorException;
import com.capgemini.exception.DuplicateParkingPremiseException;
import com.capgemini.exception.DuplicateUserException;
import com.capgemini.exception.InvalidLoginCredintialException;
import com.capgemini.exception.NoSuchParkingFloorException;
import com.capgemini.exception.NoSuchParkingPremiseException;
import com.capgemini.exception.NoSuchParkingSlotException;
import com.capgemini.exception.NoSuchPaymentFoundException;
import com.capgemini.exception.NoSuchUserException;
import com.capgemini.exception.ParkingSlotNotAvailableException;
import com.capgemini.exception.PaymentFailureException;
import com.capgemini.model.Login;
import com.capgemini.model.User;
import com.capgemini.repository.UserDao;
import com.capgemini.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private UserService service;
	
	@PostMapping("/")
	public boolean registerUser(@RequestBody User user) throws DuplicateUserException {
		return userService.registerUser(user);
	}
	
	@GetMapping("/login")
	public ResponseEntity login(@RequestBody Login login) throws InvalidLoginCredintialException{
		return userService.login(login);
	}
	
	@GetMapping("/finduserprofilebyid/{userId}")
	public User findUserProfileById(@PathVariable int userId) throws NoSuchUserException{
		return userService.findUserProfileById(userId);
	}
	
	@GetMapping("/finduserprofilebyemail/{email}")
	public User findUserProfileByEmail(@PathVariable String email) throws NoSuchUserException{
		return userService.findUserProfileByEmail(email);
	}
	
	@PutMapping("/modifyuserprofile/{userId}")
	public User modifyUserProfile(@RequestBody User userId) throws NoSuchUserException{
		return service.modifyUserProfile(userId);
	}
	
	@DeleteMapping("/deleteuserbyid/{userId}")
	public boolean deleteUserById(@PathVariable int userId) throws NoSuchUserException{
		return userService.deleteUserById(userId);
	}
	
	@PutMapping("/changepassword")
	public Login changePassword(@RequestBody Login login) throws InvalidLoginCredintialException{
		return userService.changePassword(login);
	}
	
	@GetMapping("/forgotPassword/{loginId}")
	public ResponseEntity forgotPassword(@PathVariable int loginId) throws NoSuchUserException{
		return userService.forgotPassword(loginId);
	}
	
	@GetMapping("/forgotloginid/{email}")
	public ResponseEntity forgotLoginId(@PathVariable String email) throws NoSuchUserException{
		return userService.forgotLoginId(email);
	}
	
	
}
