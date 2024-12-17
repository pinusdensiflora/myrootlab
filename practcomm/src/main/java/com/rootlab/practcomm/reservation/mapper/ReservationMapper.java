package com.rootlab.practcomm.reservation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.rootlab.practcomm.reservation.Reservation;

@Mapper
public interface ReservationMapper {
	void save(Reservation reservation);
	List<Reservation> selectAll();
	Reservation selectByFields(Reservation reservation);// 겹치는 3개의 필드를 가진 Reservation을 반환
	Reservation selectById(int id);
	void update(Reservation reservation);//id가 같은 reservation 덮어쓰기
	void delete(int id);
}
