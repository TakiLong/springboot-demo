package com.wtl.exception;

/**
 * service层异常
 *
 * @author wangtianlong 2019/3/19 16:20
 * @version 1.0
 */
public class ServiceException extends BaseException {
    private static final long serialVersionUID = 6058294324031642376L;

    public ServiceException() {}

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String code, String message) {
        super(code, message);
    }

}
