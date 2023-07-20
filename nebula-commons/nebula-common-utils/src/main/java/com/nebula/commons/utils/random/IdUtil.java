package com.nebula.commons.utils.random;

import cn.hutool.core.lang.Snowflake;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * description: IdUtil
 * date: 2020-10-11 14:04
 * author: chenxd
 * version: 1.0
 */
@Slf4j
public class IdUtil extends cn.hutool.core.util.IdUtil {

    private static final int MAX_WORKER_ID = 31;
    private static final Snowflake SNOW_FLAKE;
    private static final int IP4_LENGTH = 4;
    private static final int  IP6_LENGTH = 6;

    static {
        long workerId = 0L;
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (final UnknownHostException e) {
            throw new IllegalStateException("Cannot get LocalHost InetAddress, please check your network!");
        }
        byte[] ipAddressByteArray = address.getAddress();
        // IPV4
        if (ipAddressByteArray.length == IP4_LENGTH) {
            for (byte byteNum : ipAddressByteArray) {
                workerId += byteNum & 0xFF;
            }
            // IPV6
        } else if (ipAddressByteArray.length == IP6_LENGTH) {
            for (byte byteNum : ipAddressByteArray) {
                workerId += byteNum & 0B111111;
            }
        } else {
            throw new IllegalStateException("Bad LocalHost InetAddress, please check your network!");
        }
        //取余（不严谨，如果取余结果相同，可能造成ID冲突）
        workerId = workerId % MAX_WORKER_ID;
        log.info("本机生成workerId：{}，分布式部署时请检查多节点workerId值是否相同！", workerId);
        //支持同一个服务部署在31个数据中心，一个数据中心部署31个实例
        SNOW_FLAKE = IdUtil.getSnowflake(workerId, 1);
    }

    /**
     * 根据雪花算法生成唯一ID
     * @return
     */
    public static long nextId() {
        return SNOW_FLAKE.nextId();
    }

}
