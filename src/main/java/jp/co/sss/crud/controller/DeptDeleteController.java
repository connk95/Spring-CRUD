package jp.co.sss.crud.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.form.DepartmentForm;
import jp.co.sss.crud.repository.DepartmentRepository;
import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
public class DeptDeleteController {
	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	EmployeeRepository employeeRepository;

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
	public String checkDeleteDepartment(@ModelAttribute DepartmentForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("DepartmentForm", form);
			model.addAttribute("org.springframework.validation.BindingResult.DepartmentForm", result);
			return "dept/delete/delete_check";
		} else {
			try {
				departmentRepository.deleteById(form.getDeptId());
				return "dept/delete/delete_complete";
			} catch (org.springframework.dao.DataIntegrityViolationException e) {
				Department checkDepartment = departmentRepository.findById(form.getDeptId()).orElse(null);
				BeanUtils.copyProperties(checkDepartment, form);
				model.addAttribute("DepartmentForm", form);
				model.addAttribute("checkDepartment", checkDepartment);
				model.addAttribute("errorMessage", "削除できません。この部署には関連する子レコードが存在します。");
				return "dept/delete/delete_check";
			}
		}
	}

}
