package com.v2x.servicestreamkafka.hbase.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;


@org.springframework.context.annotation.Configuration
@ConfigurationProperties(prefix = "hbase.zookeeper")
public class HBaseConfigurationBase {

    private Logger logger = LoggerFactory.getLogger(HBaseConfigurationBase.class);

    private String quorum;
    private String baseZNode;
    private String propertyClientPort;

    /**
     * 产生HBaseConfiguration实例化Bean
     *
     * @return
     */
    @Bean
    public Configuration configuration() {
        logger.info("quorum is :" + quorum);
        logger.info("propertyClientPort is :" + propertyClientPort);
        logger.info("baseZNode is :" + baseZNode);

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", quorum);
        conf.set("hbase.zookeeper.property.clientPort", propertyClientPort);
        conf.set("zookeeper.znode.parent", baseZNode);
        return conf;
    }

    public String getQuorum() {
        return quorum;
    }

    public void setQuorum(String quorum) {
        this.quorum = quorum;
    }

    public String getBaseZNode() {
        return baseZNode;
    }

    public void setBaseZNode(String baseZNode) {
        this.baseZNode = baseZNode;
    }

    public String getPropertyClientPort() {
        return propertyClientPort;
    }

    public void setPropertyClientPort(String propertyClientPort) {
        this.propertyClientPort = propertyClientPort;
    }
}