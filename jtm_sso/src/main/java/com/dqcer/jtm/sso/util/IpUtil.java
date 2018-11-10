package com.dqcer.jtm.sso.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IpUtil {
    /**
     * 真实IP头
     */
    public static final String HTTP_HEADER_REAL_IP = "X-Real-IP";
    /**
     * 重定向IP
     */
    public static final String HTTP_HEADER_X_FORWARDED_FOR = "X-Forwarded-For";
    /**
     * 代理IP
     */
    public static final String HTTP_HEADER_CDN_SRC_IP = "Cdn-Src-Ip";

    public static String getMacAddr() {
        String MacAddr = "";
        String str = "";
        try {
            NetworkInterface NIC = NetworkInterface.getByName("eth0");
            byte[] buf = NIC.getHardwareAddress();
            for (int i = 0; i < buf.length; i++) {
                str = str + byteHEX(buf[i]);
            }
            MacAddr = str.toUpperCase();
        } catch (SocketException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return MacAddr;
    }

    public static String getLocalIP() {
        String ip = "";
        try {
            Enumeration<?> e1 = (Enumeration<?>) NetworkInterface.getNetworkInterfaces();
            while (e1.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) e1.nextElement();
                if (!ni.getName().equals("eth0")) {
                    continue;
                } else {
                    Enumeration<?> e2 = ni.getInetAddresses();
                    while (e2.hasMoreElements()) {
                        InetAddress ia = (InetAddress) e2.nextElement();
                        if (ia instanceof Inet6Address) {
                            continue;
                        }
                        ip = ia.getHostAddress();
                    }
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return ip;
    }

    private static String byteHEX(byte ib) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    public static String getRemoteRealIP(HttpServletRequest request) {
        String remoteIP = request.getHeader(HTTP_HEADER_CDN_SRC_IP);

        if (!StringUtils.isEmpty(remoteIP)) {
            return remoteIP;
        }

        remoteIP = request.getHeader(HTTP_HEADER_X_FORWARDED_FOR);
        if (!StringUtils.isEmpty(remoteIP)) {
            return remoteIP;
        }

        remoteIP = request.getHeader(HTTP_HEADER_REAL_IP);
        if (!StringUtils.isEmpty(remoteIP)) {
            return remoteIP;
        }

        return request.getRemoteAddr();
    }

    public static int getHostIP() {
        int IP = 0;

        String ip = getLocalIP();
        String[] IPArray = ip.split(".");

        if (IPArray.length == 4) {
            IP = (int) (Integer.valueOf(IPArray[0]) * Math.pow(256, 3) + Integer.valueOf(IPArray[1]) * Math.pow(256, 2)
                    + Integer.valueOf(IPArray[2]) * Math.pow(256, 1) + Integer.valueOf(IPArray[3]) * Math.pow(256, 0));
        }

        return IP;
    }

    public static String longToIp(long v) {
        return String.format("%d.%d.%d.%d", (v >> 24) & 0xFF, (v >> 16) & 0xFF, (v >> 8) & 0xFF, v & 0xFF);
    }

    public static long ipToLong(String addr) {
        final String[] addrArray = addr.split("\\.");

        long v = 0;
        for (int i = 0; i < addrArray.length; i++) {
            int power = 3 - i;
            v += ((Integer.parseInt(addrArray[i]) % 256 * Math.pow(256, power)));
        }

        return v;
    }

}