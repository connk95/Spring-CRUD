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
import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.repository.DepartmentRepository;
import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
public class RegistrationController {
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	HttpSession session;

	@GetMapping("regist/input")
	public String registerEmployee(Model model) {
		model.addAttribute("EmployeeForm", new EmployeeForm());
		model.addAttribute("departments", departmentRepository.findAll());
		model.addAttribute("user", session.getAttribute("user"));
		return "regist/regist_input";
	}

	@PostMapping("regist/check")
	public String doRegisterEmployee(@Valid @ModelAttribute EmployeeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("EmployeeForm", form);
	        model.addAttribute("org.springframework.validation.BindingResult.EmployeeForm", result);
			return "regist/regist_input";
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
			return "regist/regist_check";
		}
	}

	@PostMapping("regist/input")
	public String redoRegisterEmployee(@Valid @ModelAttribute EmployeeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("EmployeeForm", form);
	        model.addAttribute("org.springframework.validation.BindingResult.EmployeeForm", result);
			return "regist/regist_input";
		} else {
			model.addAttribute("user", session.getAttribute("user"));
			Employee checkEmployee = new Employee();
			BeanUtils.copyProperties(form, checkEmployee);
			Department department = departmentRepository.findById(form.getDeptId()).orElse(null);
			checkEmployee.setDepartment(department);
			model.addAttribute("EmployeeForm", form);
			return "regist/regist_input";
		}
	}

	@PostMapping("/regist/complete")
	@Transactional
	public String checkRegisterEmployee(@Valid @ModelAttribute EmployeeForm form, Model model) {
		Employee newEmployee = new Employee();
		BeanUtils.copyProperties(form, newEmployee);
		Department department = departmentRepository.findById(form.getDeptId()).orElse(null);
		newEmployee.setDepartment(department);
		newEmployee = employeeRepository.save(newEmployee);
		EmployeeBean employeeBean = new EmployeeBean();
		BeanUtils.copyProperties(newEmployee, employeeBean);
		model.addAttribute("employeeBean", employeeBean);
		return "regist/regist_complete";
	}

}
