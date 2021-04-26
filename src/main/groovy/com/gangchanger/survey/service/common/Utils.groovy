package com.gangchanger.survey.service.common

import groovy.time.TimeCategory
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.BeanUtils
import org.springframework.beans.BeanWrapper
import org.springframework.beans.BeanWrapperImpl

import java.beans.PropertyDescriptor
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import java.util.zip.ZipInputStream

class Utils {
    /**
     * Copies properties from one object to another
     * @param source
     * @destination
     * @return
     */
    static void copyNonNullProperties(Object source, Object destination) {
        BeanUtils.copyProperties(source, destination,
                getNullPropertyNames(source));
    }
    /**
     * Returns an array of null properties of an object
     * @param source
     * @return
     */
    static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set emptyNames = new HashSet();
        emptyNames.add("metaClass");
        for (PropertyDescriptor pd : pds) {
            //check if value of this property is null then add it to the collection
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    static String zip(String s) {
        if (s != null) {
            def targetStream = new ByteArrayOutputStream()
            def zipStream = new GZIPOutputStream(targetStream)
            zipStream.write(s.getBytes('UTF-8'))
            zipStream.close()
            def zippedBytes = targetStream.toByteArray()
            targetStream.close()
            return zippedBytes.encodeBase64()
        }
        return null;
    }

    static String unzip(String compressed) {
        if (compressed == null) return '';
        def inflaterStream = new GZIPInputStream(new ByteArrayInputStream(compressed.decodeBase64()))
        def uncompressedStr = inflaterStream.getText('UTF-8')
        return uncompressedStr
    }

    /**
     * This method HTTP split headers string to key:value pair map
     * The input format of header string is {{key1}}:{{value1}};;{{key2}}:{{value2}}*/
    static Map<String, String> splitHeadersStr(String headersStr) {
        Map<String, String> headers = [:];
        if (headersStr == null) {
            return headers;
        }
        String[] headerParts = StringUtils.splitByWholeSeparator(headersStr, ";;");
        if (headerParts == null || headerParts.size() == 0) {
            return headers;
        }
        for (String headerPart : headerParts) {
            // Split key value
            if (StringUtils.isNotBlank(headerPart) && headerPart.indexOf(":") > 0) {
                String[] keyValue = StringUtils.splitByWholeSeparator(headerPart, ":");
                headers.put(keyValue.first(), keyValue.last());
            }
        }
        return headers;
    }

    static Date nDayBefore(Date since, int n) {
        use(TimeCategory) {
            return since - n.days;
        }
    }

    static Date nSecBefore(Date since, int n) {
        use(TimeCategory) {
            return since - n.seconds;
        }
    }

    static Date nSecAfter(Date since, int n) {
        use(TimeCategory) {
            return since + n.seconds;
        }
    }

//    static String getProxyHost(boolean enableProxy, BassProperty bassProperty) {
//        return enableProxy && bassProperty.proxy.enable ? bassProperty.proxy.host : null;
//    }
//
//    static int getProxyPort(BassProperty bassProperty) {
//        return bassProperty.proxy.enable ? bassProperty.proxy.port : -1;
//    }

    static String getDomainName(String url) throws URISyntaxException {
        if (StringUtils.isBlank(url)) return null;
        if (url.startsWith("http")) {
            URI uri = new URI(url);
            String domain = uri.getHost();
            return domain?.startsWith("www.") ? domain.substring(4) : domain;
        } else {
            return StringUtils.substringBefore(url, ":");
        }
    }

    static String getBaseUrl(String url) {
        String domain = getDomainName(url);
        if (url.startsWith('https'))
            return "https://${domain}";
        return "http://${domain}";
    }

    /*-
    * Available Variables: DO NOT MODIFY
    * In : byte[] content
    * In : String encoding
    * Out : String unzippedContent
    * Available Variables: DO NOT MODIFY
    */

    static String zipDecompress(byte[] content, String encoding) {
        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = null;
        ZipInputStream zis = null;

        bais = new ByteArrayInputStream(content);
        baos = new ByteArrayOutputStream();
        zis = new ZipInputStream(bais);
        try {
            zis.getNextEntry();
            byte[] b = new byte[1024];
            for (int c = zis.read(b, 0, 1024); c != -1; c = zis.read(b, 0, 1024)) {
                baos.write(b, 0, c);
            }
            return new String(baos.toByteArray(), encoding);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bais != null) {
                try {
                    bais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
