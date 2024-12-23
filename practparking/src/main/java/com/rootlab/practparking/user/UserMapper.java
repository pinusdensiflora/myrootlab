package com.rootlab.practparking.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
	void save(User user);
	User findByUsername(String username);

}
