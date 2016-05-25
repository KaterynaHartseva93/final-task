package ua.nure.hartseva.SummaryTask4.service.impl;

import ua.nure.hartseva.SummaryTask4.dao.IUserStatusDao;
import ua.nure.hartseva.SummaryTask4.entity.Status;
import ua.nure.hartseva.SummaryTask4.service.IUserStatusService;

public class UserStatusService implements IUserStatusService{

	private final IUserStatusDao userStatusDao;
	
	public UserStatusService(IUserStatusDao userStatusDao) {
		this.userStatusDao = userStatusDao;
	}
	
	@Override
	public boolean isBlocked(int userId) {
		return userStatusDao.isBlocked(userId);
	}

	@Override
	public void changeUserStatus(final int userId, final Status newStatus) {
		userStatusDao.changeUserStatus(userId, newStatus);
	}
}
