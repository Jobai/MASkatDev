package de.skat3.io.profile;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;

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
    BufferedImage bufferedImage = (BufferedImage) image;
    WritableRaster raster = bufferedImage.getRaster();
    DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

    return data.getData();
  }

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

  private Image bytesToImage(byte[] decoded) {
    BufferedImage img = null;
    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(decoded);
      img = ImageIO.read(inputStream);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return img;
  }
}
