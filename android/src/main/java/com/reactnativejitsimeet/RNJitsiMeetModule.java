package com.reactnativejitsimeet;

import android.util.Log;
import java.net.URL;
import java.net.MalformedURLException;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.bridge.ReadableMap;

@ReactModule(name = RNJitsiMeetModule.MODULE_NAME)
public class RNJitsiMeetModule extends ReactContextBaseJavaModule {
    public static final String MODULE_NAME = "RNJitsiMeetModule";
    private IRNJitsiMeetViewReference mJitsiMeetViewReference;

    public RNJitsiMeetModule(ReactApplicationContext reactContext, IRNJitsiMeetViewReference jitsiMeetViewReference) {
        super(reactContext);
        mJitsiMeetViewReference = jitsiMeetViewReference;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void initialize() {
        Log.d("JitsiMeet", "Initialize is deprecated in v2");
    }

    @ReactMethod
    public void call(String url, ReadableMap userInfo, ReadableMap params) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mJitsiMeetViewReference.getJitsiMeetView() != null) {
                    RNJitsiMeetUserInfo _userInfo = new RNJitsiMeetUserInfo();
                    if (userInfo != null) {
                        if (userInfo.hasKey("displayName")) {
                            _userInfo.setDisplayName(userInfo.getString("displayName"));
                          }
                          if (userInfo.hasKey("email")) {
                            _userInfo.setEmail(userInfo.getString("email"));
                          }
                          if (userInfo.hasKey("avatar")) {
                            String avatarURL = userInfo.getString("avatar");
                            try {
                                _userInfo.setAvatar(new URL(avatarURL));
                            } catch (MalformedURLException e) {
                            }
                          }
                    }
                    RNJitsiMeetConferenceOptions.Builder builder = new RNJitsiMeetConferenceOptions.Builder()
                            .setRoom(url)
                            .setAudioOnly(false)
                            .setUserInfo(_userInfo);

                    
                   
                        

                    if (params != null) {
                        if (params.hasKey("jwt")) {
                            builder.setToken(params.getString("jwt"));
                        }
                        if (params.hasKey("subject")) {
                            builder.setSubject(params.getString("subject"));
                        }
                        if (params.hasKey("chat.enabled")) {
                            builder.setFeatureFlag("add-people.enabled", false);
                            builder.setFeatureFlag("calendar.enabled", false);
                            builder.setFeatureFlag("invite.enabled", false);
                            builder.setFeatureFlag("ios.recording.enabled", false);
                            builder.setFeatureFlag("kick-out.enabled", false);
                            builder.setFeatureFlag("meeting-name.enabled", false);
                            builder.setFeatureFlag("meeting-password.enabled", false);
                            builder.setFeatureFlag("recording.enabled", false);
                            builder.setFeatureFlag("server-url-change.enabled", false);
                            //builder.setFeatureFlag("toolbox.enabled", false);
                            builder.setFeatureFlag("video-share.enabled", false);
                            builder.setFeatureFlag("calendar.enabled", false);
                            builder.setFeatureFlag("chat.enabled", false);
                        }
                    }
                    RNJitsiMeetConferenceOptions options = builder.build();

                    mJitsiMeetViewReference.getJitsiMeetView().join(options);
                }
            }
        });
    }

    @ReactMethod
    public void audioCall(String url, ReadableMap userInfo) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mJitsiMeetViewReference.getJitsiMeetView() != null) {
                    RNJitsiMeetUserInfo _userInfo = new RNJitsiMeetUserInfo();
                    if (userInfo != null) {
                        if (userInfo.hasKey("displayName")) {
                            _userInfo.setDisplayName(userInfo.getString("displayName"));
                          }
                          if (userInfo.hasKey("email")) {
                            _userInfo.setEmail(userInfo.getString("email"));
                          }
                          if (userInfo.hasKey("avatar")) {
                            String avatarURL = userInfo.getString("avatar");
                            try {
                                _userInfo.setAvatar(new URL(avatarURL));
                            } catch (MalformedURLException e) {
                            }
                          }
                    }
                    RNJitsiMeetConferenceOptions options = new RNJitsiMeetConferenceOptions.Builder()
                            .setRoom(url)
                            .setAudioOnly(true)
                            .setUserInfo(_userInfo)
                            .build();
                    mJitsiMeetViewReference.getJitsiMeetView().join(options);
                }
            }
        });
    }

    @ReactMethod
    public void endCall() {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mJitsiMeetViewReference.getJitsiMeetView() != null) {
                    mJitsiMeetViewReference.getJitsiMeetView().leave();
                }
            }
        });
    }
}
