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

  public Image encodedStringToImage(String encoded) {
    byte[] decoded = Base64.getDecoder().decode(encoded);
    return bytesToImage(decoded);
  }

  private byte[] imageToBytes(Image image, String imageFormat) {
    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
    ByteArrayOutputStream s = new ByteArrayOutputStream();
    byte[] res = null;
    try {
      ImageIO.write(bImage, imageFormat, s);
      res = s.toByteArray();
      s.close();
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    return res;
  }

  // ByteArrayInputStream bai = new ByteArrayInputStream(pByte);
  // ContentHandler contenthandler = new BodyContentHandler();
  // Metadata metadata = new Metadata();
  // Parser parser = new AutoDetectParser();
  // try {
  // parser.parse(bai, contenthandler, metadata);
  //
  // } catch (IOException e) {
  // // TODO Auto-generated catch block
  // e.printStackTrace();
  // } catch (SAXException e) {
  // // TODO Auto-generated catch block
  // e.printStackTrace();
  // } catch (TikaException e) {
  // // TODO Auto-generated catch block
  // e.printStackTrace();
  // }
  // System.out.println("Mime: " + metadata.get(Metadata.CONTENT_TYPE));
  // return metadata.get(Metadata.CONTENT_TYPE);



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
