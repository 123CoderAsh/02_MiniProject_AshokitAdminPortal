package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.UserDtlsEntity;

public interface UserDtlsRepo extends JpaRepository<UserDtlsEntity, Integer>{
	
	public UserDtlsEntity findByEmail(String email);
	
	public UserDtlsEntity findByEmailAndPassword(String email,String pwd);
	

}
