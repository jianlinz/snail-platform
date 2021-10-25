package indi.jl.sp.core.util;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(GzipUtil.class);

    public static String gZip(String input) {
        byte[] bytes = null;
        GZIPOutputStream gzip = null;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(bos);
            gzip.write(input.getBytes(CharEncoding.UTF_8));
            gzip.finish();
            gzip.close();
            bytes = bos.toByteArray();
            bos.close();
        } catch (Exception e) {
            LOGGER.error("压缩出错：", e);
        } finally {
            try {
                if (gzip != null)
                    gzip.close();
                if (bos != null)
                    bos.close();
            } catch (final IOException ioe) {
                LOGGER.error("压缩出错：", ioe);
            }
        }
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 解压GZip
     *
     * @return String
     */
    public static String unGZip(String input) {
        byte[] bytes;
        String out = input;
        GZIPInputStream gzip = null;
        ByteArrayInputStream bis;
        ByteArrayOutputStream bos = null;
        try {
            bis = new ByteArrayInputStream(Base64.decodeBase64(input));
            gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            int num;
            bos = new ByteArrayOutputStream();
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                bos.write(buf, 0, num);
            }
            bytes = bos.toByteArray();
            out = new String(bytes, CharEncoding.UTF_8);
            gzip.close();
            bis.close();
            bos.flush();
            bos.close();
        } catch (Exception e) {
            LOGGER.error("解压出错：", e);
        } finally {
            try {
                if (gzip != null)
                    gzip.close();
                if (bos != null)
                    bos.close();
            } catch (final IOException ioe) {
                LOGGER.error("解压出错：", ioe);
            }
        }
        return out;
    }
}