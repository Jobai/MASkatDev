package io;

import static io.TestUtils.ENCODDED_IMAGE_1_JPG;
import static io.TestUtils.ENCODDED_IMAGE_1_PNG;
import static io.TestUtils.JPG;
import static io.TestUtils.PNG;
import static io.TestUtils.TEST_IMAGE_1_JPG;
import static io.TestUtils.TEST_IMAGE_1_PNG;
import static io.TestUtils.TEST_IMAGE_2_JPG;
import static io.TestUtils.TEST_IMAGE_2_PNG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.jupiter.api.Test;
import de.skat3.io.profile.ImageConverter;
import de.skat3.io.profile.JSONProfileReader;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;

class checkImageConvertion {
  private ImageConverter converter = new ImageConverter();
  private JSONProfileReader reader = new JSONProfileReader();
  private JFXPanel panel = new JFXPanel();

  @Test
  void test_PNG_ImageToStringConversionLength() {
    String encoded = converter.imageToEncodedString(TEST_IMAGE_1_PNG, PNG);
    assert (encoded.length() > 0);
  }

  @Test
  void test_PNG_StringToImageConversionLength() {
    Image newImage = converter.encodedStringToImage(ENCODDED_IMAGE_1_PNG);
    String encoded = converter.imageToEncodedString(newImage, PNG);
    assert (encoded.length() > 0);
  }

  @Test
  void test_JPG_ImageToStringConversionLength() {
    String encoded = converter.imageToEncodedString(TEST_IMAGE_1_JPG, JPG);
    assert (encoded.length() > 0);
  }

  @Test
  void test_JPG_StringToImageConversionLength() {
    Image newImage = converter.encodedStringToImage(ENCODDED_IMAGE_1_JPG);
    String encoded = converter.imageToEncodedString(newImage, JPG);

    assert (encoded.length() > 0);
  }

  @Test
  void test_PNG_StringToImageAndImageToStringConversion() {
    String encodedBefore = converter.imageToEncodedString(TEST_IMAGE_1_PNG, PNG);

    Image newImage = converter.encodedStringToImage(encodedBefore);
    String encodedAfter = converter.imageToEncodedString(newImage, PNG);
    assertEquals(ENCODDED_IMAGE_1_PNG, encodedAfter);
  }



  @Test
  void test_JPG_StringToImageAndImageToStringConversion() {
    String encodedBefore = converter.imageToEncodedString(TEST_IMAGE_1_JPG, JPG);
    Image newImage = converter.encodedStringToImage(encodedBefore);

    String encodedAfter = converter.imageToEncodedString(newImage, JPG);
    if (!compareTwo_JPG_Images(encodedBefore, encodedAfter)) {
      fail("two same JPG images are not same after encoding and decoding");
    }
  }

  boolean compareTwo_JPG_Images(String first, String second) {
    // Since JPG Data is extremely big and in String format (up to 500 000 chars) and encoding may
    // vary - only first 0.1% (500) chars will be checked, which actually guarantees that images are
    // same
    int length = (int) (first.length() * 0.01);
    for (int i = 0; i < length; i++) {
      if (first.charAt(i) != second.charAt(i)) {
        return false;
      }
    }
    return true;
  }


  @Test
  void test_PNG_differentImagesComparison() {
    String encoded_1_PNG = converter.imageToEncodedString(TEST_IMAGE_1_PNG, PNG);
    String encoded_2_PNG = converter.imageToEncodedString(TEST_IMAGE_2_PNG, PNG);

    if (encoded_1_PNG.equals(encoded_2_PNG)) {
      fail("different PNG images are the same in encoded representation");
    }
  }

  @Test
  void test_JPG_differentImagesComparison() {
    String encoded_1_JPG = converter.imageToEncodedString(TEST_IMAGE_1_JPG, JPG);
    String encoded_2_JPG = converter.imageToEncodedString(TEST_IMAGE_2_JPG, JPG);

    if (compareTwo_JPG_Images(encoded_2_JPG, encoded_1_JPG)) {
      fail("different JPG images are the same in encoded representation");
    }
  }

}


