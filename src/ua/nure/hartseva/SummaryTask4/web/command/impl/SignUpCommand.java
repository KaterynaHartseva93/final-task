package ua.nure.hartseva.SummaryTask4.web.command.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.Path;
import ua.nure.hartseva.SummaryTask4.bean.RegistrationFormBean;
import ua.nure.hartseva.SummaryTask4.entity.Gender;
import ua.nure.hartseva.SummaryTask4.entity.Role;
import ua.nure.hartseva.SummaryTask4.entity.Status;
import ua.nure.hartseva.SummaryTask4.entity.User;
import ua.nure.hartseva.SummaryTask4.service.IUserService;
import ua.nure.hartseva.SummaryTask4.service.IUserStatusService;
import ua.nure.hartseva.SummaryTask4.util.PasswordEncoder;
import ua.nure.hartseva.SummaryTask4.web.Transfer;
import ua.nure.hartseva.SummaryTask4.web.command.Command;
import ua.nure.hartseva.SummaryTask4.web.validation.RegistrationFormBeanValidator;

public class SignUpCommand extends Command{

	private static final long serialVersionUID = 818733048142970989L;

	@Override
	public Transfer execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		
		RegistrationFormBean formBean = getRegistrationFormBeanFromRequest(request);
		RegistrationFormBeanValidator.validate(formBean);
		
		ServletContext context = request.getServletContext();
		IUserService userService = (IUserService) context.getAttribute(Attributes.USER_SERVICE);
		IUserStatusService userStatusService = (IUserStatusService) context.getAttribute(Attributes.USER_STATUS_SERVICE);
		
		if (userService.doesUserExist(formBean.getEmail())) {
			session.setAttribute(Attributes.PAGE_ERROR_MESSAGE, "User with provided email already exists.");
			return Transfer.redirect(response, Path.COMMAND_MAIN);
		}
		
		User user = getUserFromRegistrationFormBean(formBean);
		int userId = userService.save(user);
		userStatusService.changeUserStatus(userId, Status.ACTIVE);
		
		session.setAttribute(Attributes.PAGE_SUCCESS_MESSAGE, "You have been successfully registered.");
		return Transfer.redirect(response, Path.COMMAND_MAIN);
	}

	@Override
	protected List<Role> allowedRoles() {
		return Collections.singletonList(Role.GUEST);
	}
	
	private RegistrationFormBean getRegistrationFormBeanFromRequest(HttpServletRequest request) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String repeatPassword = request.getParameter("repeatPassword");
		String genderString = request.getParameter("gender");
		
		RegistrationFormBean bean = new RegistrationFormBean();
		bean.setFirstName(firstName);
		bean.setLastName(lastName);
		bean.setEmail(email);
		bean.setPassword(password);
		bean.setConfirmPassword(repeatPassword);
		bean.setGender(genderString);
		return bean;
	}
	
	private User getUserFromRegistrationFormBean(RegistrationFormBean formBean) {
		User user = new User();
		user.setEmail(formBean.getEmail());
		user.setFirstName(formBean.getFirstName());
		user.setLastName(formBean.getLastName());
		user.setPassword(PasswordEncoder.encode(formBean.getPassword()));
		user.setStatus(Status.ACTIVE);
		user.setRole(Role.CLIENT);
		user.setGender(Gender.fromValue(formBean.getGender()));
		return user;
	}
}
