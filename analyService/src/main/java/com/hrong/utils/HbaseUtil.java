package com.hrong.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.regionserver.HRegionServer;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.regionserver.HRegionServer;
import org.apache.hadoop.hbase.util.Bytes;
import sun.misc.Cleaner;

import java.io.IOException;

/**
 * @Author hrong
 * @ClassName HbaseUtil
 * @Description
 * @Date 2019/5/20 12:28
 **/
public class HbaseUtil {
	private static Connection connection;
	public static HRegionServer server;
	private static HbaseUtil hbaseUtil = null;
	private static Admin admin;

	private HbaseUtil() {
		Configuration configuration = new Configuration();
		configuration.set("hbase.zookeeper.quorum", "s201:2181,s202:2181,s203:2181");
		configuration.set("hbase.rootdir", "hdfs://s201:8020/hbase");
		try {
			connection = ConnectionFactory.createConnection(configuration);
			admin = connection.getAdmin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized HbaseUtil getInstance() {
		if (hbaseUtil == null) {
			hbaseUtil = new HbaseUtil();
		}
		return hbaseUtil;
	}

	/**
	 * 根据表名获取table实例
	 */
	public Table getTaleByName(String tableName) {
		Table table = null;
		try {
			table = connection.getTable(TableName.valueOf(tableName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return table;
	}

	public void listTables() {
		try {
			HTableDescriptor[] tables = admin.listTables();
			for (HTableDescriptor table : tables) {
				String name = new String(table.getName());
				System.out.println(name);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<String, Long> query(String tableName, String condition) {
		Table table = getInstance().getTaleByName(tableName);
		PrefixFilter filter = new PrefixFilter(condition.getBytes());
		Map<String, Long> res = new HashMap<>();
		String family = "cf";
		String qualifier = "";
		try {
			ResultScanner scanner = table.getScanner(new Scan().setFilter(filter));
			for (Result result : scanner) {
				String row = new String(result.getRow());
				result.getValue(family.getBytes(), qualifier.getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public void createNameSpace(String nameSpace) throws Exception {
		admin.createNamespace(NamespaceDescriptor.create(nameSpace).build());
		System.out.println("命名空间创建成功");
//		HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf("testNameSpace:testTable"));
//		descriptor.setDurability(Durability.SYNC_WAL);
//		HColumnDescriptor columnDescriptor = new HColumnDescriptor("cf");
//		descriptor.addFamily(columnDescriptor);
//		admin.createTable(descriptor);
	}

	public void deleteTable(String tableName) {
		try {
			boolean exists = admin.tableExists(TableName.valueOf(tableName));
			if (exists) {
				admin.disableTable(TableName.valueOf(tableName));
				admin.deleteTable(TableName.valueOf(tableName));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showDatas(String tableName) {
		Table table = getInstance().getTaleByName(tableName);
		try {
			Scan scan = new Scan();
			ResultScanner scanner = table.getScanner(scan);
			for (Result result : scanner) {
				String row = Bytes.toString(result.getRow());
				String value = Bytes.toString(result.getValue("cf".getBytes(), "1".getBytes()));
				String value2 = Bytes.toString(result.getValue("cf".getBytes(), "2".getBytes()));
				System.out.println(row+"*********"+value+"**********"+value2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void alterColumnFamily(String tableName) {
		try {
			if (!admin.tableExists(TableName.valueOf(tableName))) {
				return;
			}
			admin.disableTable(TableName.valueOf(tableName));
			HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));
			Collection<HColumnDescriptor> families = tableDescriptor.getFamilies();
			tableDescriptor.removeFamily(Bytes.toBytes("newcf".toString()));
//			tableDescriptor.addFamily(new HColumnDescriptor("newcf"));
			admin.modifyTable(TableName.valueOf(tableName), tableDescriptor);
			admin.enableTable(TableName.valueOf(tableName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void put(String tableName) {
		Table table = getInstance().getTaleByName(tableName);
		Put put = new Put("100".getBytes());
		Cell cell = CellUtil.createCell("100".getBytes(), "cf".getBytes(), "7".getBytes());
		try {
			put.add(cell);
			table.put(put);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void delete(String tableName) {
		Table table = getInstance().getTaleByName(tableName);
		Delete delete = new Delete("row2".getBytes());
		delete.addColumns("cf".getBytes(),"2".getBytes());
		try {
			table.delete(delete);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void get(String tableName) {
		Table table = getInstance().getTaleByName(tableName);
		Get get = new Get("row1".getBytes());
		get.addFamily("cf".getBytes());
		try {
			Result result = table.get(get);
			Cell[] cells = result.rawCells();
			for (Cell cell : cells) {
				String value = Bytes.toString(cell.getValue());
				String row = Bytes.toString(cell.getRow());
				String qualifier = Bytes.toString(cell.getQualifier());
				System.out.println(value+":"+row+":"+qualifier);

			}
			table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public static void close(){
		try {
			if (connection!=null) {
				connection.close();
			}
			if (admin!=null) {
				admin.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
		HbaseUtil hbaseUtil = new HbaseUtil();
		String name = "testNameSpace:testTable";
		hbaseUtil.createNameSpace("testNameSpace");
//		hbaseUtil.listTables();
//		hbaseUtil.showDatas(name);
//		hbaseUtil.alterColumnFamily(name);
//		hbaseUtil.put(name);
//		hbaseUtil.delete(name);
//		hbaseUtil.get(name);
//		admin.close();
//		connection.close();
		close();
	}
}
