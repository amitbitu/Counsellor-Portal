package in.spring.service;

import in.spring.dto.LoginDto;
import in.spring.dto.RegisterDto;
import in.spring.entity.Counsellor;

public interface CounsellorService {
	
	public Counsellor login(LoginDto login);
	
	public Boolean register(RegisterDto registerDto);
	
	public Boolean isEmail(String email);
	
}
