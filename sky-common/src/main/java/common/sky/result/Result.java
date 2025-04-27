package common.sky.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.concurrent.Semaphore;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-04-27  15:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class Result<T> implements Serializable {
    private Integer code;

    private String msg;

    private T data;

    public static <T> Result<T> success(){
        Result<T> result = new Result<T>();
        result.code = 1;
        return result;
    }


    public static <T> Result<T>  success(T object){
        Result<T> result = new Result<>();
        result.data = object;
        result.code =1;
        return result;
    }

    public static <T> Result<T>  error(String msg){
        Result<T> result = new Result<>();
        result.msg = msg;
        result.code =0;
        return result;
    }

}
