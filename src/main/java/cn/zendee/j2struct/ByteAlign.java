package cn.zendee.j2struct;

import lombok.Getter;

import java.io.IOException;
import java.io.RandomAccessFile;

@Getter
public class ByteAlign {

    private int defaultAlignLength = 4;
    private long totalSize = 0;

    public ByteAlign(){

    }
    public ByteAlign(int defaultAlignLength){
        this.defaultAlignLength = defaultAlignLength;
    }

    private long analysisAlignLen(int alignLength){
        long len = 0;
        int sz = Math.min(defaultAlignLength,alignLength);
        long x = totalSize % sz;
        if(x != 0){
            len = sz - x;
        }
        totalSize += len;
        return len;
    }

    public void writeAlign(RandomAccessFile w,long dataLength,int alignLength) throws IOException {
        long len = analysisAlignLen(alignLength);
        for(int i=0;i<len;i++){
            w.write(-52);
        }
        totalSize += dataLength;
    }

    public void readAlign(RandomAccessFile r,long dataLength,int alignLength) throws IOException {
        long len = analysisAlignLen(alignLength);
        if(len > 0){
            r.skipBytes((int) len);
        }
        totalSize += dataLength;
    }

    public void endReadAlign(RandomAccessFile r) throws IOException {
        long len = analysisAlignLen(defaultAlignLength);
        if(len > 0){
            r.skipBytes((int) len);
        }
    }

    public void endWriteAlign(RandomAccessFile w) throws IOException {
        long len = analysisAlignLen(defaultAlignLength);
        for(int i=0;i<len;i++){
            w.write(-52);
        }
    }
}
