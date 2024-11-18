import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetection {

    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
        // Inicializa o detector de rostos com o modelo pré-treinado
        String xmlFile = "resources/haarcascade_frontalface_default.xml";
        CascadeClassifier faceDetector = new CascadeClassifier(xmlFile);

        // Imagem com os rostos
        String imagePath = "images/faces.jpg";
        detectFacesInImage(faceDetector, imagePath);
    }

    private static void detectFacesInImage(CascadeClassifier faceDetector, String imagePath) {
        // Carrega a imagem
        Mat image = Imgcodecs.imread(imagePath);
        if (image.empty()) {
            System.err.println("Não foi possível carregar a imagem: " + imagePath);
            return;
        }
        
        // Detecta os rostos
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections, 1.1, 10, 0, new Size(30, 30), new Size());

        System.out.println("Rostos detectados: " + faceDetections.toArray().length);

        // Desenha os retangulos nos rostos detectados
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, rect.tl(), rect.br(), new Scalar(0, 255, 0), 2);
        }

        // Salva a imagem com  os retangulos
        String outputPath = "images/output_image.jpeg";
        Imgcodecs.imwrite(outputPath, image);
        System.out.println("Salvo em: " + outputPath);
        
        // Exibir  a imagem processada na interface gráfica
        if (image != null){
            UIManager.displayFrame(image);
        }  else{
            System.err.println("Erro no processamento da imagem.");
        }
    }
}
