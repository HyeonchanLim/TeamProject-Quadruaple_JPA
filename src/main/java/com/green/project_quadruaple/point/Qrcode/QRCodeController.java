package com.green.project_quadruaple.point.Qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import javax.imageio.ImageIO;

@RestController("QR")
public class QRCodeController {

//    @GetMapping("generate")
//    public String generateQRCode(@RequestParam("strf_id") String strfId) {
//        try {
//            // QR 코드에 포함할 URL
//            String url = "http://112.222.157.157:5231/api/point/use?strf_id=" + strfId;
//
//            // QR 코드 생성
//            QRCodeWriter qrCodeWriter = new QRCodeWriter();
//            BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 200, 200);
//            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
//
//            // BufferedImage를 Base64로 변환
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(qrImage, "png", baos);
//            String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());
//
//            // Base64 형태로 반환하여 React에서 <img> 태그로 표시 가능
//            return "data:image/png;base64," + base64Image;
//        } catch (WriterException | java.io.IOException e) {
//            e.printStackTrace();
//            return "Error generating QR code: " + e.getMessage();
//        }
//    }


}
