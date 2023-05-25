package org.foi.emp.pomocnici;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeGenerator {
  public static String generateQRCodeImage(String qrCodeData) throws Exception {
    QRCodeWriter barcodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = barcodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, 200, 200);

    var bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

    ByteArrayOutputStream output = new ByteArrayOutputStream();
    try {
      ImageIO.write(bufferedImage, "png", output);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    String imageAsBase64 = Base64.getEncoder().encodeToString(output.toByteArray());

    return imageAsBase64;
  }
}
