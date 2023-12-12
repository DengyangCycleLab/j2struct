package cn.zendee.j2struct;


import cn.zendee.j2struct.annotation.*;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class J2Struct {
    private static boolean isStruct(Class clz){
        Struct struct = (Struct) clz.getAnnotation(Struct.class);
        return struct != null;
    }

    public static int getStructAlignLength(Class<?> clz) throws Exception {
        if(!isStruct(clz)){
            throw new Exception("Content is not a Struct");
        }
        PropertyDescriptor[] props = getProps(clz);
        if(props.length == 0){
            return MyByte.byteLen;
        }

        List<Integer> alignLengthes = new ArrayList<>();
        for(PropertyDescriptor prop:props){
            Field field = clz.getDeclaredField(prop.getName());
            Struct struct = field.getAnnotation(Struct.class);
            if(struct != null){
                return getStructAlignLength(field.getType());
            }
            StructArray structArray = field.getAnnotation(StructArray.class);
            if(structArray != null){
                return getStructAlignLength(field.getType().getComponentType());
            }
            MyLong myLong = field.getAnnotation(MyLong.class);
            if(myLong != null){
                alignLengthes.add(MyLong.byteLen);
                continue;
            }
            LongArray longArray = field.getAnnotation(LongArray.class);
            if(longArray != null){
                alignLengthes.add(LongArray.byteLen);
                continue;
            }

            MyDouble myDouble = field.getAnnotation(MyDouble.class);
            if(myDouble != null){
                alignLengthes.add(MyDouble.byteLen);
                continue;
            }
            DoubleArray doubleArray = field.getAnnotation(DoubleArray.class);
            if(doubleArray != null){
                alignLengthes.add(DoubleArray.byteLen);
                continue;
            }

            MyInt myInt = field.getAnnotation(MyInt.class);
            if(myInt != null){
                alignLengthes.add(MyInt.byteLen);
                continue;
            }
            IntArray intArray = field.getAnnotation(IntArray.class);
            if(intArray != null){
                alignLengthes.add(IntArray.byteLen);
                continue;
            }

            MyFloat myFloat = field.getAnnotation(MyFloat.class);
            if(myFloat != null){
                alignLengthes.add(MyFloat.byteLen);
                continue;
            }
            FloatArray floatArray = field.getAnnotation(FloatArray.class);
            if(floatArray != null){
                alignLengthes.add(FloatArray.byteLen);
                continue;
            }

            MyShort myShort = field.getAnnotation(MyShort.class);
            if(myShort != null){
                alignLengthes.add(MyShort.byteLen);
                continue;
            }
            ShortArray shortArray = field.getAnnotation(ShortArray.class);
            if(shortArray != null){
                alignLengthes.add(ShortArray.byteLen);
                continue;
            }

            WStringArray wStringArray = field.getAnnotation(WStringArray.class);
            if(wStringArray != null){
                alignLengthes.add(WStringArray.byteLen);
                continue;
            }
            WString wString = field.getAnnotation(WString.class);
            if(wString != null){
                alignLengthes.add(WString.byteLen);
                continue;
            }

            MyString myString = field.getAnnotation(MyString.class);
            if(myString != null){
                alignLengthes.add(MyString.byteLen);
                continue;
            }
            StringArray stringArray = field.getAnnotation(StringArray.class);
            if(stringArray != null){
                alignLengthes.add(StringArray.byteLen);
                continue;
            }

            Bool bool = field.getAnnotation(Bool.class);
            if(bool != null){
                alignLengthes.add(Bool.byteLen);
                continue;
            }
            BoolArray boolArray = field.getAnnotation(BoolArray.class);
            if(boolArray != null){
                alignLengthes.add(BoolArray.byteLen);
                continue;
            }

            MyByte myByte = field.getAnnotation(MyByte.class);
            if(myByte != null){
                alignLengthes.add(MyByte.byteLen);
                continue;
            }
            ByteArray byteArray = field.getAnnotation(ByteArray.class);
            if(byteArray != null){
                alignLengthes.add(ByteArray.byteLen);
            }
        }
        if(alignLengthes.isEmpty()){
            return MyByte.byteLen;
        }
        alignLengthes.sort(Comparator.reverseOrder());
        return alignLengthes.get(0);
    }
    public static PropertyDescriptor[] getProps(Class<?> clz) throws Exception {
        if(!isStruct(clz)){
            throw new Exception("Content is not a Struct");
        }
        BeanInfo beanInfo = Introspector.getBeanInfo(clz);
        PropertyDescriptor[] tmp_props = beanInfo.getPropertyDescriptors();
        int len = 0;
        for(PropertyDescriptor tmp_prop:tmp_props){
            if(tmp_prop.getPropertyType() == Class.class){
                continue;
            }
            Field field = clz.getDeclaredField(tmp_prop.getName());
            if(field.getAnnotations().length == 0){
                continue;
            }
            len++;
        }
        PropertyDescriptor[] props = new PropertyDescriptor[len];
        for(PropertyDescriptor tmp_prop:tmp_props){
            if(tmp_prop.getPropertyType() == Class.class){
                continue;
            }
            Field field = clz.getDeclaredField(tmp_prop.getName());
            MyByte myByte = field.getAnnotation(MyByte.class);
            if(myByte != null){
                props[myByte.index()] = tmp_prop;
                continue;
            }
            ByteArray byteArray = field.getAnnotation(ByteArray.class);
            if(byteArray != null){
                props[byteArray.index()] = tmp_prop;
                continue;
            }
            MyShort myShort = field.getAnnotation(MyShort.class);
            if(myShort != null){
                props[myShort.index()] = tmp_prop;
                continue;
            }
            ShortArray shortArray = field.getAnnotation(ShortArray.class);
            if(shortArray != null){
                props[shortArray.index()] = tmp_prop;
                continue;
            }
            MyInt myInt = field.getAnnotation(MyInt.class);
            if(myInt != null){
                props[myInt.index()] = tmp_prop;
                continue;
            }
            IntArray intArray = field.getAnnotation(IntArray.class);
            if(intArray != null){
                props[intArray.index()] = tmp_prop;
                continue;
            }
            MyLong myLong = field.getAnnotation(MyLong.class);
            if(myLong != null){
                props[myLong.index()] = tmp_prop;
                continue;
            }
            LongArray longArray = field.getAnnotation(LongArray.class);
            if(longArray != null){
                props[longArray.index()] = tmp_prop;
                continue;
            }
            MyFloat myFloat = field.getAnnotation(MyFloat.class);
            if(myFloat != null){
                props[myFloat.index()] = tmp_prop;
                continue;
            }
            FloatArray floatArray = field.getAnnotation(FloatArray.class);
            if(floatArray != null){
                props[floatArray.index()] = tmp_prop;
                continue;
            }

            MyDouble myDouble = field.getAnnotation(MyDouble.class);
            if(myDouble != null){
                props[myDouble.index()] = tmp_prop;
                continue;
            }
            DoubleArray doubleArray = field.getAnnotation(DoubleArray.class);
            if(doubleArray != null){
                props[doubleArray.index()] = tmp_prop;
                continue;
            }
            MyString myString = field.getAnnotation(MyString.class);
            if(myString != null){
                props[myString.index()] = tmp_prop;
                continue;
            }
            StringArray stringArray = field.getAnnotation(StringArray.class);
            if(stringArray != null){
                props[stringArray.index()] = tmp_prop;
            }
            WString wString = field.getAnnotation(WString.class);
            if(wString != null){
                props[wString.index()] = tmp_prop;
                continue;
            }
            WStringArray wStringArray = field.getAnnotation(WStringArray.class);
            if(wStringArray != null){
                props[wStringArray.index()] = tmp_prop;
            }
            Bool bool = field.getAnnotation(Bool.class);
            if(bool != null){
                props[bool.index()] = tmp_prop;
                continue;
            }
            BoolArray boolArray = field.getAnnotation(BoolArray.class);
            if(boolArray != null){
                props[boolArray.index()] = tmp_prop;
                continue;
            }
            Struct struct = field.getAnnotation(Struct.class);
            if(struct != null){
                props[struct.index()] = tmp_prop;
                continue;
            }
            StructArray structArray = field.getAnnotation(StructArray.class);
            if(structArray != null){
                props[structArray.index()] = tmp_prop;
                continue;
            }
        }
        return props;
    }

    public static void writeByte(byte by,RandomAccessFile w,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.writeAlign(w,MyByte.byteLen,MyByte.byteLen);
        }
        w.writeByte(by);
    }

    public static void writeByteArray(byte[] bytes,RandomAccessFile w,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.writeAlign(w,bytes.length,ByteArray.byteLen);
        }
        w.write(bytes);
    }

    public static void writeShort(short shrt,RandomAccessFile w,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.writeAlign(w,MyShort.byteLen,MyShort.byteLen);
        }
        w.write(BinaryUtils.writeShort(shrt));
    }

    public static void writeShortArray(short[] shorts,RandomAccessFile w,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.writeAlign(w, (long) shorts.length * ShortArray.byteLen,ShortArray.byteLen);
        }
        for(short shrt:shorts){
            w.write(BinaryUtils.writeShort(shrt));
        }
    }

    public static void writeInt(int i,RandomAccessFile w,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.writeAlign(w,MyInt.byteLen,MyInt.byteLen);
        }
        w.write(BinaryUtils.writeInt(i));
    }

    public static void writeIntArray(int[] ints,RandomAccessFile w,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.writeAlign(w, (long) ints.length * IntArray.byteLen,IntArray.byteLen);
        }
        for(int i:ints){
            w.write(BinaryUtils.writeInt(i));
        }
    }

    public static void writeLong(long lng,RandomAccessFile w,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.writeAlign(w,MyLong.byteLen,MyLong.byteLen);
        }
        w.write(BinaryUtils.writeLong(lng));
    }

    public static void writeLongArray(long[] longs,RandomAccessFile w,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.writeAlign(w, (long) longs.length * LongArray.byteLen,LongArray.byteLen);
        }
        for(long lng:longs){
            w.write(BinaryUtils.writeLong(lng));
        }
    }

    public static void writeFloat(float flt,RandomAccessFile w,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.writeAlign(w,MyFloat.byteLen,MyFloat.byteLen);
        }
        w.write(BinaryUtils.writeInt(Float.floatToIntBits(flt)));
    }

    public static void writeFloatArray(float[] floats,RandomAccessFile w,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.writeAlign(w, (long) floats.length * FloatArray.byteLen,FloatArray.byteLen);
        }
        for(float flt:floats){
            w.write(BinaryUtils.writeInt(Float.floatToIntBits(flt)));
        }
    }

    public static void writeDouble(double dbl,RandomAccessFile w,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.writeAlign(w, MyDouble.byteLen,MyDouble.byteLen);
        }
        w.write(BinaryUtils.writeLong(Double.doubleToLongBits(dbl)));
    }

    public static void writeDoubleArray(double[] doubles,RandomAccessFile w,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.writeAlign(w, (long) doubles.length * DoubleArray.byteLen,DoubleArray.byteLen);
        }
        for(double dbl:doubles){
            w.write(BinaryUtils.writeLong(Double.doubleToLongBits(dbl)));
        }
    }

    public static void writeString(String str,int strLength,String charset,RandomAccessFile w,ByteAlign byteAlign) throws Exception {
        byte[] strBytes = str.getBytes(charset);
        byte[] bytes = null;
        if(strLength == 0){
            bytes = new byte[strBytes.length + MyString.byteLen];
        }else{
            if(strBytes.length > strLength){
                throw new Exception("String byte length exceeds the specified length");
            }
            bytes = new byte[strLength];
        }
        System.arraycopy(strBytes, 0, bytes, 0, strBytes.length);
        if(byteAlign != null){
            byteAlign.writeAlign(w,bytes.length,MyString.byteLen);
        }
        w.write(bytes);
    }

    public static void writeStringArray(String[] strings,int strLength,String charset,RandomAccessFile w,ByteAlign byteAlign) throws Exception {
        for(String str:strings){
            writeString(str,strLength,charset,w,byteAlign);
        }
    }

    public static void writeWString(String wstr,int strLength,String charset,RandomAccessFile w,ByteAlign byteAlign) throws Exception {
        byte[] wstrBytes = wstr.getBytes(charset);
        byte[] wbytes = null;
        if(strLength == 0){
            wbytes = new byte[wstrBytes.length + WString.byteLen];
        }else{
            if(wstrBytes.length > strLength * WString.byteLen){
                throw new Exception("WString byte length exceeds the specified length");
            }
            wbytes = new byte[strLength * WString.byteLen];
        }
        System.arraycopy(wstrBytes, 0, wbytes, 0, wstrBytes.length);
        if(byteAlign != null){
            byteAlign.writeAlign(w,wbytes.length,WString.byteLen);
        }
        w.write(wbytes);
    }

    public static void writeWStringArray(String[] wstrings,int strLength,String charset,RandomAccessFile w,ByteAlign byteAlign) throws Exception {
        for (String wstr:wstrings){
            writeWString(wstr,strLength,charset,w,byteAlign);
        }
    }

    public static void writeBool(boolean bl,RandomAccessFile w,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.writeAlign(w,Bool.byteLen,Bool.byteLen);
        }
        w.writeBoolean(bl);
    }

    public static void writeBoolArray(boolean[] booleans,RandomAccessFile w,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.writeAlign(w,booleans.length * Bool.byteLen,Bool.byteLen);
        }
        for (boolean bl:booleans){
            w.writeBoolean(bl);
        }
    }

    public static <T> void writeStruct(RandomAccessFile w, T t,ByteAlign byteAlign) throws Exception {
        if(!isStruct(t.getClass())){
            throw new Exception("Content is not a Struct");
        }
        if(byteAlign != null){
            byteAlign.writeAlign(w,0,getStructAlignLength(t.getClass()));
        }
        PropertyDescriptor[] props = getProps(t.getClass());
        for(PropertyDescriptor prop:props){
            Field field = t.getClass().getDeclaredField(prop.getName());

            MyByte myByte = field.getAnnotation(MyByte.class);
            if(myByte != null){
                writeByte((byte)prop.getReadMethod().invoke(t),w,byteAlign);
                continue;
            }
            ByteArray byteArray = field.getAnnotation(ByteArray.class);
            if(byteArray != null){
                byte[] bytes = new byte[byteArray.length()];
                byte[] mbytes = (byte[]) prop.getReadMethod().invoke(t);
                if(mbytes != null){
                    if(mbytes.length > bytes.length){
                        throw new Exception("Byte array exceeds the specified length");
                    }
                    System.arraycopy(mbytes,0,bytes, 0, mbytes.length);
                }
                writeByteArray(bytes,w,byteAlign);
                continue;
            }


            MyShort myShort = field.getAnnotation(MyShort.class);
            if(myShort != null){
                writeShort((short) prop.getReadMethod().invoke(t),w,byteAlign);
                continue;
            }
            ShortArray shortArray = field.getAnnotation(ShortArray.class);
            if(shortArray != null){
                short[] shorts = new short[shortArray.length()];
                short[] mshorts = (short[]) prop.getReadMethod().invoke(t);
                if(mshorts != null){
                    if(mshorts.length > shorts.length){
                        throw new Exception("Short array exceeds the specified length");
                    }
                    System.arraycopy(mshorts,0,shorts, 0, mshorts.length);
                }
                writeShortArray(shorts,w,byteAlign);
                continue;
            }


            MyInt myInt = field.getAnnotation(MyInt.class);
            if(myInt != null){
                writeInt((int) prop.getReadMethod().invoke(t),w,byteAlign);
                continue;
            }
            IntArray intArray = field.getAnnotation(IntArray.class);
            if(intArray != null){
                int[] ints = new int[intArray.length()];
                int[] mints = (int[]) prop.getReadMethod().invoke(t);
                if(mints != null){
                    if(mints.length > ints.length){
                        throw new Exception("Int array exceeds the specified length");
                    }
                    System.arraycopy(mints,0,ints, 0, mints.length);
                }
                writeIntArray(ints,w,byteAlign);
                continue;
            }


            MyLong myLong = field.getAnnotation(MyLong.class);
            if(myLong != null){
                writeLong((long) prop.getReadMethod().invoke(t),w,byteAlign);
                continue;
            }
            LongArray longArray = field.getAnnotation(LongArray.class);
            if(longArray != null){
                long[] longs = new long[longArray.length()];
                long[] mlongs = (long[]) prop.getReadMethod().invoke(t);
                if(mlongs != null){
                    if(mlongs.length > longs.length){
                        throw new Exception("Long array exceeds the specified length");
                    }
                    System.arraycopy(mlongs,0,longs, 0, mlongs.length);
                }
                writeLongArray(longs,w,byteAlign);
                continue;
            }

            MyFloat myFloat = field.getAnnotation(MyFloat.class);
            if(myFloat != null){
                writeFloat((float) prop.getReadMethod().invoke(t),w,byteAlign);
                continue;
            }
            FloatArray floatArray = field.getAnnotation(FloatArray.class);
            if(floatArray != null){
                float[] floats = new float[floatArray.length()];
                float[] mfloats = (float[]) prop.getReadMethod().invoke(t);
                if(mfloats != null){
                    if(mfloats.length > floats.length){
                        throw new Exception("Float array exceeds the specified length");
                    }
                    System.arraycopy(mfloats,0,floats, 0, mfloats.length);
                }
                writeFloatArray(floats,w,byteAlign);
                continue;
            }

            MyDouble myDouble = field.getAnnotation(MyDouble.class);
            if(myDouble != null){
                writeDouble((double) prop.getReadMethod().invoke(t),w,byteAlign);
                continue;
            }
            DoubleArray doubleArray = field.getAnnotation(DoubleArray.class);
            if(doubleArray != null){
                double[] doubles = new double[doubleArray.length()];
                double[] mdoubles = (double[]) prop.getReadMethod().invoke(t);
                if(mdoubles != null){
                    if(mdoubles.length > doubles.length){
                        throw new Exception("Double array exceeds the specified length");
                    }
                    System.arraycopy(mdoubles,0,doubles, 0, mdoubles.length);
                }
                writeDoubleArray(doubles,w,byteAlign);
                continue;
            }

            MyString myString = field.getAnnotation(MyString.class);
            if(myString != null){
                writeString((String) prop.getReadMethod().invoke(t),myString.strLength(),myString.charset(),w,byteAlign);
                continue;
            }
            StringArray stringArray = field.getAnnotation(StringArray.class);
            if(stringArray != null){
                String[] strings = new String[stringArray.length()];
                String[] mstrings = (String[]) prop.getReadMethod().invoke(t);
                if(mstrings != null){
                    if(mstrings.length > strings.length){
                        throw new Exception("String array exceeds the specified length");
                    }
                    System.arraycopy(mstrings,0,strings, 0, mstrings.length);
                }
                writeStringArray(strings,stringArray.strLength(),stringArray.charset(),w,byteAlign);
                continue;
            }

            WString wString = field.getAnnotation(WString.class);
            if(wString != null){
                writeWString((String) prop.getReadMethod().invoke(t),wString.strLength(),wString.charset(),w,byteAlign);
                continue;
            }
            WStringArray wStringArray = field.getAnnotation(WStringArray.class);
            if(wStringArray != null){
                String[] wstrings = new String[wStringArray.length()];
                String[] mwstrings = (String[]) prop.getReadMethod().invoke(t);
                if(mwstrings != null){
                    if(mwstrings.length > wstrings.length){
                        throw new Exception("WString array exceeds the specified length");
                    }
                    System.arraycopy(mwstrings,0,wstrings, 0, mwstrings.length);
                }
                writeWStringArray(wstrings, wStringArray.strLength(),wStringArray.charset(),w,byteAlign);
                continue;
            }
            Bool bool = field.getAnnotation(Bool.class);
            if(bool != null){
                writeBool((boolean) prop.getReadMethod().invoke(t),w,byteAlign);
                continue;
            }
            BoolArray boolArray = field.getAnnotation(BoolArray.class);
            if(boolArray != null){
                boolean[] booleans = new boolean[boolArray.length()];
                boolean[] mbooleans = (boolean[]) prop.getReadMethod().invoke(t);
                if(mbooleans != null){
                    if(mbooleans.length > booleans.length){
                        throw new Exception("Boolean array exceeds the specified length");
                    }
                    System.arraycopy(mbooleans,0,booleans, 0, mbooleans.length);
                }
                writeBoolArray(booleans,w,byteAlign);
                continue;
            }

            Struct struct = field.getAnnotation(Struct.class);
            if(struct != null){
                Object obj = prop.getReadMethod().invoke(t);
                if(obj == null){
                    obj = prop.getPropertyType().newInstance();
                }
                writeStruct(w,obj,byteAlign);
                continue;
            }
            StructArray structArray = field.getAnnotation(StructArray.class);
            if(structArray != null){
                Object[] objs = new Object[structArray.length()];
                Object[] mobjs = (Object[]) prop.getReadMethod().invoke(t);
                if(mobjs != null){
                    if(mobjs.length > objs.length){
                        throw new Exception("Struct array exceeds the specified length");
                    }
                    System.arraycopy(mobjs,0,objs, 0, mobjs.length);
                }
                for(Object obj:objs){
                    if(obj == null){
                        obj = prop.getPropertyType().getComponentType().newInstance();
                    }
                    writeStruct(w,obj,byteAlign);
                }
                continue;
            }
        }
    }

    public static <T> void writeStructAutoAlign(RandomAccessFile w,T t) throws Exception {
        ByteAlign byteAlign = new ByteAlign();
        writeStruct(w,t,byteAlign);
        byteAlign.endWriteAlign(w);
    }


    public static byte readByte(RandomAccessFile r,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.readAlign(r, MyByte.byteLen,MyByte.byteLen);
        }
        return r.readByte();
    }

    public static byte[] readByteArray(RandomAccessFile r,int length,ByteAlign byteAlign) throws IOException {
        byte[] bytes = new byte[length];
        if(byteAlign != null){
            byteAlign.readAlign(r,length,ByteArray.byteLen);
        }
        r.read(bytes);
        return bytes;
    }

    public static short readShort(RandomAccessFile r,ByteAlign byteAlign) throws IOException{
        if(byteAlign != null){
            byteAlign.readAlign(r, MyShort.byteLen,MyShort.byteLen);
        }
        return BinaryUtils.readShort(r);
    }

    public static short[] readShortArry(RandomAccessFile r,int length,ByteAlign byteAlign) throws IOException{
        if(byteAlign != null){
            byteAlign.readAlign(r, (long) length * ShortArray.byteLen,ShortArray.byteLen);
        }
        short[] shorts = new short[length];
        for(int i=0;i<shorts.length;i++){
            shorts[i] = BinaryUtils.readShort(r);
        }
        return shorts;
    }

    public static int readInt(RandomAccessFile r,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.readAlign(r, MyInt.byteLen,MyInt.byteLen);
        }
        return BinaryUtils.readInt(r);
    }

    public static int[] readIntArray(RandomAccessFile r,int length,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.readAlign(r, (long) length * IntArray.byteLen,IntArray.byteLen);
        }
        int[] ints = new int[length];
        for(int i=0;i<ints.length;i++){
            ints[i] = BinaryUtils.readInt(r);
        }
        return ints;
    }

    public static long readLong(RandomAccessFile r,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.readAlign(r,MyLong.byteLen,MyLong.byteLen);
        }
        return BinaryUtils.readLong(r);
    }

    public static long[] readLongArray(RandomAccessFile r,int length,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.readAlign(r, (long) length * LongArray.byteLen,LongArray.byteLen);
        }
        long[] longs = new long[length];
        for(int i=0;i<longs.length;i++){
            longs[i] = BinaryUtils.readLong(r);
        }
        return longs;
    }

    public static float readFloat(RandomAccessFile r,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.readAlign(r, MyFloat.byteLen, MyFloat.byteLen);
        }
        return BinaryUtils.readFloat(r);
    }

    public static float[] readFloatArray(RandomAccessFile r,int length,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.readAlign(r, (long) length * FloatArray.byteLen,FloatArray.byteLen);
        }
        float[] floats = new float[length];
        for(int i=0;i<floats.length;i++){
            floats[i] = BinaryUtils.readFloat(r);
        }
        return floats;
    }

    public static double readDouble(RandomAccessFile r,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.readAlign(r,MyDouble.byteLen,MyDouble.byteLen);
        }
        return BinaryUtils.readDouble(r);
    }

    public static double[] readDoubleArray(RandomAccessFile r,int length,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.readAlign(r, (long) length * DoubleArray.byteLen,DoubleArray.byteLen);
        }
        double[] doubles = new double[length];
        for(int i=0;i<doubles.length;i++){
            doubles[i] = BinaryUtils.readDouble(r);
        }
        return doubles;
    }

    public static String readString(RandomAccessFile r,int strLength,String charset,ByteAlign byteAlign) throws IOException {
        byte[] bytes = null;
        if(strLength == 0){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte buff;
            while ((buff = r.readByte()) != 0){
                byteArrayOutputStream.write(buff);
            }
            byteArrayOutputStream.write(buff);
            bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        }else{
            bytes = new byte[strLength];
            r.read(bytes);
        }
        if(byteAlign != null){
            byteAlign.readAlign(r,bytes.length,MyString.byteLen);
        }
        int endpos = 0;
        for(int i=0;i<bytes.length;i++){
            if(bytes[i] == 0){
                endpos = i;
                break;
            }
        }
        if(endpos == 0){
            return "";
        }else{
            if(charset == null || charset.equals("")){
                charset = Charset.defaultCharset().name();
            }
            return new String(bytes,0,endpos, Charset.forName(charset));
        }
    }

    public static String[] readStringArray(RandomAccessFile r,int length,int strLength,String charset,ByteAlign byteAlign) throws IOException {
        String[] strings = new String[length];
        for(int i=0;i<strings.length;i++){
            strings[i] = readString(r,strLength,charset,byteAlign);
        }
        return strings;
    }

    public static String readWString(RandomAccessFile r,int strLength,String charset,ByteAlign byteAlign) throws IOException {
        byte[] wbytes = null;
        if(strLength == 0){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buff = new byte[2];
            while (r.read(buff) != -1){
                byteArrayOutputStream.write(buff);
                if(buff[0] == 0 && buff[1] == 0){
                    break;
                }
            }
            wbytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        }else{
            wbytes = new byte[strLength * WString.byteLen];
            r.read(wbytes);
        }
        if(byteAlign != null){
            byteAlign.readAlign(r,wbytes.length,WString.byteLen);
        }
        int endpos = 0;
        for(int i=0;i<wbytes.length;i+=2){
            if(wbytes[i] == 0 && wbytes[i + 1] == 0){
                endpos = i;
                break;
            }
        }
        if(endpos == 0){
            return "";
        }else{
            if(charset == null || charset.equals("")){
                charset = StandardCharsets.UTF_16LE.name();
            }
            return new String(wbytes,0,endpos, Charset.forName(charset));
        }
    }

    public static String[] readWStringArray(RandomAccessFile r,int length,int strLength,String charset,ByteAlign byteAlign) throws IOException {
        String[] wstrings = new String[length];
        for(int i=0;i<wstrings.length;i++){
            wstrings[i] = readWString(r,strLength,charset,byteAlign);
        }
        return wstrings;
    }

    public static boolean readBool(RandomAccessFile r,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.readAlign(r,Bool.byteLen,Bool.byteLen);
        }
        return r.readBoolean();
    }

    public static boolean[] readBoolArray(RandomAccessFile r,int length,ByteAlign byteAlign) throws IOException {
        if(byteAlign != null){
            byteAlign.readAlign(r, length * BoolArray.byteLen,BoolArray.byteLen);
        }
        boolean[] booleans = new boolean[length];
        for(int i=0;i<booleans.length;i++){
            booleans[i] = r.readBoolean();
        }
        return booleans;
    }

    public static <T> T readStruct(RandomAccessFile r,Class<T> clz,ByteAlign byteAlign) throws Exception {
        T obj = clz.newInstance();
        readStruct(r,obj,byteAlign);
        return obj;
    }

    public static <T> T[] readStructArray(RandomAccessFile r,int length,Class<T> clz,ByteAlign byteAlign) throws Exception {
        T[] objs = (T[]) Array.newInstance(clz, length);
        for(int i=0;i<length;i++){
            objs[i] = readStruct(r,clz,byteAlign);;
        }
        return objs;
    }

    public static <T> void readStruct(RandomAccessFile r, T t,ByteAlign byteAlign) throws Exception {
        if(!isStruct(t.getClass())){
            throw new Exception("Content is not a Struct");
        }
        if(byteAlign != null){
            byteAlign.readAlign(r,0,getStructAlignLength(t.getClass()));
        }
        PropertyDescriptor[] props = getProps(t.getClass());
        for(PropertyDescriptor prop:props){
            Field field = t.getClass().getDeclaredField(prop.getName());

            MyByte myByte = field.getAnnotation(MyByte.class);
            if(myByte != null){
                prop.getWriteMethod().invoke(t,readByte(r,byteAlign));
                continue;
            }
            ByteArray byteArray = field.getAnnotation(ByteArray.class);
            if(byteArray != null){
                prop.getWriteMethod().invoke(t,readByteArray(r,byteArray.length(),byteAlign));
                continue;
            }

            MyShort myShort = field.getAnnotation(MyShort.class);
            if(myShort != null){
                prop.getWriteMethod().invoke(t,readShort(r,byteAlign));
                continue;
            }
            ShortArray shortArray = field.getAnnotation(ShortArray.class);
            if(shortArray != null){
                prop.getWriteMethod().invoke(t,readShortArry(r,shortArray.length(),byteAlign));
                continue;
            }

            MyInt myInt = field.getAnnotation(MyInt.class);
            if(myInt != null){
                prop.getWriteMethod().invoke(t,readInt(r,byteAlign));
                continue;
            }
            IntArray intArray = field.getAnnotation(IntArray.class);
            if(intArray != null){
                prop.getWriteMethod().invoke(t,readIntArray(r,intArray.length(),byteAlign));
                continue;
            }

            MyLong myLong = field.getAnnotation(MyLong.class);
            if(myLong != null){
                prop.getWriteMethod().invoke(t,readLong(r,byteAlign));
                continue;
            }
            LongArray longArray = field.getAnnotation(LongArray.class);
            if(longArray != null){
                prop.getWriteMethod().invoke(t,readLongArray(r,longArray.length(),byteAlign));
                continue;
            }

            MyFloat myFloat = field.getAnnotation(MyFloat.class);
            if(myFloat != null){
                prop.getWriteMethod().invoke(t,readFloat(r,byteAlign));
                continue;
            }
            FloatArray floatArray = field.getAnnotation(FloatArray.class);
            if(floatArray != null){
                prop.getWriteMethod().invoke(t,readFloatArray(r,floatArray.length(),byteAlign));
                continue;
            }

            MyDouble myDouble = field.getAnnotation(MyDouble.class);
            if(myDouble != null){
                prop.getWriteMethod().invoke(t,readDouble(r,byteAlign));
                continue;
            }
            DoubleArray doubleArray = field.getAnnotation(DoubleArray.class);
            if(doubleArray != null){
                prop.getWriteMethod().invoke(t,readDoubleArray(r,doubleArray.length(),byteAlign));
                continue;
            }

            MyString myString = field.getAnnotation(MyString.class);
            if(myString != null){
                prop.getWriteMethod().invoke(t,readString(r,myString.strLength(),myString.charset(),byteAlign));
                continue;
            }
            StringArray stringArray = field.getAnnotation(StringArray.class);
            if(stringArray != null){
                prop.getWriteMethod().invoke(t,readStringArray(r,stringArray.length(),stringArray.strLength(),stringArray.charset(),byteAlign));
                continue;
            }

            WString wString = field.getAnnotation(WString.class);
            if(wString != null){
                prop.getWriteMethod().invoke(t,readWString(r,wString.strLength(),wString.charset(),byteAlign));
                continue;
            }
            WStringArray wStringArray = field.getAnnotation(WStringArray.class);
            if(wStringArray != null){
                prop.getWriteMethod().invoke(t,readWStringArray(r,wStringArray.length(), wStringArray.strLength(),wStringArray.charset(),byteAlign));
                continue;
            }
            Bool bool = field.getAnnotation(Bool.class);
            if(bool != null){
                prop.getWriteMethod().invoke(t,readBool(r,byteAlign));
                continue;
            }
            BoolArray boolArray = field.getAnnotation(BoolArray.class);
            if(boolArray != null){
                prop.getWriteMethod().invoke(t,readBoolArray(r,boolArray.length(),byteAlign));
                continue;
            }

            Struct struct = field.getAnnotation(Struct.class);
            if(struct != null){
                prop.getWriteMethod().invoke(t,readStruct(r,prop.getPropertyType(),byteAlign));
                continue;
            }
            StructArray structArray = field.getAnnotation(StructArray.class);
            if(structArray != null){
                prop.getWriteMethod().invoke(t,new Object[]{readStructArray(r,structArray.length(),prop.getPropertyType().getComponentType(),byteAlign)});
            }
        }
    }

    public static <T> void readStructAutoAlign(RandomAccessFile r,T t) throws Exception {
        ByteAlign byteAlign = new ByteAlign();
        readStruct(r,t,byteAlign);
        byteAlign.endReadAlign(r);
    }
}
