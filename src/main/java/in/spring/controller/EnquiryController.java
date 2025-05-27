package in.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import in.spring.dto.EnquiryDto;
import in.spring.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {

    private final CounsellorController counsellorController;
	
	@Autowired
	private EnquiryService enquiryService;

    EnquiryController(CounsellorController counsellorController) {
        this.counsellorController = counsellorController;
    }
	
	@GetMapping("/enquiry")
	public String addEnquiry(Model model) {
		
		EnquiryDto enquiryDto=new EnquiryDto();
		model.addAttribute("enq", enquiryDto);
		return "addenqpage";
	}
	
	@PostMapping("/enquiry")
	public String handleEnquiry( @ModelAttribute("enq")  EnquiryDto enquiryDto,HttpServletRequest request, Model model ) {
		
		HttpSession session = request.getSession(false);
		Integer cid = (Integer) session.getAttribute("COUNSELLOR_ID");
		
		boolean addEnquiry = enquiryService.addEnqiry(enquiryDto, cid);
		
		if (addEnquiry) {
			model.addAttribute("smsg", "Enquiry Added Successfully");
		} else {
			model.addAttribute("emsg", "Failed To AddEnquiry");
		}
		
		return "addenqpage";
	}
	
	@GetMapping("/viewenquiry")
	public String viewEnquiry(HttpServletRequest request ,Model model) {
		
		HttpSession session = request.getSession(false);
		Integer cid = (Integer) session.getAttribute("COUNSELLOR_ID");
		
		List<EnquiryDto> viewAllEnquiry = enquiryService.fetchAllEnquiry(cid);
		
		model.addAttribute("fetchenq", viewAllEnquiry);
		
		EnquiryDto filterViewDto= new EnquiryDto();
		model.addAttribute("filter", filterViewDto);
		
		return "viewenqpage";
		
	}
	
	@PostMapping("/filterdata")
	public String handleFilter( @ModelAttribute("filter") EnquiryDto filterViewDto,HttpServletRequest request,Model model) {
		
		HttpSession session = request.getSession(false);
		Integer cid = (Integer) session.getAttribute("COUNSELLOR_ID");
	
		List<EnquiryDto> searchFilter = enquiryService.searchFilter(filterViewDto, cid);
		model.addAttribute("fetchenq", searchFilter);
		
		System.out.println(searchFilter);
		return "viewenqpage";
	}
	
	@GetMapping("/editenq/{enqId}")
	public String handleEdit(@PathVariable("enqId") Integer id, Model model) {
		
		EnquiryDto editEnquiry = enquiryService.editEnquiry(id);	
		model.addAttribute("enq", editEnquiry);
		return "addenqpage";
		
	}

}
