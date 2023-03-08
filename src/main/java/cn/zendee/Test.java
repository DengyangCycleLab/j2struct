package cn.zendee;

import cn.zendee.j2struct.J2Struct;
import cn.zendee.j2struct.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.RandomAccessFile;

public class Test {


    @Struct(index = 0)
    @Getter
    @Setter
    public static class StructB{
        @MyShort(index = 0)
        private short height;
        @MyFloat(index = 1)
        private float weight;
    }

    @Struct(index = 0)
    @Getter
    @Setter
    public static class StructA{
        @MyString(index = 0)
        private String name;
        @MyInt(index = 1)
        private int age;
        @Struct(index = 2)
        private StructB imB;
    }

    public static void main(String[] args) throws Exception {
        RandomAccessFile r = new RandomAccessFile("DIEYI", "r");
        StructA a = new StructA();
        J2Struct.readStructAutoAlign(r,a);
        r.close();
    }

}
