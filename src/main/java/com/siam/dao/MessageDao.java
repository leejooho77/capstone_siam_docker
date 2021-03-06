package com.siam.dao;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.siam.config.MessageRepository;
import com.siam.model.Detail;
import com.siam.model.Message;

@Component
public class MessageDao {
	
	@Autowired
	private MessageRepository messageRepository;
	private final String GET_MESSAGE = "SELECT message.id, message.macaddr, message.ipaddr, message.success, message.message, message.rtt, message.date, message.path, device.type FROM message\r\n"
			+ "JOIN device on message.macaddr = device.macaddr";
	private final String WHERE_MAC = " WHERE message.macaddr=";
	private final String WHERE_IP = " WHERE message.ipaddr=";
	private final String WHERE_ID = " WHERE message.id=";
	private final String INSERT_DEVICE = "INSERT INTO message (macaddr, ipaddr, success, message, date) VALUES (?,?,?,?,?)";
	private final String UPDATE_MESSAGE = "UPDATE message SET ipaddr=?, success=?, message=?, date=? ";
	private final String DELETE_MESSAGE = "DELETE FROM messsage WHERE id=";
	private final String COUNT_MESSAGE = "SELECT COUNT(*) FROM message";
	private final String GET_DETAIL = "SELECT message.id, device.macaddr, device.ipaddr, device.company, device.type, message.rtt, device.last_connected FROM message\r\n" + 
										"INNER JOIN device\r\n" + 
										"ON device.macaddr = message.macaddr WHERE message.id=";
	private final Logger LOGGER = LoggerFactory.getLogger(MessageDao.class);

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<Message> getAllMessages() {
		return jdbcTemplate.query(GET_MESSAGE, new BeanPropertyRowMapper<>(Message.class));
	}
	
	public Message getMessageByIp(String ipaddr) {
		return jdbcTemplate.queryForObject(GET_MESSAGE + WHERE_IP + "\'" + ipaddr + "\'", new BeanPropertyRowMapper<>(Message.class));
	}
	
	public Message getMessageByMac(String macaddr) {
		return jdbcTemplate.queryForObject(GET_MESSAGE + WHERE_MAC + "\'" + macaddr + "\'", new BeanPropertyRowMapper<>(Message.class));
	}
	
	public Message getMessageById(int id) {
		return jdbcTemplate.queryForObject(GET_MESSAGE + WHERE_ID + "\'" + id + "\'", new BeanPropertyRowMapper<>(Message.class));
	}
	
	public Detail getDetailById(int id) {
		return jdbcTemplate.queryForObject(GET_DETAIL + "\'" + id + "\'", new BeanPropertyRowMapper<>(Detail.class));
	}
	
	public Integer countMessageByMac(String macaddr) {
		return jdbcTemplate.queryForObject(COUNT_MESSAGE + WHERE_MAC + "\'" + macaddr + "\'", Integer.class);
	}
	
	public int insertMessage(String macaddr, String ipaddr, int success, String message) {
		LOGGER.info("received: " + macaddr + " " + ipaddr + " " + success + " " + message);
		return jdbcTemplate.update(INSERT_DEVICE, new Object[] {macaddr, ipaddr, success, message, new Date()});
	}
	
	public int updateMessage(String macaddr, String ipaddr, int success, String message) {
		LOGGER.info("received: " + message + " of IP address of " + ipaddr);
		return jdbcTemplate.update(UPDATE_MESSAGE + WHERE_MAC + "\'" + macaddr + "\'", new Object[] {ipaddr, success, message, new Date()});
	}
	
	public int deleteMessageById(Integer id) {
		return jdbcTemplate.update(DELETE_MESSAGE + id);
	}
	
	public void deleteAllMessages() {
		messageRepository.deleteAll();
	}
	
}
