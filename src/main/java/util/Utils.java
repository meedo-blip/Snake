package util;

import org.joml.Vector4f;

public class Utils {
	private static float r, g, b;
	public static final float DEGREES_TO_RADIANS = 0.017453292f;

	public static int[] findWord(String myWord, String text) {
		return findWord(myWord, text, 0);
	}
	
	public static int[] findWord(String myWord, String text, int offset){
		int[] pos = null;
		
		char[] wordArray =  myWord.toCharArray();
		char[] textArray = text.toCharArray();
		
		final int iterations = textArray.length - wordArray.length + 1;
		
		for(int i = 0; i < iterations; i++) {
			int count = 0;
			
			for(int j = 0; j < wordArray.length; j++) {
				if (textArray[i + j] == wordArray[j]) count++;
				else break;
			}
			
			if(count == wordArray.length) {
				if(pos == null)
					pos = new int[1];
				else {
					int[] temp = pos;
					pos = new int[pos.length + 1];
					
					for(int m = 0; m < temp.length; m++) 
						pos[m] = temp[m];
				}
				pos[pos.length - 1] = i + offset;
				
				i += count - 1;
			}
		}

		return pos;
	}

	public static Vector4f hexToRgba(String hex) {
		hex = hex.trim();
		if(hex.charAt(0) == '#') {
			hex = hex.substring(1, 7);
		}
		r = (float) Integer.valueOf(hex.substring(0, 2), 16) / 255;
		g = (float) Integer.valueOf(hex.substring(2, 4), 16) / 255;
		b = (float) Integer.valueOf(hex.substring(4, 6), 16) / 255;

		System.out.println(r +" "+g+" "+ b);
		return new Vector4f(r,g,b,0f);
	}
}
