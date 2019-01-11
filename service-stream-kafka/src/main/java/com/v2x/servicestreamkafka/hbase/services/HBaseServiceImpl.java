package com.v2x.servicestreamkafka.hbase.services;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class HBaseServiceImpl implements IHBaseService {

    private static final Logger LOG = LoggerFactory.getLogger(HBaseServiceImpl.class);

    @Autowired
    private Configuration configuration;

    Connection connection = null;

    long idTimestamp = System.currentTimeMillis();

    public boolean checkConnection() {
        if (connection == null || connection.isClosed()) {
            try {
                connection = ConnectionFactory.createConnection(configuration);
                return true;
            } catch (IOException e) {
                LOG.error("[" + idTimestamp + "]" + e.getMessage(), e);
                return false;
            }
        }
        return true;
    }

    @Override
    public void createTable(String tableName, String... families) {
        if (!checkConnection()) {
            return;
        }
        try (Admin admin = connection.getAdmin()) {
            //-----HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
            //creating table descriptor
            TableDescriptorBuilder.ModifyableTableDescriptor table = new TableDescriptorBuilder.ModifyableTableDescriptor(TableName.valueOf(tableName));
            for (String family : families) {
                //-----tableDescriptor.addFamily(new HColumnDescriptor(family));
                //creating column family descriptor
                ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor familyAdd = new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor(Bytes.toBytes(family));
                //adding coloumn family to HTable
                table.setColumnFamily(familyAdd);
            }

            if (admin.tableExists(TableName.valueOf(tableName))) {
                System.out.println("Table Exists");
                LOG.info("[" + idTimestamp + "]Table:[" + tableName + "] Exists");
            } else {
                admin.createTable(table);
                //-----admin.createTable(tableDescriptor);
                LOG.info("[" + idTimestamp + "]Create table Successfully!!!Table Name:[" + tableName + "]");
            }
        } catch (IOException e) {
            LOG.error("[" + idTimestamp + "]" + e.getMessage(), e);
        }
    }

    @Override
    public void deleteTable(String tableName) {
        if (!checkConnection()) {
            return;
        }
        try (Admin admin = connection.getAdmin()) {
            TableName table = TableName.valueOf(tableName);
            if (!admin.tableExists(TableName.valueOf(tableName))) {
                LOG.info("[" + idTimestamp + "]" + tableName + " is not existed. Delete failed!");
                return;
            }
            admin.disableTable(table);
            admin.deleteTable(table);
            LOG.info("[" + idTimestamp + "]delete table " + tableName + " successfully!");
        } catch (IOException e) {
            LOG.error("[" + idTimestamp + "]" + e.getMessage(), e);
        }
    }

    @Override
    public void putRowValue(String tableName, String rowKey, String family, String qualifier, String value) {
        if (!checkConnection()) {
            return;
        }
        try (Table table = connection.getTable(TableName.valueOf(tableName))) {
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
            table.put(put);
            if (LOG.isDebugEnabled())
                LOG.debug("[" + idTimestamp + "]update table:" + tableName + ",rowKey:" + rowKey + ",family:" + family + ",qualifier:" + qualifier + ",value:" + value + " successfully!");
        } catch (IOException e) {
            LOG.error("[" + idTimestamp + "]" + e.getMessage(), e);
        }
    }

    @Override
    public void putRowValueBatch(String tableName, String rowKey, String family, List<String> qualifiers, List<String> values) {
        if (!checkConnection()) {
            return;
        }
        try (Table table = connection.getTable(TableName.valueOf(tableName))) {
            Put put = new Put(Bytes.toBytes(rowKey));
            for (int j = 0; j < qualifiers.size(); j++) {
                put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifiers.get(j)), Bytes.toBytes(values.get(j)));
            }
            table.put(put);
            if (LOG.isDebugEnabled())
                LOG.debug("[" + idTimestamp + "]update table:" + tableName + ",rowKey:" + rowKey + ",family:" + family + ",qualifiers:" + qualifiers + ",values:" + values + " successfully!");

        } catch (IOException e) {
            LOG.error("[" + idTimestamp + "]" + e.getMessage(), e);
        }
    }

    @Override
    public void putRowValueBatch(String tableName, String rowKey, String family, Map<String, String> qualifierValues) {
        if (!checkConnection()) {
            return;
        }
        try (Table table = connection.getTable(TableName.valueOf(tableName))) {
            Put put = new Put(Bytes.toBytes(rowKey));
            for (Map.Entry<String, String> entry : qualifierValues.entrySet()) {
                put.addColumn(Bytes.toBytes(family), Bytes.toBytes(entry.getKey()), Bytes.toBytes(entry.getValue()));
            }
            table.put(put);
            if (LOG.isDebugEnabled())
                LOG.debug("[" + idTimestamp + "]update table:" + tableName + ",rowKey:" + rowKey + " successfully!");

        } catch (IOException e) {
            LOG.error("[" + idTimestamp + "]" + e.getMessage(), e);
        }
    }

    @Override
    public List<Cell> scanRegexRowKey(String tableName, String regexKey) {
        if (!checkConnection()) {
            return null;
        }
        try (Table table = connection.getTable(TableName.valueOf(tableName))) {
            Scan scan = new Scan();
            //扫描指定列簇
            //scan.addFamily(Bytes.toBytes("personal"));
            //扫描指定列
            //scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("city"));
            Filter filter = new RowFilter(CompareOperator.EQUAL, new RegexStringComparator(regexKey));
            scan.setFilter(filter);
            ResultScanner rs = table.getScanner(scan);
            for (Result r : rs) {
                return r.listCells();
            }
        } catch (IOException e) {
            LOG.error("[" + idTimestamp + "]" + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void deleteAllColumn(String tableName, String rowKey) {
        if (!checkConnection()) {
            return;
        }
        try (Table table = connection.getTable(TableName.valueOf(tableName))) {
            Delete delAllColumn = new Delete(Bytes.toBytes(rowKey));
            table.delete(delAllColumn);
            LOG.info("[" + idTimestamp + "]Delete rowKey:" + rowKey + "'s all Columns Successfully");
        } catch (IOException e) {
            LOG.error("[" + idTimestamp + "]" + e.getMessage(), e);
        }
    }

    @Override
    public void deleteColumn(String tableName, String rowKey, String family, String qualifier) {
        if (!checkConnection()) {
            return;
        }
        try (Table table = connection.getTable(TableName.valueOf(tableName))) {
            Delete delColumn = new Delete(Bytes.toBytes(rowKey));
            delColumn.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
            table.delete(delColumn);
            LOG.info("[" + idTimestamp + "]Delete rowKey:" + rowKey + "'s qualifier:" + qualifier + " Successfully");
        } catch (IOException e) {
            LOG.error("[" + idTimestamp + "]" + e.getMessage(), e);
        }
    }
}