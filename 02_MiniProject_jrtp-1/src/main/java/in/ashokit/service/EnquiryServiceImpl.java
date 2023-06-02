package in.ashokit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.binding.DashBoardResponse;
import in.ashokit.binding.EnquiryForm;
import in.ashokit.binding.EnquriySearcCriteria;
import in.ashokit.entity.CourseEntity;
import in.ashokit.entity.EnqStatusEntity;
import in.ashokit.entity.StudentEnqEntity;
import in.ashokit.entity.UserDtlsEntity;
import in.ashokit.repo.CourseRepo;
import in.ashokit.repo.EnqStatusRepo;
import in.ashokit.repo.StudentEnqRepo;
import in.ashokit.repo.UserDtlsRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private StudentEnqRepo enqRepo;

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private EnqStatusRepo statusRepo;

	@Autowired
	private HttpSession session;

	@Override
	public DashBoardResponse getDashboardData(Integer userId) {

		DashBoardResponse response = new DashBoardResponse();

		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);

		if (findById.isPresent()) {

			UserDtlsEntity userDtlsEntity = findById.get();

			List<StudentEnqEntity> listStudent = userDtlsEntity.getListStudent();

			Integer totalCnt = listStudent.size();

			Integer enrolledCnt = listStudent.stream().filter(e -> e.getStudentStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();

			Integer lostCnt = listStudent.stream().filter(e -> e.getStudentStatus().equals("Lost"))
					.collect(Collectors.toList()).size();

			response.setTotalEnq(totalCnt);
			response.setEnrolled(enrolledCnt);
			response.setLost(lostCnt);
		}

		return response;
	}

	@Override
	public List<String> getCourses() {

		List<CourseEntity> findAll = courseRepo.findAll();

		List<String> names = new ArrayList<>();

		for (CourseEntity entity : findAll) {
			names.add(entity.getCourseName());
		}
		return names;
	}

	@Override
	public List<String> getEnqStatus() {

		List<EnqStatusEntity> findAll = statusRepo.findAll();

		List<String> statusList = new ArrayList<>();

		for (EnqStatusEntity entity : findAll) {
			statusList.add(entity.getCourseStatus());
		}
		return statusList;
	}

	@Override
	public boolean saveEnquiry(EnquiryForm form) {

		StudentEnqEntity enqEntity = new StudentEnqEntity();
		BeanUtils.copyProperties(form, enqEntity);

		Integer userId = (Integer) session.getAttribute("userId");

		UserDtlsEntity userEntity = userDtlsRepo.findById(userId).get();

		enqEntity.setUser(userEntity);

		enqRepo.save(enqEntity);

		return true;
	}

	@Override
	public List<StudentEnqEntity> getStudentData() {

		Integer userId = (Integer) session.getAttribute("userId");

		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);

		if (findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> student = userDtlsEntity.getListStudent();
			return student;
		}
		return null;
	}

	@Override
	public List<StudentEnqEntity> getFilterEnqs(EnquriySearcCriteria criteria, Integer userId) {
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);

		if (findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> student = userDtlsEntity.getListStudent();

			if (null != criteria.getCourse() & !"".equals(criteria.getCourse())) {
				student = student.stream().filter(e -> e.getStudentCourse().equals(criteria.getCourse()))
						.collect(Collectors.toList());
			}

			if (null != criteria.getEnquiryStatus() & !"".equals(criteria.getEnquiryStatus())) {
				student = student.stream().filter(e -> e.getStudentStatus().equals(criteria.getEnquiryStatus()))
						.collect(Collectors.toList());
			}

			if (null != criteria.getClassMode() & !"".equals(criteria.getClassMode())) {
				student = student.stream().filter(e -> e.getClassMode().equals(criteria.getClassMode()))
						.collect(Collectors.toList());
			}

			return student;
		}

		return null;
	}

	@Override
	public StudentEnqEntity getStudentById(Integer id) {

		Optional<StudentEnqEntity> findById = enqRepo.findById(id);

		if (findById.isPresent()) {
			StudentEnqEntity studentEnqEntity = findById.get();
			return studentEnqEntity;
		}
		return null;
	}

}
