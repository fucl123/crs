package com.kzkj.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

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
        System.out.println(Basse64Util.decodeBase64("PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPGNlYjpDRUIzMDRNZXNzYWdlIGd1aWQ9IjMxMWFmMTI1LTZmZWQtNDYwMy04YzVkLTQ5YjFmYTRiNGI5YiIgdmVyc2lvbj0iMS4wIiAgeG1sbnM6Y2ViPSJodHRwOi8vd3d3LmNoaW5hcG9ydC5nb3YuY24vY2ViIiB4bWxuczp4c2k9Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvWE1MU2NoZW1hLWluc3RhbmNlIj4KCTxjZWI6T3JkZXJSZXR1cm4+CgkJPGNlYjpndWlkPjMxMWFmMTI1LTZmZWQtNDYwMy04YzVkLTQ5YjFmYTRiNGI5YjwvY2ViOmd1aWQ+CgkJPGNlYjplYnBDb2RlPjExMDU5MTAxNTk8L2NlYjplYnBDb2RlPgoJCTxjZWI6ZWJjQ29kZT4xMTA1OTEwMTU5PC9jZWI6ZWJjQ29kZT4KCQk8Y2ViOm9yZGVyTm8+b3JkZXIyMDE4MDUwNzExMzQwMDAxPC9jZWI6b3JkZXJObz4KCQk8Y2ViOnJldHVyblN0YXR1cz4yPC9jZWI6cmV0dXJuU3RhdHVzPgoJCTxjZWI6cmV0dXJuVGltZT4yMDE4MDUwNzE1MzAwMTAwMTwvY2ViOnJldHVyblRpbWU+CgkJPGNlYjpyZXR1cm5JbmZvPuaWsOWinueUs+aKpeaIkOWKn1s0Q0RFMUNGRC1FREVELTQ2QjEtOTQ2Qy1CODAyMkU0MkZDOTRdPC9jZWI6cmV0dXJuSW5mbz4KCTwvY2ViOk9yZGVyUmV0dXJuPgoJCjwvY2ViOkNFQjMwNE1lc3NhZ2U+Cg=="));
    }
}
