package com.rootlab.practparking.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
	void save(UserEntity user);
	UserEntity findByUsername(String username);

}
