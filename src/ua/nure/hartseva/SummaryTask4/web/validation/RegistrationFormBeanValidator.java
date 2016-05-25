package ua.nure.hartseva.SummaryTask4.web.validation;

import org.apache.log4j.Logger;

import ua.nure.hartseva.SummaryTask4.bean.RegistrationFormBean;
import ua.nure.hartseva.SummaryTask4.entity.Gender;
import ua.nure.hartseva.SummaryTask4.exception.WebBadRequestException;

public class RegistrationFormBeanValidator {

	private static final Logger LOG = Logger.getLogger(RegistrationFormBeanValidator.class);
	
	private static final String NAME_REGEX = "^([a-zA-Zà-ÿÀ-ß]{3,30})$";
	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PASSWORD_REGEX = "^([a-zA-Z0-9_-]{6,30})$";
	
	public static void validate(RegistrationFormBean bean) {
		
		// Validate first and last names
		String firstName = bean.getFirstName();
		String lastName = bean.getLastName();
		if (firstName == null || !firstName.matches(NAME_REGEX)) {
			throw new WebBadRequestException("First name is incorrect.");
		}
		if (lastName == null || !lastName.matches(NAME_REGEX)) {
			throw new WebBadRequestException("Last name is incorrect.");
		}
		
		// Validate email
		String email = bean.getEmail();
		if (email == null || !email.matches(EMAIL_REGEX)) {
			throw new WebBadRequestException("Email is incorrect.");
		}
		
		// Validate gender
		String genderString = bean.getGender();
		LOG.info("Gender string is --> " + genderString);
		Gender gender = Gender.fromValue(genderString);
		if (gender == null) {
			throw new WebBadRequestException("Gender is incorrect.");
		}
		
		// Validate passwords
		String password = bean.getPassword();
		String confirmPassword = bean.getConfirmPassword();
		if (password == null || !password.matches(PASSWORD_REGEX)) {
			throw new WebBadRequestException("Password is incorrect.");
		}
		if (!password.equals(confirmPassword)) {
			throw new WebBadRequestException("Passwords are not equal.");
		}
	}
}
