package com.hrong.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hrong.conf.ConfigurationManager;
import com.hrong.conf.HbaseConfig;
import com.hrong.constant.ConfigConstant;
import com.hrong.core.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NavigableMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author hrong
 * @ClassName HbaseUtil
 * @Description
 * @Date 2019/5/20 20:22
 **/
@DependsOn("springContextHolder")
@Component
@Slf4j
public class HbaseUtil {

	private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-hh HH:mm:ss");
	private static Configuration conf = HBaseConfiguration.create();
	/**
	 * 设置连接池
	 */
	private static ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("pool-%d").build();

	private static ExecutorService pool = new ThreadPoolExecutor(10,
			15,
			1,
			TimeUnit.MINUTES,
			new LinkedBlockingQueue<>(15),
			factory);
	private static Connection connection = null;
	private static HbaseUtil instance = null;
	private static Admin admin = null;

	private HbaseUtil() {
		if (connection == null) {
			try {
				conf.set(ConfigConstant.ZK_URL, ConfigurationManager.getProperty(ConfigConstant.ZK_URL));
				conf.set(ConfigConstant.HBASE_DIR, ConfigurationManager.getProperty(ConfigConstant.HBASE_DIR));
				connection = ConnectionFactory.createConnection(conf, pool);
				admin = connection.getAdmin();
				log.error("{}获取到hbase连接", FORMAT.format(new Date()));
			} catch (IOException e) {
				log.error("HbaseUtils实例初始化失败！错误信息为：" + e.getMessage(), e);
			}
		}
	}

	public static synchronized HbaseUtil getInstance() {
		if (instance == null) {
			instance = new HbaseUtil();
		}
		return instance;
	}


	/**
	 * 创建表
	 *
	 * @param tableName    表名
	 * @param columnFamily 列族（数组）
	 */
	public void createTable(String tableName, String[] columnFamily) throws IOException {
		TableName name = TableName.valueOf(tableName);
		//如果存在则删除
		if (admin.tableExists(name)) {
			admin.disableTable(name);
			admin.deleteTable(name);
			log.warn("create htable warning! this table {} already exists! disable and drop the table first,now waiting for creating it", tableName);
		}
		HTableDescriptor desc = new HTableDescriptor(name);
		for (String cf : columnFamily) {
			desc.addFamily(new HColumnDescriptor(cf));
		}
		admin.createTable(desc);
		log.error("成功创建表:{},columnFamily:{}", tableName, Arrays.toString(columnFamily));
	}

	/**
	 * 插入记录（单行单列族-多列多值）
	 *
	 * @param tableName     表名
	 * @param row           行名
	 * @param columnFamilys 列族名
	 * @param columns       列名（数组）
	 * @param values        值（数组）（且需要和列一一对应）
	 */
	public void insertRecords(String tableName, String row, String columnFamilys, String[] columns, String[] values) throws IOException {
		TableName name = TableName.valueOf(tableName);
		Table table = connection.getTable(name);
		Put put = new Put(Bytes.toBytes(row));
		for (int i = 0; i < columns.length; i++) {
			put.addColumn(Bytes.toBytes(columnFamilys), Bytes.toBytes(columns[i]), Bytes.toBytes(values[i]));
			table.put(put);
		}
	}

	/**
	 * 插入记录（单行单列族-单列单值）
	 *
	 * @param tableName    表名
	 * @param row          行名
	 * @param columnFamily 列族名
	 * @param column       列名
	 * @param value        值
	 */
	public void insertOneRecord(String tableName, String row, String columnFamily, String column, String value) throws IOException {
		TableName name = TableName.valueOf(tableName);
		Table table = connection.getTable(name);
		Put put = new Put(Bytes.toBytes(row));
		put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
		table.put(put);
	}

	/**
	 * 删除一行记录
	 *
	 * @param tablename 表名
	 * @param rowkey    行名
	 */
	public void deleteRow(String tablename, String rowkey) throws IOException {
		TableName name = TableName.valueOf(tablename);
		Table table = connection.getTable(name);
		Delete d = new Delete(rowkey.getBytes());
		table.delete(d);
	}

	/**
	 * 删除单行单列族记录
	 *
	 * @param tablename    表名
	 * @param rowkey       行名
	 * @param columnFamily 列族名
	 */
	public void deleteColumnFamily(String tablename, String rowkey, String columnFamily) throws IOException {
		TableName name = TableName.valueOf(tablename);
		Table table = connection.getTable(name);
		Delete d = new Delete(rowkey.getBytes()).addFamily(Bytes.toBytes(columnFamily));
		table.delete(d);
	}

	/**
	 * 删除单行单列族单列记录
	 *
	 * @param tablename    表名
	 * @param rowkey       行名
	 * @param columnFamily 列族名
	 * @param column       列名
	 */
	public void deleteColumn(String tablename, String rowkey, String columnFamily, String column) throws IOException {
		TableName name = TableName.valueOf(tablename);
		Table table = connection.getTable(name);
		Delete d = new Delete(rowkey.getBytes()).addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
		table.delete(d);
	}


