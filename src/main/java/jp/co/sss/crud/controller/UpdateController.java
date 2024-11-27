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
import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.repository.DepartmentRepository;
import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
public class UpdateController {
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	HttpSession session;

	@GetMapping("/update/input")
	public String updateEmployee(@RequestParam Integer empId, Model model) {
		model.addAttribute("user", session.getAttribute("user"));
		Employee updateEmployee = employeeRepository.findById(empId).orElse(null);
		EmployeeForm form = new EmployeeForm();
		BeanUtils.copyProperties(updateEmployee, form);
		model.addAttribute("EmployeeForm", form);
		model.addAttribute("departments", departmentRepository.findAll());
		return "update/update_input";
	}

	@PostMapping("update/check")
	public String doRegisterEmployee(@Valid @ModelAttribute EmployeeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("EmployeeForm", form);
			model.addAttribute("org.springframework.validation.BindingResult.EmployeeForm", result);
			return "update/update_input";
		} else {
			Employee checkEmployee = new Employee();
			BeanUtils.copyProperties(form, checkEmployee);
			if (checkEmployee.getAuthority() != 1) {
				checkEmployee.setAuthority(2);
			}
			Department department = departmentRepository.findById(form.getDeptId()).orElse(null);
			checkEmployee.setDepartment(department);
			model.addAttribute("EmployeeForm", form);
			model.addAttribute("checkEmployee", checkEmployee);
			return "update/update_check";
		}
	}

	@PostMapping("update/input")
	public String redoRegisterEmployee(@Valid @ModelAttribute EmployeeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("EmployeeForm", form);
			model.addAttribute("org.springframework.validation.BindingResult.EmployeeForm", result);
			return "update/update_input";
		} else {
			model.addAttribute("user", session.getAttribute("user"));
			Employee checkEmployee = new Employee();
			BeanUtils.copyProperties(form, checkEmployee);
			Department department = departmentRepository.findById(form.getDeptId()).orElse(null);
			checkEmployee.setDepartment(department);
			model.addAttribute("EmployeeForm", form);
			return "update/update_input";
		}
	}

	@PostMapping("/update/complete")
	public String checkRegisterEmployee(@Valid @ModelAttribute EmployeeForm form, Model model) {
		Employee updateEmployee = employeeRepository.findById(form.getEmpId()).orElse(null);
		BeanUtils.copyProperties(form, updateEmployee, "empId");
		Department department = departmentRepository.findById(form.getDeptId()).orElse(null);
		updateEmployee.setDepartment(department);
		employeeRepository.save(updateEmployee);
		EmployeeBean employeeBean = new EmployeeBean();
		BeanUtils.copyProperties(updateEmployee, employeeBean);
		model.addAttribute("employeeBean", employeeBean);
		return "update/update_complete";
	}
}
