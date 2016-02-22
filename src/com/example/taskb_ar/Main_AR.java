package com.example.taskb_ar;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
//import android.location.LocationListener;
import android.location.LocationManager;
//import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.ToggleButton;




import com.example.taskb_ar.CameraOverlayView.CameraOrientation;
import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesClient;
//import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.location.LocationClient;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class Main_AR extends FragmentActivity implements SensorEventListener,
		LocationListener {
	
	
//    // A request to connect to Location Services
//    private LocationRequest mLocationRequest;
//
//    // Stores the current instantiation of the location client in this object
//    private LocationClient mLocationClient;
    
    // Handle to SharedPreferences for this app
    SharedPreferences mPrefs;

    // Handle to a SharedPreferences editor
    SharedPreferences.Editor mEditor;
    
    
    /*
     * Note if updates have been turned on. Starts out as "false"; is set to "true" in the
     * method handleRequestSuccess of LocationUpdateReceiver.
     *
     */
//    boolean mUpdatesRequested = false;

	FrameLayout mFrameLayout = null;
	CameraPreviewView mCameraPreview = null;
	CameraOverlayView mCameraOverlay = null;
	DebugOverlayView mDebugOverlay = null;
	SensorManager mSensorManager = null;
	LocationManager mLocationManager = null;
	SensorEvent mLastAccelerometer = null;
	SensorEvent mLastMagneticField = null;
	Toast toast = null;
	Location latestLoc;
	private static final String TAG = Main_AR.class.getSimpleName();

//	SurfaceView mySurfaceView;// SurfaceView的引用
//	SurfaceHolder mySurfaceHolder;// SurfaceHolder的引用

	private final int X = 0;
	private final int Y = 1;
	private final int Z = 2;
	private final int AZIMUTH = 0;
	private final int PITCH = 1;
	private final int ROLL = 2;

//	private final String LOG_TAG = "Main_AR";
	private ToggleButton toggle_button_debug;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main__ar);

		mFrameLayout = (FrameLayout) findViewById(R.id.frameLayout1);
//		mySurfaceView = (SurfaceView) findViewById(R.id.surfaceView);// 得到SurfaceView的引用

		// FrameLayout 
		// mFrameLayout = new FrameLayout(this);

//		FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(300, 400);
//		mFrameLayout.setLayoutParams(frameLayoutParams);

		toggle_button_debug = (ToggleButton) findViewById(R.id.toggle_button_debug);// debug

		// CameraPreview 
		mCameraPreview = new CameraPreviewView(this);
		mFrameLayout.addView(mCameraPreview);

		// CameraOverlay 
		mCameraOverlay = new CameraOverlayView(this);

		ArrayList<TargetInfo> targetList = new ArrayList<TargetInfo>();
		for (int i = 0; i < data.length; i++) {
			targetList.add(getSample(data[i]));
		}
		mCameraOverlay.setTargetInfoList(targetList);

		mFrameLayout.addView(mCameraOverlay);

		// DebugOverlay 
		mDebugOverlay = new DebugOverlayView(this);
//		mFrameLayout.addView(mDebugOverlay); /*  */

		// View 
//		setContentView(mFrameLayout);
		

