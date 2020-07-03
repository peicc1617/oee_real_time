package xjtu.oee_real_time.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @基本功能:用户获取支付宝用户信息
 * @program:spider
 * @author:peicc
 * @create:2020-06-18 15:01:37
 **/
public class AliPayUtil {
    private static Logger LOGGER= LoggerFactory.getLogger(AliPayUtil.class);
    //沙箱APPID
//    public static final  String app_id = "2016100100637313";
    public static final  String app_id = "2021001162682237";
    //沙箱私钥
//    public static final  String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCwQSQl7aoKoUXfMx5XZlgeH0108Awp54JY4JV3jwk803GNKd/atDEw2vnsom+WZ4TTMkwmxdBWvju0vOaX3esYq0wLFRQNmksxVZyysT6jEYOTeGvGgr3af1SV/Ygo0G93PxMMxXAIJuovCLntU3GrY5Xoa61mEXueAvkeDsQpIhABrD194FBQ4pT0ceGUzLsZsCpaR1k6gWHAEz8f1WEmFDIAiiGoezk+aMZu0kNHkE/+h4AF1tjWMu4/TT3U8bWLjV8EHxGVFI/RSM1sMFxToUCjlddKPJJ/zOx7l4T4JDvQpPSCyRH/H31/0KFwuWFrzHILLqHeNXvDYPHkXw4RAgMBAAECggEBAIosNxtoysIUlgMZPvMDb5cFsSI4QUDklgH45hPZVUOS+1TP/Aam9l03fXAuE06UjsNGjf5jWyV1crcI07lAptorB600dAkJ6WKem/5WByWtSDnm0I7J1e4hnf0LyZ89b/TKxLNhYKdzYmlG7QMJRHY41r9jEoeOsxs3d3ZB2zPmi43HzRd4TAK96eX4CztTr/C28U0gEV0aAnACkjMC4WWRGMoDvPsppzBrG5R0fx2Q4c+mWdcOelBgDzdFPq4+nH/P38Zi+lcexCDC/1sYZaad5T99V3pKmaRf0ENZH+ydmxOz0TCfsb8v4PnUVBjePk0UN7sqD05X6UzLpwTdp3ECgYEA4QG6X4qcstObo4uT1HPWd6WRpfkBATH+9nAFNMT4gIACaQtLRMgvdvTSp7T0z/1dMoYTmVG8RQDTj66x9zG1i/CwkSnDTB3rhFs41dID5n9TKhEdhzZ1InY1UZvZr4q/X+adVj5LmBZeWP3vScjhK/QroSLkshPlrqEqi+57Rx0CgYEAyIhKqmg2f4XT88wGXvL015HNW4u55uYETrYkUocLbz5XMXniZwdv/KfCZZ+FTqVt5mc5+p4eRJK++dYg/7cif13IJIHQ2RUItdbsAYR62wOyzkAP2V6VxlB4S4cJSBE63aG3HLF6m/jXObcqHb6vFIeQxHk0McpECzIQHI/IzIUCgYEA1RRc2cObd2IbfvpcSZJVZML/tgyj3ezpxZj55SovJwmSvsV6Ry3IfT+GClm8iQcn7gO4pbYYs8P/lY7ba/vAMEEEBMRm9TtUYOg+X4beOLcVG75Pl0+EB9mnzpvnv2yGpRvwnc6qqeFtAZugsuAwFP7LHnK73sQm+4QWlaOHTSUCgYBQVsoTG006KdOQiNzEjXGqK6+qTYBdESoIntBOf+wE1WT5XWtm+4PSJSOsswwlRgGRnSmNTmux7Vi1nyP5TtVzUY8tkRSq49Jph4Fl2j2eIQ0xtFilLU+z8+k92QvO45hB0tgZlsEYW8leRhOJdyuNfgJxtF1E1VQNaDEsQC6iQQKBgGLkWr4HfqI8Y0LsP979qaHpYKr7DsCnvtlEQE6PZOVQSBAsJxON6a04VoLFgy0ovekWmQlt8rlQwvZeyCNvDkL9huwjYVo/8Nh4t8pNhY4ArqN7nJHgRXX3z9PMn4TTrFfagPaWqPXEAov0u+cnOSl69gFMKM14nFLH1KUmjRJ/";
    public static final  String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCIDYx8DRh95TA+5UfMsu1MCHUhlsrBxgQoNGM9pMwsXID/IipCJ7l2TYRUfA5zCO30WSoubOAjdtSKsGZitRgPcLwlf9wr8f0S791mMa1/c+mRjth7+wtblk4ua3RhmwtEMbagUZj8OjePEQo/oDlvamFo+l+zsGBaTS/zPOq9UvLqS7yhRToJ6GeEzCPiQRyWnSsOr8vkK/c8mNqpp0Ns0x0Xt55i1BOqwkh6Te90qoC/lYSgjS2aMnjnvUQIP6DextK0wNdwikN3MiPEO+pm4dTELbdmPstUfRO1zalw+w2LbUCgmZXWYaPJAc6cjchqWBxREM9TUCcRJjGgSEEBAgMBAAECggEAd85uioLBsYNUQ96FpoVdn1nHQ4fVDmQAHyNazNrFDL98vK7qRY1TrRC/2eHQm44edNDb6zvm+HPY4DRMRNmx0U7175OzX2Nd9wIQlcBjRtvcr1f53NuJ+eHXANuRBa6RckDQ0EZqkJn3eN2Jh9PhH/KuytHH2qUThBF6ixh2obYUDuj9j6jUJwSSgOSSvlZN6wwhfEPp8q833wzodwtBo+7WLfqS+R9O+A5JOgQd49WXAxEz0v74yIl+wwo7KrF3L7PieXGXshA69k5dMhroFIYqnDDbOg7kpy5rdLbBCy250PIJW20jpV5vP/RAGTLRFMfBZ4HULj9WllyBcTuiQQKBgQC8CQOKXRW0rPQGWg1/gSoOCHJvtMv89HFPqoF4bLHVwqtMqeOgtJTID3z22FqbBbr7CMPDbjD5URgV4tNgIGR6cdn8kw4DDrDHlQvUyT8VW5HvO6ItrqcKfUhsS9OY6pi8IF2x7TuPvbk+Okp/P4Hvl+uNkyxEbmXpxuqga6EbuQKBgQC5Opb2ZGW6eq0kJQx1TS56LXADhFgUw3+A/ZCxwHQFeTdzQPt2NbEP9tuLLr6MKWadDq4hZsw8+2p+SejbFB56icfDz1dml0RV7pK5WeOEBmsPuwBWcowrAlFdXEurGlgqD1lp66t/FUgfKHiKtrkEx2/jXBkS5f/enI260MdDiQKBgHcokyjMomOzspSP3rvtY3QvIC3v+dEcKu5cU923q96Vla5ofsqphRtezUc0hf51XC+XN0+SddCY9lVwP5lMDcCWg5iOrDN9zMM9jZjTy3lCZTGgrb6bTlnUWN/gEYB3MkENlbKabsNBbLGliO4olbP6pxg5qrTW7Rh/qNHuQtNpAoGBALULwGx6ezhHGh6/G6s30lrXOKonXFSHNYQl05z56C1Fat57QI4tMrDQ74veF582BDc1FxxMtwpQD6n6oeAojRXmv9vzOJFs/v0jIESMyKXOBC0u8ciNU9XiARdI1CkcqdoKgLHYCRtA+OqDizbCHJvAN+u5pERljK/Qw3M7B9lJAoGBALgUU2N7npfoWwFsKi3nL0DbiC/XpgnF4MLEq3yafSjSzxqTJFzSp7h6tU75hIInd+44B95fso/ItlNRqn3160PPppONZJF2y+x54yrTTQ49PXMNYcPmH8fKbLyoBGZ0NIlh2zbirC+RKAVLikw2oStM5y2ekMQGRoumTzOticIy";
//        public static final  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsInm+eo15xZ3/4Ie6XRuiTJQN1lLRwghGinqHDA5Y5UXu0sDe5MMl5KeOf0X5IE7EBIsPh4w3HLiN+Tqr10jWMB8UtNObArRwI9tx9XLh0LbXdAEgWQ69DU6jo7R+m4a5CJPQoU9DwRmRJl/n7c/KdSbOZ+2fDDfEmKOZM8kTBq2qZrI5myfaQSE/Cg7SyVVEzKaREI7FFWwe8Gpj0lhCNy97K9Dht+wqSJ+gSv8MY/n/nGXdRnZP/EjOtDzFqBPucbud+WHVzHaNvBhGXvkMBbD5idYPcXUeV0kbkqAKYw57VTEwRIQ3xB1JCSQRWSHZOlpvRywPtshQAxu8rRamQIDAQAB";
    public static final  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyroCro6cAaV6MqVUTT7MThs0mKI+XRZblKCYTbkfsqr+2NEpcluQwc3ShTbCkTfVKCbo8mhayO3OoLE+ecum70XBzUAt21tsR34YCh4T+5On5S45cZLu17ou4Jr0/XSWBo6u23oS18ou0LpRGAAcgIZpfMe5duTqqp+WTB8SDrjhFuv9EQTIFuqZmD4wTwmWThCQn7isEa5grgXOtALZgQcUrOAWGjcfCcEVRp6qVRgXRZ7NwzceZcVNJ7HThT8oTi9VuirDYlgfOutQVsFXCR9JKyxPpRJTaHW76DARf5S2l9Grrc5y8rCLM4vSsk0bcrdBO3FaXsPYMld/0URf6wIDAQAB";
    //	//沙箱网关地址
    public static final  String gatewayUrl = "https://openapi.alipay.com/gateway.do";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://121.89.222.196:8084/oee.html";

    // 签名方式
    public static final String sign_type = "RSA2";

    // 字符编码格式
    public static final String charset = "UTF-8";

    //参数返回格式
    public static final String format = "json";
    // 使用auth_code换取access_token及用户userId
    public static String  getAcessToken(String auth_code) {
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, format,charset, alipay_public_key, sign_type);
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(auth_code);
        request.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            String access_token=oauthTokenResponse.getAccessToken();
            LOGGER.info("获取access_token成功："+access_token);
            return access_token;
        } catch (AlipayApiException e) {
            //处理异常
            LOGGER.error("获取access_token失败");
            return "";
        }
    }
    // 使用access_token换取用户信息
    public static String getUserInfo(String access_token) {
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, format, charset, alipay_public_key, sign_type);
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();

        String auth_token = access_token;
        try {
            AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(request, auth_token);
            String userInfo=userinfoShareResponse.getBody();
            LOGGER.info("获取用户信息成功："+userInfo);
            return userInfo;
        } catch (AlipayApiException e) {
            //处理异常
            LOGGER.error("获取access_token失败");
            return "";
        }
    }
}
