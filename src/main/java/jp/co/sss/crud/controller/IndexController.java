package jp.co.sss.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.LoginForm;
import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
public class IndexController {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	HttpSession session;

	@GetMapping("/")
	public String index(@ModelAttribute LoginForm loginForm) {
		session.invalidate();
		return "index";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute LoginForm form, BindingResult result, HttpSession session) {
	    if (result.hasErrors()) {
	        return "index";
	    }

	    Employee employee = employeeRepository.findById(form.getEmpId()).orElse(null);
	    if (employee == null || !employee.getEmpPass().trim().equals(form.getEmpPass().trim())) {
	        result.rejectValue("empPass", "error.login", "社員ID、またはパスワードが間違っています。");
	        return "index";
	    }

	    session.setAttribute("empId", form.getEmpId());
	    session.setAttribute("user", employee);
	    return "redirect:/list";
	}


	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

}
