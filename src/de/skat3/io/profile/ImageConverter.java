package de.skat3.io.profile;


import java.io.ByteArrayInputStream;
import java.util.Base64;
import javafx.embed.swing.JFXPanel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;

public class ImageConverter {

  public String imageToEncodedString(Image image) {
    if (image == null) {
      System.out.println("Image == null");
      return "";
    } else {
      byte[] imageToBytes = imageToBytes(image);
      String encoded = Base64.getEncoder().encodeToString(imageToBytes);
      return encoded;
    }
  }

  public Image encodedStringToImage(String encoded) {
    byte[] decoded = Base64.getDecoder().decode(encoded);
    return bytesToImage(decoded);
  }

  private byte[] imageToBytes(Image image) {
    int w = (int) image.getWidth();
    int h = (int) image.getHeight();
    byte[] buf = new byte[w * h * 4];
    image.getPixelReader().getPixels(0, 0, w, h, PixelFormat.getByteBgraInstance(), buf, 0, w * 4);

    return buf;
  }

  private Image bytesToImage(byte[] decoded) {
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

  // private Image bytesToImage(byte[] decoded) {
  // BufferedImage img = null;
  // try {
  // ByteArrayInputStream inputStream = new ByteArrayInputStream(decoded);
  // img = ImageIO.read(inputStream);
  // } catch (IOException e) {
  // // TODO Auto-generated catch block
  // e.printStackTrace();
  // }
  // return img;
  // }

  // private byte[] imageToBytes(Image image) {
  // BufferedImage bufferedImage = (BufferedImage) image;
  // WritableRaster raster = bufferedImage.getRaster();
  // DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
  //
  // return data.getData();
  // }

  // private byte[] imageToBytes(Image image, String format) {
  // BufferedImage bufferedImage = (BufferedImage) image;
  // byte[] imageInBytes = null;
  // try {
  // ByteArrayOutputStream baos = new ByteArrayOutputStream();
  // // String format = checkImageFormat(image);
  // ImageIO.write(bufferedImage, format, baos);
  // baos.flush();
  // imageInBytes = baos.toByteArray();
  // baos.close();
  // } catch (IOException e) {
  // // TODO Auto-generated catch block
  // e.printStackTrace();
  // }
  // return imageInBytes;
  // }


}
