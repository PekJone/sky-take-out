package com.oa.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-03-27  15:54
 */
@Slf4j
@Component
public class VolatileDemo {
    //核心 :  volatile 关键字的作用是保证多线程间的可见性
    private volatile boolean running = true ;
    private Thread workThread ;
    @PostConstruct
    public void start(){
        workThread = new Thread(()->{
            while (running){
                doBusinessWork();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.info("工作线程被中断，准备停止...");
                    Thread.currentThread().interrupt(); // 恢复中断状态
                }
            }
            log.info("工作线程已停止.");
        },"background-worker");
        workThread.start();
        log.info("服务已启动...");
    }

    @PreDestroy
    public void stop(){
        log.info("正在停止服务...");
        running = false ;
        workThread.interrupt();
    }

    private void doBusinessWork(){
        log.info("正在执行业务逻辑...");
    }


}