//        // Create a new global location parameters object
//        mLocationRequest = LocationRequest.create();
//		
//        /*
//        * Set the update interval
//        */
//       mLocationRequest.setInterval(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);
//
//       // Use high accuracy
//       mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//       // Set the interval ceiling to one minute
//       mLocationRequest.setFastestInterval(LocationUtils.FAST_INTERVAL_CEILING_IN_MILLISECONDS);
//
//       // Note that location updates are off until the user turns them on
//       mUpdatesRequested = false;
//
//       // Open Shared Preferences
//       mPrefs = getSharedPreferences(LocationUtils.SHARED_PREFERENCES, Context.MODE_PRIVATE);
//
//       // Get an editor
//       mEditor = mPrefs.edit();
//
//       /*
//        * Create a new location client, using the enclosing class to
//        * handle callbacks.
//        */
//       mLocationClient = new LocationClient(this, this, this);
		
		
		
		

		// SensorManager �
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		// LocationManager �
		mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		toggle_button_debug.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {

				if (toggle_button_debug.isChecked()) {
					Toast.makeText(Main_AR.this, "DEBUG mode enabled",
							Toast.LENGTH_SHORT).show();
					mFrameLayout.addView(mDebugOverlay);
				}

				else {
					Toast.makeText(Main_AR.this, "DEBUG mode disabled",
							Toast.LENGTH_SHORT).show();
					mFrameLayout.removeView(mDebugOverlay);

				}

			}
		});

	}
	
	
    /*
     * Called when the Activity is no longer visible at all.
     * Stop updates and disconnect.
     */
    @Override
    public void onStop() {

//        // If the client is connected
//        if (mLocationClient.isConnected()) {
//            stopPeriodicUpdates();
//        }
//
//        // After disconnect() is called, the client is considered "dead".
//        mLocationClient.disconnect();

        super.onStop();
    }
	
	
	

	@Override
	public void onResume() {
		super.onResume();
		Log.w(TAG, "onResume");
		
		
		
//        // If the app already has a setting for getting location updates, get it
//        if (mPrefs.contains(LocationUtils.KEY_UPDATES_REQUESTED)) {
//            mUpdatesRequested = mPrefs.getBoolean(LocationUtils.KEY_UPDATES_REQUESTED, false);
//
//        // Otherwise, turn off location updates until requested
//        } else {
//            mEditor.putBoolean(LocationUtils.KEY_UPDATES_REQUESTED, false);
//            mEditor.commit();
//        }
        
//        startUpdates(); //has done it on onConnect()

		// SensorManager init and register 
		Sensor sensorAccel = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, sensorAccel,
				SensorManager.SENSOR_DELAY_GAME);
		Sensor sensorMagne = mSensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		mSensorManager.registerListener(this, sensorMagne,
				SensorManager.SENSOR_DELAY_GAME);

		// LocationManager init and get provider 
		Criteria criteria = new Criteria();
		criteria.setAltitudeRequired(true);
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setSpeedRequired(true);
		String provider = mLocationManager.getBestProvider(criteria, true);

		mLocationManager.requestLocationUpdates(provider, 0, 0, this);
		Location lastLocation = mLocationManager.getLastKnownLocation(provider);
		mCameraOverlay.setDeviceLocation(lastLocation);
		mDebugOverlay.setProviderInfo(provider, "AVAILABLE");
		
//		if (getLocation() != null)
//		{
//			mCameraOverlay.setDeviceLocation(getLocation());
//		}
//		
//	

		

	}
	
	
	
    /**
     * Invoked by the "Get Location" button.
     *
     * Calls getLastLocation() to get the current location
     *
     * @param v The view object associated with this method, in this case a Button.
     */
