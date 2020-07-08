package fr.gouv.beta.fabnum.commun.metier.util;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;

public class QRCodeUtils {
    
    public static String generateQRCodePng(String barcodeText) throws Exception {
        
        if (barcodeText.length() > 2950) { throw new Exception("Texte trop long pour la génération d'un QR Code optimal."); }
        
        ByteArrayOutputStream stream = QRCode.from(barcodeText)
                                               .withSize(250, 250)
                                               .to(ImageType.PNG)
                                               .stream();
        
        BASE64Encoder encoder = new BASE64Encoder();
        
        return "data:image/png;base64," + encoder.encode(stream.toByteArray());
    }
}
