/* 
 * Copyright (c) 2020, Avidesa MacPollo S.A.
 * Todos los Derechos Reservados.
 * 
 * Este es un software propietario y su contenido es confidencial de Avidesa MacPollo S.A y sus afiliados.
 * 
 * Toda la informacion contenida en el presente es y sigue siendo propiedad de Avidesa MacPollo S.A y sus
 * afiliados. Los conceptos intelectuales y tecnicos contenidos en este documento son propiedad de
 * Avidesa MacPollo S.A y sus afiliados y estan protegidos por secretos comerciales o leyes de derechos 
 * de autor. La difusion de esta informacion o reproduccion de este material esta estrictamente prohibida 
 * a menos que se obtenga el permiso previo escrito de Avidesa MacPollo S.A o sus afiliados.
 */

package com.macpollo.facturas.componentes.principal;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Formulario para procesar las facturas electrónicas.
 * 
 * @author Joan Romero
 */
public class Principal extends JFrame
{
    /**
     * Servidor FTP para copiar los archivos.
     */
    private final String SERV_FTP;
    
    /**
     * Usuario para la conexión FTP.
     */
    private final String USR_FTP;
    
    /**
     * Contraseña para la conexión FTP.
     */
    private final String PASS_FTP;
    
    /**
     * Ruta para copiar los archivos temporalmente para procesar.
     */
    private final String RUTA_FTP_TMP;
    
    /**
     * Ruta para copiar los archivos después de procesados.
     */
    private final String RUTA_FTP_FAC;
    
    /** NIT sin digito de verificación de la empresa BALANCEADOS S.A. */
    private final String NIT_BALANCEADOS = "900182154";
    /** NIT sin digito de verificación de la empresa AVIDESA MAC POLLO S.A. */
    private final String NIT_AVIDESA_MACPOLLO = "890201881";
    /** NIT sin digito de verificación de la empresa AVIDESA OCCIDENTE S.A. */
    private final String NIT_OCCIDENTE = "815000863";
    
    /**
     * Genera una instancia para {@code Principal}.
     */
    public Principal()
    {
        initComponents();
        
        setSize(690, 490);
        setLocationRelativeTo(null);
        
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("/com/macpollo/facturas/iconos/componentes/app.png")));
        
//        SERV_FTP = "192.168.1.38";
//        USR_FTP = "sapfacturacion";
//        PASS_FTP = "87kp8ina74";

        /** Datos productivo */
        SERV_FTP = "192.168.1.36";
        /** Datos calidad */
//        SERV_FTP = "192.168.1.18";
        
        USR_FTP = "root";
        PASS_FTP = "Sl1200mk2.";
        
        /** Datos productivo */
        RUTA_FTP_TMP = "/home/shares/sapfacturacion/tmpfactura";
        RUTA_FTP_FAC = "/sapfacturacion/facturae/recepcion%s/soc%s";
        /** Datos calidad */
