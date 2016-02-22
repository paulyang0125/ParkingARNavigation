package com.example.taskb_ar;

import java.util.ArrayList;



import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class CameraOverlayView extends View {
	private Location mDeviceLocation = null;
	private ArrayList<TargetInfo> mTargetLocations = null;
	private CameraOrientation mCameraOrientation = null;
	private static final String TAG = CameraOverlayView.class.getSimpleName();

	Bitmap up;
	Bitmap bottom;
	Bitmap left;
	Bitmap right;
	Bitmap target;

	private static final float EPSILON = 67.0f; // ���(mm)
	private final static float MILLI_PER_INCH = 25.4f; // 1 inch = 0.0254 meter
	private static float DENSITY = 6.30f; // 1mm�(dot/mm)
	private static float LEFTUPLIMIT = 5.1f;
	private static float LEFTDOWNLIMIT = 3.1f;
	private static float RIGHTDOWNLIMIT = 2.9f;
	private static float RIGHTUPLIMIT = 0.2f;

	private static final float DISPLAY_THRESHOLD = 0.01f;

	public CameraOverlayView(Context context) {
		super(context);
		Resources r = context.getResources();
		DisplayMetrics metrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		DENSITY = metrics.densityDpi / MILLI_PER_INCH;

		// init picture
		up = BitmapFactory.decodeResource(r, R.drawable.up);
		bottom = BitmapFactory.decodeResource(r, R.drawable.down);
		left = BitmapFactory.decodeResource(r, R.drawable.left);
		right = BitmapFactory.decodeResource(r, R.drawable.right);
		target = BitmapFactory.decodeResource(r, R.drawable.my_park_1);

	}

	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//Log.i(TAG, "onDraw");
		// int canvasWidth = canvas.getWidth();
		// int canvasHeight = canvas.getHeight();
		if ((mDeviceLocation != null) && (mTargetLocations != null)
				&& (mTargetLocations.size() != 0)
				&& (mCameraOrientation != null)) {
			//Log.d(TAG, "Drawing Target.");

			// Paint paint3 = new Paint();
			// paint3.setColor(Color.rgb(236, 187, 60));
			// paint3.setStrokeWidth(6.0f);
			// paint3.setTextSize(10.0f);
			//
			// StringBuilder myDistance = new StringBuilder();

			// 
			for (int i = 0; i < mTargetLocations.size(); i++) {
				//Log.d(TAG, "Target:" + i);
				DisplayInfo displayInfo = getDisplayPoint(mDeviceLocation,
						mTargetLocations.get(i).getLocation(),
						mCameraOrientation);
				updateNumber(canvas);

				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
				}
				// show distance

				// myDistance.append("角度: " + mCameraOrientation.theta + "\n");
				// myDistance.append("\n");
				// myDistance.append("現在目標距離約 " + mCameraOrientation.distance /
				// 1000 + " 公里" + "\n");
				// myDistance.append("\n");
				// myDistance.append("你現在的速度約 " + mCameraOrientation.speed *
				// 3600 / 1000 + " 公里" + "\n");
				// myDistance.append("\n");
				// myDistance.append("預計到達時間 " + (mCameraOrientation.distance /
				// 1000) / (mCameraOrientation.speed * 3600 / 1000) + " 小時" +
				// "\n");
				//
				// canvas.drawText(String.valueOf(myDistance), 0, canvasHeight -
				// 400, paint2);

				if (displayInfo != null) {
					//

					updateLoc(canvas, displayInfo, i);

					// float textSize = this.getFontSize(mDeviceLocation,
					// mTargetLocations.get(i).getLocation());
					// if (textSize == 0)
					// continue;
					// paint.setTextSize(textSize);
					// canvas.drawText(mTargetLocations.get(i).getName(),
					// canvasWidth / 2 + displayInfo.point.x, canvasHeight
					// / 2 - displayInfo.point.y, paint);
				}

				else {
					/*  */
					// canvas.drawText(mTargetLocations.get(i).getName()+": Behind your back.",
					// canvasWidth/2,height+=LINEHEIGHT,paint2);
				}
			}
		} else {
			if (this.mDeviceLocation == null) {
				Log.e(TAG, "mDeviceLocation is null");
			}
			if (this.mTargetLocations == null) {
				Log.e(TAG, "mTargetLocations is null");
			}
			if (this.mCameraOrientation == null) {
				Log.e(TAG, "mCameraOrientation is null");
			}
		}
	}

	private void updateNumber(Canvas canvas) {

		Paint paint2 = new Paint();
		paint2.setColor(Color.rgb(236, 187, 60));
		paint2.setStrokeWidth(6.0f);
		paint2.setTextSize(35.0f);

		int canvasWidth = canvas.getWidth();
		int canvasHeight = canvas.getHeight();
		canvas.drawText(
				String.valueOf("角度: " + mCameraOrientation.theta + "\n"), 0,
				canvasHeight - 400, paint2);
		canvas.drawText(
				String.valueOf("現在目標距離約 "
						+ (int) Math.round(mCameraOrientation.distance) / 1000
						+ " 公里" + "\n"), 0, canvasHeight - 430, paint2);
		canvas.drawText(
				String.valueOf("你現在的速度約 " + mCameraOrientation.speed * 3600
						/ 1000 + " 公里" + "\n"), 0, canvasHeight - 460, paint2);
		if (mCameraOrientation.speed != 0) {
			canvas.drawText(String.valueOf("預計到達時間 "
					+ (int) Math.round((mCameraOrientation.distance / 1000)
							/ (mCameraOrientation.speed * 3600 / 1000)) + " 小時"
					+ "\n"), 0, canvasHeight - 490, paint2);
		} else {
			canvas.drawText(String.valueOf("預計到達時間 "
					+ ((int) Math
							.round((mCameraOrientation.distance / 1000) / 3))
					+ " 小時" + "\n"), 0, canvasHeight - 490, paint2);
		}

		paint2.setColor(Color.GREEN);
		paint2.setStrokeWidth(10.0f);
		paint2.setTextSize(35.0f);

		switch (mCameraOrientation.direction) {
		case (CameraOrientation.LEFT):
			canvas.drawText(String.valueOf("請往右走"), 0, canvasHeight - 520,
					paint2);

			canvas.drawBitmap(right, canvasWidth - 150, canvasHeight/2, paint2); // replace x,y,and
														// mPaint with whatever
														// you need to.
			break;
		case (CameraOrientation.RIGHT):
			canvas.drawText(String.valueOf("請往左走"), 0, canvasHeight - 520,
					paint2);
		canvas.drawBitmap(left, 0+100, canvasHeight/2, paint2);
			break;
		case (CameraOrientation.UP):
			canvas.drawText(String.valueOf("請繼續直走"), 0, canvasHeight - 520,
					paint2);
			canvas.drawBitmap(up, canvasWidth/2 - 50, 0+20, paint2);
			break;
		default:
			break;
		}
		
		
		if (Math.round(mCameraOrientation.distance) == 0){
		
			canvas.drawText(
					String.valueOf("你已經到達目的地!!!"), canvasWidth/2 - 100, canvasHeight/2, paint2);
			
		}
				
		
		
		

	}

	private void updateLoc(Canvas canvas, DisplayInfo displayInfo, int index) {

		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(6.0f);
		paint.setTextSize(30.0f);

		int canvasWidth = canvas.getWidth();
		int canvasHeight = canvas.getHeight();

		float textSize = this.getFontSize(mDeviceLocation, mTargetLocations
				.get(index).getLocation());
		int[] ratio = this.getImageRatio(mDeviceLocation, mTargetLocations
				.get(index).getLocation());
		
		if (textSize == 0)
			return;
		else {
			paint.setTextSize(textSize);
			canvas.drawText(mTargetLocations.get(index).getName(), canvasWidth
					/ 2 + displayInfo.point.x, canvasHeight / 2
					- displayInfo.point.y, paint);
			
//			int nh = (int) (target.getHeight() * (512.0 / (target.getWidth())));
//			
//			Bitmap scaled = Bitmap.createScaledBitmap(target, 512, nh,true);
			
			 Bitmap vB2 = Bitmap.createScaledBitmap( target, ratio[0], ratio[1], true);
			
			
			canvas.drawBitmap(vB2, canvasWidth
					/ 2 + displayInfo.point.x, canvasHeight / 2
					- displayInfo.point.y, paint);
		}
		
		
//		bitmap = MediaStore.Images.Media.getBitmap(
//				getApplicationContext().getContentResolver(),
//				outputFileUri);
//		ImageView ivTest1 = (ImageView) findViewById(R.id.imageView1);
//
//		int nh = (int) (bitmap.getHeight() * (512.0 / bitmap
//				.getWidth()));
//		Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh,
//				true);

	}

	private int[] getImageRatio(Location me, Location dest) {
		int width;
		int height;
		float dist = me.distanceTo(dest);
		if (dist < 5000) {
			width = target.getWidth() * 2/3;
			height = target.getHeight() * 2/3;
		} else if (dist < 25000) {
			width = target.getWidth() * 1/3;
			height = target.getHeight() * 1/3;;
		} else if (dist < 75000) {
			width = target.getWidth() * 1/5;
			height = target.getHeight() * 1/5;;
		} else {
			width = 0;
			height = 0;
		}
		
		return new int[] {width, height};
	}
	
	
	private float getFontSize(Location me, Location dest) {
		float ret = 0.0f;
		float dist = me.distanceTo(dest);
		if (dist < 5000) {
			ret = 30.0f;
		} else if (dist < 25000) {
			ret = 20.0f;
		} else if (dist < 75000) {
			ret = 10.0f;
		} else {
			ret = 0.0f;
		}
		return ret;
	}
	

	private DisplayInfo getDisplayPoint(Location deviceLocation,
			Location targetLocation, CameraOrientation cameraOrientation) {
		// parameter log
		// logd("device Location:"+deviceLocation.toString());
		// logd("target Location:"+targetLocation.toString());
		// logd("Camera Orientation:"+cameraOrientation.toString());
		// variant
		double x, z = 0;
		// Return Values
		DisplayInfo info = new DisplayInfo();
		Point point = new Point();
		// Distance
		double distance = deviceLocation.distanceTo(targetLocation);
		cameraOrientation.distance = distance;

		// speed

		double initSpeed = deviceLocation.getSpeed();
		cameraOrientation.speed = initSpeed;

		/* ��height  0  */
		double height = targetLocation.getAltitude()
				- deviceLocation.getAltitude();
		cameraOrientation.height = height;

		// Azimuth
		double cameraAzimuth = cameraOrientation.Azimuth;

		/* targetAzimuth */
		// double targetAzimuth = Math.atan2(
		// targetLocation.getLongitude()-deviceLocation.getLongitude(),
		// targetLocation.getLatitude()-deviceLocation.getLatitude()
		// );

		/* Android API Location.bearintTo  */
		double targetAzimuth = Math.toRadians(deviceLocation
				.bearingTo(targetLocation));

		// cameraOrientation.targetAzimuth = (float)
		// Math.toDegrees(deviceLocation
		// .bearingTo(targetLocation));

		cameraOrientation.targetAzimuth = targetAzimuth;

		//Log.d(TAG, "targetAzimuth=" + Double.toString(targetAzimuth));

		double theta = targetAzimuth - cameraAzimuth;
		cameraOrientation.theta = -theta;
		// cameraOrientation.theta = (float) Math.toDegrees(targetAzimuth -
		// cameraOrientation.Azimuth);

		// check direction
		// use theta's angel to figure out up, down, left and right

		// determine left or right direction
		if ((cameraOrientation.theta >= LEFTDOWNLIMIT && cameraOrientation.theta <= LEFTUPLIMIT)
				|| cameraOrientation.theta <= (-0.21f)) {

			cameraOrientation.direction = CameraOrientation.LEFT;

		} else if (cameraOrientation.theta <= RIGHTDOWNLIMIT
				&& cameraOrientation.theta >= RIGHTUPLIMIT) {

			cameraOrientation.direction = CameraOrientation.RIGHT;
		} else {

			cameraOrientation.direction = CameraOrientation.UP;

		}

		if (Math.cos(theta) < 0) {
			return null;
		}

		x = EPSILON * Math.tan(theta);
		z = (EPSILON * height) / (distance * Math.cos(theta));
		// Pitch
		double cameraPitch = -cameraOrientation.Pitch;
		double y_ = Math.cos(-cameraPitch) * EPSILON + Math.sin(-cameraPitch)
				* z;
		double z_ = -Math.sin(-cameraPitch) * EPSILON + Math.cos(-cameraPitch)
				* z;
		if (y_ == 0) {
			return null;
		}
		double z__ = (EPSILON / y_) * z_;
		// Roll
		double cameraRoll = cameraOrientation.Roll;
		double x_ = Math.cos(-cameraRoll) * x + Math.sin(-cameraRoll) * z__;
		double z___ = -Math.sin(-cameraRoll) * x + Math.cos(-cameraRoll) * z__;
		point.x = (int) ((float) DENSITY * x_);
		point.y = (int) ((float) DENSITY * z___);
		info.point = point;
		// Matrix
		Matrix matrix = new Matrix();
		matrix.setRotate((float) (-Math.toDegrees(cameraRoll)));
		info.matrix = matrix;
		// distance
		info.distance = (float) distance;
		// return
		//Log.d(TAG, info.toString());

		return info;
	}

	public void setDeviceLocation(Location location) {
		if (location == null)
			Log.d(TAG, "set null location!");
		mDeviceLocation = location;
		invalidate();
	}

	public void setCameraOrientation(CameraOrientation cameraOrientation) {
		// Log.d(TAG,"setCameraDirection");
		if ((mCameraOrientation == null)
				|| checkCameraOrientation(cameraOrientation)) {
			mCameraOrientation = cameraOrientation;
			invalidate();
		}
	}

	private boolean checkCameraOrientation(CameraOrientation cameraOrientation) {
		boolean ret = true;
		float diffAzimuth = cameraOrientation.Azimuth
				- mCameraOrientation.Azimuth;
		float diffPitch = cameraOrientation.Pitch - mCameraOrientation.Pitch;
		float diffRoll = cameraOrientation.Roll - mCameraOrientation.Roll;
		double a = Math.abs(Math.sin(diffAzimuth));
		double p = Math.abs(Math.sin(diffPitch));
		double r = Math.abs(Math.sin(diffRoll));
		if (Math.max(Math.max(a, p), r) < DISPLAY_THRESHOLD) {
			ret = false;
		}
		return ret;
	}

	public void setTargetInfoList(ArrayList<TargetInfo> infoList) {
		Log.d(TAG, "setTargetInfoList");
		mTargetLocations = infoList;
	}

	class CameraOrientation {
		public float Azimuth = 0; // in radian
		public float Pitch = 0; // in radian
		public float Roll = 0; // in radian
		double distance = 0;

		public String toString() {
			return new String("Azimuth=" + Math.toDegrees(Azimuth) + ",Pitch="
					+ Math.toDegrees(Pitch) + ",Roll=" + Math.toDegrees(Roll));
		}

		double speed = 0;

		double targetAzimuth = 0;
		double theta = 0;
		double height = 0;

		int direction;
		static final int UP = 1;
		static final int DOWN = 2;
		static final int LEFT = 3;
		static final int RIGHT = 4;

	}

	class DisplayInfo {
		public Point point = null;
		public float distance = -1.0f;
		public Matrix matrix = null;

		public String toString() {
			return new String("Point:" + point.toString() + ",distance:"
					+ Float.toString(distance) + ",Matrix:"
					+ matrix.toShortString());
		}
	}

}
