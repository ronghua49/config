package com.xyjsoft.core.util;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyjsoft.core.config.XyjconfigProperties;
@Component
public class Snowflake {
	/**
     * 每一部分所占位数
     */
    /** 数据标识id所占的位数 */
    private final long datacenterIdBits = 5L;
    /** 机器id所占的位数 */
    private final long workerIdBits = 5L;
    /** 序列在id中占的位数 */
    private final long sequenceBits = 12L;

    /**
     * 向左的位移
     */
    /** 时间截向左移22位(5+5+12) */
    private final long timestampShift = sequenceBits + datacenterIdBits + workerIdBits;
    /** 数据标识id向左移17位(12+5) */
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    /** 机器ID向左移12位 */
    private final long workerIdShift = sequenceBits;

    /**
     * 起始时间戳，初始化后不可修改
     */
    private final long epoch = 1451606400000L; // 2016-01-01

    /**
     * 数据中心编码，初始化后不可修改
     * 最大值: 2^5-1 取值范围: [0,31]
     */
    private final long datacenterId;

    /**
     * 机器或进程编码，初始化后不可修改
     * 最大值: 2^5-1 取值范围: [0,31]
     */
    private final long workerId;

    /**
     * 毫秒内序列(0~4095)
     * 最大值: 2^12-1 取值范围: [0,4095]
     */
    private long sequence = 0L;

    /** 上次执行生成 ID 方法的时间戳 */
    private long lastTimestamp = -1L;

    /*
     * 每一部分最大值
     */
    /** 支持的最大数据标识id，结果是31 */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits); // 2^5-1
    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits); // 2^5-1
    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private final long maxSequence = -1L ^ (-1L << sequenceBits); // 2^12-1

    /**
     * 生成序列号
     */
    public synchronized long nextId() {
        long currTimestamp = timestampGen();

        if (currTimestamp < lastTimestamp) {
            throw new IllegalStateException(
                    String.format("Clock moved backwards. Refusing to generate id for %d milliseconds",
                            lastTimestamp - currTimestamp));
        }

        if (currTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0) { // overflow: greater than max sequence
                currTimestamp = waitNextMillis(currTimestamp);
            }

        } else { // reset to 0 for next period/millisecond
            sequence = 0L;
        }

        // track and memo the time stamp last snowflake ID generated
        lastTimestamp = currTimestamp;

        return ((currTimestamp - epoch) << timestampShift) | //
                (datacenterId << datacenterIdShift) | //
                (workerId << workerIdShift) | // new line for nice looking
                sequence;
    }


    public Snowflake(long datacenterId, long workerId) {
        this.datacenterId = datacenterId;
        this.workerId = workerId;
    }

    @Autowired
    public Snowflake(XyjconfigProperties xyjconfigProperties) {
    	datacenterId = xyjconfigProperties.getDatacenterId();
    	workerId = xyjconfigProperties.getWorkerId();
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
    }

    /**
     * 追踪调用 waitNextMillis 方法的次数
     */
    private final AtomicLong waitCount = new AtomicLong(0);

    public long getWaitCount() {
        return waitCount.get();
    }

    /**
     * 循环阻塞直到下一秒
     */
    protected long waitNextMillis(long currTimestamp) {
        waitCount.incrementAndGet();
        while (currTimestamp <= lastTimestamp) {
            currTimestamp = timestampGen();
        }
        return currTimestamp;
    }

    /**
     * 获取当前时间戳
     */
    public long timestampGen() {
        return System.currentTimeMillis();
    }
}
