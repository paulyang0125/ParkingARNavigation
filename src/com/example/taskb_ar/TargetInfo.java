package com.example.taskb_ar;

import android.location.Location;

public class TargetInfo {
	
	private Location mLocation = null;
	private String mName = null;
	
	public TargetInfo(final Location location, final String name){
		mLocation = location;
		mName = name;
	}
	
	public Location getLocation(){
		return mLocation;
	}
	
	public String getName(){
		return mName;
	}
	
	public String toString(){
		if(mLocation != null){
			return new String("Name:"+mName+",Location:"+mLocation.toString());
		}else{
			return new String("Name:"+mName+",Location:nodata");
		}
	}

}
