package cn.itcast.store.test;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016102100728583";

	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCgN9qUNuki5vYRACsF7e2wsqtWPMrvZBwUH+RFc1myBn/JS7cD7JuFK7ZM+2WBFNj74vE5LHME0B+t7eThq9OhnwzFmJ/BmJHWLUWU9/3kVWutseA3m6FO6LC2/JzyOCjMcDJDjpWl0VmMEdHsz48njPvqkMM0AJ0vHW+w2ibVi2LCYCO0g/X23ofUUrIRHryYNAMiQ2qixFGi1U6wvjpCxwT7OO3SAUYnVXYbjhNScNEz97KszxIqMmguL+4T8xmwiEtZPruy6izBxfwj916bE3E7cLO4zfxTzhZDu5f13FnTj9QxX294aLnL09rqdqzTEPmbBxtMm1YmT5Ncr3fXAgMBAAECggEBAIPY8vt9pKWgQ2cCKGWcwzniDrXNGYOfcqvtAsFUh7/FMWvJ7zY/hEw3CLKZP5LWQSooPDK8rXqMe0V6vBwqduJOLQh+7Rk4IOuMHfLVkg5RbWdnNgkM/dYeDy7G+7EpHgn/Wp3VvLjUa0ilcYfb1wbJMpZ5wuoMh026f6ueGs8ns9Rolkxhd0k3usp3LlW9eXQrmOETz8k29l91rckUbi0ORtlY27BIRR4Im41e7N0I8Cq3WvDyaaVz20lD9LrHQJLl99eUczmpibgN32NU1S7Cjs0YmVecU+n6C/par8S28/fq90aAE+6NvgjzeJBE040pWFVYW668uahmwJ7VaQECgYEA1ATqja6AqFvGESqjSqj1fClpeXzL2Agc1E28Pjbt09+eXFPEj6ptjdwax0DkHvyA8NmqLt2L1tWuT827LgLPk0QF22ht3hQO+sdCb0CESBFxsRHpTcYtbHM+xWNTzvt+ciBSwWYIwPjYMHqUssvpbu9KuUKzxxj6LJAgckv3yCECgYEAwXQXkbV4wez4vIYrnLyT4sNCPrO0Nm/dISPAMyu15dbTH5b00/tRnrUmF/56+GYpiKduAzXJCfcEex9HGKprfQi9jKQ4sQxeb3gwnksxWmEw53xVYA+TAfVXVVYrF8ubtEBHMn7TF0/04RS1m86+SSO3y5T8OzNEUJSRee9xYPcCgYBKJGX5odpwBZfgDO8JfshJ4Rl3WILYSw352C1J/F9yxjJUyx5jtw+UmjHrF/AuPdLBZvyyYtsyB4trcOBSGL2KHDGIJp61GIKcg+3hhcg8g40khMT3Y6Qi1rJOyItYSG5RbqhB5XKOOPcxDMzPMauyFZAl9QN1TnC+ALC4B4TgwQKBgESV5yFGqGiAnnMgUmgAR6IWve7DAXpnUIvGRAcgUR4KetFxUJ2zNW3CJZ5OH8TfW4xSZ+OFdyAsTo9kOG2bRf6PUbyCIINMslJs1zfXysiTOqZnY1FqVCFWnT7pDOAj1LEkNNnHMwnJ1DTUhcqWksoVer0XthE7DNZQxf7oEFdjAoGAYD+zXVEikopBtfqDLPUXjCoOUHq55eO2cSeJbBAAcBux/ozx8oRcfNwiX3y/Z6GeKAPXy6k/Re6A2efjqGtJTBv5AwXtUjJ4z0XYbKWANtqiOzDqwwWxgeuWo/9iaAwfFlP5yXDe84J3ueenAe7QXdzvPZRmG5V41M2rN7W5VIY=";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxfFVg/eASU+MCCwvkYjkjNwBXdxLFCOKo2aKZ4Qys2UQeJS2PoaO9xnzCHOVFDQ8idAjrmvJuhHI8R8gG0WMnTH8IHHLzKv/dWsEWBcI2whLMPC7MS38jBaXitSMJcaAMCcdvjcqbkyQvRKBPbFhY0EL1kENYbCy0kGAJsQlVI8hxEqq/QJ2pj8uTBZOvk0sGehpqnsov4NEcjEGZX4fsP1/vQgAlKBBxKPKsgTh3GGzmIKCXdL+v61JG+S4K5fg1NoKOQAcR9N9KOZKxAFzLDOmLcbCWQFQe5TWdppyVRoAeSrUcqjarcf3T7oqJhFxR3htwapas1dfdZRKz8oUXwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";

	// 字符编码格式
	public static String charset = "utf-8";

	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

	// 支付宝
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}