package com.app.ignouapp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.ignouapp.API.SendEmailServices;
import com.app.ignouapp.dto.AdminInfoDto;
import com.app.ignouapp.dto.EnquiryDto;
import com.app.ignouapp.dto.StudentInfoDto;
import com.app.ignouapp.model.AdminInfo;
import com.app.ignouapp.model.Enquiry;
import com.app.ignouapp.model.StudentInfo;
import com.app.ignouapp.service.AdminInfoRepository;
import com.app.ignouapp.service.EnquiryRepository;
import com.app.ignouapp.service.StudentInfoRepository;


import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	@Autowired
	StudentInfoRepository srepo;
	
	@Autowired
	StudentInfoRepository stdrepo;
	
	@Autowired
	AdminInfoRepository adrepo;
	
	@Autowired
	EnquiryRepository erepo;
	
	@Autowired
	private SendEmailServices emailService;
	
	@GetMapping("/")
	public String ShowIndex() {
		return "index";
	}
	@GetMapping("/aboutus")
	public String ShowAboutUs() {
		return "aboutus";
	}
	@GetMapping("/EmployeeServices")
	public String ShowEmployeeServices() {
		return "EmployeeServices";
	}
	@GetMapping("/StudentServices")
	public String ShowStudentServices() {
		return "StudentServices";
	}
	@GetMapping("/registration")
	public String ShowRegistration(Model model) {
		StudentInfoDto dto = new StudentInfoDto();
		model.addAttribute("dto",dto);
		return "registration";
	}
	@PostMapping("/registration")
	public String Registration(@ModelAttribute StudentInfoDto dto, RedirectAttributes attrib) {
		try {
			StudentInfo stdinfo=new StudentInfo();
			stdinfo.setEnrollmentno(dto.getEnrollmentno());
			stdinfo.setName(dto.getName());
			stdinfo.setFname(dto.getFname());
			stdinfo.setMname(dto.getMname());
			stdinfo.setGender(dto.getGender());
			stdinfo.setAddress(dto.getAddress());
			stdinfo.setProgram(dto.getProgram());
			stdinfo.setBranch(dto.getBranch());
			stdinfo.setYear(dto.getYear());
			stdinfo.setContactno(dto.getContactno());
			stdinfo.setEmailaddress(dto.getEmailaddress());
			stdinfo.setPassword(dto.getPassword());
			Date dt=new Date();
			SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
			String regdate=df.format(dt);
			stdinfo.setRegdate(regdate);
			stdrepo.save(stdinfo);
			
			emailService.SendEmail(dto.getName(), dto.getEmailaddress());
			
			
			attrib.addFlashAttribute("msg","Registration Successfully!");
			return "redirect:/registration";	
		}catch(Exception e) {
			attrib.addFlashAttribute("msg","Something  went wrong"+e.getMessage());
			return "redirect:/registration";
		}
	}
	
	
	@GetMapping("/stulogin")
	public String ShowStudentLogin(Model model) {
		StudentInfoDto dto = new StudentInfoDto();
		model.addAttribute("dto",dto);
		return "stulogin";
	}
	
	@PostMapping("/stulogin")
	public String StudentLogin(@ModelAttribute StudentInfoDto dto, RedirectAttributes attributes, HttpSession session) {
		try {
			StudentInfo stdinfo = stdrepo.getById(dto.getEnrollmentno());
			if(stdinfo.getPassword().equals(dto.getPassword())) {
				session.setAttribute("studentid", dto.getEnrollmentno());
				return "redirect:/student/stdhome";
			}
			else {
				attributes.addFlashAttribute("msg","Invalid Users");
				return "redirect:/stulogin";
			}
			
		}catch(Exception e) {
			attributes.addFlashAttribute("msg","user does not exist");
			return "redirect:/stulogin";
			
		}
	}
	
	@GetMapping("/adminlogin")
	public String ShowAdminLogin(Model model) {
		AdminInfoDto dto = new AdminInfoDto();
		model.addAttribute("dto",dto);
		return "adminlogin";
	}
	@PostMapping("/adminlogin")
	public String AdminLogin(@ModelAttribute AdminInfoDto dto, HttpSession session, RedirectAttributes attributes) {
		try {
			AdminInfo ad = adrepo.getById(dto.getUserid());
			if(ad.getPassword().equals(dto.getPassword())) {
				session.setAttribute("admin", ad.getUserid().toString());
				return "redirect:/admin/adhome";
			}
			else {
				attributes.addFlashAttribute("msg","Invalid User");
				return "redirect:/adminlogin";
			}
			
		}catch(Exception e) {
		attributes.addFlashAttribute("msg","User does not exists "+e.getMessage());
		return "redirect:/adminlogin";
		}
	}
	
	
	@GetMapping("/Media")
	public String ShowMedia() {
		return "Media";
		
	}
	@GetMapping("/contactus")
	public String ShowContactUs(Model model) {
		EnquiryDto dto=new EnquiryDto();
		model.addAttribute("dto", dto);
		return "contactus";
	}
	@PostMapping("/contactus")
	public String saveEnquiry(@ModelAttribute EnquiryDto dto, RedirectAttributes attrib) {
		Enquiry e=new Enquiry();
		e.setName(dto.getName());
		e.setGender(dto.getGender());
		e.setAddress(dto.getAddress());
		e.setContactno(dto.getContactno());
		e.setEmailaddress(dto.getEmailaddress());
		e.setEnquirytext(dto.getEnquirytext());
		Date dt=new Date();
		SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		String posteddate = df.format(dt);
		e.setPosteddate(posteddate);
		erepo.save(e);
		attrib.addFlashAttribute("msg", "Enquiry saved successfully");
		return "redirect:/contactus";
		
		}

}