//    public Location getLocation() {
//
//        // If Google Play Services is available
//        if (servicesConnected()) {
//
//            // Get the current location
//            Location currentLocation = mLocationClient.getLastLocation();
//
//            // Display the current location in the UI
////            mLatLng.setText(LocationUtils.getLatLng(this, currentLocation));
//            return currentLocation;
//        }
//		return null;
//
//    }
	
	
	
	
	 /*
     * Handle results returned to this Activity by other Activities started with
     * startActivityForResult(). In particular, the method onConnectionFailed() in
     * LocationUpdateRemover and LocationUpdateRequester may call startResolutionForResult() to
     * start an Activity that handles Google Play services problems. The result of this
     * call returns here, to onActivityResult.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // Choose what to do based on the request code
        switch (requestCode) {

            // If the request code matches the code sent in onConnectionFailed
            case LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST :

                switch (resultCode) {
                    // If Google Play services resolved the problem
                    case Activity.RESULT_OK:

                        // Log the result
                        Log.d(LocationUtils.APPTAG, getString(R.string.resolved));

                        // Display the result
//                        mConnectionState.setText(R.string.connected);
//                        mConnectionStatus.setText(R.string.resolved);
                    break;

                    // If any other result was returned by Google Play services
                    default:
                        // Log the result
                        Log.d(LocationUtils.APPTAG, getString(R.string.no_resolution));

                        // Display the result
//                        mConnectionState.setText(R.string.disconnected);
//                        mConnectionStatus.setText(R.string.no_resolution);

                    break;
                }

            // If any other request code was received
            default:
               // Report that this Activity received an unknown requestCode
               Log.d(LocationUtils.APPTAG,
                       getString(R.string.unknown_activity_request_code, requestCode));

               break;
        }
    }

	private CameraOrientation getCameraOrientation(SensorEvent maccel,
			SensorEvent mmagne, boolean isDIY) throws InvalidParameterException {
		//Log.d(TAG, "getCameraOrientation");
		CameraOrientation co = mCameraOverlay.new CameraOrientation();
		//Log.d(TAG, "DIY:" + isDIY);
		float[] Wx, Wy, Wz = null;
		float[] accel, magne = null;

		accel = maccel.values;
		magne = mmagne.values;

		/* �� */
		if (isDIY) {
			// Calculate World Coordinates
			// mDebugOverlay.setVectorFields(accel, magne);
			try {
				Wz = MathUtil.normalize(accel);
			} catch (InvalidParameterException e) {
				throw new InvalidParameterException(
						"Given accelerometer is zero.");
			}
			try {
				Wx = MathUtil.normalize(MathUtil.vectorProduct(magne, Wz));
			} catch (InvalidParameterException e) {
				throw new InvalidParameterException(
						"Given Magnetic Field is zero, or is parallel to given accelerometer.");
			}

			Wy = MathUtil.vectorProduct(Wz, Wx);
			// Camera Azimuth
			co.Azimuth = (float) Math.atan2(-Wx[Z], -Wy[Z]);
			// Camera Pitch
			co.Pitch = (float) Math.asin(-Wz[Z]);
			// Camera Roll
			co.Roll = (float) Math.atan2(Wz[Y], Wz[X]);

			/*  */
			float deviceAzimuth = (float) Math.atan2(Wx[Y], Wy[Y]);
			float devicePitch = (float) -Math.asin(Wz[Y]);
			float deviceRoll = (float) Math.atan2(Wz[X], Wz[Z]);
			mDebugOverlay.setVectorFields(accel, magne);
			mDebugOverlay.setWorldCoordinateSystem(Wx, Wy, Wz);
			mDebugOverlay.setDeviceOrientation(
					(float) Math.toDegrees(deviceAzimuth),
					(float) Math.toDegrees(devicePitch),
					(float) Math.toDegrees(deviceRoll));
			mDebugOverlay.setCameraOrientation(
					(float) Math.toDegrees(co.Azimuth),
					(float) Math.toDegrees(co.Pitch),
					(float) Math.toDegrees(co.Roll));
		}
		/* Android �API�� */
		else {
			mDebugOverlay.setVectorFields(accel, magne);
			// get rotation matrix
			float[] inR = new float[9];
			float[] inI = new float[9];
			float[] outR = new float[9];
			if (SensorManager.getRotationMatrix(inR, inI, accel, magne)) {
				/*  */
				Wx = new float[3];
				Wy = new float[3];
				Wz = new float[3];
				Wx[0] = inR[0];
				Wx[1] = inR[1];
				Wx[2] = inR[2];
				Wy[0] = inR[3];
				Wy[1] = inR[4];
				Wy[2] = inR[5];
				Wz[0] = inR[6];
				Wz[1] = inR[7];
				Wz[2] = inR[8];
				mDebugOverlay.setWorldCoordinateSystem(Wx, Wy, Wz);
				float[] orientation2 = new float[3];
				SensorManager.getOrientation(inR, orientation2);
				float deviceAzimuth = orientation2[AZIMUTH];
				float devicePitch = orientation2[PITCH];
				float deviceRoll = orientation2[ROLL];
				mDebugOverlay.setDeviceOrientation(
						(float) Math.toDegrees(deviceAzimuth),
						(float) Math.toDegrees(devicePitch),
						(float) Math.toDegrees(deviceRoll));
				/*  */
				SensorManager.remapCoordinateSystem(inR, SensorManager.AXIS_Z,
						SensorManager.AXIS_MINUS_X, outR);
			} else {
				throw new InvalidParameterException("invalid parameter.");
			}

			// get Orientation
			float[] orientation = new float[3];
			SensorManager.getOrientation(outR, orientation);
			// Camera Azimuth
			co.Azimuth = orientation[AZIMUTH];
			// Camera Pitch
			co.Pitch = -orientation[PITCH];
			// Camera Roll
			co.Roll = orientation[ROLL];

			/*  */
			mDebugOverlay.setCameraOrientation(
					(float) Math.toDegrees(co.Azimuth),
					(float) Math.toDegrees(co.Pitch),
					(float) Math.toDegrees(co.Roll));
			/*  */
		}
		//Log.d(TAG, co.toString());
		return co;
	}

	  /*
     * Called when the Activity is going into the background.
     * Parts of the UI may be visible, but the Activity is inactive.
     */
	@Override
	public void onPause() {
		super.onPause();
		
		
		Log.w(TAG, "onPause");
		
		
		
        // Save the current setting for updates
//        mEditor.putBoolean(LocationUtils.KEY_UPDATES_REQUESTED, mUpdatesRequested);
//        mEditor.commit();
		
		
		
		// SensorManager 
		mSensorManager.unregisterListener(this);
		// LocationManager 
		mLocationManager.removeUpdates(this);

//		if (null != toast) {
//			toast.cancel();
//		}
	}
	
	
    /*
     * Called when the Activity is restarted, even before it becomes visible.
     */
    @Override
    public void onStart() {

        super.onStart();

        /*
         * Connect the client. Don't re-start any requests here;
         * instead, wait for onResume()
         */
//        mLocationClient.connect();

    }

	@Override
	public void onDestroy() {
		super.onDestroy();

//		toast.cancel();
		Log.w(TAG, "onDestroy");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main__ar, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		Log.d(TAG, "onAccuracyChanged:" + arg0 + " ; " + arg1);

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// logs("onSensorChanged");
		int sensorType = event.sensor.getType();
		switch (sensorType) {
		// case Sensor.TYPE_ACCELEROMETER:
		case (1):
			// logs("TYPE_ACCELEROMETER");
			mLastAccelerometer = event;
			break;
		// case Sensor.TYPE_MAGNETIC_FIELD:
		case (2):
			// logs("TYPE_MAGNETIC_FIELD");
			mLastMagneticField = event;
			break;
		default:
			break;
		}

		if ((mLastAccelerometer != null) && (mLastMagneticField != null)) {
			//Log.i(TAG, "try to get Camera Orientation");
			try {
				/* �� */
				// CameraOrientation co =
				// getCameraOrientation(mLastAccelerometer.values,mLastMagneticField.values,true);
				CameraOrientation co = getCameraOrientation(mLastAccelerometer,
						mLastMagneticField, false);
				/* Android �API�� */
				// CameraOrientation co =
				// getCameraOrientation(mLastAccelerometer.values,mLastMagneticField.values,false);
				mCameraOverlay.setCameraOrientation(co);
			} catch (InvalidParameterException e) {
				Log.e(TAG, e.getMessage());
			}
		}

	}

	@Override
	public void onLocationChanged(Location arg0) {
		mCameraOverlay.setDeviceLocation(arg0);
		mDebugOverlay.setDeviceLocation(arg0);
		latestLoc = arg0;

	}
	
	

	@Override
	public void onProviderDisabled(String arg0) {
		Log.d(TAG, "onProviderDisabled:" + arg0);

	}

	@Override
	public void onProviderEnabled(String arg0) {
		Log.d(TAG, "onProviderEnabled:" + arg0);

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

		Log.d(TAG, "onStatusChanged:provider=" + arg0 + ", status=" + arg1);

		switch (arg1) {
		case LocationProvider.AVAILABLE:
			mDebugOverlay.setProviderInfo(arg0, "AVAILABLE");
			break;
		case LocationProvider.OUT_OF_SERVICE:
			mDebugOverlay.setProviderInfo(arg0, "OUT_OF_SERVICE");
			break;
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			mDebugOverlay.setProviderInfo(arg0, "TEMPORARILY_UNAVAILABLE");
			break;
		default:
			break;
		}

	}

	private TargetInfo getSample(String[] data) {
		Log.d(TAG, "getSample");
		Location location = new Location("SampleProvider");
		location.setLatitude(Location.convert(data[1]));
		location.setLongitude(Location.convert(data[2]));

		// Set the altitude, in meters above sea level.
		// Following this call hasAltitude() will return true.

		location.setAltitude(Integer.parseInt(data[3]));
		TargetInfo info = new TargetInfo(location, data[0]);
		return info;
	}

	private String[][] data = {

//	{ "南港展覽館", "25.056921", "121.617438", "40" },
//			{ "台灣惠普", "25.057271", "121.616076", "13" },
//			{ "IBM", "25.058758", "121.613297", "20" },
//			{ "南港高鐵", "25.052927", "121.607139", "14" },
			{ "台北101", "25.034293", "121.564738", "509" }

	};
	
	 public void getAddress(View v) {

	        // In Gingerbread and later, use Geocoder.isPresent() to see if a geocoder is available.
	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD && !Geocoder.isPresent()) {
	            // No geocoder is present. Issue an error message
	            Toast.makeText(this, "no_geocoder_available", Toast.LENGTH_LONG).show();
	            return;
	        }

	        if (servicesConnected()) {

	            // Get the current location
//	            Location currentLocation = getLocation();
	        	Location currentLocation =  latestLoc;

	            // Turn the indefinite activity indicator on
//	            mActivityIndicator.setVisibility(View.VISIBLE);

	            // Start the background task
	            (new Main_AR.GetAddressTask(this)).execute(currentLocation);
	        }
	    }
	 
	 
	    /**
	     * Verify that Google Play services is available before making a request.
	     *
	     * @return true if Google Play services is available, otherwise false
	     */
	    private boolean servicesConnected() {

	        // Check that Google Play services is available
	        int resultCode =
	                GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

	        // If Google Play services is available
	        if (ConnectionResult.SUCCESS == resultCode) {
	            // In debug mode, log the status
	            Log.d(LocationUtils.APPTAG, getString(R.string.play_services_available));

	            // Continue
	            return true;
	        // Google Play services was not available for some reason
	        } else {
	            // Display an error dialog
	            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
	            if (dialog != null) {
	                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
	                errorFragment.setDialog(dialog);
	                errorFragment.show(getSupportFragmentManager(), LocationUtils.APPTAG);
	            }
	            return false;
	        }
	    }
	    
	    /**
	     * Define a DialogFragment to display the error dialog generated in
	     * showErrorDialog.
	     */
	    public static class ErrorDialogFragment extends DialogFragment {

	        // Global field to contain the error dialog
	        private Dialog mDialog;

	        /**
	         * Default constructor. Sets the dialog field to null
	         */
	        public ErrorDialogFragment() {
	            super();
	            mDialog = null;
	        }

	        /**
	         * Set the dialog to display
	         *
	         * @param dialog An error dialog
	         */
	        public void setDialog(Dialog dialog) {
	            mDialog = dialog;
	        }

	        /*
	         * This method must return a Dialog to the DialogFragment.
	         */
	        @Override
	        public Dialog onCreateDialog(Bundle savedInstanceState) {
	            return mDialog;
	        }
	    }
	    
	    /**
	     * An AsyncTask that calls getFromLocation() in the background.
	     * The class uses the following generic types:
	     * Location - A {@link android.location.Location} object containing the current location,
	     *            passed as the input parameter to doInBackground()
	     * Void     - indicates that progress units are not used by this subclass
	     * String   - An address passed to onPostExecute()
	     */
	    protected class GetAddressTask extends AsyncTask<Location, Void, String> {

	        // Store the context passed to the AsyncTask when the system instantiates it.
	        Context localContext;

	        // Constructor called by the system to instantiate the task
	        public GetAddressTask(Context context) {

	            // Required by the semantics of AsyncTask
	            super();

	            // Set a Context for the background task
	            localContext = context;
	        }

	        /**
	         * Get a geocoding service instance, pass latitude and longitude to it, format the returned
	         * address, and return the address to the UI thread.
	         */
	        @Override
	        protected String doInBackground(Location... params) {
	            /*
	             * Get a new geocoding service instance, set for localized addresses. This example uses
	             * android.location.Geocoder, but other geocoders that conform to address standards
	             * can also be used.
	             */
	            //Geocoder geocoder = new Geocoder(localContext, Locale.getDefault());
	            Geocoder geocoder = new Geocoder(localContext, Locale.TRADITIONAL_CHINESE); //use Taiwan locale 

	            // Get the current location from the input parameter list
	            Location location = params[0];

	            // Create a list to contain the result address
	            List <Address> addresses = null;

	            // Try to get an address for the current location. Catch IO or network problems.
	            try {

	                /*
	                 * Call the synchronous getFromLocation() method with the latitude and
	                 * longitude of the current location. Return at most 1 address.
	                 */
	                addresses = geocoder.getFromLocation(location.getLatitude(),
	                    location.getLongitude(), 1
	                );

	                // Catch network or other I/O problems.
	                } catch (IOException exception1) {

	                    // Log an error and return an error message
	                    Log.e(LocationUtils.APPTAG, getString(R.string.IO_Exception_getFromLocation));

	                    // print the stack trace
	                    exception1.printStackTrace();

	                    // Return an error message
	                    return (getString(R.string.IO_Exception_getFromLocation));

	                // Catch incorrect latitude or longitude values
	                } catch (IllegalArgumentException exception2) {

	                    // Construct a message containing the invalid arguments
	                    String errorString = getString(
	                            R.string.illegal_argument_exception,
	                            location.getLatitude(),
	                            location.getLongitude()
	                    );
	                    // Log the error and print the stack trace
	                    Log.e(LocationUtils.APPTAG, errorString);
	                    exception2.printStackTrace();

	                    //
	                    return errorString;
	                }
	                // If the reverse geocode returned an address
	                if (addresses != null && addresses.size() > 0) {

	                    // Get the first address
	                    Address address = addresses.get(0);
	                    
	                    //debug purpose 
	                    Log.d(TAG, "Country: " + addresses.get(0).getCountryName());//台灣省
	                    
	                    Log.d(TAG, "Area: " + addresses.get(0).getAdminArea());
	                    
	                    Log.d(TAG, "Locality: " + addresses.get(0).getLocality());//台灣省
	                    
	                    Log.d(TAG, "Thoroughfare: " + addresses.get(0).getThoroughfare());
	                    
	                    Log.d(TAG, "FeatureName: " + addresses.get(0).getFeatureName());
	                    
	                    Log.d(TAG, "PostalCode: " + addresses.get(0).getPostalCode());
	                    
	                    Log.d(TAG, "Full addressLine: " + addresses.get(0).getAddressLine(0));
	                    

//	                    // Format the first line of address
//	                    String addressText = getString(R.string.address_output_string,
//
//	                            // If there's a street address, add it
//	                            address.getMaxAddressLineIndex() > 0 ?
//	                                    address.getAddressLine(0) : "",
//
//	                            // Locality is usually a city
//	                            address.getLocality(),
//
//	                            // The country of the address
//	                            address.getCountryName()
//	                    );
	                    
	                    String addressText = addresses.get(0).getAddressLine(0);
	                    
//	                    // Format the first line of address
//	                    String addressText = getString(R.string.address_output_string_my,
//
//	                            // If there's a street address, add the full address
//	                            address.getMaxAddressLineIndex() > 0 ?
//	                            		addresses.get(0).getAddressLine(0) : ""
//
//	                    );

	                    // Return the text
	                    return addressText;

	                // If there aren't any addresses, post a message
	                } else {
	                  return getString(R.string.no_address_found);
	                }
	        }

	        /**
	         * A method that's called once doInBackground() completes. Set the text of the
	         * UI element that displays the address. This method runs on the UI thread.
	         */
	        @Override
	        protected void onPostExecute(String address) {

	            // Turn off the progress bar
//	            mActivityIndicator.setVisibility(View.GONE);

	            // Set the address in the UI
	            //mAddress.setText(address);
	            mDebugOverlay.setAddress(address);
	        }
	    }

