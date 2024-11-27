package jp.co.sss.crud.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jp.co.sss.crud.bean.DepartmentBean;
import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.form.DepartmentForm;
import jp.co.sss.crud.repository.DepartmentRepository;

@Controller
public class DeptRegisterController {
	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	HttpSession session;

	@GetMapping("dept/regist/input")
	public String registerDepartment(Model model) {
		model.addAttribute("DepartmentForm", new DepartmentForm());
		model.addAttribute("user", session.getAttribute("user"));
		return "dept/regist/regist_input";
	}

	@PostMapping("dept/regist/check")
	public String doRegisterDepartment(@Valid @ModelAttribute DepartmentForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("DepartmentForm", form);
			model.addAttribute("org.springframework.validation.BindingResult.DepartmentForm", result);
			return "dept/regist/regist_input";
		} else {
			Department checkDepartment = new Department();
			BeanUtils.copyProperties(form, checkDepartment);
			model.addAttribute("DepartmentForm", form);
			model.addAttribute("checkDepartment", checkDepartment);
			return "dept/regist/regist_check";
		}
	}

	@PostMapping("dept/regist/input")
	public String redoRegisterDepartment(@Valid @ModelAttribute DepartmentForm form, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("DepartmentForm", form);
			model.addAttribute("org.springframework.validation.BindingResult.DepartmentForm", result);
			return "dept/regist/regist_input";
		} else {
			model.addAttribute("user", session.getAttribute("user"));
			Department checkDepartment = new Department();
			BeanUtils.copyProperties(form, checkDepartment);
			model.addAttribute("DepartmentForm", form);
			return "dept/regist/regist_input";
		}
	}

	@PostMapping("dept/regist/complete")
	@Transactional
	public String checkRegisterEmployee(@Valid @ModelAttribute DepartmentForm form, Model model) {
		Department newDepartment = new Department();
		BeanUtils.copyProperties(form, newDepartment);
		System.out.println(newDepartment.getDeptName());
		System.out.println(newDepartment.getDeptId());
		newDepartment = departmentRepository.save(newDepartment);
		DepartmentBean departmentBean = new DepartmentBean();
		BeanUtils.copyProperties(newDepartment, departmentBean);
		model.addAttribute("departmentBean", departmentBean);
		return "dept/regist/regist_complete";
	}
}
