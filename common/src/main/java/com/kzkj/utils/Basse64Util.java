package com.kzkj.utils;


import com.alibaba.fastjson.JSONObject;
import com.kzkj.pojo.vo.request.arrival.CEB507Message;
import com.kzkj.pojo.vo.request.customs.Custom;
import com.kzkj.pojo.vo.response.order.CEB304Message;
import com.kzkj.pojo.vo.response.order.OrderReturn;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Basse64Util {

    /**
     * 海关加密
     * signXml 加签后的传输报文
     */
    public static String encodeBase64(String signXml) {
        String data = null;
        try {
            data = new String(Base64.encodeBase64(signXml.getBytes()),"utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("base64加密失败",e);
        }
        return data;
    }

    /**
     * 海关回执报文解密
     *
     */
    public static String decodeBase64(String resultXml) {
        byte[] s=Base64.decodeBase64(resultXml.getBytes());
        return new String(s);
    }

    public static void main(String[] args) {

        String customXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<dxp:DxpMsg xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:dxp=\"http://www.chinaport.gov.cn/dxp\" ver=\"1.0\">\n" +
                "  <dxp:TransInfo>\n" +
                "    <dxp:CopMsgId>704ea0f1-b229-4e89-9f2f-c2e550e95c86</dxp:CopMsgId>\n" +
                "    <dxp:SenderId>DXP1234567</dxp:SenderId>\n" +
                "    <dxp:ReceiverIds>\n" +
                "      <dxp:ReceiverId>DXPLGS0000000001</dxp:ReceiverId>\n" +
                "    </dxp:ReceiverIds>\n" +
                "    <dxp:CreatTime>2019-10-08T14:13:41.141+08:00</dxp:CreatTime>\n" +
                "    <dxp:MsgType>CEB303Message</dxp:MsgType>\n" +
                "  </dxp:TransInfo>\n" +
                "  <dxp:Data>PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPGNlYjpDRUIzMDNNZXNzYWdlIGd1aWQ9IjMxMWFmMTI1LTZmZWQtNDYwMy04YzVkLTQ5YjFmYTRiNGI5YiIgdmVyc2lvbj0iMS4wIiAgeG1sbnM6Y2ViPSJodHRwOi8vd3d3LmNoaW5hcG9ydC5nb3YuY24vY2ViIiB4bWxuczp4c2k9Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvWE1MU2NoZW1hLWluc3RhbmNlIj4KCTxjZWI6T3JkZXI+CgkJPGNlYjpPcmRlckhlYWQ+CgkJCTxjZWI6Z3VpZD43MDRlYTBmMS1iMjI5LTRlODktOWYyZi1jMmU1NTBlOTVjODY8L2NlYjpndWlkPgoJCQk8Y2ViOmFwcFR5cGU+MTwvY2ViOmFwcFR5cGU+CgkJCTxjZWI6YXBwVGltZT4yMDE4MDUwNzE1MzAwMTwvY2ViOmFwcFRpbWU+CgkJCTxjZWI6YXBwU3RhdHVzPjI8L2NlYjphcHBTdGF0dXM+CgkJCTxjZWI6b3JkZXJUeXBlPkU8L2NlYjpvcmRlclR5cGU+CgkJCTxjZWI6b3JkZXJObz5vcmRlcjIwMTgwNTA3MTEzNDAwMDE8L2NlYjpvcmRlck5vPgoJCQk8Y2ViOmVicENvZGU+MTEwNTkxMDE1OTwvY2ViOmVicENvZGU+CgkJCTxjZWI6ZWJwTmFtZT7kuJzmlrnnianpgJrnp5HmioAo5YyX5LqsKeaciemZkOWFrOWPuDwvY2ViOmVicE5hbWU+CgkJCTxjZWI6ZWJjQ29kZT4xMTA1OTEwMTU5PC9jZWI6ZWJjQ29kZT4KCQkJPGNlYjplYmNOYW1lPuS4nOaWueeJqemAmuenkeaKgCjljJfkuqwp5pyJ6ZmQ5YWs5Y+4PC9jZWI6ZWJjTmFtZT4KCQkJPGNlYjpnb29kc1ZhbHVlPjEyMzQ1Njc4OTEyMzQ1LjEyMzQ1PC9jZWI6Z29vZHNWYWx1ZT4KCQkJPGNlYjpmcmVpZ2h0PjA8L2NlYjpmcmVpZ2h0PgoJCQk8Y2ViOmN1cnJlbmN5PjE0MjwvY2ViOmN1cnJlbmN5PgoJCQk8Y2ViOm5vdGU+dGVzdDwvY2ViOm5vdGU+CgkJPC9jZWI6T3JkZXJIZWFkPgoJCTxjZWI6T3JkZXJMaXN0PgoJCQk8Y2ViOmdudW0+MTwvY2ViOmdudW0+CgkJCTxjZWI6aXRlbU5vPkFGMDAxLTAwMTwvY2ViOml0ZW1Obz4KCQkJPGNlYjppdGVtTmFtZT7lsI/nsbPnm5LlrZA8L2NlYjppdGVtTmFtZT4KCQkJPGNlYjppdGVtRGVzY3JpYmU+5bCP57Gz55uS5a2QPC9jZWI6aXRlbURlc2NyaWJlPgoJCQk8Y2ViOmJhckNvZGU+MjM0NTEyMzwvY2ViOmJhckNvZGU+CgkJCTxjZWI6dW5pdD5hYWE8L2NlYjp1bml0PgoJCQk8Y2ViOmN1cnJlbmN5PmFhYTwvY2ViOmN1cnJlbmN5PgoJCQk8Y2ViOnF0eT4xMDA8L2NlYjpxdHk+CgkJCTxjZWI6cHJpY2U+MjA8L2NlYjpwcmljZT4KCQkJPGNlYjp0b3RhbFByaWNlPjIwMDA8L2NlYjp0b3RhbFByaWNlPgoJCQk8Y2ViOm5vdGU+dGVzdDwvY2ViOm5vdGU+CgkJPC9jZWI6T3JkZXJMaXN0PgoJPC9jZWI6T3JkZXI+Cgk8Y2ViOkJhc2VUcmFuc2Zlcj4KCQk8Y2ViOmNvcENvZGU+MTEwNTkxMDE1OTwvY2ViOmNvcENvZGU+CgkJPGNlYjpjb3BOYW1lPuS4nOaWueeJqemAmuenkeaKgCjljJfkuqwp5pyJ6ZmQ5YWs5Y+4PC9jZWI6Y29wTmFtZT4KCQk8Y2ViOmR4cE1vZGU+RFhQPC9jZWI6ZHhwTW9kZT4KCQk8Y2ViOmR4cElkPkRYUExHUzAwMDAwMDAwMDE8L2NlYjpkeHBJZD4KCQk8Y2ViOm5vdGU+dGVzdDwvY2ViOm5vdGU+Cgk8L2NlYjpCYXNlVHJhbnNmZXI+Cgk8ZHM6U2lnbmF0dXJlIHhtbG5zOmRzPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwLzA5L3htbGRzaWcjIj4KPGRzOlNpZ25lZEluZm8+CjxkczpDYW5vbmljYWxpemF0aW9uTWV0aG9kIEFsZ29yaXRobT0iaHR0cDovL3d3dy53My5vcmcvVFIvMjAwMS9SRUMteG1sLWMxNG4tMjAwMTAzMTUiPjwvZHM6Q2Fub25pY2FsaXphdGlvbk1ldGhvZD4KPGRzOlNpZ25hdHVyZU1ldGhvZCBBbGdvcml0aG09Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvMDkveG1sZHNpZyNyc2Etc2hhMSI+PC9kczpTaWduYXR1cmVNZXRob2Q+CjxkczpSZWZlcmVuY2UgVVJJPSIiPgo8ZHM6VHJhbnNmb3Jtcz4KPGRzOlRyYW5zZm9ybSBBbGdvcml0aG09Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvMDkveG1sZHNpZyNlbnZlbG9wZWQtc2lnbmF0dXJlIj48L2RzOlRyYW5zZm9ybT4KPC9kczpUcmFuc2Zvcm1zPgo8ZHM6RGlnZXN0TWV0aG9kIEFsZ29yaXRobT0iaHR0cDovL3d3dy53My5vcmcvMjAwMC8wOS94bWxkc2lnI3NoYTEiPjwvZHM6RGlnZXN0TWV0aG9kPgo8ZHM6RGlnZXN0VmFsdWU+U0YvUCsyc1ZzUlE5ZEpJcUpTcm9XNmFqYjBZPTwvZHM6RGlnZXN0VmFsdWU+CjwvZHM6UmVmZXJlbmNlPgo8L2RzOlNpZ25lZEluZm8+CjxkczpTaWduYXR1cmVWYWx1ZT4KQUkwd2RjVUhhYU9SK0ZaN1c4bE42RnJqelMraXJ1MXFvQlRDeG00UzZrbm1MRkx1blBrTHVlRUxWNjluWVpyNHgrdUNQbk5ERC93cQpqU3F5UG5IMnhycng4RUZ2c0loeE5NQ2krSWxmUzF6NDQwWW1lTUVYbmhmZjBweFNCR2dyaEVUcnExdHFwNlFCWkU1c2lCRjRvdzEwCjBROVJLYUIrT01zNEFCNkkrMGc9CjwvZHM6U2lnbmF0dXJlVmFsdWU+CjxkczpLZXlJbmZvPgo8ZHM6S2V5TmFtZT4wMDAxPC9kczpLZXlOYW1lPgo8ZHM6WDUwOURhdGE+CjxkczpYNTA5Q2VydGlmaWNhdGU+Ck1JSUVXekNDQThTZ0F3SUJBZ0lEQUprbk1BMEdDU3FHU0liM0RRRUJCUVVBTUhZeEN6QUpCZ05WQkFZVEFtTnVNUkV3RHdZRFZRUUsKSGdoMU5WdFFVK05jdURFTk1Bc0dBMVVFQ3g0RUFFTUFRVEVOTUFzR0ExVUVDQjRFVXhkT3JERWpNQ0VHQTFVRUF4NGFUaTFXL1hVMQpXMUJUNDF5NFpYQmpiazR0WDhOZkFGUFJVem94RVRBUEJnTlZCQWNlQ0U0Y1pibGVmMWM2TUI0WERURTFNRFV4T1RBd01EQXdNRm9YCkRUUTVNVEl3T0RBd01EQXdNRm93SlRFak1DRUdBMVVFQXg0YUFERUFNMVAzVzhhVXBXMUxpOVZuRFZLaFZtaGZBRlBSVXpvd2daOHcKRFFZSktvWklodmNOQVFFQkJRQURnWTBBTUlHSkFvR0JBS29RYzd0eFhNYjVWdVhKbkFMcEtRMW1BeEZXMmh4UHdSaFhZMG1pck5JTAoyZ0xZMno3eXNxUnZrcFNSenIzQkVxOTd4V0xsWGtRREcxUW56dlR2aDFZUXpyZmZBUlNsQ3k2ZFlKM1lnTStCcHMyTnNQWGh3MUxrCnZrMHduN0xYQXNrRWJ3UmdMaWh1MXBINi9JR0VvY2dlRnVzV1RYVDZCL3BwVGlFWEwvODdBZ01CQUFHamdnSkdNSUlDUWpBTEJnTlYKSFE4RUJBTUNCc0F3Q1FZRFZSMFRCQUl3QURDQm9BWURWUjBqQklHWU1JR1ZnQlFzYURpUXJsaDlyeUlMcjJCWU1LL3d2bVJ2bHFGNgpwSGd3ZGpFTE1Ba0dBMVVFQmhNQ1kyNHhFVEFQQmdOVkJBb2VDSFUxVzFCVDQxeTRNUTB3Q3dZRFZRUUxIZ1FBUXdCQk1RMHdDd1lEClZRUUlIZ1JURjA2c01TTXdJUVlEVlFRREhocE9MVmI5ZFRWYlVGUGpYTGhsY0dOdVRpMWZ3MThBVTlGVE9qRVJNQThHQTFVRUJ4NEkKVGh4bHVWNS9WenFDQVNVd0hRWURWUjBPQkJZRUZQY0Q5MGhmcFNMS3p1aGFFM052aFUxeHdIREFNRUlHQTFVZElBUTdNRGt3TndZRwpLNEVIQVFFQ01DMHdLd1lJS3dZQkJRVUhBZ0VXSDJoMGRIQTZMeTlqY0hNdVkyaHBibUZ3YjNKMExtZHZkaTVqYmk5RFVGTXdRZ1lEClZSMGZCRHN3T1RBM29EV2dNNFl4YUhSMGNEb3ZMMnhrWVhBdVkyaHBibUZ3YjNKMExtZHZkaTVqYmpvNE1EZzRMMlI2YTJFd01EQXQKTVRrMkxtTnliREE5QmdnckJnRUZCUWNCQVFReE1DOHdMUVlJS3dZQkJRVUhNQUdHSVdoMGRIQTZMeTl2WTNOd0xtTm9hVzVoY0c5eQpkQzVuYjNZdVkyNDZPREE0T0RBcUJnb3JCZ0VFQWFsRFpBVUJCQndXR3RiUXVmcTE1OWZUdjlxd3Rzcjl2dDNXME5ERXY2cTNvc2Y0Ck1Cb0dDaXNHQVFRQnFVTmtCUVlFREJZS1V6QXlNREV5TURBek9EQWFCZ29yQmdFRUFhbERaQVVKQkF3V0NsTXdNakF4TWpBd016Z3cKRWdZS0t3WUJCQUdwUTJRQ0JBUUVGZ0pEUVRBU0Jnb3JCZ0VFQWFsRFpBSUJCQVFXQWpFNU1CTUdCU3BXQ3djRkJBb1dDTFhuMTlPLwoyckMyTUEwR0NTcUdTSWIzRFFFQkJRVUFBNEdCQUZxZE9PcUNzLzB6ZkpqNU5NM1VQWHpBSy95SXl4NmI4WkVRWHVZL2FvanpFNDZRClFYWDEvTitHM0RzS1B2VWhYUWoxbUFzWlFlVDBhTWlVYTFhTkNkMFA4cCtQc2ZyQjlFNW9abkZocDRjTERra3VoMmd4K01DRk9IZTIKb0ViaTIvbkNacHZXUkozNGlkNXN6VEl3MW45Ni9ucnJnMitxRmsrZGRGcjB4Unp6CjwvZHM6WDUwOUNlcnRpZmljYXRlPgo8L2RzOlg1MDlEYXRhPgo8L2RzOktleUluZm8+CjwvZHM6U2lnbmF0dXJlPgo8L2NlYjpDRUIzMDNNZXNzYWdlPgo=</dxp:Data>\n" +
                "</dxp:DxpMsg>";
        Custom Custom = (Custom) XMLUtil.convertXmlStrToObject(Custom.class,customXml);
        //System.out.println(Basse64Util.decodeBase64(Custom.getData()));
//        System.out.println("解密："+Basse64Util.decodeBase64("PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/Pgo8Q0VCNTEwTWVzc2FnZSBndWlkPSI0Q0RFMUNGRC1FREVELTQ2QjEtOTQ2Qy1CODAyMkU0MkZDOTQiIHhtbG5zPSJodHRwOi8vd3d3LmNoaW5hcG9ydC5nb3YuY24vY2ViIj4KICAgIDxEZXBhcnR1cmVSZXR1cm4+CiAgICAgICAgPGd1aWQ+NENERTFDRkQtRURFRC00NkIxLTk0NkMtQjgwMjJFNDJGQzk0PC9ndWlkPgogICAgICAgIDxsb2dpc3RpY3NDb2RlPjExMDU5MTAxNTk8L2xvZ2lzdGljc0NvZGU+CiAgICAgICAgPGNvcE5vPkNvcDIwMTgwNTA3MDAxPC9jb3BObz4KICAgICAgICA8cHJlTm8+MTIzNDU2Nzg5PC9wcmVObz4KICAgICAgICA8bXNnU2VxTm8+MTwvbXNnU2VxTm8+CiAgICAgICAgPHJldHVyblN0YXR1cz4yPC9yZXR1cm5TdGF0dXM+CiAgICAgICAgPHJldHVyblRpbWU+MjAxOTEyMDUxNjIyNTM8L3JldHVyblRpbWU+CiAgICAgICAgPHJldHVybkluZm8+5paw5aKe55Sz5oql5oiQ5YqfWzRDREUxQ0ZELUVERUQtNDZCMS05NDZDLUI4MDIyRTQyRkM5NF08L3JldHVybkluZm8+CiAgICA8L0RlcGFydHVyZVJldHVybj4KICAgIDxEZXBhcnR1cmVSZXR1cm4+CiAgICAgICAgPGd1aWQ+NENERTFDRkQtRURFRC00NkIxLTk0NkMtQjgwMjJFNDJGQzk0PC9ndWlkPgogICAgICAgIDxsb2dpc3RpY3NDb2RlPjExMDU5MTAxNTk8L2xvZ2lzdGljc0NvZGU+CiAgICAgICAgPGNvcE5vPkNvcDIwMTgwNTA3MDAxPC9jb3BObz4KICAgICAgICA8cHJlTm8+MTIzNDU2Nzg5PC9wcmVObz4KICAgICAgICA8bXNnU2VxTm8+MTwvbXNnU2VxTm8+CiAgICAgICAgPHJldHVyblN0YXR1cz4yPC9yZXR1cm5TdGF0dXM+CiAgICAgICAgPHJldHVyblRpbWU+MjAxOTEyMDUxNjIyNTM8L3JldHVyblRpbWU+CiAgICAgICAgPHJldHVybkluZm8+5paw5aKe55Sz5oql5oiQ5YqfWzRDREUxQ0ZELUVERUQtNDZCMS05NDZDLUI4MDIyRTQyRkM5NF08L3JldHVybkluZm8+CiAgICA8L0RlcGFydHVyZVJldHVybj4KICAgIDxEZXBhcnR1cmVSZXR1cm4+CiAgICAgICAgPGd1aWQ+NENERTFDRkQtRURFRC00NkIxLTk0NkMtQjgwMjJFNDJGQzk0PC9ndWlkPgogICAgICAgIDxsb2dpc3RpY3NDb2RlPjExMDU5MTAxNTk8L2xvZ2lzdGljc0NvZGU+CiAgICAgICAgPGNvcE5vPkNvcDIwMTgwNTA3MDAxPC9jb3BObz4KICAgICAgICA8cHJlTm8+MTIzNDU2Nzg5PC9wcmVObz4KICAgICAgICA8bXNnU2VxTm8+MTwvbXNnU2VxTm8+CiAgICAgICAgPHJldHVyblN0YXR1cz4yPC9yZXR1cm5TdGF0dXM+CiAgICAgICAgPHJldHVyblRpbWU+MjAxOTEyMDUxNjIyNTM8L3JldHVyblRpbWU+CiAgICAgICAgPHJldHVybkluZm8+5paw5aKe55Sz5oql5oiQ5YqfWzRDREUxQ0ZELUVERUQtNDZCMS05NDZDLUI4MDIyRTQyRkM5NF08L3JldHVybkluZm8+CiAgICA8L0RlcGFydHVyZVJldHVybj4KICAgIDxEZXBhcnR1cmVSZXR1cm4+CiAgICAgICAgPGd1aWQ+NENERTFDRkQtRURFRC00NkIxLTk0NkMtQjgwMjJFNDJGQzk0PC9ndWlkPgogICAgICAgIDxsb2dpc3RpY3NDb2RlPjExMDU5MTAxNTk8L2xvZ2lzdGljc0NvZGU+CiAgICAgICAgPGNvcE5vPkNvcDIwMTgwNTA3MDAxPC9jb3BObz4KICAgICAgICA8cHJlTm8+MTIzNDU2Nzg5PC9wcmVObz4KICAgICAgICA8bXNnU2VxTm8+MTwvbXNnU2VxTm8+CiAgICAgICAgPHJldHVyblN0YXR1cz4yPC9yZXR1cm5TdGF0dXM+CiAgICAgICAgPHJldHVyblRpbWU+MjAxOTEyMDUxNjIyNTM8L3JldHVyblRpbWU+CiAgICAgICAgPHJldHVybkluZm8+5paw5aKe55Sz5oql5oiQ5YqfWzRDREUxQ0ZELUVERUQtNDZCMS05NDZDLUI4MDIyRTQyRkM5NF08L3JldHVybkluZm8+CiAgICA8L0RlcGFydHVyZVJldHVybj4KICAgIDxEZXBhcnR1cmVSZXR1cm4+CiAgICAgICAgPGd1aWQ+NENERTFDRkQtRURFRC00NkIxLTk0NkMtQjgwMjJFNDJGQzk0PC9ndWlkPgogICAgICAgIDxsb2dpc3RpY3NDb2RlPjExMDU5MTAxNTk8L2xvZ2lzdGljc0NvZGU+CiAgICAgICAgPGNvcE5vPkNvcDIwMTgwNTA3MDAxPC9jb3BObz4KICAgICAgICA8cHJlTm8+MTIzNDU2Nzg5PC9wcmVObz4KICAgICAgICA8bXNnU2VxTm8+MTwvbXNnU2VxTm8+CiAgICAgICAgPHJldHVyblN0YXR1cz4yPC9yZXR1cm5TdGF0dXM+CiAgICAgICAgPHJldHVyblRpbWU+MjAxOTEyMDUxNjIyNTM8L3JldHVyblRpbWU+CiAgICAgICAgPHJldHVybkluZm8+5paw5aKe55Sz5oql5oiQ5YqfWzRDREUxQ0ZELUVERUQtNDZCMS05NDZDLUI4MDIyRTQyRkM5NF08L3JldHVybkluZm8+CiAgICA8L0RlcGFydHVyZVJldHVybj4KICAgIDxEZXBhcnR1cmVSZXR1cm4+CiAgICAgICAgPGd1aWQ+NENERTFDRkQtRURFRC00NkIxLTk0NkMtQjgwMjJFNDJGQzk0PC9ndWlkPgogICAgICAgIDxsb2dpc3RpY3NDb2RlPjExMDU5MTAxNTk8L2xvZ2lzdGljc0NvZGU+CiAgICAgICAgPGNvcE5vPkNvcDIwMTgwNTA3MDAxPC9jb3BObz4KICAgICAgICA8cHJlTm8+MTIzNDU2Nzg5PC9wcmVObz4KICAgICAgICA8bXNnU2VxTm8+MTwvbXNnU2VxTm8+CiAgICAgICAgPHJldHVyblN0YXR1cz4yPC9yZXR1cm5TdGF0dXM+CiAgICAgICAgPHJldHVyblRpbWU+MjAxOTEyMDUxNjIyNTM8L3JldHVyblRpbWU+CiAgICAgICAgPHJldHVybkluZm8+5paw5aKe55Sz5oql5oiQ5YqfWzRDREUxQ0ZELUVERUQtNDZCMS05NDZDLUI4MDIyRTQyRkM5NF08L3JldHVybkluZm8+CiAgICA8L0RlcGFydHVyZVJldHVybj4KICAgIDxEZXBhcnR1cmVSZXR1cm4+CiAgICAgICAgPGd1aWQ+NENERTFDRkQtRURFRC00NkIxLTk0NkMtQjgwMjJFNDJGQzk0PC9ndWlkPgogICAgICAgIDxsb2dpc3RpY3NDb2RlPjExMDU5MTAxNTk8L2xvZ2lzdGljc0NvZGU+CiAgICAgICAgPGNvcE5vPkNvcDIwMTgwNTA3MDAxPC9jb3BObz4KICAgICAgICA8cHJlTm8+MTIzNDU2Nzg5PC9wcmVObz4KICAgICAgICA8bXNnU2VxTm8+MTwvbXNnU2VxTm8+CiAgICAgICAgPHJldHVyblN0YXR1cz4yPC9yZXR1cm5TdGF0dXM+CiAgICAgICAgPHJldHVyblRpbWU+MjAxOTEyMDUxNjIyNTM8L3JldHVyblRpbWU+CiAgICAgICAgPHJldHVybkluZm8+5paw5aKe55Sz5oql5oiQ5YqfWzRDREUxQ0ZELUVERUQtNDZCMS05NDZDLUI4MDIyRTQyRkM5NF08L3JldHVybkluZm8+CiAgICA8L0RlcGFydHVyZVJldHVybj4KICAgIDxEZXBhcnR1cmVSZXR1cm4+CiAgICAgICAgPGd1aWQ+NENERTFDRkQtRURFRC00NkIxLTk0NkMtQjgwMjJFNDJGQzk0PC9ndWlkPgogICAgICAgIDxsb2dpc3RpY3NDb2RlPjExMDU5MTAxNTk8L2xvZ2lzdGljc0NvZGU+CiAgICAgICAgPGNvcE5vPkNvcDIwMTgwNTA3MDAxPC9jb3BObz4KICAgICAgICA8cHJlTm8+MTIzNDU2Nzg5PC9wcmVObz4KICAgICAgICA8bXNnU2VxTm8+MTwvbXNnU2VxTm8+CiAgICAgICAgPHJldHVyblN0YXR1cz4yPC9yZXR1cm5TdGF0dXM+CiAgICAgICAgPHJldHVyblRpbWU+MjAxOTEyMDUxNjIyNTM8L3JldHVyblRpbWU+CiAgICAgICAgPHJldHVybkluZm8+5paw5aKe55Sz5oql5oiQ5YqfWzRDREUxQ0ZELUVERUQtNDZCMS05NDZDLUI4MDIyRTQyRkM5NF08L3JldHVybkluZm8+CiAgICA8L0RlcGFydHVyZVJldHVybj4KICAgIDxEZXBhcnR1cmVSZXR1cm4+CiAgICAgICAgPGd1aWQ+NENERTFDRkQtRURFRC00NkIxLTk0NkMtQjgwMjJFNDJGQzk0PC9ndWlkPgogICAgICAgIDxsb2dpc3RpY3NDb2RlPjExMDU5MTAxNTk8L2xvZ2lzdGljc0NvZGU+CiAgICAgICAgPGNvcE5vPkNvcDIwMTgwNTA3MDAxPC9jb3BObz4KICAgICAgICA8cHJlTm8+MTIzNDU2Nzg5PC9wcmVObz4KICAgICAgICA8bXNnU2VxTm8+MTwvbXNnU2VxTm8+CiAgICAgICAgPHJldHVyblN0YXR1cz4yPC9yZXR1cm5TdGF0dXM+CiAgICAgICAgPHJldHVyblRpbWU+MjAxOTEyMDUxNjIyNTM8L3JldHVyblRpbWU+CiAgICAgICAgPHJldHVybkluZm8+5paw5aKe55Sz5oql5oiQ5YqfWzRDREUxQ0ZELUVERUQtNDZCMS05NDZDLUI4MDIyRTQyRkM5NF08L3JldHVybkluZm8+CiAgICA8L0RlcGFydHVyZVJldHVybj4KICAgIDxEZXBhcnR1cmVSZXR1cm4+CiAgICAgICAgPGd1aWQ+NENERTFDRkQtRURFRC00NkIxLTk0NkMtQjgwMjJFNDJGQzk0PC9ndWlkPgogICAgICAgIDxsb2dpc3RpY3NDb2RlPjExMDU5MTAxNTk8L2xvZ2lzdGljc0NvZGU+CiAgICAgICAgPGNvcE5vPkNvcDIwMTgwNTA3MDAxPC9jb3BObz4KICAgICAgICA8cHJlTm8+MTIzNDU2Nzg5PC9wcmVObz4KICAgICAgICA8bXNnU2VxTm8+MTwvbXNnU2VxTm8+CiAgICAgICAgPHJldHVyblN0YXR1cz4yPC9yZXR1cm5TdGF0dXM+CiAgICAgICAgPHJldHVyblRpbWU+MjAxOTEyMDUxNjIyNTM8L3JldHVyblRpbWU+CiAgICAgICAgPHJldHVybkluZm8+5paw5aKe55Sz5oql5oiQ5YqfWzRDREUxQ0ZELUVERUQtNDZCMS05NDZDLUI4MDIyRTQyRkM5NF08L3JldHVybkluZm8+CiAgICA8L0RlcGFydHVyZVJldHVybj4KPC9DRUI1MTBNZXNzYWdlPgo="));
//        String str = "";
//        System.out.println("待加密xml:"+str);
//        System.out.println("加密："+encodeBase64(str));

        /*OrderReturn orderReturn =new OrderReturn();
        orderReturn.setGuid("4CDE1CFD-EDED-46B1-946C-B8022E42FC94");
        orderReturn.setEbcCode("123456");
        orderReturn.setEbpCode("123456");
        orderReturn.setOrderNo("order123456");
        orderReturn.setReturnInfo("新增申报成功！");
        orderReturn.setReturnStatus("200");
        orderReturn.setReturnTime("201912021524");

        OrderReturn orderReturn1 =new OrderReturn();
        orderReturn1.setGuid("4CDE1CFD-EDED-46B1-946C-B8022E42FC94");
        orderReturn1.setEbcCode("123456");
        orderReturn1.setEbpCode("123456");
        orderReturn1.setOrderNo("order123456");
        orderReturn1.setReturnInfo("新增申报成功！");
        orderReturn1.setReturnStatus("200");
        orderReturn1.setReturnTime("201912021524");
        List<OrderReturn> List= new ArrayList<OrderReturn>();
        List.add(orderReturn);
        List.add(orderReturn1);
        CEB304Message ceb304Message = new CEB304Message();
        ceb304Message.setGuid("4CDE1CFD-EDED-46B1-946C-B8022E42FC94");
        ceb304Message.setOrderReturn(List);
        String xml= XMLUtil.convertToXml(ceb304Message);
        System.out.println(xml);

        CEB304Message ceb304Messageqq = new CEB304Message();
        Object ob = null;
        ob = XMLUtil.convertXmlStrToObject(CEB304Message.class,xml);


        ceb304Messageqq = (CEB304Message)ob;*/

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ceb:CEB303Message guid=\"311af125-6fed-4603-8c5d-49b1fa4b4b9b\" version=\"1.0\"  xmlns:ceb=\"http://www.chinaport.gov.cn/ceb\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "\t<ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>33169609NY</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>33169609NY</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>2000</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "        <ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>33169609NY</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>33169609NY</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>2000</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "    <ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>33169609NY</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>33169609NY</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>2000</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "    <ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>33169609NY</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>33169609NY</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>2000</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "    <ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>33169609NY</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>33169609NY</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>2000</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "\t<ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>33169609NY</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>33169609NY</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>2000</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "        <ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>33169609NY</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>33169609NY</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>2000</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "    <ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>33169609NY</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>33169609NY</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>2000</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "    <ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>33169609NY</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>33169609NY</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>2000</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "    <ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>33169609NY</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>33169609NY</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>200</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "\t<ceb:BaseTransfer>\n" +
                "\t\t<ceb:copCode>33169609NY</ceb:copCode>\n" +
                "\t\t<ceb:copName>东方物通科技(北京)有限公司</ceb:copName>\n" +
                "\t\t<ceb:dxpMode>DXP</ceb:dxpMode>\n" +
                "\t\t<ceb:dxpId>DXPENT0000024685</ceb:dxpId>\n" +
                "\t\t<ceb:note>test</ceb:note>\n" +
                "\t</ceb:BaseTransfer>\n" +
                "</ceb:CEB303Message>\n";

        String dxpId = xml.substring(xml.indexOf("<ceb:dxpId>"), xml.indexOf("</ceb:dxpId>")).substring("<ceb:dxpId>".length());
        System.out.println("dxpid:"+dxpId);
        JSONObject sendJson=new JSONObject();
        sendJson.put("companyCode","33169609NY");
        sendJson.put("status","0000");
        sendJson.put("xmlId","4CDE1CFD-EDED-46B1-946C-B8022E42FC94");
        sendJson.put("msgType","CEB304Message");
        sendJson.put("data",xml);
        System.out.println(sendJson.toJSONString());

        String xml2="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ceb:CEB303Message guid=\"311af125-6fed-4603-8c5d-49b1fa4b4b9b\" version=\"1.0\"  xmlns:ceb=\"http://www.chinaport.gov.cn/ceb\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "\t<ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>1105910159</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>1105910159</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>2000</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "        <ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>1105910159</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>1105910159</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>2000</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "    <ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>1105910159</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>1105910159</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>2000</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "    <ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>1105910159</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>1105910159</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>2000</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "    <ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>1105910159</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>1105910159</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>2000</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "\t<ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>1105910159</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>1105910159</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>2000</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "        <ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>1105910159</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>1105910159</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>2000</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "    <ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>1105910159</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>1105910159</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>2000</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "    <ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>1105910159</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>1105910159</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>2000</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "    <ceb:Order>\n" +
                "\t\t<ceb:OrderHead>\n" +
                "\t\t\t<ceb:guid>704ea0f1-b229-4e89-9f2f-c2e550e95c86</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:orderType>E</ceb:orderType>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:ebpCode>1105910159</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:ebcCode>1105910159</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:goodsValue>12345678912345.12345</ceb:goodsValue>\n" +
                "\t\t\t<ceb:freight>0</ceb:freight>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderHead>\n" +
                "\t\t<ceb:OrderList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:itemDescribe>小米盒子</ceb:itemDescribe>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:unit>aaa</ceb:unit>\n" +
                "\t\t\t<ceb:currency>aaa</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>200</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:OrderList>\n" +
                "\t</ceb:Order>\n" +
                "\t<ceb:BaseTransfer>\n" +
                "\t\t<ceb:copCode>1105910159</ceb:copCode>\n" +
                "\t\t<ceb:copName>东方物通科技(北京)有限公司</ceb:copName>\n" +
                "\t\t<ceb:dxpMode>DXP</ceb:dxpMode>\n" +
                "\t\t<ceb:dxpId>DXPENT0000024685</ceb:dxpId>\n" +
                "\t\t<ceb:note>test</ceb:note>\n" +
                "\t</ceb:BaseTransfer>\n" +
                "<ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"></ds:CanonicalizationMethod><ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"></ds:SignatureMethod><ds:Reference URI=\"\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"></ds:Transform></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"></ds:DigestMethod><ds:DigestValue>F90Y8FhhGjLa+mTbjn01GAUzL8Y=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>h0RarN6cK+3HBDTHXxKaeNl0n766A6MfsMlQ2rX/Z705UVwWgSZeAzmwX0VuZcWRG9GpAwqXkx57\n" +
                "xLkmsTULg5i1cosf2/3liTE+p/VM76OYDn/79dR9CeqLP14rXYLeYhHWJQO+qN6p4N6ZHh1AoqUT\n" +
                "snsjVhRyUpJqCGt0O20=</ds:SignatureValue><ds:KeyInfo><ds:KeyName>0148f0a3</ds:KeyName><ds:X509Data><ds:X509Certificate>MIIE7DCCBFWgAwIBAgIEAUjwozANBgkqhkiG9w0BAQUFADB8MQswCQYDVQQGEwJjbjEVMBMGA1UE\n" +
                "Ch4MTi1W/XU1W1BT41y4MRUwEwYDVQQLHgyLwU5me6F0Bk4tX8MxDTALBgNVBAgeBFMXTqwxITAf\n" +
                "BgNVBAMeGE4tVv11NVtQThpSoYvBTmZ7oXQGTi1fwzENMAsGA1UEBx4EUxdOrDAeFw0xOTEwMjIw\n" +
                "MDAwMDBaFw0yOTEwMjIwMDAwMDBaMBExDzANBgNVBAMeBnOLTpGeTzCBnzANBgkqhkiG9w0BAQEF\n" +
                "AAOBjQAwgYkCgYEAvvC2OZ/s4nTKj9NKzkM5FhaMsLQQi6odMlvhOQiiFWf+cBPk049+nXDAncQm\n" +
                "wa74/AVQgVDp2NPHScC+8WcPApi8UAli48kX/w+Loqc1NNrgBNsl5YacMwDk7vC/7FRhmMpGapMH\n" +
                "Vu7ha4BApgwovKnTU6ZwzqS09i+EwsfqIPsCAwEAAaOCAuQwggLgMAsGA1UdDwQEAwIGwDAJBgNV\n" +
                "HRMEAjAAMIGnBgNVHSMEgZ8wgZyAFPl1o3hFC0JowgRTq+vDTj5UeHRgoYGApH4wfDELMAkGA1UE\n" +
                "BhMCY24xFTATBgNVBAoeDE4tVv11NVtQU+NcuDEVMBMGA1UECx4Mi8FOZnuhdAZOLV/DMQ0wCwYD\n" +
                "VQQIHgRTF06sMSEwHwYDVQQDHhhOLVb9dTVbUE4aUqGLwU5me6F0Bk4tX8MxDTALBgNVBAceBFMX\n" +
                "TqyCAQAwHQYDVR0OBBYEFOA5drh16ymu5IPILDhV+VhbHBiDMEIGA1UdIAQ7MDkwNwYGK4EHAQEC\n" +
                "MC0wKwYIKwYBBQUHAgEWH2h0dHA6Ly9jcHMuY2hpbmFwb3J0Lmdvdi5jbi9DUFMwcgYDVR0fBGsw\n" +
                "aTAwoC6gLIYqbGRhcDovL2xkYXAuY2hpbmFwb3J0Lmdvdi5jbjozODkvMDAwLTEuY3JsMDWgM6Ax\n" +
                "hi9odHRwOi8vbGRhcC5jaGluYXBvcnQuZ292LmNuOjgwODgvZHprYTAwMC0xLmNybDBtBggrBgEF\n" +
                "BQcBAQRhMF8wLgYIKwYBBQUHMAGGImh0dHA6Ly9vY3NwLmNoaW5hcG9ydC5nb3YuY246ODgwMC8w\n" +
                "LQYIKwYBBQUHMAGGIWh0dHA6Ly9vY3NwLmNoaW5hcG9ydC5nb3YuY246ODA4ODAoBgorBgEEAalD\n" +
                "ZAUBBBoWGL/t1a2/xry8o6i6vNbdo6nT0M/euavLvjASBgorBgEEAalDZAUDBAQWAjAxMCIGCisG\n" +
                "AQQBqUNkBQgEFBYSMjExMzAyMTk4OTA5MTIwNDE0MB0GCisGAQQBqUNkBQkEDxYNMjEwMDA0MDAz\n" +
                "MzgwOTAZBgorBgEEAalDZAULBAsWCU1BMkdZNEdUMDASBgorBgEEAalDZAUMBAQWAjAwMBIGCisG\n" +
                "AQQBqUNkAgQEBBYCMTkwEgYKKwYBBAGpQ2QCAQQEFgIxMjANBgkqhkiG9w0BAQUFAAOBgQBUjWmV\n" +
                "1ZORPIB7zxA176y+eR0SKCq0+v8G1iRHH2cZ0Pb7BlBzOnxyJ4di0hRueo5x3ORNlZ8bFNTlOULp\n" +
                "JzhQ+d5G3tzzqyYFj306krKQPphJYEypWbfEHKczUY/MsTtHH5yqxEyMYzVfKi1XdvpEsTeePT5b\n" +
                "/COHkyBukcF5CQ==</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature>\n" +
                "</ceb:CEB303Message>\n";
        //System.out.println("加密："+Basse64Util.encodeBase64(xml2));

    }
}
