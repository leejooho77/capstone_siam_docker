package com.siam.services;

import java.util.Date;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;

import org.icmp4j.IcmpPingRequest;
import org.icmp4j.IcmpPingResponse;
import org.icmp4j.IcmpPingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import com.siam.dao.DeviceDao;
import com.siam.model.Device;
import com.siam.model.Message;

@Service
public class DeviceServices {
	
	@Autowired
	private DeviceDao deviceDao;
	private IcmpPingRequest request;
	private IcmpPingResponse response;
	private final Logger LOGGER = LoggerFactory.getLogger(DeviceServices.class);
	
	public Device getDeviceByIp(String host) {
		return deviceDao.getDeviceByIp(host);
	}
	
	public ArrayList<Device> getAllDevices() {
		ArrayList<Device> list = new ArrayList<>();
		Iterator<Device> deviceIter = deviceDao.getAllDevices().iterator();
		while(deviceIter.hasNext()) {
			list.add(deviceIter.next());
		}
		return list;
	}
	
//	public Message singleDevice(String host) {
//		request = IcmpPingUtil.createIcmpPingRequest();
//		request.setHost(host);
//		response = IcmpPingUtil.executePingRequest(request);
//		boolean successFlag = response.getSuccessFlag();
//		String message = response.getErrorMessage();
//		String hostMsg = "IP ADDRESS: " + host;
//		String successMsg = "SUCCESS: " + successFlag;
//		String messageFromPing = "MESSAGE: " + message;
//		LOGGER.info(hostMsg);
//		LOGGER.info(successMsg);
//		LOGGER.info(messageFromPing);
//		return new Message(host, successFlag, messageFromPing, new Date());
//	}
//	
//	public ArrayList<Message> results() {
//		ArrayList<Message> results = new ArrayList<>();
//		Iterable<Device> deviceList = getAllDevices();
//		Iterator<Device> deviceIter = deviceList.iterator();
//		LOGGER.info("iterating all devices in db.....");
//		while(deviceIter.hasNext()) {
//			Device device = deviceIter.next();
//			String host = device.getIpaddr();
//			request = IcmpPingUtil.createIcmpPingRequest();
//			request.setHost(host);
//			response = IcmpPingUtil.executePingRequest(request);
//			boolean successFlag = response.getSuccessFlag();
//			String message = response.getErrorMessage();
//			String hostMsg = "IP ADDRESS: " + host;
//			String successMsg = "SUCCESS: " + successFlag;
//			String messageFromPing = "MESSAGE: " + message;
//			LOGGER.info(hostMsg);
//			LOGGER.info(successMsg);
//			LOGGER.info(messageFromPing);
//			results.add(new Message(host, successFlag, messageFromPing, new Date()));
//		}
//		return results;
//	}

}
