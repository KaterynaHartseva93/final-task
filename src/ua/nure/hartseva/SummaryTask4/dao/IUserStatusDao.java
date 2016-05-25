package ua.nure.hartseva.SummaryTask4.dao;

import ua.nure.hartseva.SummaryTask4.entity.Status;

public interface IUserStatusDao {
	
	boolean isBlocked(int userId);

	void changeUserStatus(int userId, Status newStatus);
}
