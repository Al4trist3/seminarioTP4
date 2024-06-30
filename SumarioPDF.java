import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SumarioPDF {

    private static String directorioSalida = "pdfs"; // Carpeta para guardar los archivos PDF relativa al proyecto
    private static PDType1Font courier = new PDType1Font(Standard14Fonts.FontName.COURIER);
    private static PDType1Font courierBold = new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD);

    public static void imprimirDiligencia(Diligencia diligencia, PDDocument documento) {
    
        try {
            PDPage pagina = new PDPage();

            documento.addPage(pagina);

            PDPageContentStream contenidoStream = new PDPageContentStream(documento, pagina);

            // Establecer la fuente y el tamaño del texto
            contenidoStream.setFont(SumarioPDF.courier, 12);

            // Insertar texto en la página
            contenidoStream.beginText();
            contenidoStream.newLineAtOffset(50, 700); 
            contenidoStream.showText(diligencia.generarTextoCuerpo());
            contenidoStream.endText();

            contenidoStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void imprimirSumario(Sumario sumario) {

        String nombreArchivo = "Sumario_" + sumario.getNumero();
        try {
            PDDocument documento = new PDDocument();
            PDPage pagina = new PDPage();

            documento.addPage(pagina);

            // Crear un objeto content stream
            PDPageContentStream contenidoStream = new PDPageContentStream(documento, pagina);

            SumarioPDF.generarCaratula(sumario,  contenidoStream);

            for (Diligencia diligencia : sumario.getDiligencias()) {
                imprimirDiligencia(diligencia, documento);
            }

            // Cerrar el content stream
            contenidoStream.close();

            // Guardar documento
            documento.save(nombreArchivo);


            // Obtener la ruta completa al directorio de salida relativo al directorio actual
            String directorioActual = System.getProperty("user.dir");
            String rutaCompleta = Paths.get(directorioActual, SumarioPDF.directorioSalida, nombreArchivo).toString();

            // Guardar el documento PDF en la ruta especificada
            Path path = Paths.get(rutaCompleta);
            documento.save(Files.newOutputStream(path));


            // Cerrar documento
            documento.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void generarCaratula(Sumario sumario,  PDPageContentStream contenidoStream) {

        try {
            // Establecer la fuente y el tamaño del texto
            contenidoStream.setFont(SumarioPDF.courierBold, 18);

            // Imprime la caratula del sumario
            contenidoStream.beginText();
            contenidoStream.newLineAtOffset(50, 700); // Posición del texto en la página (x, y)
            contenidoStream.showText("Numero: " + sumario.getNumero());
            contenidoStream.newLineAtOffset(0, -50);
            contenidoStream.showText("Juzgado: " + sumario.getJuzgado());
            contenidoStream.newLineAtOffset(0, -50);
            contenidoStream.showText("Fiscalia: " + sumario.getFiscalia());
            contenidoStream.newLineAtOffset(0, -50);
            contenidoStream.showText("Juez: " + sumario.getJuez());
            contenidoStream.newLineAtOffset(0, -50);
            contenidoStream.showText("Fiscal: " + sumario.getFiscal());
            contenidoStream.newLineAtOffset(0, -50);
            contenidoStream.showText("Imputados: " + sumario.getImputados());
            contenidoStream.newLineAtOffset(0, -50);
            contenidoStream.showText("Damnificados: " + sumario.getDamnificados());
            contenidoStream.endText();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
