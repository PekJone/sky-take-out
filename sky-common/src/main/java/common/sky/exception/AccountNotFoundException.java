package common.sky.exception;

import com.fasterxml.jackson.databind.ser.Serializers;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-04-28  15:00
 */
public class AccountNotFoundException extends BaseException {
    public AccountNotFoundException(){

    }

    public AccountNotFoundException(String msg){
        super(msg);
    }
}
