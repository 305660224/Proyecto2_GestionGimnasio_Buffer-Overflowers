/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class GeneradorXML {
        
      
    public static void generarReportePagoXML(String ruta, int idPago, String cliente, Date fecha, double subtotal, double impuesto, double total) {
        try {
            // Crear documento XML
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            
            // Elemento raíz
            Element facturaElement = doc.createElement("Factura");
            doc.appendChild(facturaElement);
            
            // Formatear fecha
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            
            // Agregar elementos
            agregarElemento(doc, facturaElement, "IdPago", String.valueOf(idPago));
            agregarElemento(doc, facturaElement, "Cliente", cliente);
            agregarElemento(doc, facturaElement, "Fecha", sdf.format(fecha));
            agregarElemento(doc, facturaElement, "Subtotal", String.format("%.2f", subtotal));
            agregarElemento(doc, facturaElement, "Impuesto", String.format("%.2f", impuesto));
            agregarElemento(doc, facturaElement, "Total", String.format("%.2f", total));
            agregarElemento(doc, facturaElement, "Moneda", "CRC");
            agregarElemento(doc, facturaElement, "Emisor", "Gimnasio UTN");
            agregarElemento(doc, facturaElement, "TipoDocumento", "FacturaElectronica");
            
            // Guardar archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(ruta));
            
            transformer.transform(source, result);
            
            System.out.println("Factura XML generada: " + ruta);
            
        } catch (Exception e) {
            System.err.println("Error generando XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void agregarElemento(Document doc, Element parent, String nombre, String valor) {
        Element element = doc.createElement(nombre);
        element.appendChild(doc.createTextNode(valor));
        parent.appendChild(element);
    }
}

