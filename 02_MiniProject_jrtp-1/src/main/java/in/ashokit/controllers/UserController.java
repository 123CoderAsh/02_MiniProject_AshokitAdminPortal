package in.ashokit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.SignUpForm;
import in.ashokit.binding.UnlockForm;
import in.ashokit.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/sign")
	public String handleSignUp(@ModelAttribute("user") SignUpForm form, Model model) {
		
		boolean status = userService.signUp(form);
		
		if (status) {
			model.addAttribute("succMsg", "Account Created: Check Your Email");
		} else {
			model.addAttribute("errMsg", "Choose Unique Email");
		}
		
		return "signup";
	}

	@GetMapping("/signup")
	public String createNew(Model model) {		
		model.addAttribute("user", new SignUpForm());		
		return "signup";
	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {
		
		UnlockForm unlockForm = new UnlockForm();
		
		unlockForm.setEmail(email);
		
		model.addAttribute("user", unlockForm);
		
		return "unlock";
	}

	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("user") UnlockForm unlock, Model model) {
		
		if (unlock.getNewPwd().equals(unlock.getConfirmPwd())) {
			
			boolean status = userService.unlockFormAccount(unlock);
			
			if (status) {
				model.addAttribute("succMsg", "Your Account Unlocked Successfully..");
			} else {
				model.addAttribute("errMsg", "Given Temporary PWD is Wrong, Check Your mail.");
			}
		} else {
			model.addAttribute("errMsg", "New PWD and conform PWD is should be same.");
		}
		
		return "unlock";
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		
		model.addAttribute("loginForm", new LoginForm());
		
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm form, Model model) {
		
		String status = userService.loginForm(form);
		
		if (status.contains("success")) {
			return "redirect:/dashboard";
		}
		
		model.addAttribute("errMsg", status);
		
		return "login";
	}

	@GetMapping("/forgot")
	public String forgetPwd() {
		return "forgot";
	}

	@PostMapping("/forgotPwd")
	public String forgotPwdPage(@RequestParam("email") String email, Model model) {

		boolean status = userService.forgotPwd(email);

		if (status) {
			model.addAttribute("succMsg", "Your Password sent your mail");
		} else {
			model.addAttribute("errMsg", "Invalid Email");
		}

		return "forgot";

	}

}