//        RUTA_FTP_TMP = "/home/tmpfactura";
//        RUTA_FTP_FAC = "/home/recepcion";
        
        setAnchosColumnasArchivos();
    }
    
    /**
     * Establece el ancho para las columnas de la tabla de archivos.
     */
    private void setAnchosColumnasArchivos()
    {
        archivos.getTableHeader().setReorderingAllowed(false);
        archivos.getTableHeader().setResizingAllowed(false);
        
        TableColumnModel columnModel = archivos.getColumnModel();

        int[] anchos = { 200, 30, 30, 30, 100 };
        for (int i = 0; i < columnModel.getColumnCount(); i++)
        {
            columnModel.getColumn(i).setPreferredWidth(anchos[i]);
        }
    }

    /**
     * Inicializa los componentes de la ventana.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        labIcono = new javax.swing.JLabel();
        labTitulo = new javax.swing.JLabel();
        labCarpeta = new javax.swing.JLabel();
        carpeta = new javax.swing.JTextField();
        cargar = new javax.swing.JButton();
        scrollArchivos = new javax.swing.JScrollPane();
        archivos = new javax.swing.JTable()
        {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof JComponent)
                {
                    if(column == 4)
                    {
                        JComponent jc = (JComponent) c;
                        jc.setToolTipText(getValueAt(row, column).toString());
                    }
                }
                return c;
            }
        }
        ;
        procesar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Recepción Facturas Electrónicas");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labIcono.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/macpollo/facturas/iconos/componentes/logo.png"))); // NOI18N
        getContentPane().add(labIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 640, -1));

        labTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labTitulo.setText("RECEPCIÓN FACTURAS ELECTRÓNICAS");
        getContentPane().add(labTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 640, 20));

        labCarpeta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labCarpeta.setText("Carpeta Facturas");
        getContentPane().add(labCarpeta, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 90, 20));

        carpeta.setEditable(false);
        carpeta.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                carpetaMouseClicked(evt);
            }
        });
        getContentPane().add(carpeta, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 520, -1));

        cargar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/macpollo/facturas/iconos/botones/ico_cargar.png"))); // NOI18N
        cargar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cargarActionPerformed(evt);
            }
        });
        getContentPane().add(cargar, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 149, 30, 22));

        archivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "Archivo", "Descomprimir", "Procesar", "Finalizar", "Estado"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        archivos.setSelectionBackground(new java.awt.Color(102, 102, 102));
        scrollArchivos.setViewportView(archivos);

        getContentPane().add(scrollArchivos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 640, 220));

        procesar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        procesar.setText("Procesar");
        procesar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                procesarActionPerformed(evt);
            }
        });
        getContentPane().add(procesar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 410, 90, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Acción al hacer clic sobre el campo de Carpeta. En doble clic muestra el diálogo para seleccionar la
     * carpeta con los archivos {@code ZIP} de facturas.
     * 
     * @param evt Evento lanzado por el componente.
     */
    private void carpetaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_carpetaMouseClicked
    {//GEN-HEADEREND:event_carpetaMouseClicked
        if (evt.getClickCount() == 2 && carpeta.isEnabled())
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int sel = fileChooser.showOpenDialog(null);

            if (sel == JFileChooser.APPROVE_OPTION)
            {
                String ruta = fileChooser.getSelectedFile().getAbsolutePath();
                carpeta.setText(ruta);
                
                getArchivosZip();
            }
        }
    }//GEN-LAST:event_carpetaMouseClicked

    /**
     * Evento al presionar el botón de Procesar.
     * 
     * @param evt Evento lanzado por el componente.
     */
    private void procesarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_procesarActionPerformed
    {//GEN-HEADEREND:event_procesarActionPerformed
        accionProcesar();
    }//GEN-LAST:event_procesarActionPerformed

    /**
     * Evento al presionar el botón de Cargar.
     * 
     * @param evt Evento lanzado por el componente.
     */
    private void cargarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cargarActionPerformed
    {//GEN-HEADEREND:event_cargarActionPerformed
        getArchivosZip();
    }//GEN-LAST:event_cargarActionPerformed

    /**
     * Acción para validar y realizar el procesamiento de las facturas.
     */
    private void accionProcesar()
    {
        if (carpeta.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una carpeta con las facturas",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
        else if (archivos.getRowCount() == 0)
        {
            JOptionPane.showMessageDialog(null, "No hay ningún archivo ZIP con facturas en la carpeta "
                    + "seleccionada", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            new Thread()
            {
                @Override
                public void run() 
                {
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    setEnabledComponentes(new boolean[] { false, false, false });
                    
                    procesarArchivos();
                    
                    archivos.clearSelection();
                    JOptionPane.showMessageDialog(null, "Ha realizado el procesamiento de las facturas", 
                            "Procesado", JOptionPane.INFORMATION_MESSAGE);
                    
                    setEnabledComponentes(new boolean[] { true, true, true });
                    setCursor(Cursor.getDefaultCursor());
                }
            }.start();
        }
    }
    
    /**
     * Procesa cada uno de los archivos {@code ZIP} dentro de la carpeta suministrada, descompimiéndolos,
     * enviando el {@code XML} y {@code PDF} a SAP para procesamiento y almacenamiento.
     */
    private void procesarArchivos()
    {
        archivos.clearSelection();
        limpiarCamposTabla();
        
        String rutaCar = carpeta.getText();
        
        for (int i = 0; i < archivos.getRowCount(); i++)
        {
            String nom = archivos.getValueAt(i, 0).toString();
            
            File zip = new File(rutaCar + "/" + nom);
            if (!zip.exists())
            {
                archivos.setValueAt("Archivo No Existe", i, 4);
            }
            else
            {
                String rutaZip = rutaCar + "/tmp_" + nom.replace(".zip", "");
                
                try
                {
                    archivos.scrollRectToVisible(archivos.getCellRect(i, 0, true));
                    archivos.setRowSelectionInterval(i, i);
                    
                    extraerArchivos(rutaCar, nom, rutaZip);
                    archivos.setValueAt(true, i, 1);
                    
                    if(validacionesArchivo(rutaZip, i)) {
                        String ruta = enviarFacturaSap(rutaZip);
                        archivos.setValueAt(true, i, 2);

                        //String sociedad = socFec.split(",")[0];JOrtega 15.03.2021
                        //String fecha = socFec.split(",")[1];JOrtega 15.03.2021

                        //antes asi
                        //copiarArchivosFtp(rutaZip, ".xml", String.format(RUTA_FTP_FAC, fecha, sociedad));
                        //copiarArchivosFtp(rutaZip, ".pdf", String.format(RUTA_FTP_FAC, fecha, sociedad));
                        //ahora asi
                        copiarArchivosFtp(rutaZip, ".xml", ruta);
                        copiarArchivosFtp(rutaZip, ".pdf", ruta);

                        archivos.setValueAt(true, i, 3);

                        archivos.setValueAt("Completado", i, 4);

                        zip.delete();
                    } else {
                        continue;
                    }
                }
                catch (Exception ex)
                {
                    archivos.setValueAt("Error Procesando. " + ex.getMessage(), i, 4);
                }
                
                try
                {
                    eliminarArchivosFtp(rutaZip, ".xml", RUTA_FTP_TMP);
                    eliminarArchivosFtp(rutaZip, ".pdf", RUTA_FTP_TMP);
                }
                catch (Exception ex)
                {
                    System.err.println("Error Eliminando FTP. " + ex.getMessage());
                }
                
                eliminarArchivos(rutaZip);
            }
        }
    }
    
    /**
     * Extrae los archivos {@code XML} y {@code PDF} que se encuentran en un archivo {@code ZIP} en una
     * carpeta temporal.
     * 
     * @param rutaCar Ruta de la ubicación del archivo.
     * @param nombre Nombre del archivos {@code ZIP}.
     * @param rutaZip Ruta de la carpeta temporal para extraer los archivos.
     * @throws Exception Si se presenta algún error en el proceso.
     */
     private void extraerArchivos(String rutaCar, String nombre, String rutaZip) throws Exception {
         //Open the file
        try (ZipFile zip = new ZipFile(rutaCar + "/" + nombre)) {
          //FileSystem fileSystem = FileSystems.getDefault();
          Enumeration<? extends ZipEntry> entries = zip.entries();
          //We will unzip files in this folder
          new File(rutaZip + "/").mkdirs();
          //Iterate over entries
          while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            File f = new File(rutaZip + "/" + entry.getName());
            //If directory then create a new directory in uncompressed folder
            if (entry.isDirectory()) {
              if (!f.isDirectory() && !f.mkdirs()) {
                throw new IOException("failed to create directory " + f);
              }
            }
            //Else create the file
            else {
              File parent = f.getParentFile();
              if (!parent.isDirectory() && !parent.mkdirs()) {
                throw new IOException("failed to create directory " + parent);
              }
              try(InputStream in = zip.getInputStream(entry)) {
                  Files.copy(in, f.toPath());
              }
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
     }

    /**
     * Valida los archivos {@code XML} y {@code PDF} que vengan los archivos que son
     * 
     * @param rutaZip Ruta de la ubicación del archivo.
     * @param indice 
     * @return
     * @throws Exception Si se presenta algún error en el proceso.
     */
    private boolean validacionesArchivo(String rutaZip, int indice) throws Exception {
        File[] listaXml = getArchivosExtension(rutaZip, ".xml");
        File[] listaPdf = getArchivosExtension(rutaZip, ".pdf");
        if (listaXml.length == 0) {
            archivos.setValueAt("Error Procesando. Falta el archivo .xml", indice, 4);
            JOptionPane.showMessageDialog(null, "No se encuentra el archivo XML dentro del archivo ZIP, por favor revise", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (listaPdf.length == 0) {
            archivos.setValueAt("Error Procesando. Falta el archivo .pdf", indice, 4);
            JOptionPane.showMessageDialog(null, "No se encuentra el archivo PDF dentro del archivo ZIP, por favor revise", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (listaPdf.length > 1 || listaXml.length > 1) {
            archivos.setValueAt("Error Procesando. Solo debe haber un archivo .pdf y .xml", indice, 4);
            JOptionPane.showMessageDialog(null, "Se encontraron archivos PDF o XML adicionales dentro del archivo ZIP, por favor revise", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                
//                Charset utf8 = StandardCharsets.UTF_8;
                
//                List<String> fileContent = Files.readAllLines(Paths.get(listaXml[0].getPath()), utf8);
                // Assume the ArrayList is named stringList
//                StringBuilder buffer = new StringBuilder();
//                fileContent.forEach(current -> {
//                    buffer.append(current).append("\n");
//                });
//
//                BufferedReader br = new BufferedReader(new StringReader(buffer.toString()));
                Document document = null;
                try {
                    document = builder.parse(listaXml[0]);
                } catch(IOException | SAXException ex) {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(listaXml[0]), "UTF-8"))) {
                        document = builder.parse(new InputSource(in));
                    }
                }
                
                if (document != null) {
                    document.getDocumentElement().normalize();
                    boolean encontroAttachedDocument = false;
                    Element root = document.getDocumentElement();
                    if (root.getNodeName().equals("AttachedDocument")) {
                        String atributo = root.getAttribute("xmlns");
                        if(atributo != null && !atributo.equals("")) {
                            encontroAttachedDocument = true;
                        }
                    }
                    if (!encontroAttachedDocument) { // validación Attachment Document
                        archivos.setValueAt("Error Procesando. No subió archivo a SAP Attachment Document", indice, 4);
                        JOptionPane.showMessageDialog(null, "En el archivo XML NO se encontro la etiqueta AttachedDocument, por favor revise", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                    // Validación NIT
                    boolean encontroEtiquetaNIT = false;
                    NodeList nodeReceiver = root.getElementsByTagName("cac:ReceiverParty");
                    Element ReceiverParty = (Element) nodeReceiver.item(0);
                    if (ReceiverParty != null) {
                        NodeList nodePartyTaxScheme = ReceiverParty.getElementsByTagName("cac:PartyTaxScheme");
                        Element PartyTaxScheme = (Element) nodePartyTaxScheme.item(0);
                        if (PartyTaxScheme != null ) {
                            NodeList nodeCompanyID = PartyTaxScheme.getElementsByTagName("cbc:CompanyID");
                            Element CompanyID = (Element) nodeCompanyID.item(0);
                            if (CompanyID != null ) {
                                encontroEtiquetaNIT = true;
                                String valor = CompanyID.getTextContent();
                                if (valor == null || valor.equals("")){
                                    archivos.setValueAt("Error Procesando. No subió archivo a SAP NO se encuentra NIT empresa", indice, 4);
                                    JOptionPane.showMessageDialog(null, "En el archivo XML NO se encontró el NIT de la empresa en la estructura requerida, por favor revise", "Advertencia", JOptionPane.WARNING_MESSAGE);
                                    return false;
                                }
                                if(!(valor.equals(NIT_AVIDESA_MACPOLLO) || valor.equals(NIT_BALANCEADOS) || valor.equals(NIT_OCCIDENTE))) {
                                    archivos.setValueAt("Error Procesando. No subió archivo a SAP NO se encuentra NIT empresa", indice, 4);
                                    JOptionPane.showMessageDialog(null, "En el archivo XML el NIT " + valor + " NO coincide con ninguno de los NITS de las empresas registradas, por favor revise", "Advertencia", JOptionPane.WARNING_MESSAGE);
                                    return false;
                                }
                            }
                        }
                    }
                    if (!encontroEtiquetaNIT) {
                        archivos.setValueAt("Error Procesando. No subió archivo a SAP NO se encuentra NIT empresa", indice, 4);
                        JOptionPane.showMessageDialog(null, "En el archivo XML NO se encontró el NIT de la empresa en la estructura requerida, por favor revise", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                } else {
                    archivos.setValueAt("Error Procesando. No subió archivo a SAP Attachment Document", indice, 4);
                    JOptionPane.showMessageDialog(null, "En el archivo XML NO se encontro la etiqueta AttachedDocument, por favor revise", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            } catch (HeadlessException | IOException | ParserConfigurationException | DOMException | SAXException ex) {
                archivos.setValueAt("Error Procesando. No subió archivo a SAP Attachment Document", indice, 4);
                JOptionPane.showMessageDialog(null, "En el archivo XML NO se encontro la etiqueta AttachedDocument, por favor revise", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        return true;
    }
    
    /**
     * Copia los archivos {@code XML} y {@code PDF} de la factura a la carpeta temporal {@link #RUTA_FTP_TMP}
     * e invoca la función en SAP para procesar los documentos.
     * 
     * @param ruta Ruta donde se encuentran los archivos {@code XML} y {@code PDF}.
     * @return Sociedad y fecha (año) de la factura separados por coma: {@code sociedad,año}.
     * @throws Exception Si se presenta algún error en el proceso. En caso de que la factura no se procese y
     * el código de retorno sea diferente a {@code 1}.
     */
    private String enviarFacturaSap(String ruta) throws Exception
    {
        copiarArchivosFtp(ruta, ".xml", RUTA_FTP_TMP);
        copiarArchivosFtp(ruta, ".pdf", RUTA_FTP_TMP);
        
        JCoDestination destination = JCoDestinationManager.getDestination("config/sap");
        
        JCoFunction funcion = destination.getRepository().getFunction("ZFSD_GET_FACT_RECIBIDAS");

        if (funcion == null)
        {
            throw new Exception("La función ZFSD_GET_FACT_RECIBIDAS no fue encontrada en SAP");
        }
        
        JCoTable factura = funcion.getTableParameterList().getTable("TFACT_PROVEEDOR");
        
        File[] listaXml = getArchivosExtension(ruta, ".xml");
        String xml = listaXml.length == 0 ? "" : listaXml[0].getName();
        
        File[] listaPdf = getArchivosExtension(ruta, ".pdf");
        String pdf = listaPdf.length == 0 ? "" : listaPdf[0].getName();
        
        factura.appendRow();
        //factura.setValue("NIT", "");
        System.out.println(RUTA_FTP_TMP + "/" + xml.toLowerCase().replace(" ",""));
        System.out.println(RUTA_FTP_TMP + "/" + pdf.toLowerCase().replace(" ",""));
        factura.setValue("RUT_XML", RUTA_FTP_TMP + "/" + xml.toLowerCase().replace(" ",""));
        factura.setValue("RUT_PDF", RUTA_FTP_TMP + "/" + pdf.toLowerCase().replace(" ",""));
        //factura.setValue("ESTADO", "");
        factura.setValue("ESCRITORIO", "X");
        
        funcion.execute(destination);
        
        String codigo = funcion.getExportParameterList().getString("CODIGO");
        if (!codigo.equals("1"))
        {
            throw new Exception(codigo.equals("4") ? "Error al procesar el archivo ya subio a SAP " : "Error al procesar la factura en SAP. Codigo " + codigo);
        }
        
        
        String sociedad = funcion.getExportParameterList().getString("SOCIEDAD");
        //String fecha = funcion.getExportParameterList().getString("FECHA");JOrtega 16.03.2021 campo fecha ya no se retorna
        String rutares = funcion.getExportParameterList().getString("RUTA");//JOrtega 15.03.2021
        
        return rutares;//JOrtega 15.03.2021 se cambio la respuesta
    }
    
    /**
     * Copia los archivos de una carpeta local extensión definida a una carpeta de destino en el FTP.
     * 
     * @param ruta Ruta de los archivos.
     * @param extension Extensión de los archivos a copiar.
     * @param destino Carpeta de destino en el FTP.
     * @throws Exception Si se presenta algún error en el proceso.
     */
    private void copiarArchivosFtp(String ruta, String extension, String destino) throws Exception
    {
        FTPClient ftpClient = new FTPClient();
        
        ftpClient.connect(InetAddress.getByName(SERV_FTP));
        ftpClient.login(USR_FTP, PASS_FTP);
        
        int rcConexion = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(rcConexion))
        {
            throw new Exception("Error al conectarse con el FTP. No se pudo conectar con los parámetros "
                    + "suministrados");
        }
        
        ftpClient.changeWorkingDirectory(destino);
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        
        File[] listaXml = getArchivosExtension(ruta, extension);
        for (File xml : listaXml)
        {
            try (BufferedInputStream buffIn = new BufferedInputStream(new FileInputStream(xml)))
            {
                ftpClient.enterLocalPassiveMode();
                System.out.println(destino + "/" + xml.getName().toLowerCase().replace(" ",""));
                ftpClient.storeFile(destino + "/" + xml.getName().toLowerCase().replace(" ",""), buffIn);
            }
        }
        
        ftpClient.logout();
        ftpClient.disconnect();
    }
    
    /**
     * Elimina los archivos de una carpeta local con una extensión definida de una carpeta en el FTP.
     * 
     * @param ruta Ruta de los archivos.
     * @param extension Extensión de los archivos a copiar.
     * @param destino Carpeta de destino en el FTP.
     * @throws Exception Si se presenta algún error en el proceso.
     */
    private void eliminarArchivosFtp(String ruta, String extension, String destino) throws Exception
    {
        FTPClient ftpClient = new FTPClient();
        
        ftpClient.connect(InetAddress.getByName(SERV_FTP));
        ftpClient.login(USR_FTP, PASS_FTP);
        
        int rcConexion = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(rcConexion))
        {
            throw new Exception("Error al conectarse con el FTP. No se pudo conectar con los parámetros "
                    + "suministrados");
        }
        
        ftpClient.changeWorkingDirectory(destino);
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        
        File[] listaXml = getArchivosExtension(ruta, extension);
        for (File xml : listaXml)
        {
            ftpClient.enterLocalPassiveMode();
            ftpClient.deleteFile(destino + "/" + xml.getName().toLowerCase().replace(" ",""));
        }
        
        ftpClient.logout();
        ftpClient.disconnect();
    }
    
    /**
     * Elimina un archivo en una ruta local. En caso de ser una carpeta, elimina todos los archivos internos
     * antes de eliminarla.
     * 
     * @param archivo Ruta del archivo/carpeta.
     */
    private void eliminarArchivos(String archivo)
    {
        File file = new File(archivo);
        if (file.isDirectory())
        {
            File[] listaArchivos = file.listFiles();
            for (File f : listaArchivos)
            {
                eliminarArchivos(f.getAbsolutePath());
            }
        }
        
        file.delete();
    }
    
    /**
     * Obtiene y lista los archivos {@code ZIP} en la carpeta seleccionada.
     */
    private void getArchivosZip()
    {
        limpiarTabla();
        
        String ruta = carpeta.getText();
        if (!ruta.equals(""))
        {
            File[] listaZip = getArchivosExtension(ruta, ".zip");
            
            DefaultTableModel model = (DefaultTableModel) archivos.getModel();
            for (File zip : listaZip)
            {
                Object[] data = { zip.getName(), false, false, false, "" };
                model.addRow(data);
            }
        }
    }
    
    /**
     * Obtiene el listado de archivos con una extensión específica en una ruta.
     * 
     * @param ruta Ruta del directorio.
     * @param extension Extensión de archivos para buscar.
     * @return Listado de archivos con la extensión suministrada.
     */
    private File[] getArchivosExtension(String ruta, String extension)
    {
        File dir = new File(ruta);
        File[] listaExt = dir.listFiles(new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String filename)
            {
                return filename.toLowerCase().replace(" ","").endsWith(extension);
            }
        });
        
        return listaExt;
    }
    
    /**
     * Limpia los campos de la tabla de archivos.
     */
    private void limpiarCamposTabla()
    {
        for (int i = 0; i < archivos.getRowCount(); i++)
        {
            archivos.setValueAt(false, i, 1);
            archivos.setValueAt(false, i, 2);
            archivos.setValueAt(false, i, 3);
            archivos.setValueAt("", i, 4);
        }
    }
    
    /**
     * Elimina todas las filas de la tabla de archivos.
     */
    private void limpiarTabla()
    {
        DefaultTableModel model = (DefaultTableModel) archivos.getModel();
        int rows = archivos.getRowCount();
        
        for (int i = rows; i > 0; i--)
        {
            model.removeRow(i - 1);
        }
    }
    
    /**
     * Establece si se habilitan o no los componentes dentro del formulario.
     *
     * @param b Estados para los componentes.
     */
    private void setEnabledComponentes(boolean[] b)
    {
        carpeta.setEnabled(b[0]);
        cargar.setEnabled(b[1]);
        procesar.setEnabled(b[2]);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable archivos;
    private javax.swing.JButton cargar;
    private javax.swing.JTextField carpeta;
    private javax.swing.JLabel labCarpeta;
    private javax.swing.JLabel labIcono;
    private javax.swing.JLabel labTitulo;
    private javax.swing.JButton procesar;
    private javax.swing.JScrollPane scrollArchivos;
    // End of variables declaration//GEN-END:variables
}
