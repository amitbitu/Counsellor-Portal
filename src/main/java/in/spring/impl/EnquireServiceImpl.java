package in.spring.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.spring.constant.AppConstant;
import in.spring.dto.DashboardDto;
import in.spring.dto.EnquiryDto;
import in.spring.entity.Counsellor;
import in.spring.entity.Enquiry;
import in.spring.repo.CounsellorRepository;
import in.spring.repo.EnquiryRepository;
import in.spring.service.EnquiryService;

@Service
public class EnquireServiceImpl implements EnquiryService {

	@Autowired
	private EnquiryRepository enquiryRepository;

	@Autowired
	private CounsellorRepository counsellorRepository;

	@Override
	public DashboardDto dashboardResponse(Integer counsellor_id) {
		List<Enquiry> allEnquries = enquiryRepository.findByCounsellorCounsellorId(counsellor_id);
		int totalEnq = allEnquries.size();
		int openEnq = (int) allEnquries.stream().filter(e -> e.getStatus().equals(AppConstant.OPEN_ENQUIRY)).count();
		int enrolledEnq = (int) allEnquries.stream().filter(e -> e.getStatus().equals(AppConstant.ENROLLED_ENQUIRY)).count();
		int lostEnq = (int) allEnquries.stream().filter(e -> e.getStatus().equals(AppConstant.LOST_ENQUIRY)).count();
		DashboardDto dashboardDto = new DashboardDto();
		dashboardDto.setTotalEnquiry(totalEnq);
		dashboardDto.setOpenEnquiry(openEnq);
		dashboardDto.setEnrolledEnquiry(enrolledEnq);
		dashboardDto.setLostEnquiry(lostEnq);
		return dashboardDto;
	}

	@Override
	public boolean addEnqiry(EnquiryDto dto, Integer counsellor_id) {
		Enquiry e = new Enquiry();
		BeanUtils.copyProperties(dto, e);
		Counsellor c = counsellorRepository.findById(counsellor_id).orElseThrow();
		e.setCounsellor(c);
		Enquiry save = enquiryRepository.save(e);
		return save.getId() != null;
   }

	@Override
	public List<EnquiryDto> fetchAllEnquiry(Integer counsellor_id) {
		Counsellor c= new Counsellor();
		c.setCounsellorId(counsellor_id);
		Enquiry e= new Enquiry();
		e.setCounsellor(c);
		List<Enquiry> all = enquiryRepository.findAll(Example.of(e));
		
		//converting list of enquiry to enquiry dto
		List<EnquiryDto> dtos=new ArrayList<>();
		for(Enquiry enq:all) {
			EnquiryDto dto= new EnquiryDto();
			BeanUtils.copyProperties(enq, dto);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public List<EnquiryDto> searchFilter(EnquiryDto dto, Integer counsellor_id) {
		Enquiry e= new Enquiry();
		Counsellor orElseThrow = counsellorRepository.findById(counsellor_id).orElseThrow();
		e.setCounsellor(orElseThrow);
		
		if(dto.getCourse()!=null && !dto.getCourse().equals("")) {
			e.setCourse(dto.getCourse());
		}
		
		if(dto.getClassMode()!=null && !dto.getClassMode().equals("")) {
			e.setClassMode(dto.getClassMode());
		}
		
		if(dto.getStatus()!=null && !dto.getStatus().equals("")) {
			e.setStatus(dto.getStatus());
		}
		
		List<Enquiry> all = enquiryRepository.findAll(Example.of(e));
		List<EnquiryDto> dtos=new ArrayList<>();
		for(Enquiry enq:all) {
			EnquiryDto edto= new EnquiryDto();
			BeanUtils.copyProperties(enq, edto);
			dtos.add(edto);
		}
		return dtos;
	}

	@Override
	public EnquiryDto editEnquiry(Integer eid) {
		Enquiry enq = enquiryRepository.findById(eid).orElseThrow();
		EnquiryDto dto=new EnquiryDto();
		BeanUtils.copyProperties(enq, dto);
		return dto;
	}
	
	
	

}
