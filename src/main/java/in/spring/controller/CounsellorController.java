package in.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.spring.dto.DashboardDto;
import in.spring.dto.LoginDto;
import in.spring.dto.RegisterDto;
import in.spring.entity.Counsellor;
import in.spring.service.CounsellorService;
import in.spring.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {
	
	@Autowired
	private CounsellorService counsellorService;
	
	@Autowired
	private EnquiryService enquiryService;
	
	@GetMapping("/")
	public String index(Model model) {
		
		LoginDto loginDto= new LoginDto();
		model.addAttribute("counsellor", loginDto);
		
		return "index";
	}
	
	@PostMapping("/login")
	public String handleLogin(@ModelAttribute("counsellor") LoginDto loginDto,HttpServletRequest request, Model model) {
		
		Counsellor login = counsellorService.login(loginDto);
		if(login==null) {
			model.addAttribute( "emsg", "Invalid Credentials");
			
			return "index";
		}
		else {
			HttpSession session = request.getSession(true);
			session.setAttribute("COUNSELLOR_ID", login.getCounsellorId());
			return "redirect:dashboard";
		}
		
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		session.invalidate();
		
		return "redirect:/";
		
	}
	
	@GetMapping("/dashboard")
	public String  createDashBoard(HttpServletRequest request,DashboardDto dashboardDto,Model model) {
		
		HttpSession session = request.getSession(false);
		Integer cid = (Integer) session.getAttribute("COUNSELLOR_ID");
		DashboardDto dashboardResponse = enquiryService.dashboardResponse(cid);
		model.addAttribute("dashboardinfo", dashboardResponse);
		
		return "dashboardpage";
	}
	
	@GetMapping("/register")
	public String createRegister(Model model) {
		
		RegisterDto registerDto = new RegisterDto();
		model.addAttribute("registerdata", registerDto);
		
		return "registerviewpage";
		
	}
	
	@PostMapping("/register")
	public String handleRegister(@ModelAttribute("registerdata")  RegisterDto registerDto ,Model model) {
		
		Boolean email = counsellorService.isEmail(registerDto.getEmail());
		
		if(email) {
			Boolean register = counsellorService.register(registerDto);
			if(register) {
				model.addAttribute("smsg", "Registration Successful");
			}
			else {
				model.addAttribute("emsg", "Registration Failed");
			}
;		}
		
		else {
			model.addAttribute("emsg", "Duplicate Email Found");
		}
		return "registerviewpage";
		
	}

}
