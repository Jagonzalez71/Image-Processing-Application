import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Filters {

  public static void main(String[] args)throws IOException {
    // TODO: modify this main method as you wish, to run and test your filter implementations.

    // Read in the image file.
    File f = new File("dog.png");
    BufferedImage img = ImageIO.read(f);

    // For debugging
    System.out.println("Before:");
    System.out.println(Utilities.getRGBArray(0, 0, img)[0]);
    System.out.println(Utilities.getRGBArray(0, 0, img)[1]);
    System.out.println(Utilities.getRGBArray(0, 0, img)[2]);
    // 92 40 27

    // Apply a filter implementation on img.
    int [] borderColor = {235, 40, 100};
    int borderThickness = 15;
    applyCustom(img);

    // For debugging
    System.out.println("After:");
    System.out.println(Utilities.getRGBArray(0, 0, img)[0]);
    System.out.println(Utilities.getRGBArray(0, 0, img)[1]);
    System.out.println(Utilities.getRGBArray(0, 0, img)[2]);
    // 53 53 53

    // Write the result to a new image file.
    f = new File("dog_filtered.png");
    ImageIO.write(img, "png", f);
  }

  public static void applyGrayscale(BufferedImage img) {
    int width = img.getWidth();
    int height = img.getHeight();

    // Creating a nested for loop that goes into the image and reads each pixel value.
    for(int row = 0; row < height; row++){
      for(int col = 0; col < width; col++){

        // This gets a copy of the RGB value of a single pixel in the image
        int p = img.getRGB(col, row);
        int a = (p>>24)&0xff;
        int r = (p>>16)&0xff;
        int g = (p>>8)&0xff;
        int b = p&0xff;
        
        // Here we create our condition that will be able to apply the grayscale filter to the image
        // By adding all three variable and dividing them by 3 we are able to apply a gray tone to each pixel.
        int rgb = (r + g + b) / 3;

       
        p = (a<<24) | (rgb<<16) | (rgb<<8) | rgb;
        img.setRGB(col, row, p);

      }
    }
  }

  public static void applyNorak(BufferedImage img) {
    int width = img.getWidth();
    int height = img.getHeight();

    for(int row = 0; row < height; row++){
      for(int col = 0; col < width; col++){

        int p = img.getRGB(col, row);
        int a = (p>>24)&0xff;
        int r = (p>>16)&0xff;
        int g = (p>>8)&0xff;
        int b = p&0xff;

        // Here we get the average of the RGB value.
        int rgb = (r + g + b) / 3;

        if(rgb > 153){
          p = (a<<24) | (rgb<<16) | (rgb<<8) | rgb;
        }
        img.setRGB(col, row, p);

      }
    }

  }

  public static void applyBorder(BufferedImage img, int borderThickness, int[] borderColor) {
    int width = img.getWidth();
    int height = img.getHeight();

    for (int x = 0; x < height; x++) {
      for (int y = 0; y < width; y++) {

        // This if statement will check that the pixel is within the specified border
        if (x < borderThickness || x >= height - borderThickness || y < borderThickness || y >= width - borderThickness) {
          
          // Here we will update the RGB values into the new image with the border properly applied
          int[] p = Utilities.getRGBArray(x,y, img);
          p[0]= borderColor[0];
          p[1] = borderColor[1];
          p[2] = borderColor[2];
          Utilities.setRGB(p, x, y, img);
        }
      }
    }
  }

  public static void applyMirror(BufferedImage img) {
    int width = img.getWidth();
    int height = img.getHeight();

    for(int row = 0; row < height; row++){
      // We do divided by 2 since it only needs to go through the first half of the image.
      for(int col = 0; col < width / 2; col++){
        
        // Our temporary value that stores our pixel value to help swamp them.
        int tempV = width - 1 - col;

        // In these lines we simply swamp the pixels to give it the mirror effect.
        int pixel1 = img.getRGB(col, row);
        int pixel2 = img.getRGB(tempV, row);


        img.setRGB(col, row, pixel2);
        img.setRGB(tempV, row, pixel1);
      }
    }
  }

  public static void applyBlur(BufferedImage img) {
    // TODO
  }

  public static void applyCustom(BufferedImage img) {
    int width = img.getWidth();
    int height = img.getHeight();

    // For my custom filter I am using what I had for mirror filter but messed around with the values 
    // By doing so it splits up the image and flips certain parts of the image and keeps the original ones for some 
    for(int row = 0; row < height; row++){
      // Like the mirror filter except we set the value to 5
      for(int col = 0; col < width / 5; col++){
        
        // Our temperary value that stores our pixel value to help swamp them.
        int tempV = width - 50 - col;
 
        int pixel1 = img.getRGB(col, row);
        int pixel2 = img.getRGB(tempV, row);

        img.setRGB(col, row, pixel2);
        img.setRGB(tempV, row, pixel1);
      }
    }
  }
}
