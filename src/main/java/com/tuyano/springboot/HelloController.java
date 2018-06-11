package com.tuyano.springboot;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

	@RequestMapping(value="/")
	public ModelAndView index(ModelAndView mav){
		mav.setViewName("index");
		ArrayList<String[]> data = new ArrayList<String[]>();
		data.add(new String[] {"taro","taro@mail.com","090-9999-9999"});
		data.add(new String[] {"hanako","hanako@mail.com","190-9999-9999"});
		data.add(new String[] {"sachiko","sachi@mail.com","290-9999-9999"});
		System.out.println(data);
		mav.addObject("data",data);
		return mav;
	}

}

class DataObject{

	private int id;
	private String name;
	private String value;

	public DataObject(int id, String name, String value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


}