package com.example.dwquestion1.listener;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.example.dwquestion1.util.BlackIpUtil;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class NacosListener implements InitializingBean {

    @NacosInjected
    private ConfigService configService;

    @Value("${nacos.config.data-id}")
    private String dataId;

    @Value("${nacos.config.group}")
    private String group;

    @Override
    public void afterPropertiesSet() throws Exception {

        String configAndSignListener = configService.getConfigAndSignListener(dataId, group, 3000L, new Listener() {

            final ThreadFactory threadFactory = new ThreadFactory() {
                private final AtomicInteger poolNumber = new AtomicInteger(1);

                @Override
                public Thread newThread(@NotNull Runnable r) {
                    Thread thread = new Thread(r);
                    //给线程设置名字
                    thread.setName("refresh-ThreadPool" + poolNumber.getAndIncrement());
                    return thread;
                }
            };


            final ExecutorService executorService = Executors.newFixedThreadPool(1, threadFactory);

            @Override
            public Executor getExecutor() {
                return executorService;
            }

            /*
            到nacos配置中心的配置变化时,会监听到，然后将本地的黑名单动态变更
             */
            @Override
            public void receiveConfigInfo(String config) {
                log.info("监听到 配置变化 {}",config );
                BlackIpUtil.rebuildBlackIp(config);
            }
        });

        //初始化黑名单在本地内存
        BlackIpUtil.rebuildBlackIp(configAndSignListener);
        log.info("监听到 配置变化 {}",configAndSignListener );
    }
}