//		@Override
//		public void onConnectionFailed(ConnectionResult connectionResult) {
//		       /*
//	         * Google Play services can resolve some errors it detects.
//	         * If the error has a resolution, try sending an Intent to
//	         * start a Google Play services activity that can resolve
//	         * error.
//	         */
//	        if (connectionResult.hasResolution()) {
//	            try {
//
//	                // Start an Activity that tries to resolve the error
//	                connectionResult.startResolutionForResult(
//	                        this,
//	                        LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);
//
//	                /*
//	                * Thrown if Google Play services canceled the original
//	                * PendingIntent
//	                */
//
//	            } catch (IntentSender.SendIntentException e) {
//
//	                // Log the error
//	                e.printStackTrace();
//	            }
//	        } else {
//
//	            // If no resolution is available, display a dialog to the user with the error.
//	            showErrorDialog(connectionResult.getErrorCode());
//	        }
//			
//		}
		
		/*
	     * Called by Location Services when the request to connect the
	     * client finishes successfully. At this point, you can
	     * request the current location or start periodic updates
	     */

//		@Override
//		public void onConnected(Bundle arg0) {
//		      if (mUpdatesRequested) {
//		            startPeriodicUpdates();
//		        }
//		      
//				if (getLocation() != null)
//				{
//					mCameraOverlay.setDeviceLocation(getLocation());
//					mDebugOverlay.setDeviceLocation(getLocation());
//				}
//			
//		}
//
//		@Override
//		public void onDisconnected() {
//
//			
//		}
		
	    /**
	     * In response to a request to start updates, send a request
	     * to Location Services
	     */
