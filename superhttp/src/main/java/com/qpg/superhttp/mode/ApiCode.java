package com.qpg.superhttp.mode;

/**
 * @Description: 网络通用状态码定义
 */
public class ApiCode {

    /**
     * 对应HTTP的状态码
     */
    public static class Http {
        public static final int OK = 200;//请求成功。一般用于GET与POST请求
        public static final int Bad_Request = 400;//1、客户端请求的语法错误，服务器无法理解。2、请求参数有误。
        public static final int UNAUTHORIZED = 401;//请求要求用户的身份认证
        public static final int FORBIDDEN = 403;//服务器理解请求客户端的请求，但是拒绝执行此请求
        public static final int NOT_FOUND = 404;
        public static final int REQUEST_TIMEOUT = 408;//服务器等待客户端发送的请求时间过长，超时
        public static final int INTERNAL_SERVER_ERROR = 500;//服务器内部错误，无法完成请求
        public static final int BAD_GATEWAY = 502;//作为网关或者代理工作的服务器尝试执行请求时，从远程服务器接收到了一个无效的响应
        public static final int SERVICE_UNAVAILABLE = 503;//由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中
        public static final int GATEWAY_TIMEOUT = 504;//充当网关或代理的服务器，未及时从远端服务器获取请求
    }

    /**
     * Request请求码
     */
    public static class Request {
        //未知错误
        public static final int UNKNOWN = 1000;
        //解析错误
        public static final int PARSE_ERROR = 1001;
        //网络错误
        public static final int NETWORK_ERROR = 1002;
        //协议出错
        public static final int HTTP_ERROR = 1003;
        //证书出错
        public static final int SSL_ERROR = 1005;
        //连接超时
        public static final int TIMEOUT_ERROR = 1006;
        //调用错误
        public static final int INVOKE_ERROR = 1007;
        //类转换错误
        public static final int CONVERT_ERROR = 1008;
    }
}
