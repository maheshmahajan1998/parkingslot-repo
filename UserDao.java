package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.model.User;


@Repository //which is using repository class: //IT CONTAINS BASIC CRUD OPERATIONS
public interface UserDao extends JpaRepository<User, Integer>
{
@Transactional //When we want to configure the transactional behavior of a method,
@Modifying //to enhance the query annotation
@Query(value = "update User u set u=?1 where u.userId=?2")
//used to define sql to execute for a spring data repository

int updateUser(User user, int userId);

}


