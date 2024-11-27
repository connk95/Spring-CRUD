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
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.repository.DepartmentRepository;
import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
public class DeleteController {
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	HttpSession session;

	@GetMapping("/delete/check")
	public String deleteEmployee(@RequestParam Integer empId, Model model) {
		model.addAttribute("user", session.getAttribute("user"));
		Employee checkEmployee = employeeRepository.findById(empId).orElse(null);
		EmployeeForm form = new EmployeeForm();
		BeanUtils.copyProperties(checkEmployee, form);
		model.addAttribute("EmployeeForm", form);
		model.addAttribute("checkEmployee", checkEmployee);
		return "delete/delete_check";
	}

	@PostMapping("/delete/complete")
	public String checkDeleteEmployee(@ModelAttribute EmployeeForm form, Model model) {
		employeeRepository.deleteById(form.getEmpId());
		return "delete/delete_complete";
	}
}
