package ua.nure.hartseva.SummaryTask4.service.impl;

import java.sql.SQLException;
import java.util.List;

import ua.nure.hartseva.SummaryTask4.bean.ITransactedOperation;
import ua.nure.hartseva.SummaryTask4.dao.IUsersDao;
import ua.nure.hartseva.SummaryTask4.entity.User;
import ua.nure.hartseva.SummaryTask4.service.BaseService;
import ua.nure.hartseva.SummaryTask4.service.ITransactionProvider;
import ua.nure.hartseva.SummaryTask4.service.IUserService;

public class UserService extends BaseService implements IUserService {

	private final IUsersDao usersDao;

	public UserService(ITransactionProvider transactionProvider, IUsersDao usersDao) {
		super(transactionProvider);
		this.usersDao = usersDao;
	}

	@Override
	public User getUser(final String email, final String password) {
		return transactionProvider.execute(new ITransactedOperation<User>() {
			@Override
			public User execute() throws SQLException  {
				return usersDao.getUserByEmailAndPassword(email, password);
			}
		});
	}

	@Override
	public List<User> getAllUsersExceptAdmin(final int adminId) {
		return transactionProvider.execute(new ITransactedOperation<List<User>>() {
			@Override
			public List<User> execute() throws SQLException  {
				return usersDao.findAllUsersExceptAdmin(adminId);
			}
		});
	}

	@Override
	public void changeUserStatus(final int userId, final String newStatus) {
		transactionProvider.execute(new ITransactedOperation<Void>() {
			@Override
			public Void execute() throws SQLException {
				usersDao.changeUserStatus(userId, newStatus);
				return null;
			}
		});
	}

	public boolean doesUserExist(final String email) {
		return transactionProvider.execute(new ITransactedOperation<Boolean>() {
			@Override
			public Boolean execute() throws SQLException  {
				return usersDao.doesUserExist(email);
			}
		});
	}

	@Override
	public int save(final User user) {
		return transactionProvider.execute(new ITransactedOperation<Integer>() {
			@Override
			public Integer execute() throws SQLException  {
				return usersDao.save(user);
			}
		});
	}
}
