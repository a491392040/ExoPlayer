package com.caixun.vivid;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.SimpleDecoder;
import com.google.android.exoplayer2.decoder.SimpleDecoderOutputBuffer;
import com.google.android.exoplayer2.util.Log;
import com.migu.audiovivid.AudioVividNative;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.FileHandler;

public class VividDecoder extends SimpleDecoder<DecoderInputBuffer, SimpleDecoderOutputBuffer,VividDecoderException> {
  private long handler = 0;
  private static final int  LOUDER_SPEAKER_TYPE_51 = 51;
  private static final int  LOUDER_SPEAKER_TYPE_71 = 71;

  private static final String path = "/storage/emulated/0/"+System.currentTimeMillis()+".txt";
  protected VividDecoder(
      int numInputBuffers,
      int numOutputBuffers
     ) {
    super(new DecoderInputBuffer[numInputBuffers], new SimpleDecoderOutputBuffer[numOutputBuffers]);
    handler = AudioVividNative.migu_vivid_init(LOUDER_SPEAKER_TYPE_71);
  }

  @Override
  public String getName() {
    return "vivid";
  }

  @Override
  protected DecoderInputBuffer createInputBuffer() {
    return new DecoderInputBuffer(
        DecoderInputBuffer.BUFFER_REPLACEMENT_MODE_DIRECT,
        0);
  }

  @Override
  protected SimpleDecoderOutputBuffer createOutputBuffer() {
    return new SimpleDecoderOutputBuffer(this::releaseOutputBuffer);
  }

  @Override
  protected VividDecoderException createUnexpectedDecodeException(Throwable error) {
    return new VividDecoderException("decoder error :"+error);
  }

  @Override
  protected VividDecoderException decode(DecoderInputBuffer inputBuffer,
      SimpleDecoderOutputBuffer outputBuffer, boolean reset) {
    Log.e("ares","start decode");

    try {

      FileOutputStream fos = new FileOutputStream(path);
      byte[] ss = inputBuffer.data.array();
      fos.write(ss,0,ss.length);
      fos.flush();
      fos.close();
      Log.e("ares","write success");
    }catch (Exception e){
      e.printStackTrace();
    }

    byte[] bytes = AudioVividNative.migu_vivid_main_process(handler,inputBuffer.data.array(), inputBuffer.data.limit());
    ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder());
    buffer.put(bytes);
    buffer.flip();
    outputBuffer.data = buffer;
    outputBuffer.data.position(0);
    outputBuffer.data.limit(bytes.length);
    return null;
  }

  @Override
  public void release() {
    Log.e("ares","start release");
    super.release();
    AudioVividNative.migu_vivid_close(handler);
  }

  public int getChannelCount() {
    Log.e("ares","getChannelCount = "+AudioVividNative.get_decode_channel_number());
    return AudioVividNative.get_decode_channel_number();
  }

  public int getSampleRate() {
    Log.e("ares","getSampleRate = "+AudioVividNative.get_decode_rate());
    return AudioVividNative.get_decode_rate();
  }

  public int getEncoding() {
    return C.ENCODING_PCM_16BIT;
  }
}
