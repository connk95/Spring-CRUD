package jp.co.sss.crud.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.form.DepartmentForm;
import jp.co.sss.crud.repository.DepartmentRepository;

@Controller
public class DeptDeleteController {
	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	HttpSession session;
	
	@GetMapping("/dept/delete/check")
	public String deleteDepartment(@RequestParam Integer deptId, Model model) {
		model.addAttribute("user", session.getAttribute("user"));
		Department checkDepartment = departmentRepository.findById(deptId).orElse(null);
		DepartmentForm form = new DepartmentForm();
		BeanUtils.copyProperties(checkDepartment, form);
		model.addAttribute("DepartmentForm", form);
		model.addAttribute("checkDepartment", checkDepartment);
		return "dept/delete/delete_check";
	}

	@PostMapping("/dept/delete/complete")
	public String checkDeleteDepartment(@ModelAttribute DepartmentForm form, Model model) {
		departmentRepository.deleteById(form.getDeptId());
		return "dept/delete/delete_complete";
	}

}
