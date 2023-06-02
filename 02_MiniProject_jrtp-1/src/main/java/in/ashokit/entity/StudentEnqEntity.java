package in.ashokit.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
public class StudentEnqEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String studentName;
	
	private Long phno;
	
	private String classMode;
	
	private String studentCourse;
	
	private String studentStatus;
	
	@CreationTimestamp
	private LocalDate createDate;
	
	@UpdateTimestamp
	private LocalDate updateDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserDtlsEntity user;

}
