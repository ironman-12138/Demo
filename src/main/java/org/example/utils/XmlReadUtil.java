package org.example.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.Objects;

public class XmlReadUtil {

    public static void node(NodeList list, Document d, String text, String name){
        for (int i = 0; i <list.getLength() ; i++) {
            Node node = list.item(i);
            NodeList childNodes = node.getChildNodes();
            for (int j = 0; j <childNodes.getLength() ; j++) {
                if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    if (text.equals(childNodes.item(j).getFirstChild().getNodeValue())) {
                        NodeList sList = d.getElementsByTagName(name);
                        getText(sList);
                    }
                }
            }
        }
    }

    public static void getText(NodeList list){
        for (int i = 0; i <list.getLength() ; i++) {
            Node node = list.item(i);
            NodeList childNodes = node.getChildNodes();
            for (int j = 0; j <childNodes.getLength() ; j++) {
                if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    System.out.println(childNodes.item(j).getFirstChild().getNodeValue());
                }
            }
        }
    }

    public static void main(String[] args) {

        String xml = "<xml>\n" +
                "    <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                "    <FromUserName><![CDATA[sys]]></FromUserName> \n" +
                "    <CreateTime>1403610513</CreateTime>\n" +
                "    <MsgType><![CDATA[event]]></MsgType>\n" +
                "    <Event><![CDATA[change_contact]]></Event>\n" +
                "    <ChangeType>create_user</ChangeType>\n" +
                "    <UserID><![CDATA[zhangsan]]></UserID>\n" +
                "    <Name><![CDATA[张三]]></Name>\n" +
                "    <Department><![CDATA[1,2,3]]></Department>\n" +
                "    <MainDepartment>1</MainDepartment>\n" +
                "    <IsLeaderInDept><![CDATA[1,0,0]]></IsLeaderInDept>\n" +
                "    <Position><![CDATA[产品经理]]></Position>\n" +
                "    <Mobile>13800000000</Mobile>\n" +
                "    <Gender>1</Gender>\n" +
                "    <Email><![CDATA[zhangsan@gzdev.com]]></Email>\n" +
                "    <Status>1</Status>\n" +
                "    <Avatar><![CDATA[http://wx.qlogo.cn/mmopen/ajNVdqHZLLA3WJ6DSZUfiakYe37PKnQhBIeOQBO4czqrnZDS79FH5Wm5m4X69TBicnHFlhiafvDwklOpZeXYQQ2icg/0]]></Avatar>\n" +
                "    <Alias><![CDATA[zhangsan]]></Alias>\n" +
                "    <Telephone><![CDATA[020-123456]]></Telephone>\n" +
                "    <Address><![CDATA[广州市]]></Address>\n" +
                "    <ExtAttr>\n" +
                "        <Item>\n" +
                "        <Name><![CDATA[爱好]]></Name>\n" +
                "        <Type>0</Type>\n" +
                "        <Text>\n" +
                "            <Value><![CDATA[旅游]]></Value>\n" +
                "        </Text>\n" +
                "        </Item>\n" +
                "        <Item>\n" +
                "        <Name><![CDATA[卡号]]></Name>\n" +
                "        <Type>1</Type>\n" +
                "        <Web>\n" +
                "            <Title><![CDATA[企业微信]]></Title>\n" +
                "            <Url><![CDATA[https://work.weixin.qq.com]]></Url>\n" +
                "        </Web>\n" +
                "        </Item>\n" +
                "    </ExtAttr>\n" +
                "</xml>";


        try {
            StringReader sr = new StringReader(xml);
            InputSource is = new InputSource(sr);
            //1.创建DocumentBuilderFactory对象
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //2.创建DocumentBuilder对象
            DocumentBuilder builder=factory.newDocumentBuilder();
            Document d = builder.parse(is);
            NodeList sList = d.getElementsByTagName("Item");
            node(sList, d, "卡号", "Web");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
