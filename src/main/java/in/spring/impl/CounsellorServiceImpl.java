package in.spring.impl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.spring.dto.LoginDto;
import in.spring.dto.RegisterDto;
import in.spring.entity.Counsellor;
import in.spring.repo.CounsellorRepository;
import in.spring.service.CounsellorService;

@Service
public class CounsellorServiceImpl implements CounsellorService {

	@Autowired
	private CounsellorRepository counsellorrepo;

	@Override
	public Counsellor login(LoginDto login) {
		Optional<Counsellor> byEmailAndPassword = counsellorrepo.findByEmailAndPassword(login.getEmail(),
				login.getPassword());
		if (byEmailAndPassword.isPresent()) {
			return byEmailAndPassword.get();
		}
		return null;
	}

	@Override
	public Boolean register(RegisterDto registerDto) {
		Counsellor counsellor = new Counsellor();
		BeanUtils.copyProperties(registerDto, counsellor);
		Counsellor save = counsellorrepo.save(counsellor);
		// save contains all the variable and we have given auto generated and if id
		// generated it returns true
		return save.getCounsellorId() != null;
	}

	@Override
	public Boolean isEmail(String email) {
		Optional<Counsellor> byEmail = counsellorrepo.findByEmail(email);
		if (byEmail.isPresent()) {
			return false;
		}
		return true;
	}

}
