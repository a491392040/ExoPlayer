package com.migu.vivid;


public class AudioVividNative {

    static {
        System.loadLibrary("miguvividd");
    }


    //使用Android Studio中的cmake打包so文件的测试函数。
    public static native long migu_vivid_init(int louderSpeakerType);
    public static native int migu_vivid_write_data(byte[] inputData, int inputLength);
    public static native int migu_vivid_decode();
    public static native byte[] migu_vivid_read_result_data();
    public static native int get_decode_bit_per();
    public static native int get_decode_rate();
    public static native int get_decode_channel_number();            //获取声道配置，比如714，512这种
    public static native int get_decode_result_len();      //获取位深//当解码中有元数据时，该函数才有返回值。
    public static native int get_decode_channel_type();
    public static native byte[] migu_vivid_render_ambisonics(byte[] inputData, int inputChannels,int length,int type,int nByte,int rate);
    public static native int migu_vivid_close(long handlerAddress);
    public static native byte[] migu_vivid_main_process(long handler,byte[] inputData, int inputLength);
    public static native byte[] migu_vivid_main_panner_process(long handler,byte[] inputData, int inputLength);
}
