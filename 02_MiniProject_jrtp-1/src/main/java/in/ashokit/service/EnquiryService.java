package in.ashokit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import in.ashokit.binding.DashBoardResponse;
import in.ashokit.binding.EnquiryForm;
import in.ashokit.binding.EnquriySearcCriteria;
import in.ashokit.entity.StudentEnqEntity;

public interface EnquiryService {

	public DashBoardResponse getDashboardData(Integer userId);
	
	public List<String> getCourses();
	
	public List<String> getEnqStatus();

	public boolean saveEnquiry(EnquiryForm form);
	
	public List<StudentEnqEntity> getStudentData();
	
	public List<StudentEnqEntity> getFilterEnqs(EnquriySearcCriteria criteria, Integer userId);
	
	public StudentEnqEntity getStudentById(Integer id);
}
