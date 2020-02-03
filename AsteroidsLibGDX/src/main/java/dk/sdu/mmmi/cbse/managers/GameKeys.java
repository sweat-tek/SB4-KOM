package dk.sdu.mmmi.cbse.managers;

public class GameKeys {
	
	private static boolean[] keys;
	private static boolean[] pkeys;
	
	private static final int NUM_KEYS = 8;
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	public static final int ENTER = 4;
	public static final int ESCAPE = 5;
	public static final int SPACE = 6;
	public static final int SHIFT = 7;
	
	static {
		keys = new boolean[NUM_KEYS];
		pkeys = new boolean[NUM_KEYS];
	}
	
	public static void update() {
		for(int i = 0; i < NUM_KEYS; i++) {
			pkeys[i] = keys[i];
		}
	}
	
	public static void setKey(int k, boolean b) {
		keys[k] = b;
	}
	
	public static boolean isDown(int k) {
		return keys[k];
	}
	
	public static boolean isPressed(int k) {
		return keys[k] && !pkeys[k];
	}
	
}
















