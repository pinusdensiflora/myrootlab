package com.rootlab.practcomm.reservation.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rootlab.practcomm.reservation.dto.Reservation;
import com.rootlab.practcomm.reservation.mapper.ReservationMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
	
	private final ReservationMapper reservationMapper;
	
	public void save(Reservation reservation) { //id 반환
		reservation.setCreateDate(LocalDateTime.now());
		reservation.setStatus("실행 중");
		reservation.setUse_yn("y");
		reservationMapper.save(reservation);
	}
	
	public List<Reservation> selectAll(){
		return reservationMapper.selectAll();
	}
	public Reservation selectByFields(Reservation reservation) {
		//3개의 필드가 같은 reservation 을 반환하는 것. 파라미터로 주는 reservation은 사용하지 않는다.
		return reservationMapper.selectByFields(reservation);
	}
	
	public Reservation selectById(int id) {
		return reservationMapper.selectById(id);
	}
	public void update(Reservation reservation) {
		reservationMapper.update(reservation);
	}
	public void delete(int id) {
		reservationMapper.delete(id);
	}
	
}
