import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class UIManager {
    private static JFrame frame;
    private static JLabel imageLabel;

    static {
        initializeDisplay("Detecção de Rostos - OpenCV");
    }
    
    // Inicializa a janela principal para exibição da imagem
    public static void initializeDisplay(String title) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        imageLabel = new JLabel();
        frame.add(imageLabel);
        frame.setVisible(true);
    }
    
    // Atualiza o JLabel com a nova imagem
    public static void displayFrame(Mat frame) {
        BufferedImage image = convertMatToBufferedImage(frame);
        if (imageLabel != null) {
            imageLabel.setIcon(new ImageIcon(image)); // Atualiza o JLabel com a nova imagem
            imageLabel.repaint(); // Força a repintura do JLabel
        }
    }
    
    // Converte um objeto Mat (OpenCV) para BufferedImage (Swing)
    private static BufferedImage convertMatToBufferedImage(Mat mat) {
        int width = mat.width();
        int height = mat.height();
        int channels = mat.channels();
        
        // Copia os dados do array do Mat  para o BufferedImage
        byte[] sourcePixels = new byte[width * height * channels];
        mat.get(0, 0, sourcePixels);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);
        
        // Retorna a BufferedImage criada
        return image;
    }
}
