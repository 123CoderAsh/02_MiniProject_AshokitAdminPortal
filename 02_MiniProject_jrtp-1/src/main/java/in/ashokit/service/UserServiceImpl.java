package in.ashokit.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.SignUpForm;
import in.ashokit.binding.UnlockForm;
import in.ashokit.entity.UserDtlsEntity;
import in.ashokit.repo.UserDtlsRepo;
import in.ashokit.utils.EmailUtils;
import in.ashokit.utils.PwdUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired 
	private EmailUtils emailUtils;
	
	@Autowired
	private HttpSession session;

	@Override
	public boolean unlockFormAccount(UnlockForm form) {
		UserDtlsEntity entity = userDtlsRepo.findByEmail(form.getEmail());
		if (entity.getPassword().equals(form.getTempPwd())) {
			entity.setPassword(form.getNewPwd());
			entity.setAccStatus("Unlocked");
			userDtlsRepo.save(entity);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean signUp(SignUpForm form) {
		
		UserDtlsEntity findByEmail = userDtlsRepo.findByEmail(form.getEmail());
		if (findByEmail != null) {
			return false;
		}
		// TODO: Copy data to binding object to entity obj
		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(form, entity);
		// TODO: generate random pwd and set to object
		String tempPwd = PwdUtils.generateRandomPwd();
		entity.setPassword(tempPwd);
		// TODO: set Account Status as LOCKED
		entity.setAccStatus("LOCKED");
		// TODO: Insert Record
		userDtlsRepo.save(entity);
		// TODO: send email to unlock the account
		String to = form.getEmail();
		String subject = "<h1>User below Temporary password to unlock your Account</h1>";
		StringBuffer body = new StringBuffer("");
		body.append("<h1>Use Below Temporary Passworld To Unlock Your Account</h1>");
		body.append("Temporary PWD :" + tempPwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:9090/unlock?email=" + to + "\">Click Here to Unlock Your Account");
		emailUtils.sendEmail(to, subject, body.toString());
		return true;
	}

	@Override
	public String loginForm(LoginForm form) {
		UserDtlsEntity entity = userDtlsRepo.findByEmailAndPassword(form.getEmail(), form.getPwd());
		if (entity == null) {
			return "Invalid Credential";
		}
		if (entity.getAccStatus().equals("LOCKED")) {
			return "Your Account is Locked...";
		}
		//create session and store user data in session
		session.setAttribute("userId", entity.getUserId());
		return "success";
	}

	@Override
	public boolean forgotPwd(String email) {

		// check Record Presence in db with given mail
		UserDtlsEntity entity = userDtlsRepo.findByEmail(email);

		// if record not available sent msg
		if (entity == null) {
			return false;
		}

		// if record available in db sent password and sent success msg
		String subject = "<h1>Recover Password<h1>";
		String body = "Your Pwd :: " + entity.getPassword();

		emailUtils.sendEmail(email, subject, body);

		return true;
	}

}
