package com.google.android.exoplayer2.demo;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;
import xcrash.XCrash;
import androidx.multidex.MultiDexApplication;

public class App extends MultiDexApplication {


  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
//    xcrash.XCrash.init(this,xcrash.XCrash.InitParameters().apply {
//      setLogDir(base?.externalCacheDir?.path)
//    })
    XCrash.init(this,new XCrash.InitParameters().setLogDir(base.getExternalCacheDir().getPath()));
  }

  @Override
  public void onCreate() {
    super.onCreate();
    MultiDex.install(this);
  }
}
