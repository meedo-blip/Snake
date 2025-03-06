package renderer;

import org.lwjgl.*;

import java.nio.*;
import java.nio.charset.Charset;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;


import static org.lwjgl.opengl.GL20.glGenTextures;
import static org.lwjgl.stb.STBImage.*;

public class Texture {
	private String filepath;
	private int texID;
	
	public Texture(String filepath) {
		this.filepath = filepath;
		
		// Generate texture on GPU
		texID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texID);
		
		// Set texture parameter
		// Repeat image in both directions
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		// When stretching image, pixelate
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		// When shrinking an image, also pixelate
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer channels = BufferUtils.createIntBuffer(1);

		// Images will be flipped back up from upside down
		stbi_set_flip_vertically_on_load(true);
		ByteBuffer image = stbi_load(filepath, width, height, channels, 0);
		
		if(image != null) {
			// Upload image and its format as a texture to OpenGL
			if(channels.get(0) == 3) {
				glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0),
						0, GL_RGB, GL_UNSIGNED_BYTE, image);
			} else if(channels.get(0) == 4) {
				glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0),
						0, GL_RGBA, GL_UNSIGNED_BYTE, image);
			} else {
				assert false : "Error: (Texture) Unknown number of channels '" + channels.get(0) + "'";
			}
		} else {
			assert false : "Error: [Texture] Could not load image '"+ this.filepath +"'";
		}
		
		// free up the image from memory  
		stbi_image_free(image);
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, texID);
	}
	
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}
