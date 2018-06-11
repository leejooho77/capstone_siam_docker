package com.siam.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.siam.model.Detail;
import com.siam.model.Device;
import com.siam.model.Message;
import com.siam.services.DeviceServices;
import com.siam.services.MessageService;

@RestController
public class DeviceController {
	
	@Autowired
	DeviceServices deviceServices;
	@Autowired
	MessageService messageService;
	
	@CrossOrigin
	@RequestMapping(value="/device/{host}", method=RequestMethod.GET)
	public @ResponseBody Device getDeviceByIp(@PathVariable("host") String host) {
		return deviceServices.getDeviceByIp(host);
	}
	
	@CrossOrigin
	@GetMapping(path="/device")
	public @ResponseBody ArrayList<Device> getAllDevice() {
		return deviceServices.getAllDevices();
	}
	
	@CrossOrigin
	@RequestMapping(value="/data/{id}", method=RequestMethod.GET)
	public @ResponseBody Detail getDetailById(@PathVariable("id") int id) {
		return messageService.getDetailById(id);
	}
	
	@CrossOrigin
	@GetMapping(path="/data")
	public @ResponseBody ArrayList<Message> getDataFromPing() {
		return messageService.results();
	}
	
	@CrossOrigin
	@RequestMapping(value="/history/{id}", method=RequestMethod.GET)
	public @ResponseBody String getHistoryById(@PathVariable("id") int id) {
		Message message = messageService.getMessageById(id);
		String history = "";
		String line;
		String fileName = message.getPath();
		
		try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                history += line + "\n";
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
		System.out.println(history);
		return history;
	}
	
	@GetMapping(path="/history")
	public String store() throws IOException {
		String filePath = null;
		System.out.println("history triggered");
		ArrayList<Message> list = messageService.results();
		for(Message m: list) {
			
			File file = new File("log/log" + m.getId() + ".txt");
			System.out.println(file.getAbsolutePath());
			BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath(), true));
	
			// rest
			writer.append("MAC address: " + m.getMacAddr());
			writer.append(" / IP address: " + m.getIpAddr());
			writer.append(" / message: " + m.getMessage());
			writer.append(" / Date: " + new Date());
			writer.newLine();
			writer.close();
			filePath = file.getAbsolutePath();
			
		}
		return filePath;
	}

}
