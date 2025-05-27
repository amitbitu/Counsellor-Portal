package in.spring.dto;

import lombok.Data;

@Data
public class DashboardDto {
	
	private Integer totalEnquiry;
	private Integer openEnquiry;
	private Integer enrolledEnquiry;
	private Integer lostEnquiry;

}
