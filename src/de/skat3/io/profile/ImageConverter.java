package de.skat3.io.profile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class ImageConverter {

  /**
   * Converts JavaFX Image to Encoded String.
   * 
   * @param image the image to be encoded.
   * @param imageFormat the format of the image to encode.
   * @return String that contains incoded image
   */
  public String imageToEncodedString(Image image, String imageFormat) {
    if (image == null) {
      System.out.println("Image == null");
      return "";
    } else {
      byte[] imageToBytes = imageToBytes(image, imageFormat);
      String encoded = Base64.getEncoder().encodeToString(imageToBytes);
      return encoded;
    }
  }

  /**
   * Converts encoded String to Image.
   * 
   * @param encoded the encoded String that is to be converted to Image.
   * @return JavaFX Image that was generated from the encoded String.
   */
  public Image encodedStringToImage(String encoded) {
    byte[] decoded;
    try {
      decoded = Base64.getDecoder().decode(encoded);
    } catch (NullPointerException ex) {
      return null;
    }
    return bytesToImage(decoded);
  }

  /**
   * Converts JavaFX Image to byte array.
   * 
   * @param image the Image that is to be converted.
   * @param imageFormat the format of the Image that is to be converted.
   * @return byte array generated from the Image.
   */
  private byte[] imageToBytes(Image image, String imageFormat) {
    BufferedImage bufImage = SwingFXUtils.fromFXImage(image, null);
    ByteArrayOutputStream s = new ByteArrayOutputStream();
    byte[] res = null;
    try {
      ImageIO.write(bufImage, imageFormat, s);
      res = s.toByteArray();
      s.close();
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    return res;
  }

  /**
   * Converts syntax of decoded bytes to JavaFX Image. Unused local variables are required by JavaFX
   * syntax rules.
   * 
   * @param decoded the array of the decoded bytes.
   * @return JavaFX Image generated from array of the decoded bytes.
   */
  private Image bytesToImage(byte[] decoded) {
    // The unused javafx components are needed to be created in order for image to work
    // It is javafx platform specific requirement
    JFXPanel panel = new JFXPanel();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(decoded);
    Image image = new Image(inputStream);
    ImageView imageView = new ImageView(image);

    int width = (int) image.getWidth();
    int height = (int) image.getHeight();

    final Canvas canvas = new Canvas(width, height);
    GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    graphicsContext.drawImage(image, 0, 0, width, height);
    return image;
  }
}