	/**
	 * 查找一行记录
	 *
	 * @param tablename 表名
	 * @param rowKey    行名
	 */
	public static String selectRow(String tablename, String rowKey) throws IOException {
		StringBuilder record = new StringBuilder();
		TableName name = TableName.valueOf(tablename);
		Table table = connection.getTable(name);
		Get g = new Get(rowKey.getBytes());
		Result rs = table.get(g);
		NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> map = rs.getMap();
		for (Cell cell : rs.rawCells()) {
			String str = transformCell2Str(cell);
			record.append(str);
		}
		return record.toString();
	}

	/**
	 * 查找单行单列族单列记录
	 *
	 * @param tablename    表名
	 * @param rowKey       行名
	 * @param columnFamily 列族名
	 * @param column       列名
	 * @return
	 */
	public static String selectValue(String tablename, String rowKey, String columnFamily, String column) throws IOException {
		TableName name = TableName.valueOf(tablename);
		Table table = connection.getTable(name);
		Get g = new Get(rowKey.getBytes());
		g.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
		Result rs = table.get(g);
		return Bytes.toString(rs.value());
	}

	/**
	 * 查询表中所有行（Scan方式）
	 *
	 * @param tablename
	 * @return
	 */
	public String scanAllRecord(String tablename) throws IOException {
		String record = "";
		TableName name = TableName.valueOf(tablename);
		Table table = connection.getTable(name);
		Scan scan = new Scan();
		ResultScanner scanner = table.getScanner(scan);
		try {
			for (Result result : scanner) {
				for (Cell cell : result.rawCells()) {
					String str = transformCell2Str(cell);
					record += str;
				}
			}
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}

		return record;
	}

	/**
	 * 根据rowkey关键字查询报告记录
	 *
	 * @param tablename
	 * @param rowKeyword
	 * @return
	 */
	public List scanReportDataByRowKeyword(String tablename, String rowKeyword) throws IOException {
		ArrayList<String> list = new ArrayList<>();

		Table table = connection.getTable(TableName.valueOf(tablename));
		Scan scan = new Scan();

		//添加行键过滤器，根据关键字匹配
		RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator(rowKeyword));
		scan.setFilter(rowFilter);
		ResultScanner scanner = table.getScanner(scan);
		try {
			for (Result result : scanner) {
				list.add(null);
			}
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}

		return list;
	}

	/**
	 * 根据rowkey关键字和时间戳范围查询报告记录
	 *
	 * @param tablename
	 * @param rowKeyword
	 * @return
	 */
	public List scanReportDataByRowKeywordTimestamp(String tablename, String rowKeyword, Long minStamp, Long maxStamp) throws IOException {
		ArrayList<String> list = new ArrayList<>();

		Table table = connection.getTable(TableName.valueOf(tablename));
		Scan scan = new Scan();
		//添加scan的时间范围
		scan.setTimeRange(minStamp, maxStamp);

		RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator(rowKeyword));
		scan.setFilter(rowFilter);

		ResultScanner scanner = table.getScanner(scan);
		try {
			for (Result result : scanner) {
				list.add(null);
			}
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}

		return list;
	}


	/**
	 * 删除表操作
	 *
	 * @param tablename
	 */
	public void deleteTable(String tablename) throws IOException {
		TableName name = TableName.valueOf(tablename);
		if (admin.tableExists(name)) {
			admin.disableTable(name);
			admin.deleteTable(name);
		}
	}

	/**
	 * 利用协处理器进行全表count统计
	 *
	 * @param tablename
	 */
	public Long countRowsWithCoprocessor(String tablename) throws Throwable {
		TableName name = TableName.valueOf(tablename);
		HTableDescriptor descriptor = admin.getTableDescriptor(name);

		String coprocessorClass = "org.apache.hadoop.hbase.coprocessor.AggregateImplementation";
		if (!descriptor.hasCoprocessor(coprocessorClass)) {
			admin.disableTable(name);
			descriptor.addCoprocessor(coprocessorClass);
			admin.modifyTable(name, descriptor);
			admin.enableTable(name);
		}

		//计时
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Scan scan = new Scan();
		AggregationClient aggregationClient = new AggregationClient(conf);

		Long count = aggregationClient.rowCount(name, new LongColumnInterpreter(), scan);

		stopWatch.stop();
		System.out.println("RowCount：" + count + "，全表count统计耗时：" + stopWatch.getTotalTimeMillis());

		return count;
	}

	private static String transformCell2Str(Cell cell) {
		return Bytes.toString(CellUtil.cloneRow(cell)) + "\t" +
				Bytes.toString(CellUtil.cloneFamily(cell)) + "\t" +
				Bytes.toString(CellUtil.cloneQualifier(cell)) + "\t" +
				Bytes.toString(CellUtil.cloneValue(cell)) + "\n";
	}

	private void close() {
		try {
			if (admin != null) {
				admin.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		HbaseUtil hbaseUtil = getInstance();
		List student = hbaseUtil.scanReportDataByRowKeyword("student", "1");
		hbaseUtil.close();
	}

}

