package cn.zendee.j2struct;

import java.io.*;
import java.security.MessageDigest;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class BinaryUtils {
    public static int readInt(RandomAccessFile raf)
            throws EOFException, IOException {
        byte[] b = new byte[4];
        raf.read(b);
        return readInt(b);
    }

    public static byte[] writeShort(short v) {
        byte[] b = new byte[2];
        b[0] = ((byte) (v >>> 0 & 0xFF));
        b[1] = ((byte) (v >>> 8 & 0xFF));
        return b;
    }

    public static byte[] writeShort(int v) {
        byte[] b = new byte[2];
        b[0] = ((byte) (v >>> 0 & 0xFF));
        b[1] = ((byte) (v >>> 8 & 0xFF));
        return b;
    }

    public static byte[] writeInt(int v) {
        byte[] b = new byte[4];
        b[0] = ((byte) (v >>> 0 & 0xFF));
        b[1] = ((byte) (v >>> 8 & 0xFF));
        b[2] = ((byte) (v >>> 16 & 0xFF));
        b[3] = ((byte) (v >>> 24 & 0xFF));
        return b;
    }

    public static byte[] writeLong(long v) {
        byte[] b = new byte[8];
        b[0] = ((byte) (int) (v >>> 0 & 0xFF));
        b[1] = ((byte) (int) (v >>> 8 & 0xFF));
        b[2] = ((byte) (int) (v >>> 16 & 0xFF));
        b[3] = ((byte) (int) (v >>> 24 & 0xFF));
        b[4] = ((byte) (int) (v >>> 32 & 0xFF));
        b[5] = ((byte) (int) (v >>> 40 & 0xFF));
        b[6] = ((byte) (int) (v >>> 48 & 0xFF));
        b[7] = ((byte) (int) (v >>> 56 & 0xFF));
        return b;
    }

    public static int readInt(byte[] buf, int pos) {
        int ch1 = buf[(pos + 0)] & 0xFF;
        int ch2 = buf[(pos + 1)] & 0xFF;
        int ch3 = buf[(pos + 2)] & 0xFF;
        int ch4 = buf[(pos + 3)] & 0xFF;
        return (ch4 << 24) + (ch3 << 16) + (ch2 << 8) + ch1;
    }

    public static int readInt(byte[] buf)
            throws EOFException {
        int ch1 = buf[0] & 0xFF;
        int ch2 = buf[1] & 0xFF;
        int ch3 = buf[2] & 0xFF;
        int ch4 = buf[3] & 0xFF;
        if ((ch1 | ch2 | ch3 | ch4) < 0) {
            throw new EOFException();
        }
        return (ch4 << 24) + (ch3 << 16) + (ch2 << 8) + ch1;
    }

    public static long readLong(RandomAccessFile raf)
            throws EOFException, IOException {
        return (readInt(raf) & 0xFFFFFFFFL) + ((long)(readInt(raf)) << 32);
    }

    public static double readDouble(RandomAccessFile raf)
            throws EOFException, IOException {
        return Double.longBitsToDouble(readLong(raf));
    }

    public static float readFloat(RandomAccessFile raf)
            throws EOFException, IOException {
        return Float.intBitsToFloat(readInt(raf));
    }

    public static int readUShort(RandomAccessFile raf)
            throws IOException {
        byte[] b = new byte[2];
        raf.read(b);
        return readUShort(b);
    }

    public static int readUShort(byte[] buf)
            throws EOFException {
        int ch1 = buf[0] & 0xFF;
        int ch2 = buf[1] & 0xFF;
        if ((ch1 | ch2) < 0) {
            throw new EOFException();
        }
        return (ch2 << 8) + (ch1 << 0);
    }

    public static short readShort(RandomAccessFile raf)
            throws IOException {
        byte[] b = new byte[2];
        raf.read(b);
        return readShort(b);
    }

    public static short readShort(byte[] buf)
            throws EOFException {
        int ch1 = buf[0] & 0xFF;
        int ch2 = buf[1] & 0xFF;
        if ((ch1 | ch2) < 0) {
            throw new EOFException();
        }
        return (short) ((ch2 << 8) + (ch1 << 0));
    }

    public static byte[] getIntToByte(int number) {
        byte[] buf = new byte[4];
        buf[0] = ((byte) (number & 0xFF));
        buf[1] = ((byte) (number >>> 8 & 0xFF));
        buf[2] = ((byte) (number >>> 16 & 0xFF));
        buf[3] = ((byte) (number >>> 24 & 0xFF));
        return buf;
    }

    public static byte[] getShortToByte(int number) {
        byte[] buf = new byte[2];
        buf[0] = ((byte) (number & 0xFF));
        buf[1] = ((byte) (number >>> 8 & 0xFF));
        return buf;
    }

    public static byte[] compress(byte[] array) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(array.length);
        Deflater compressor = new Deflater(1, false);
        compressor.setInput(array);
        compressor.finish();
        byte[] buf = new byte[1024];
        while (!compressor.finished()) {
            int count = compressor.deflate(buf);
            bos.write(buf, 0, count);
        }
        try {
            bos.close();
        } catch (IOException e) {
        }
        return bos.toByteArray();
    }

    public static byte[] decompress(byte[] array) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(array.length);
        Inflater decompressor = new Inflater(false);
        decompressor.setInput(array);
        byte[] buf = new byte[1024];
        while (!decompressor.finished()) {
            try {
                int count = decompressor.inflate(buf);
                bos.write(buf, 0, count);
            } catch (DataFormatException e) {
                System.out.println(e);
                e.printStackTrace();
                return new byte[0];
            }
        }
        try {
            bos.close();
        } catch (IOException e) {
        }
        return bos.toByteArray();
    }

    public static TreeMap<String, String> getFileList(String startedDir, String curDir, TreeMap<String, String> list) {
        for (File f : new File(curDir).listFiles()) {
            if (f.isDirectory()) {
                getFileList(startedDir, f.getAbsolutePath(), list);
            } else {
                list.put(f.getAbsolutePath().replace("\\", "/").replace(startedDir, ""), "");
            }
        }
        return list;
    }

    public static String calcMD5File(String file) {
        String md5hash = "";
        try {
            FileInputStream fis = new FileInputStream(new File(file));
            byte[] buffer = new byte[1024];
            MessageDigest complete = MessageDigest.getInstance("MD5");
            int numRead;
            while ((numRead = fis.read(buffer)) > 0) {
                complete.update(buffer, 0, numRead);
            }
            fis.close();
            byte[] b = complete.digest();
            for (int i = 0; i < b.length; i++) {
                md5hash = md5hash + Integer.toString((b[i] & 0xFF) + 256, 16).substring(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5hash;
    }

    public static void copyFile(String from, String to) {
        File f = new File(from);
        new File(to.substring(0, to.lastIndexOf("/"))).mkdirs();
        File f2 = new File(to);
        byte[] b = new byte[1048576];
        try {
            FileInputStream fis = new FileInputStream(f);
            FileOutputStream fos = new FileOutputStream(f2);
            while (fis.available() > 0) {
                int count = fis.read(b);
                fos.write(b, 0, count);
            }
            fos.close();
            fis.close();
        } catch (Exception ex) {
            Logger.getLogger(BinaryUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static byte[] getFileData(String file) {
        byte[] b = new byte[0];
        try {
            FileInputStream fis = new FileInputStream(new File(file));
            b = new byte[fis.available()];
            fis.read(b);
            fis.close();
        } catch (Exception ex) {
            Logger.getLogger(BinaryUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    public static byte[] putFileData(String file, byte[] b) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(file));
            fos.write(b);
            fos.close();
        } catch (Exception ex) {
            Logger.getLogger(BinaryUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
}
