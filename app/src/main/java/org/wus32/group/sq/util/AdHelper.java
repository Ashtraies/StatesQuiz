package org.wus32.group.sq.util;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * StatesQuiz
 * <p>
 * Created by Wu Shuang on 21/12/2017.
 */

public class AdHelper extends AdListener {
  
  public static void LoadAd(AdView adView) {
    adView.setAdListener(new AdHelper());
    AdRequest adRequest = new AdRequest.Builder().build();
    adView.loadAd(adRequest);
  }
  
  @Override
  public void onAdFailedToLoad(int errorCode) {
    switch (errorCode) {
      case AdRequest.ERROR_CODE_INTERNAL_ERROR:
        Debug.log("############################# ERROR_CODE_INTERNAL_ERROR");
        break;
      case AdRequest.ERROR_CODE_INVALID_REQUEST:
        Debug.log("############################# ERROR_CODE_INVALID_REQUEST");
        break;
      case AdRequest.ERROR_CODE_NETWORK_ERROR:
        Debug.log("############################# ERROR_CODE_NETWORK_ERROR");
        break;
      case AdRequest.ERROR_CODE_NO_FILL:
        Debug.log("############################# ERROR_CODE_NO_FILL");
        break;
    }
  }
}
