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
import jakarta.validation.Valid;
import jp.co.sss.crud.bean.DepartmentBean;
import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.form.DepartmentForm;
import jp.co.sss.crud.repository.DepartmentRepository;

@Controller
public class DeptUpdateController {
	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	HttpSession session;

	@GetMapping("/dept/update/input")
	public String updateDepartment(@RequestParam Integer deptId, Model model) {
		model.addAttribute("user", session.getAttribute("user"));
		Department updateDepartment = departmentRepository.findById(deptId).orElse(null);
		DepartmentForm form = new DepartmentForm();
		BeanUtils.copyProperties(updateDepartment, form);
		model.addAttribute("DepartmentForm", form);
		return "dept/update/update_input";
	}

	@PostMapping("dept/update/check")
	public String doRegisterDepartment(@Valid @ModelAttribute DepartmentForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("DepartmentForm", form);
			model.addAttribute("org.springframework.validation.BindingResult.DepartmentForm", result);
			return "dept/update/update_input";
		} else {
			Department checkDepartment = new Department();
			BeanUtils.copyProperties(form, checkDepartment);
			model.addAttribute("DepartmentForm", form);
			model.addAttribute("checkDepartment", checkDepartment);
			return "dept/update/update_check";
		}
	}

	@PostMapping("dept/update/input")
	public String redoRegisterDepartment(@Valid @ModelAttribute DepartmentForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("DepartmentForm", form);
			model.addAttribute("org.springframework.validation.BindingResult.DepartmentForm", result);
			return "dept/update/update_input";
		} else {
			model.addAttribute("user", session.getAttribute("user"));
			Department checkDepartment = new Department();
			BeanUtils.copyProperties(form, checkDepartment);
			model.addAttribute("DepartmentForm", form);
			return "dept/update/update_input";
		}
	}
	
	@PostMapping("dept/update/complete")
	public String checkRegisterDepartment(@Valid @ModelAttribute DepartmentForm form, Model model) {
		Department updateDepartment = departmentRepository.findById(form.getDeptId()).orElse(null);
		BeanUtils.copyProperties(form, updateDepartment, "deptId");
		departmentRepository.save(updateDepartment);
		DepartmentBean departmentBean = new DepartmentBean();
		BeanUtils.copyProperties(updateDepartment, departmentBean);
		model.addAttribute("departmentBean", departmentBean);
		return "dept/update/update_complete";
	}

}
