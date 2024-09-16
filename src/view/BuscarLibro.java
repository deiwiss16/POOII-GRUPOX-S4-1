/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Autor;
import model.Libro;

/**
 *
 * @author Carlos
 */
public class BuscarLibro extends javax.swing.JFrame {
    private String archivoAutores = "autores.txt";
    private String archivoLibros = "libros.txt";
    private List<Autor> listaAutores;
    private List<Libro> listaLibros;
    
    /**
     * Creates new form CrearLibro
     */
    public BuscarLibro() {
        initComponents();
        listaAutores = new ArrayList<>();
        listaLibros = new ArrayList<>();
        leerAutores(archivoAutores);
        leerLibros(archivoLibros);
        cargar(listaLibros);
    }
    
    private void leerAutores(String archivo) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;
            cboxAutores.removeAllItems();  // Limpiar ítems previos en el JComboBox

            while ((linea = reader.readLine()) != null) {
                // Dividimos la línea por el tabulador (\t)
                String[] datos = linea.split("\t");
                if (datos.length == 2) {
                    String codigo = datos[0];  // Código de autor
                    String nombre = datos[1];  // Nombre de autor

                    // Crear objeto Autor
                    Autor autor = new Autor(codigo, nombre);
                    
                    listaAutores.add(autor);
                    
                    // Añadir el objeto Autor al JComboBox
                    cboxAutores.addItem(autor.getNombre());  // Se añadirá el nombre porque toString() en Autor devuelve el nombre
                }
            }

            reader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo de autores: " + e.getMessage());
        }
    }
    private String getCodigoAutor() {
        String nombreSeleccionado = (String) cboxAutores.getSelectedItem();
        for (Autor autor : listaAutores) {
            if (autor.getNombre().equals(nombreSeleccionado)) {
                return autor.getCodigo();  // Retornar el código del autor
            }
        }
        return "No se encontro el codigo";  // En caso de no encontrar el autor
    }
    private Autor getAutorxCodigo(String codigoAutor) {
        for (Autor autor : listaAutores) {
            if (autor.getCodigo().equals(codigoAutor)) {
                return autor;
            }
        }
        JOptionPane.showMessageDialog(null, "Autor con código " + codigoAutor + " no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        return null;
    }
    private void leerLibros(String archivo){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;
            listaLibros.clear();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split("\t");
                if (datos.length == 7) {
                    String isbn = datos[0];
                    String titulo = datos[1];
                    Autor autor = getAutorxCodigo(datos[2]);
                    String sinopsis = datos[3];
                    String genero = datos[4];
                    boolean leido = datos[5].equals("true");  // 1 = leído, 0 = no leído
                    boolean lotengo = datos[6].equals("true");  // 1 = lo tengo, 0 = no lo tengo

                    Libro libro = new Libro(isbn, titulo, autor, sinopsis, genero, leido, lotengo);
                    listaLibros.add(libro);
                }
            }
            reader.close();            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo de libros: " + e.getMessage());
        }
    }
    private void filtrarLibros(){
        String isbn = txtISBN.getText();
        String titulo = txtTitulo.getText();
        String codigoAutorSeleccionado = getCodigoAutor();

        List<Libro> listaLibrosFiltrados = new ArrayList<>();

        for(Libro libro : listaLibros) {
            boolean coincideIsbn = isbn.isEmpty() || libro.getIsbn().equals(isbn);
            boolean coincideTitulo = titulo.isEmpty() || libro.getTitulo().toLowerCase().contains(titulo.toLowerCase());
            boolean coincideAutor = codigoAutorSeleccionado.equals("No se encontro el codigo") || libro.getAutor().getCodigo().equals(codigoAutorSeleccionado);

            if (coincideIsbn && coincideTitulo && coincideAutor) {
                listaLibrosFiltrados.add(libro);
            }
        }

        if (listaLibrosFiltrados.isEmpty()) {
        String mensajeError = "";
        
        if (!isbn.isEmpty()) {
            mensajeError += "No se encontraron libros con el ISBN proporcionado.\n";
        }
        if (!titulo.isEmpty()) {
            mensajeError += "No se encontraron libros con el título proporcionado.\n";
        }
        if (!codigoAutorSeleccionado.equals("No se encontro el codigo")) {
            mensajeError += "No se encontraron libros con el autor seleccionado.\n";
        }

        if (!mensajeError.isEmpty()) {
            JOptionPane.showMessageDialog(this, mensajeError, "No se encontraron resultados", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron libros con los filtros aplicados.", "No se encontraron resultados", JOptionPane.INFORMATION_MESSAGE);
        }
    } else {
        cargar(listaLibrosFiltrados);
    }
    }
    
    private void cargar(List<Libro> libros){
        DefaultTableModel model = new DefaultTableModel(new String[] {"ISBN", "Titulo", "Leido", "Lo tengo", "Autor"}, 0){
            Class<?>[] columnTypes = new Class[] {String.class, String.class, Boolean.class, Boolean.class, String.class};
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no son editables
            }
        };
        for (Libro libro : libros) {
            model.addRow(new Object[]{
                libro.getIsbn(), 
                libro.getTitulo(), 
                libro.isLeido(), 
                libro.isLoTengo(), 
                libro.getAutor().getNombre()
            });
        }
        table.setModel(model);
        table.getColumnModel().getColumn(2).setPreferredWidth(15);
        table.getColumnModel().getColumn(3).setPreferredWidth(30);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtISBN = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cboxAutores = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        pnlLibros = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("ISBN:");

        txtISBN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtISBNKeyTyped(evt);
            }
        });

        jLabel2.setText("Título:");

        jLabel3.setText("Autor:");

        cboxAutores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        pnlLibros.setBorder(javax.swing.BorderFactory.createTitledBorder("Libros"));
        pnlLibros.setName(""); // NOI18N

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout pnlLibrosLayout = new javax.swing.GroupLayout(pnlLibros);
        pnlLibros.setLayout(pnlLibrosLayout);
        pnlLibrosLayout.setHorizontalGroup(
            pnlLibrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
        );
        pnlLibrosLayout.setVerticalGroup(
            pnlLibrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLibros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBuscar)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtTitulo)
                                .addComponent(txtISBN)
                                .addComponent(cboxAutores, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cboxAutores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnBuscar)
                .addGap(29, 29, 29)
                .addComponent(pnlLibros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pnlLibros.getAccessibleContext().setAccessibleName("");
        pnlLibros.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        String isbn = txtISBN.getText();
        
        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El ISBN es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            txtISBN.requestFocus();
            return;
        }
        leerLibros(archivoLibros);
        filtrarLibros();
        
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtISBNKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtISBNKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtISBNKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BuscarLibro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuscarLibro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuscarLibro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuscarLibro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BuscarLibro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JComboBox<String> cboxAutores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlLibros;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
