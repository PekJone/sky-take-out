package com.sky.aspect;

import com.sky.anno.AutoFill;
import common.sky.constant.AutoFillConstant;
import common.sky.context.BaseContext;
import common.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.sql.rowset.Joinable;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-01  9:24
 * @Des 公共字段自动填充切面类
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    @Before("@annotation(com.sky.anno.AutoFill)")
    public void autoFill(JoinPoint joinPoint){
        System.out.println("进入切面了");
         //获取目标方法的注解，并拿到注解里面的值
        BaseContext.setCurrentId(2L);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();//方法签名
        Method method = methodSignature.getMethod();//方法对象
        AutoFill autoFill =  method.getAnnotation(AutoFill.class);
        OperationType operationType =  autoFill.value();
        System.out.println(operationType);
        //获取目标方法参数对象
        Object[] objects = joinPoint.getArgs();

        if(objects==null && objects.length==0){
           return;
        }
        System.out.println("进入切面了");
        Object entity =  objects[0];//拿到实体对象
        //判断属性值  如果是insert就补充四个属性
        try {
            if (operationType == OperationType.INSERT) {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                setCreateTime.invoke(entity, LocalDateTime.now());
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                setUpdateTime.invoke(entity, LocalDateTime.now());
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                setCreateUser.invoke(entity, BaseContext.getCurrentId());
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                setUpdateUser.invoke(entity,BaseContext.getCurrentId());
                log.info("BaseContext_I={}",BaseContext.getCurrentId());
            } else if(operationType == OperationType.UPDATE){
                System.out.println("我执行了 ");
                //判断属性值  如果是update就补充两个属性
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                setUpdateTime.invoke(entity, LocalDateTime.now());
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                setUpdateUser.invoke(entity,BaseContext.getCurrentId());
            }
        }catch (Exception e){
               throw new RuntimeException();
        }

    }


}
