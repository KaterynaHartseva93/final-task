package ua.nure.hartseva.SummaryTask4.service;

import java.util.List;

import ua.nure.hartseva.SummaryTask4.entity.User;

public interface IUserService {

	User getUser(String email, String password);
	
	List<User> getAllUsersExceptAdmin(int adminId);
	
	void changeUserStatus(int userId, String newStatus);

	boolean doesUserExist(String email);

	int save(User user);
}
