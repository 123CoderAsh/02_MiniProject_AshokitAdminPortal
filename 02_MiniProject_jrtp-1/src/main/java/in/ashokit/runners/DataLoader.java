package in.ashokit.runners;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.ashokit.entity.CourseEntity;
import in.ashokit.entity.EnqStatusEntity;
import in.ashokit.repo.CourseRepo;
import in.ashokit.repo.EnqStatusRepo;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private EnqStatusRepo statusRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		courseRepo.deleteAll();

		CourseEntity c1 = new CourseEntity();
		c1.setCourseName("Java");

		CourseEntity c2 = new CourseEntity();
		c2.setCourseName("DevOps");

		CourseEntity c3 = new CourseEntity();
		c3.setCourseName("Phython");

		courseRepo.saveAll(Arrays.asList(c1, c2, c3));

		statusRepo.deleteAll();

		EnqStatusEntity e1 = new EnqStatusEntity();
		e1.setCourseStatus("New");

		EnqStatusEntity e2 = new EnqStatusEntity();
		e2.setCourseStatus("Enrolled");

		EnqStatusEntity e3 = new EnqStatusEntity();
		e3.setCourseStatus("Lost");

		statusRepo.saveAll(Arrays.asList(e1, e2, e3));

	}

}
