package common.sky.context;

import org.apache.poi.ss.formula.functions.T;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-04-28  10:56
 */
public class BaseContext {
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }

    public static void removeCurrentId(){
        threadLocal.remove();
    }
}
