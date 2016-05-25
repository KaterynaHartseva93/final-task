package ua.nure.hartseva.SummaryTask4.web.command.impl;

import org.apache.log4j.Logger;

import ua.nure.hartseva.SummaryTask4.Method;
import ua.nure.hartseva.SummaryTask4.Path;
import ua.nure.hartseva.SummaryTask4.web.command.Command;
import ua.nure.hartseva.SummaryTask4.web.command.CommandMap;
import ua.nure.hartseva.SummaryTask4.web.command.NoCommand;

public class CommandContainer {

	private static final Logger LOG = Logger.getLogger(CommandContainer.class);

	private static final CommandMap COMMANDS_MAP = new CommandMap();
	private static final Command NO_COMMAND = new NoCommand();

	static {
		// Main page
		COMMANDS_MAP.put(Method.GET, "", new MainPageCommand());
		COMMANDS_MAP.put(Method.GET, Path.COMMAND_MAIN, new MainPageCommand());
		// Clothes
		COMMANDS_MAP.put(Method.GET, Path.COMMAND_CLOTHES, new ClothesCommand());
		// Users
		COMMANDS_MAP.put(Method.GET, Path.COMMAND_USERS, new UsersCommand());
		COMMANDS_MAP.put(Method.POST, Path.COMMAND_USERS, new ChangeUserStatusCommand());

		COMMANDS_MAP.put(Method.GET, "change-order-status", new ChangeOrderStatusCommand());
		// Authorization
		COMMANDS_MAP.put(Method.GET, Path.COMMAND_SIGN_OUT, new SignOutCommand());
		COMMANDS_MAP.put(Method.POST, Path.COMMAND_SIGN_IN, new SignInCommand());
		COMMANDS_MAP.put(Method.POST, Path.COMMAND_SIGN_UP, new SignUpCommand());

		// Cart
		COMMANDS_MAP.put(Method.GET, Path.COMMAND_CART, new CartCommand());
		COMMANDS_MAP.put(Method.GET, Path.COMMAND_CHECKOUT, new CheckoutCommand());

		// Orders
		COMMANDS_MAP.put(Method.GET, Path.COMMAND_ORDERS, new OrdersCommand());
		COMMANDS_MAP.put(Method.GET, Path.COMMAND_MAKE_ORDER, new MakeOrderCommand());

		// Only admin
		COMMANDS_MAP.put(Method.POST, Path.COMMAND_EDIT_PRODUCT, new EditProductCommand());
		COMMANDS_MAP.put(Method.POST, Path.COMMAND_ADD_PRODUCT, new CreateProductCommand());
		COMMANDS_MAP.put(Method.GET, Path.COMMAND_DELETE_PRODUCT, new DeleteProductCommand());

		COMMANDS_MAP.put(Method.GET, Path.COMMAND_CHANGE_LOCALE, new ChangeLocaleCommand());

		LOG.debug("Command container was successfully initialized");
		LOG.trace("Number of commands --> " + COMMANDS_MAP.size());
	}

	/**
	 * Returns command object with the given name.
	 * 
	 * @param commandName
	 *            Name of the command.
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !COMMANDS_MAP.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return NO_COMMAND;
		}
		return COMMANDS_MAP.getByKey(commandName);
	}
}