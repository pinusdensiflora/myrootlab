package com.rootlab.practice.date;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EventMapper {
	List<Event> selectAllEvents();
}
