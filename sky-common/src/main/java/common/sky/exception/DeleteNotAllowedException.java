package common.sky.exception;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-04  15:41
 */
public class DeleteNotAllowedException extends BaseException{
    public DeleteNotAllowedException(){

    }

    public DeleteNotAllowedException(String msg){
        super(msg);
    }
}
