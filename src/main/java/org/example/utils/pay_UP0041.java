package org.example.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class pay_UP0041 {
    public static void main(String[] args) throws Exception{
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjGpnmKByseFngibUJiNIc4"
                + "/6wfIQHMOHAsUNe1EACJUrFDFat78IqCJZIsYZ91G8He+88rBQL9+zK7DoubJsHPb6JTHE8krdFN1u/oaEDQz"
                + "/k5YNff89byrAdjsa3g5GcRU4d1/1D1TpkSVk0BTojtAgCgyNfRPqxB95gF"
                + "+Bsu2LQB1qMojSVoUx7rbSMofexahjqCWBqIryDYskegjNZhcfh0fiIZd/hK7bZDILN"
                + "+gHhi60H5vvAEe9M84GYo4yszW51qt8Blf2u5SZ0WkiAsh4+AMFdoRpBDP7MGsmWIYLkylSYy2QoSM4nyaEFZXp3qk"
                + "/zI9k2HSB/XuW8FwFXQIDAQAB";
        String reqSeq = System.currentTimeMillis() + "";
        int Random = (int) ((Math.random()*9+1)*100000);
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        // 请求接口实际数据
        Map<String,String> messageMap = new HashMap<String,String>();
        messageMap.put("version", "1.0.0");
        messageMap.put("transCode", "UP0041");
        messageMap.put("reqSeq", reqSeq);
        messageMap.put("reqTransDate","20220216");

        messageMap.put("reqTransTime", "175019");
        messageMap.put("totalAmount", "2000");
        messageMap.put("discountAmount", "0");
        messageMap.put("tradeAmount", "2000");
        messageMap.put("channelNo", "01");
        messageMap.put("merCode", "330132100021");
        messageMap.put("couponId", "");
        messageMap.put("couponNo", "");
        messageMap.put("mobileNo", "17888222496");
        messageMap.put("remark", "创建订单测试");

        String messageMapStr = gson.toJson(messageMap);
        System.out.println("请求参数：" + messageMapStr);
        // 请求开放平台数据
        Map<String, String> openMap = new HashMap<String, String>();
        openMap.put("reqSeq", reqSeq);
        openMap.put("appId", "INTERNET_126893413116054");
        openMap.put("method", "createOrder");
        openMap.put("bizContent", messageMapStr);
        openMap.put("sign_param", "appId,method,bizContent");
        // 加签
//        openMap.put("sign", RSAUtil.rsaSignByCert(openMap, "src/main/resources/test.pfx", "hzsmk"));
        String PRIVATE_KEY ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDRllIZsPm7Q5AR"
                + "DpSfsyNzEz2ornF6rWsxVBx0SIcpyv10cudzA+UMlNEUMORy2BvjGViGYhm37rJp"
                + "W4gOx/uaVVhi4SiqSlXPK0ve5c3BepzGz5vnf81J7sLAz8ZdN6YEuwkrVvwlGC4q"
                + "xwIQGxAnjnpk30CyBts6lpxbmHdXtRFEO51rXH55HJFrhSX3BKCZJhj5jSqaXBs7"
                + "UcFTi0cf50G/DKA3d2RoSfcxokbS4DTdYfSpa/cvIBkW/LbauT6vda9ZsuOCyrqk"
                + "rwfNtNwpLFVi2f/PQ6pu1ihj8cyZrXHSCWbhyyn4t5I+66Y78w7VUPq7VodtgHOt"
                + "8d86p17F   AgMBAAECggEAaX+uUgpYmt/WfA+H1p+Yv4s25Vrx+lui8pCxRgNgLjRk"
                + "wqI4SRfzyxJ58BzwUbLgzr7qZbKp2YFIw2n0oeQejUBanmNSUe3bJc5YgZnRqYur"
                + "sRO+GZimYInT9LRh2HhzlDwF0JAXmiCC1LuaGTtWNWMtzurmKPb0kZhQmnaz1CyC"
                + "ZfD27CtuxhGcLT2uPAdOjXNiyoK8g5rvtImkqdvfVKqGov6ft4Yc/Q0XQXuLnzPX"
                + "39TnJfRSUrQaRSB7h79vflGYjsU14r0/GEJ7r37kUTLJ2NrgytuDSjsFACLql4bk"
                + "bZzrtumhdx+F5MVBySPl+JYUNohqEhBMM0p37XgSoQKBgQDw1qRaP7O/69Riupff"
                + "pDaSy1p6ctqZHOm0lT1xgFPNxTsuaii7kcc+0BSWe3FXBBOmDfWoSBoLlxMMxCdo"
                + "8+qwHdJUAG0Y0Pzqry8+ruI53zRWumdCMy9mJPvRR/BDWJ5fuVqaDQcMehb1FyGZ"
                + "SRZgcL4msrW7qgYer2wTzOHCDQKBgQDeyAhiIe7TqdQgk5g0QJZssHRmoWljyavk"
                + "w2MDiezxp+ML0NNjt/7govCwsPRgKVFXZb8zSO8Vr1QB3HA3H6xw/TN2ySsPiQvr"
                + "D7Fnj09De3pNlOUM856DTZFOviPEsYvir4enwxnslZEIBq7osqgq1r2ckSvKkpQG"
                + "G4xXr7u5mQKBgQC7sVvm5cxtXxpnUyo3ZeAOKSM7WLVaLHlKfiCjizJEpFhKBPv3"
                + "qlXQcxQIbu3WtdumRyzTWFXMAJIdGeXo2mdll+gqBScA9yaUs7CrNloiZfyBNsZw"
                + "hjmDboE+CcWKhs7upSKh+lMq+x9XJQ7PSnGA+XyH5jDRr2ETmv0stTigJQKBgEBS"
                + "bOk4duYyAIcvViCwDT5wbAs9y1n+Xlz8dNgOuJTFLm646m++Lh7ZrLi3PreM9mlP"
                + "fTbndfBC4PRwmw2vXXB6CqPfTUl8/i3uKACIhhAr1JIVzVRB+qBagvvDNjySLptS"
                + "ps4vLIr8XBV/KH0TKC7GNZTA7W3diFODHLAskvzxAoGAbe2oVKlk5v/3laQ2UuxZ"
                + "yerjFazqrspwxP166K9MnpI/hnKzJLPyZXoBKpCZ2afuna/x7Ylu4iamcUiIa7Mi"
                + "G/AKfV6f6OiPoLVPd8ElJVCM5qkyx++lNLj3V31aL5y1wyJEp7cPFcPni94aChf6"
                + "Mn+C32KINSHbnUSi5SvDgdo=";
        openMap.put("sign", OpenApiRSAUtil.rsaSign(openMap, PRIVATE_KEY));
        String message = gson.toJson(openMap);
        System.out.println("发送报文："+message);
        //发送报文
        String respStr = HttpUtil.postReq("https://wechat-test.iconntech.com/open",message);
//        String respStr2 = HttpRequest.postJson("https://wechat-test.iconntech.com/open",message);


        System.out.println("接收报文1：" + "\n" + respStr);
//        System.out.println("接收报文2：" + "\n" + respStr2);


    }
}
