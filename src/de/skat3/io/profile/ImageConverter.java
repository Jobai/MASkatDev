package de.skat3.io.profile;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageConverter {

  public String imageToString(String filepath) {
    byte[] imageToBytes = imageToBytes(filepath);
    String encoded = Base64.getEncoder().encodeToString(imageToBytes);
    return encoded;
  }

  public Image encodedStringToImage(String encoded, String filepath) {
    byte[] decoded = Base64.getDecoder().decode(encoded);
    return bytesToImage(decoded, filepath);
  }

  private byte[] imageToBytes(String filepath) {
    File imgFile = new File(filepath);
    BufferedImage bufferedImage = null;
    byte[] imageInBytes = null;
    try {
      bufferedImage = ImageIO.read(imgFile);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      String format = checkImageFormat(imgFile);
      ImageIO.write(bufferedImage, format, baos);
      baos.flush();
      imageInBytes = baos.toByteArray();
      baos.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return imageInBytes;
  }

  private Image bytesToImage(byte[] decoded, String filepath) {
    BufferedImage img = null;
    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(decoded);
      img = ImageIO.read(inputStream);
      File outputfile = new File(filepath);
      ImageIO.write(img, "jpg", outputfile);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return img;
  }

  private String checkImageFormat(File imgFile) throws IOException {
    // get image format in a file
    // File file = new File("newimage.jpg");
    // create an image input stream from the specified file
    ImageInputStream iis = ImageIO.createImageInputStream(imgFile);
    // get all currently registered readers that recognize the image format
    Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
    if (!iter.hasNext()) {
      throw new RuntimeException("No readers found!");
    }
    // get the first reader
    ImageReader reader = iter.next();
    // System.out.println("Format: " + reader.getFormatName());
    // close stream
    iis.close();
    return reader.getFormatName();
  }

  // private String bytesToString(byte[] array) {
  // String encoded = Base64.getEncoder().encodeToString(array);
  // return encoded;
  // }
  //
  // private JsonObject stringToJsonObject(String encoded) {
  // JsonParser parser = new JsonParser();
  // JsonObject object = parser.parse("{\"image\": \"" + encoded + "\"}").getAsJsonObject();
  // return object;
  // }
  //
  // private String jsonObjectToString(JsonObject object) {
  // return object.toString();
  // }
  //
  // private byte[] stringToBytes(String encoded) {
  // byte[] decoded = Base64.getDecoder().decode(encoded);
  // return decoded;
  // }


}
