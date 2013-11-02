package org.db.techjam.dao;

import java.io.IOException;

import org.apache.hadoop.hbase.client.HTablePool;

public class UsersTool {

	public static final String usage = "UsersTool action ...\n"
			+ " help - print this message and exit.\n"
			+ " add user name email password" + " - add a new user.\n"
			+ " get user - retrieve a specific user.\n"
			+ " list - list all installed users.\n";

	public static void main(String[] args) throws IOException {
		if (args.length == 0 || "help".equals(args[0])) {
			System.out.println(usage);
			System.exit(0);
		}
		HTablePool pool = new HTablePool();
		UsersDAO dao = new UsersDAO(pool);
		if ("get".equals(args[0])) {
			System.out.println("Getting user " + args[1]);
			AUser u = dao.getUser(args[1]);
			System.out.println(u);
		}
		if ("add".equals(args[0])) {
			System.out.println("Adding user...");
			dao.addUser(args[1], args[2], args[3], args[4]);
			AUser u = dao.getUser(args[1]);
			System.out.println("Successfully added user " + u);
		}
		if ("list".equals(args[0])) {
			for (AUser u : dao.getUsers()) {
				System.out.println(u);
			}
		}
		pool.closeTablePool(UsersDAO.TABLE_NAME);
	}
}
