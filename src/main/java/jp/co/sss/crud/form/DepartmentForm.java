package jp.co.sss.crud.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DepartmentForm {

	private Integer deptId;

	@NotBlank
	@Size(min = 1, max = 15)
	private String deptName;

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
