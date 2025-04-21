package com.app.ignouapp.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.ignouapp.dto.ResponseDto;
import com.app.ignouapp.model.Response;
import com.app.ignouapp.model.StudentInfo;
import com.app.ignouapp.model.StudyMaterial;
import com.app.ignouapp.service.ResponseRepository;
import com.app.ignouapp.service.StudentInfoRepository;
import com.app.ignouapp.service.StudyMaterialRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	StudentInfoRepository stdrepo;
	
	@Autowired
	ResponseRepository resrepo;
	
	@Autowired
	StudyMaterialRepository smrepo;
	
	
	@GetMapping("/stdhome")
	public String ShowStudentDashboard(HttpSession session, Model model) {
		if(session.getAttribute("studentid")!=null) {
			StudentInfo stdinfo= stdrepo.getById((long) session.getAttribute("studentid"));
			model.addAttribute("stdinfo",stdinfo);
			return "student/studentdashboard";
		}
		else {
			return "redirect:/stulogin";
		}
	}
	@PostMapping("/stdhome")
	public String UploadPic(@RequestParam MultipartFile file, RedirectAttributes attributes, HttpSession session) {
		try {
			String StorageFileName = file.getOriginalFilename();
			String uploadDir = "public/profile/";
			Path uploadPath = Paths.get(uploadDir);
			
			if(!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			try(InputStream inputstream=file.getInputStream()) {
				Files.copy(inputstream, Paths.get(uploadDir+StorageFileName), StandardCopyOption.REPLACE_EXISTING);
			}
			StudentInfo std= stdrepo.findById((long)session.getAttribute("studentid")).get();
			std.setProfilepic(StorageFileName);
			stdrepo.save(std);
			attributes.addFlashAttribute("msg","Profile pic uploaded Susccessfully");
			return "redirect:/student/stdhome";
		}catch(Exception e) {
			attributes.addFlashAttribute("msg","Something Went Wrong" +e.getMessage());
			return "redirect:/student/stdhome";
		}
	}
	
	@GetMapping("/giveresponse")
	public String ShowGiveResponse(HttpSession session, Model model) {
		if(session.getAttribute("studentid")!=null) {
			ResponseDto dto=new ResponseDto();
			model.addAttribute("dto",dto);
			return "student/giveresponse";
		}
		else {
			return "redirect:/stulogin";
		}
	}
	@PostMapping("/giveresponse")
	public String SubmitResponse(@ModelAttribute ResponseDto dto, HttpSession session,RedirectAttributes attributes) {
		if(session.getAttribute("studentid")!=null) {
			try {
				StudentInfo stdinfo = stdrepo.getById((Long) session.getAttribute("studentid"));
				
				Response res = new Response();
				res.setName(stdinfo.getName());
				res.setEnrollmentno(stdinfo.getEnrollmentno());
				res.setContactno(stdinfo.getContactno());
				res.setResponsetype(dto.getResponsetype());
				res.setResponsetitle(dto.getResponsetitle());
				res.setResponsetext(dto.getResponsetext());
				Date dt = new Date();
				SimpleDateFormat df =new SimpleDateFormat("dd/MM/yyyy");
				String resdate = df.format(dt);
				res.setResdate(resdate);
				resrepo.save(res);
				attributes.addFlashAttribute("msg","Response Submitted Successfully...  ");
				return "redirect:/student/giveresponse";
			
				}catch(Exception e) {
					attributes.addFlashAttribute("msg","Something Went Wrong" +e.getMessage());
					return "redirect:/student/giveresponse";
				}
		}else {
			return "redirect:/stulogin";
		}
	}
	
	@GetMapping("/changepassword")
	public String ShowChangePassword(HttpSession session, Model model, RedirectAttributes attributes) {
		if(session.getAttribute("studentid")!=null) {
			StudentInfo std = stdrepo.getById((long)session.getAttribute("studentid"));
			model.addAttribute("name",std.getName());
			return "student/changepassword";
		}
		else {
			attributes.addFlashAttribute("msg","Session Expired!");
			return "redirect:/stulogin";
		}
	}
	@PostMapping("/changepassword")
	public String ChangePassword(HttpSession session, RedirectAttributes attributes, HttpServletRequest request) {
		try {
			StudentInfo stdinfo = stdrepo.findById((long)session.getAttribute("studentid")).get();
			String oldpass = request.getParameter("oldpass");
			String newpass = request.getParameter("newpass");
			String confirmpass = request.getParameter("confirmpass");
			if(newpass.equals(confirmpass)) {
				if(oldpass.equals(stdinfo.getPassword())) {
					stdinfo.setPassword(confirmpass);
					stdrepo.save(stdinfo);
					session.invalidate();
					attributes.addFlashAttribute("msg","Password change Successfully!");
					return "redirect:/stulogin";
				}
				else {
					attributes.addFlashAttribute("message","Invalid old Password");
				}
			}
			else {
				attributes.addFlashAttribute("message","New Password and Confirm Password not match");
			}
			return "redirect:/student/changepassword";
		}catch(Exception e) {
			attributes.addFlashAttribute("message", "Something went Wrong" +e.getMessage());
			return "redirect:/student/changepassword";
		}
	}
	
	@GetMapping("/studymaterial")
	public String ShowStudyMaterial(HttpSession session, Model model) {
		if(session.getAttribute("studentid")!=null) {
			StudentInfo st = stdrepo.getById((long)session.getAttribute("studentid"));
			model.addAttribute("name",st.getName());
			String program = st.getProgram();
			String branch=st.getBranch();
			String year = st.getYear();
			String materialtype = "studymaterial";
			List<StudyMaterial> smlist = smrepo.findAllbyType(program, branch, year, materialtype);
			model.addAttribute("smlist",smlist);
			return "student/studymaterial";
			
		}
		else {
			return "redirect:/stulogin";
		}
		
	}
	@GetMapping("/assignment")
	public String ShowAssignment(HttpSession session, Model model) {
		if(session.getAttribute("studentid")!=null) {
			StudentInfo st=stdrepo.getById((long)session.getAttribute("studentid"));
			model.addAttribute("name",st.getName());
			String program = st.getProgram();
			String branch=st.getBranch();
			String year=st.getYear();
			String materialtype="assignment";
			List<StudyMaterial> smlist=smrepo.findAllbyType(program, branch, year, materialtype);
			model.addAttribute("smlist",smlist);
			return "student/assignment";
		}
		else {
			return "redirect:/stulogin";
		}
		
	}
	@GetMapping("/logout")
	public String Logout(HttpSession session) {
		if(session.getAttribute("studentid")!=null) {
			session.invalidate();
			return "redirect:/student/stdhome";
		}
		else {
			return "redirect:/stulogin";
		}
	}
	
	

}
