package com.example.taskb_ar;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreviewView extends SurfaceView implements
		SurfaceHolder.Callback {

	private final String LOG_TAG = "CameraPreview";
	private Camera mCamera = null;

	private void logd(final String str) {
		Log.d(LOG_TAG, str);
	}

	public CameraPreviewView(Context context) {
		super(context);
		logd("Constructor");
		// SurfaceHolder
		SurfaceHolder surfaceHolder = getHolder();
		// SurfaceHolderCallBack
		surfaceHolder.addCallback(this);
		// SurfaceHolder
		// surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// logd("surfaceChanged");
		// ����
		mCamera.stopPreview();
		mCamera.setDisplayOrientation(90);
		mCamera.startPreview();
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		logd("surfaceCreated");
		// ����
		mCamera = Camera.open();
		// mCamera = android.hardware.Camera.open();
		if (mCamera == null) {

			Log.e("Camera", "my camera is null...");

		}
		try {
			mCamera.setPreviewDisplay(arg0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		logd("surfaceDestroyed");
		// ����
		try {
			mCamera.setPreviewDisplay(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ����
		mCamera.release();
	}

}
