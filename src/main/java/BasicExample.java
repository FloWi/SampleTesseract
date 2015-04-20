import org.bytedeco.javacpp.*;
import static org.bytedeco.javacpp.lept.*;
//import static org.bytedeco.javacpp.tesseract.*;

public class BasicExample {
    public static void main(String[] args) {
        System.out.println("TESSDATA: " + System.getenv("TESSDATA_PREFIX"));
        BytePointer outText;

        tesseract.TessBaseAPI api = new tesseract.TessBaseAPI();
        // Initialize tesseract-ocr with English, without specifying tessdata path
        if (api.Init(null, "eng") != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }

        // Open input image with leptonica library
        lept.PIX image = pixRead(args.length > 0 ? args[0] : "/usr/src/tesseract-3.02/phototest.tif");
        api.SetImage(image);
        // Get OCR result
        outText = api.GetUTF8Text();
        System.out.println("OCR output:\n" + outText.getString());

        // Destroy used object and release memory
        api.End();
        outText.deallocate();
        pixDestroy(image);
    }
}