//	    private void startPeriodicUpdates() {
//
//	        mLocationClient.requestLocationUpdates(mLocationRequest, this);
////	        mConnectionState.setText(R.string.location_requested);
//	    }
//
//	    /**
//	     * In response to a request to stop updates, send a request to
//	     * Location Services
//	     */
//	    private void stopPeriodicUpdates() {
//	        mLocationClient.removeLocationUpdates(this);
////	        mConnectionState.setText(R.string.location_updates_stopped);
//	    }
	    
	    /**
	     * Show a dialog returned by Google Play services for the
	     * connection error code
	     *
	     * @param errorCode An error code returned from onConnectionFailed
	     */
	    private void showErrorDialog(int errorCode) {

	        // Get the error dialog from Google Play services
	        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
	            errorCode,
	            this,
	            LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

	        // If Google Play services can provide an error dialog
	        if (errorDialog != null) {

	            // Create a new DialogFragment in which to show the error dialog
	            ErrorDialogFragment errorFragment = new ErrorDialogFragment();

	            // Set the dialog in the DialogFragment
	            errorFragment.setDialog(errorDialog);

	            // Show the error dialog in the DialogFragment
	            errorFragment.show(getSupportFragmentManager(), LocationUtils.APPTAG);
	        }
	    }

	    
	    /**
	     * Invoked by the "Start Updates" button
	     * Sends a request to start location updates
	     *
	     * @param v The view object associated with this method, in this case a Button.
	     */
//	    public void startUpdates() {
//	        mUpdatesRequested = true;
//
//	        if (servicesConnected()) {
//	            startPeriodicUpdates();
//	        }
//	    }

	    /**
	     * Invoked by the "Stop Updates" button
	     * Sends a request to remove location updates
	     * request them.
	     *
	     * @param v The view object associated with this method, in this case a Button.
	     */
//	    public void stopUpdates() {
//	        mUpdatesRequested = false;
//
//	        if (servicesConnected()) {
//	            stopPeriodicUpdates();
//	        }
//	    }
	    

}
