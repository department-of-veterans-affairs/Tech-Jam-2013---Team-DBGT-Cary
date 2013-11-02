package org.db.techjam.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class UsersDAO {

	public static final byte[] TABLE_NAME = Bytes.toBytes("users");
	public static final byte[] INFO_FAM = Bytes.toBytes("info");
	private static final byte[] USER_COL = Bytes.toBytes("user");
	private static final byte[] NAME_COL = Bytes.toBytes("name");
	private static final byte[] EMAIL_COL = Bytes.toBytes("email");
	private static final byte[] PASS_COL = Bytes.toBytes("password");
	public static final byte[] TWEETS_COL = Bytes.toBytes("tweet_count");

	private HTablePool pool;

	public UsersDAO(HTablePool pool) {
		this.pool = pool;
	}

	private static Get mkGet(String user) {
		Get g = new Get(Bytes.toBytes(user));
		g.addFamily(INFO_FAM);
		return g;
	}

	private static Put mkPut(User u) {
		Put p = new Put(Bytes.toBytes(u.user));
		p.add(INFO_FAM, USER_COL, Bytes.toBytes(u.user));
		p.add(INFO_FAM, NAME_COL, Bytes.toBytes(u.name));
		p.add(INFO_FAM, EMAIL_COL, Bytes.toBytes(u.email));
		p.add(INFO_FAM, PASS_COL, Bytes.toBytes(u.password));
		return p;
	}

	private static Delete mkDel(String user) {
		Delete d = new Delete(Bytes.toBytes(user));
		return d;
	}

	public void addUser(String user, String name, String email, String password)
			throws IOException {
		HTableInterface users = pool.getTable(TABLE_NAME);
		Put p = mkPut(new User(user, name, email, password));
		users.put(p);
		users.close();
	}

	public User getUser(String user) throws IOException {
		HTableInterface users = pool.getTable(TABLE_NAME);
		Get g = mkGet(user);
		Result result = users.get(g);
		if (result.isEmpty()) {
			return null;
		}
		User u = new User(result);
		users.close();
		return u;
	}

	public void deleteUser(String user) throws IOException {
		HTableInterface users = pool.getTable(TABLE_NAME);
		Delete d = mkDel(user);
		users.delete(d);
		users.close();
	}

	private static class User extends AUser {

		private User(Result r) {
			this(r.getValue(INFO_FAM, USER_COL),
					r.getValue(INFO_FAM, NAME_COL), r.getValue(INFO_FAM,
							EMAIL_COL), r.getValue(INFO_FAM, PASS_COL), r
							.getValue(INFO_FAM, TWEETS_COL) == null ? Bytes
							.toBytes(0L) : r.getValue(INFO_FAM, TWEETS_COL));
		}

		private User(byte[] user, byte[] name, byte[] email, byte[] password,
				byte[] tweetCount) {
			this(Bytes.toString(user), Bytes.toString(name), Bytes
					.toString(email), Bytes.toString(password));
			this.tweetCount = Bytes.toLong(tweetCount);
		}

		private User(String user, String name, String email, String password) {
			this.user = user;
			this.name = name;
			this.email = email;
			this.password = password;
		}
	}

	private static Scan mkScan() {
		Scan s = new Scan();
		s.addFamily(INFO_FAM);
		return s;
	}

	public List<AUser> getUsers() throws IOException {
		HTableInterface users = pool.getTable(TABLE_NAME);

		ResultScanner results = users.getScanner(mkScan());
		ArrayList<AUser> ret = new ArrayList<AUser>();
		for (Result r : results) {
			ret.add(new User(r));
		}

		users.close();
		return ret;
	}
}
