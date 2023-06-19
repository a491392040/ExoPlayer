package com.caixun.vivid;

import com.google.android.exoplayer2.decoder.DecoderException;

public class VividDecoderException extends DecoderException {

  public VividDecoderException(String message) {
    super(message);
  }

  public VividDecoderException(Throwable cause) {
    super(cause);
  }

  public VividDecoderException(String message, Throwable cause) {
    super(message, cause);
  }
}
