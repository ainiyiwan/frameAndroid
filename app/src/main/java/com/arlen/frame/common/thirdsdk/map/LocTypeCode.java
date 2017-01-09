package com.arlen.frame.common.thirdsdk.map;

/**
 * Created by andy.pang on 2016/3/8.
 */
public enum LocTypeCode {
    /**
     * 61 ： GPS定位结果，GPS定位成功。
     *62 ： 无法获取有效定位依据，定位失败，请检查运营商网络或者wifi网络是否正常开启，尝试重新请求定位。
     *63 ： 网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位。
     *65 ： 定位缓存的结果。
     *66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果。
     *67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果。
     *68 ： 网络连接失败时，查找本地离线定位时对应的返回结果。
     *161： 网络定位结果，网络定位定位成功。
     *162： 请求串密文解析失败，一般是由于客户端SO文件加载失败造成，请严格参照开发指南或demo开发，放入对应SO文件。
     *167： 服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。
     *502： key参数错误，请按照说明文档重新申请KEY。
     *505： key不存在或者非法，请按照说明文档重新申请KEY。
     *601： key服务被开发者自己禁用，请按照说明文档重新申请KEY。
     *602： key mcode不匹配，您的ak配置过程中安全码设置有问题，请确保：sha1正确，“;”分号是英文状态；且包名是您当前运行应用的包名，请按照说明文档重新申请KEY。
     *501～700：key验证失败，请按照说明文档重新申请KEY。
     */
    CODE_61(61,"GPS定位成功"),
    CODE_62(62,"无法获取有效定位依据，定位失败"),
    CODE_63(63,"网络异常，没有成功向服务器发起请求"),
    CODE_65(65,"定位缓存的结果"),
    CODE_66(66,"离线定位结果"),
    CODE_67(67,"离线定位失败"),
    CODE_68(68,"网络连接失败时，查找本地离线定位时对应的返回结果"),
    CODE_161(161,"网络定位结果，网络定位成功"),
    CODE_162(162,"请求串密文解析失败"),
    CODE_167(167,"服务端定位失败"),
    CODE_501(501,"key验证失败"),
    CODE_502(502,"key参数错误"),
    CODE_505(505,"key不存在或者非法"),
    CODE_601(601,"key服务被开发者自己禁用"),
    CODE_602(602,"key mcode不匹配，您的ak配置过程中安全码设置有问题，请确保：sha1正确"),
    CODE_999(999,"百度地图定位未知错误");
    private int statusCode;
    private String statusMsg;

    LocTypeCode(int statusCode, String statusMsg){
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }
    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    // 普通方法
    public static String getStatusMsg(int statusCode) {
        for (LocTypeCode locTypeCode : LocTypeCode.values()) {
            if (locTypeCode.getStatusCode()== statusCode) {
                return locTypeCode.getStatusMsg();
            }
        }
        if (statusCode>501 &&  statusCode<700){
            return CODE_501.getStatusMsg();
        }
        return CODE_999.getStatusMsg();
    }
}
