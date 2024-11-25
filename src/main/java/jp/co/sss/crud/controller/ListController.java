package jp.co.sss.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
public class ListController {
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	HttpSession session;

	@GetMapping("/list")
	public String showEmployeeList(Model model) {
		model.addAttribute("employees", employeeRepository.findAllWithDepartment());
		model.addAttribute("user", session.getAttribute("user"));
		return "list/list";
	}

	@GetMapping("/list/empName")
	public String searchByName(@RequestParam String empName, Model model) {
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("employees", employeeRepository.findByEmpName(empName.trim()));
		return "list/list";
	}

	@GetMapping("/list/deptId")
	public String searchByDeptId(@RequestParam Integer deptId, Model model) {
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("employees", employeeRepository.findAllByDeptId(deptId));
		return "list/list";
	}

}
