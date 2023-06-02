package in.ashokit.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.binding.DashBoardResponse;
import in.ashokit.binding.EnquiryForm;
import in.ashokit.binding.EnquriySearcCriteria;
import in.ashokit.entity.StudentEnqEntity;
import in.ashokit.service.EnquiryService;

@Controller
public class EnquiryController {

	@Autowired
	private EnquiryService enquiryService;

	@Autowired
	private HttpSession session;

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {

		Integer userId = (Integer) session.getAttribute("userId");
		DashBoardResponse dashboardData = enquiryService.getDashboardData(userId);
		model.addAttribute("dashboardData", dashboardData);

		return "dashboard";
	}

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("add-enquiry")
	public String addEnquiryPage(Model model) {
		init(model);
		return "add-enquiry";
	}

	@GetMapping("view-enquiry")
	public String viewEnquiryPage(Model model) {

		init(model);

		List<StudentEnqEntity> studentData = enquiryService.getStudentData();

		model.addAttribute("student", studentData);

		return "view-enquiry";
	}

	private void init(Model model) {

		List<String> courses = enquiryService.getCourses();

		List<String> enqStatus = enquiryService.getEnqStatus();

		EnquiryForm formObj = new EnquiryForm();

		model.addAttribute("courseNames", courses);
		model.addAttribute("enqStatus", enqStatus);
		model.addAttribute("formObj", formObj);
	}

	@PostMapping("/addEnquiries")
	public String addEnqiry(@ModelAttribute("formObj") EnquiryForm formObj, Model model) {

		System.out.println(formObj);

		boolean status = enquiryService.saveEnquiry(formObj);

		if (status) {
			model.addAttribute("succMsg", "Data Added SuccessFully..");
		} else {
			model.addAttribute("errMsg", "Problem Occured..");
		}

		return "add-enquiry";
	}

	@GetMapping("/filter_enquiries")
	public String getFilterdEnqs(@RequestParam String cname, @RequestParam String mode, @RequestParam String status,
			Model model) {

		EnquriySearcCriteria criteria = new EnquriySearcCriteria();
		criteria.setCourse(cname);
		criteria.setEnquiryStatus(status);
		criteria.setClassMode(mode);

		Integer userId = (Integer) session.getAttribute("userId");

		List<StudentEnqEntity> filterEnqs = enquiryService.getFilterEnqs(criteria, userId);

		model.addAttribute("enquiries", filterEnqs);

		return "filter-enquiry";
	}

	@GetMapping("/edit")
	public String editEnq(@RequestParam("id") Integer id, Model model) {
		init(model);
		StudentEnqEntity studentById = enquiryService.getStudentById(id);
		model.addAttribute("formObj", studentById);
		return "add-enquiry";
	}

}
