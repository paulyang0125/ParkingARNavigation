package com.example.taskb_ar;

import java.security.InvalidParameterException;

public class MathUtil {

	public static float[] vectorProduct(float[] a, float[] b) {
		float[] c = new float[3];
		if (a.length == 3 && b.length == 3) {
			c[0] = a[1] * b[2] - a[2] * b[1];
			c[1] = a[2] * b[0] - a[0] * b[2];
			c[2] = a[0] * b[1] - a[1] * b[0];
		} else {
			c[0] = c[1] = c[2] = 0.0f;
		}
		return c;
	}

	public static float normOf(float[] a) {
		float ret = 0;
		for (int i = 0; i < a.length; i++) {
			ret += a[i] * a[i];
		}
		ret = (float) Math.sqrt(ret);
		return ret;
	}

	public static float[] normalize(float[] a) throws InvalidParameterException {
		float norm = normOf(a);
		if (norm > 0) {
			float[] ret = new float[a.length];
			for (int i = 0; i < a.length; i++) {
				ret[i] = a[i] / norm;
			}
			return ret;
		} else {
			throw new InvalidParameterException("cannot nomalize zero vector.");
		}
	}

}
