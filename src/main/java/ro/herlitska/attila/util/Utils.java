package ro.herlitska.attila.util;

public class Utils {

	public static double dist(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	public static String secondsToString(int seconds) {
		String minutesStr = String.format("%02d", seconds / 60);
		String secondsStr = String.format("%02d", seconds % 60);
		return minutesStr + ":" + secondsStr;
	}
}
