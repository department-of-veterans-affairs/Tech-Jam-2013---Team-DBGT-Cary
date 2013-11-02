package org.db.techjam.hbase.connector;

import org.apache.hadoop.hbase.client.HTablePool;

public class HTablePoolFactory {

	private static HTablePool htablePool = new HTablePool();

	public static HTablePool getPool() {
		return htablePool;
	}

}
