package ua.nure.hartseva.SummaryTask4.service;

import ua.nure.hartseva.SummaryTask4.entity.Status;

public interface IUserStatusService {

	boolean isBlocked(int userId);
	
	void changeUserStatus(int userId, Status newStatus);
}
