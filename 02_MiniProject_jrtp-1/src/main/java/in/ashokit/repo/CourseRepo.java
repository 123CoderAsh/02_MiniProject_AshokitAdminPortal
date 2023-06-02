package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer>{

}
