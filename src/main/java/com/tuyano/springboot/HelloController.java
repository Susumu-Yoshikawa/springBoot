package com.tuyano.springboot;


import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tuyano.springboot.repositories.MyDataRepository;

@Controller
public class HelloController {

	@Autowired
	MyDataRepository repository;

	@PersistenceContext
	EntityManager entityManager;

	MyDataDaoImpl dao;

	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView index(
			@ModelAttribute("formModel") MyData mydata,
			ModelAndView mav){
		mav.setViewName("index");
		mav.addObject("msg","this is sample content.");
		mav.addObject("formModel", mydata);
		Iterable<MyData> list = dao.getAll();
		mav.addObject("datalist",list);
		return mav;
	}

	@RequestMapping(value="/", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView form(
			@ModelAttribute("formModel")
			@Validated MyData mydata,
			BindingResult result,
			ModelAndView mav){

		ModelAndView res = null;
		if(!result.hasErrors()) {
			repository.saveAndFlush(mydata);
			res = new ModelAndView("redirect:/");
		} else {
			mav.setViewName("index");
			mav.addObject("msg","sorry, error is occured...");
			Iterable<MyData> list = repository.findAll();
			mav.addObject("datalist",list);
			res = mav;
		}

		return res;
	}

	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView edit(
			@ModelAttribute MyData mydata,
			@PathVariable int id, ModelAndView mav) {
		mav.setViewName("edit");
		mav.addObject("title","edit mydata. ");
		Optional<MyData> data = repository.findById((long)id);
		mav.addObject("formModel",data.get());
		return mav;
	}

	@RequestMapping(value="/edit", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView update(
			@ModelAttribute("formModel") MyData mydata,
			ModelAndView mav){
		repository.saveAndFlush(mydata);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public ModelAndView delete(
			@PathVariable int id,
			ModelAndView mav){
		mav.setViewName("delete");
		mav.addObject("title","delete mydata. ");
		Optional<MyData> data = repository.findById((long)id);
		mav.addObject("formModel",data.get());
		return mav;
	}

	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView remove(
			@RequestParam long id,
			ModelAndView mav){
		repository.deleteById(id);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value="/find", method = RequestMethod.GET)
	public ModelAndView find(ModelAndView mav){
		mav.setViewName("find");
		mav.addObject("title","Find Page");
		mav.addObject("value","");
		Iterable<MyData> list = dao.getAll();
		mav.addObject("datalist",list);
		return mav;
	}

	@RequestMapping(value="/find", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView search(
			HttpServletRequest request,
			ModelAndView mav){
		mav.setViewName("find");
		String param = request.getParameter("fstr");
		if(param == "") {
			mav = new ModelAndView("redirect:/find");
		} else {
			mav.addObject("title","Find result");
			mav.addObject("msg","「" + param + "」の検索結果");
			mav.addObject("value","param");
			List<MyData> list = dao.find(param);
			mav.addObject("datalist",list);
		}
		return mav;
	}


	@PostConstruct
	public void init() {

		dao = new MyDataDaoImpl(entityManager);

		MyData d1 = new MyData();
		d1.setName("test1");
		d1.setAge(123);
		d1.setMail("mail@test1");
		d1.setMemo("090-9999-9999");
		repository.saveAndFlush(d1);

		MyData d2 = new MyData();
		d2.setName("test2");
		d2.setAge(200);
		d2.setMail("mail@test2");
		d2.setMemo("080-9999-9999");
		repository.saveAndFlush(d2);

		MyData d3 = new MyData();
		d3.setName("test3");
		d3.setAge(199);
		d3.setMail("mail@test3");
		d3.setMemo("070-9999-9999");
		repository.saveAndFlush(d3);
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