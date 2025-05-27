package in.spring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.spring.entity.Enquiry;

public interface EnquiryRepository extends JpaRepository<Enquiry, Integer> {
	
	public List<Enquiry> findByCounsellorCounsellorId(Integer counsellor_id);

}
