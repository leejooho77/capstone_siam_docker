package com.siam.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.siam.config.DeviceRepository;
import com.siam.model.Device;

@Component
public class DeviceDao {
	
	@Autowired
	DeviceRepository deviceRepository;
	private final String GET_DEVICE = "SELECT * FROM device ";
	private final String WHERE_MAC = "WHERE macaddr=";
	private final String WHERE_IP = "WHERE ipaddr=";

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public Device getDeviceByIp(String ipaddr) {
		return jdbcTemplate.queryForObject(GET_DEVICE + WHERE_IP + "\'" + ipaddr + "\'", new BeanPropertyRowMapper<>(Device.class));
	}
	
	public Device getDeviceByMac(String macaddr) {
		return jdbcTemplate.queryForObject(GET_DEVICE + WHERE_MAC + "\'" + macaddr + "\'", new BeanPropertyRowMapper<>(Device.class));
	}
}
