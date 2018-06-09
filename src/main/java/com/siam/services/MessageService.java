package com.siam.services;

import java.util.ArrayList;
import java.util.Iterator;

import org.icmp4j.IcmpPingRequest;
import org.icmp4j.IcmpPingResponse;
import org.icmp4j.IcmpPingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import com.siam.dao.DeviceDao;
import com.siam.dao.MessageDao;
import com.siam.model.Device;
import com.siam.model.Message;

@Service
public class MessageService {
	
	@Autowired
	private MessageDao messageDao;
	private final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);
	
	public ArrayList<Message> results() {
		ArrayList<Message> list = new ArrayList<>();
		Iterator<Message> messageIter = messageDao.getAllMessages().iterator();
		while(messageIter.hasNext()) {
			Message message = messageIter.next();
			LOGGER.info(message.getMacAddr());
			LOGGER.info(message.getIpAddr());
			LOGGER.info(message.getMessage());
			LOGGER.info(message.getDate().toString());
			list.add(message);
		}
		return list;
	}
	
	public Message getMessageById(int id) {
		return messageDao.getMessageById(id);
	}

}
