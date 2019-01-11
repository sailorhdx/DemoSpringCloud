package com.v2x.servicestreamkafka.kafka;

import com.v2x.servicestreamkafka.hbase.services.IHBaseService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@EnableBinding(BSMChannel.class)
public class KafkaReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaReceiver.class);


    /**
     * 接收消息存储Queue列表，至少一个Queue
     */
    private ConcurrentHashMap<Integer, LinkedBlockingQueue<String>> queueRecvList = new ConcurrentHashMap<Integer, LinkedBlockingQueue<String>>();
    /**
     * 多线程解码时，存储解码线程对象列表，当threadCount>1时创建
     */
    private List<ReceiverProcessThread> receiverProcessThreadList = null;

    private int threadCount = 2;
    private int queueRecvSize = 10240;


    @Autowired
    IHBaseService ihBaseService;

    /**
     * 线程索引号，每接到一条消息依次放入不同索引号的队列中
     */
    private int threadQueueIdx = 0;

    public KafkaReceiver() {
        LOG.info("KafkaReceiver initial ......");
        this.receiverProcessThreadList = new ArrayList<ReceiverProcessThread>();
        for (int i = 0; i < this.threadCount; i++) {
            // 创建消息接收队列列表
            LinkedBlockingQueue<String> queueRecv = new LinkedBlockingQueue<String>(queueRecvSize);
            this.queueRecvList.put(i, queueRecv);

            ReceiverProcessThread receiverProcessThread = new ReceiverProcessThread(i);
            receiverProcessThread.setName("Thread-KafkaReceiver-Processor-" + i);
            receiverProcessThread.start();
            this.receiverProcessThreadList.add(receiverProcessThread);
        }
    }

    @StreamListener(BSMChannel.input_bsm)
    private void receive(String msg) {
        LOG.info("receive message : " + msg);
        try {
            ihBaseService.createTable("TestTable02", "info");
            ihBaseService.putRowValue("TestTable02", "" + System.currentTimeMillis(), "info", "record0", msg);

            int flagCount = queueRecvList.size();// 控制循环次数，每条数据最多对队列列表中的每个队列尝试一次，如果都失败则丢弃
            while (flagCount > 0) { // 如果当前索引号的队列满了，就循环到下一个索引号的队列插入，如果都满了则会导致阻塞
                if (threadQueueIdx >= queueRecvList.size()) {
                    threadQueueIdx = 0;
                } // if (this.threadQueueIdx >= queueRecvList.size()) {

                boolean success = queueRecvList.get(threadQueueIdx++).offer(msg, 1, TimeUnit.MILLISECONDS);
                if (success) { // 如果添加成功，则跳出本次循环，接收处理下一条信息
                    break;
                } else {// 如果添加失败，则打印刚才添加失败的队列size
                    LOG.warn(Thread.currentThread().getName() + " Recv[" + (threadQueueIdx - 1) + "," + flagCount
                            + "] Queue Full : " + queueRecvList.get(threadQueueIdx - 1).size());
                    flagCount--;
                }
            } // while (flagCount > 0) {
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }


    /**
     * 解码处理线程类，与spout主线程功能相同
     */
    class ReceiverProcessThread extends Thread {

        /**
         * 线程退出标识
         */
        boolean flag = true;

        /**
         * 线程/队列索引号
         */
        int theQueueIdx = 0;

        /**
         * 构造方法
         *
         * @param idx 线程/队列索引号
         */
        public ReceiverProcessThread(int idx) {
            this.theQueueIdx = idx;
        }

        @Override
        public void run() {
            LOG.info("<<<< " + this.getName() + " start >>>>");
            try {
                while (flag) {
                    String msg = queueRecvList.get(theQueueIdx).take();
                    LOG.info(this.getName() + "-----------------------------------------" + msg);
                    if (StringUtils.isNotEmpty(msg)) {
                    }
                }
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
                e.printStackTrace();
            }
            LOG.info("<<<< " + this.getName() + " shutdown >>>>");
        }

        public void shutdown() {
            this.flag = false;
        }

    }

}
