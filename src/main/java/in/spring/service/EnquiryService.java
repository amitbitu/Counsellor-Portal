package in.spring.service;

import java.util.List;

import in.spring.dto.DashboardDto;
import in.spring.dto.EnquiryDto;
import in.spring.entity.Enquiry;

public interface EnquiryService {
	
	
	public DashboardDto dashboardResponse(Integer counsellor_id);
	
	public boolean addEnqiry(EnquiryDto dto,Integer counsellor_id);
	
	public List<EnquiryDto> fetchAllEnquiry(Integer counsellor_id);
	
	public List<EnquiryDto> searchFilter(EnquiryDto dto,Integer counsellor_id);
	
	public  EnquiryDto editEnquiry(Integer eid);

}
