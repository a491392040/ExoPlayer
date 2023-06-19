package com.caixun.vivid;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.DecoderAudioRenderer;
import com.google.android.exoplayer2.decoder.CryptoConfig;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;

public class VividRender extends DecoderAudioRenderer<VividDecoder> {

  public static final String TAG = "VividRender";
  private static final int NUM_BUFFERS = 16;
  /** The default input buffer size. */
  private static final int DEFAULT_INPUT_BUFFER_SIZE = 960 * 6;

  @Override
  public String getName() {
    return TAG;
  }



  @Override
  protected int supportsFormatInternal(Format format) {

    Log.e(TAG,"isSupport type="+format.sampleMimeType);
    if(format.sampleMimeType!=null&&format.sampleMimeType.contains("vivid")){
      return C.FORMAT_HANDLED;
    }
    return C.FORMAT_UNSUPPORTED_TYPE;
  }

  @Override
  protected VividDecoder createDecoder(Format format, CryptoConfig cryptoConfig) {
    Log.e(TAG,"create coder");
    return new VividDecoder(NUM_BUFFERS,NUM_BUFFERS);
  }

  @Override
  protected Format getOutputFormat(VividDecoder decoder) {
    return new Format.Builder()
        .setSampleMimeType(MimeTypes.AUDIO_RAW)
        .setChannelCount(decoder.getChannelCount())
        .setSampleRate(decoder.getSampleRate())
        .setPcmEncoding(decoder.getEncoding())
        .build();
  }
}
