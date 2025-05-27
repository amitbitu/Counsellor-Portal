package in.spring.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.spring.entity.Counsellor;

public interface CounsellorRepository extends JpaRepository<Counsellor, Integer> {
	
	//select * from counsellor where email=email and password= password
	public Optional<Counsellor> findByEmailAndPassword(String eamil,String password);
	
	//select * from counsellor where email=email
	public Optional<Counsellor> findByEmail(String email);

}
