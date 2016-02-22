package com.example.taskb_ar;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class DebugOverlayView extends View {
	
	
	// world coordinate system
		float[] mAccel = { 0, 0, 0 };
		float[] mMagne = { 0, 0, 0 };

		float[] mWx = { 0, 0, 0 };
		float[] mWy = { 0, 0, 0 };
		float[] mWz = { 0, 0, 0 };

		float mYaw = 0.0f;
		float mPitch = 0.0f;
		float mRoll = 0.0f;

		float mCameraYaw = 0.0f;
		float mCameraPitch = 0.0f;
		float mCameraRoll = 0.0f;

		// final float WORDHEIGHT = 10.0f;
		final float WORDHEIGHT = 30.0f;
		final float LINEHEIGHT = 30.0f;

		private Location mDeviceLocation = null;
		DecimalFormat mFieldFormat = new DecimalFormat("##0.000");
		DecimalFormat mUnitVectorFormat = new DecimalFormat("0.000000");
		DecimalFormat mDegreeFormat = new DecimalFormat("##0.0000");

		private String mProvider = null;
		private String mProviderStatus = null;
		private String mAddress = null;

		private final static float MILLI_PER_INCH = 25.4f; // 1 inch = 0.0254 meter
		DisplayMetrics mMetrics = null;

		public DebugOverlayView(Context context) {
			super(context);
			DisplayMetrics metrics = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay()
					.getMetrics(metrics);
			mMetrics = metrics;
		}

		public void setDeviceLocation(Location location) {
			// logd("setDeviceLocation");
			if (location == null)
				Log.e("Location", "set null location!");
			mDeviceLocation = location;
			invalidate();
		}
		
		
		public void setAddress(String address){
			
			if (address == null)
				Log.e("Adress", "set null!");
			mAddress = address;
		}

		public void onDraw(Canvas canvas) {
			super.onDraw(canvas);

			float line = 0;
			int canvasWidth = canvas.getWidth();
			int canvasHeight = canvas.getHeight();
			Paint paint = new Paint();
			paint.setColor(Color.BLUE);
			paint.setTextSize(WORDHEIGHT);
			// 
			canvas.drawText("canvas:" + Integer.toString(canvasWidth) + "x"
					+ Integer.toString(canvasHeight), canvasWidth / 2,
					line += LINEHEIGHT, paint);
			// ��
			canvas.drawPoint(0, 0, paint);
			// �
			canvas.drawText(
					"provider:" + mProvider + ", status:" + mProviderStatus, 0,
					line += LINEHEIGHT, paint);
			canvas.drawText(
					"accel:(" + mFieldFormat.format(mAccel[0]) + ", "
							+ mFieldFormat.format(mAccel[1]) + ", "
							+ mFieldFormat.format(mAccel[2]) + ")", WORDHEIGHT,
					line += LINEHEIGHT, paint);
			canvas.drawText(
					"magne:(" + mFieldFormat.format(mMagne[0]) + ", "
							+ mFieldFormat.format(mMagne[1]) + ", "
							+ mFieldFormat.format(mMagne[2]) + ")", WORDHEIGHT,
					line += LINEHEIGHT, paint);
			canvas.drawText(
					"Wx:(" + mUnitVectorFormat.format(mWx[0]) + ", "
							+ mUnitVectorFormat.format(mWx[1]) + ", "
							+ mUnitVectorFormat.format(mWx[2]) + ")", WORDHEIGHT,
					line += LINEHEIGHT, paint);
			canvas.drawText(
					"Wy:(" + mUnitVectorFormat.format(mWy[0]) + ", "
							+ mUnitVectorFormat.format(mWy[1]) + ", "
							+ mUnitVectorFormat.format(mWx[2]) + ")", WORDHEIGHT,
					line += LINEHEIGHT, paint);
			canvas.drawText(
					"Wz:(" + mUnitVectorFormat.format(mWz[0]) + ", "
							+ mUnitVectorFormat.format(mWz[1]) + ", "
							+ mUnitVectorFormat.format(mWz[2]) + ")", WORDHEIGHT,
					line += LINEHEIGHT, paint);
			canvas.drawText("Device-Yaw  :" + mDegreeFormat.format(mYaw),
					WORDHEIGHT, line += LINEHEIGHT, paint);
			canvas.drawText("Device-Pitch:" + mDegreeFormat.format(mPitch),
					WORDHEIGHT, line += LINEHEIGHT, paint);
			canvas.drawText("Device-Roll :" + mDegreeFormat.format(mRoll),
					WORDHEIGHT, line += LINEHEIGHT, paint);
			canvas.drawText("Camera-Yaw  :" + mDegreeFormat.format(mCameraYaw),
					WORDHEIGHT, line += LINEHEIGHT, paint);
			canvas.drawText("Camera-Pitch:" + mDegreeFormat.format(mCameraPitch),
					WORDHEIGHT, line += LINEHEIGHT, paint);
			canvas.drawText("Camera-Roll :" + mDegreeFormat.format(mCameraRoll),
					WORDHEIGHT, line += LINEHEIGHT, paint);
			canvas.drawText("dpmm=" + mMetrics.densityDpi / MILLI_PER_INCH,
					WORDHEIGHT * 20, line, paint);
			if (mDeviceLocation != null) {
				canvas.drawText("My lat:"
						+ mDeviceLocation.getLatitude() + "My loc:"
						+ mDeviceLocation.getLongitude(), WORDHEIGHT,
						line += LINEHEIGHT, paint);
			}
			// add the address from GeoCode 
			canvas.drawText("Adress =" + mAddress,
					WORDHEIGHT,
					line += LINEHEIGHT, paint);
			

		}

		public void setVectorFields(float[] accel, float[] magne) {
			mAccel = accel;
			mMagne = magne;
		}

		public void setWorldCoordinateSystem(float[] Wx, float[] Wy, float[] Wz) {
			mWx = Wx;
			mWy = Wy;
			mWz = Wz;
			invalidate();
		}

		public void setDeviceOrientation(float yaw, float pitch, float roll) {
			mYaw = yaw;
			mPitch = pitch;
			mRoll = roll;
			invalidate();
		}

		public void setCameraOrientation(float yaw, float pitch, float roll) {
			mCameraYaw = yaw;
			mCameraPitch = pitch;
			mCameraRoll = roll;
			invalidate();
		}

		public void setProviderInfo(String provider, String status) {
			mProvider = provider;
			mProviderStatus = status;
		}

}
