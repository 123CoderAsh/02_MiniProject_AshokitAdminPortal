package in.ashokit.service;

import org.springframework.stereotype.Service;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.SignUpForm;
import in.ashokit.binding.UnlockForm;

@Service
public interface UserService {

	public boolean signUp(SignUpForm form);
	
	public boolean unlockFormAccount(UnlockForm form);
	
	public String loginForm(LoginForm form);
	
	public boolean forgotPwd(String email);

}
