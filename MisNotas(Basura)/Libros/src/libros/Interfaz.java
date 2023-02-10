package libros;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Interfaz extends javax.swing.JFrame {

    /**
     * Creates new form Interfaz
     */
    public Interfaz() {
        initComponents();
        cancelar.setVisible(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        idInput = new javax.swing.JTextField();
        mostrar1 = new javax.swing.JButton();
        eliminar = new javax.swing.JButton();
        t1 = new javax.swing.JLabel();
        modificar = new javax.swing.JButton();
        mostrarTodo = new javax.swing.JButton();
        crear = new javax.swing.JButton();
        tituloInput = new javax.swing.JTextField();
        autorInput = new javax.swing.JTextField();
        anhoInput = new javax.swing.JTextField();
        t4 = new javax.swing.JLabel();
        t5 = new javax.swing.JLabel();
        t6 = new javax.swing.JLabel();
        t2 = new javax.swing.JLabel();
        cambiar = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        nuevoXML = new javax.swing.JButton();
        panel1 = new java.awt.Panel();
        t3 = new javax.swing.JLabel();
        seleccionado = new javax.swing.JLabel();
        panel2 = new java.awt.Panel();
        jScrollPane2 = new javax.swing.JScrollPane();
        vista = new javax.swing.JTextArea();
        seleccionado1 = new javax.swing.JLabel();
        errores = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(225, 225, 225));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setBackground(new java.awt.Color(119, 201, 141));
        jPanel1.setForeground(new java.awt.Color(225, 255, 225));

        jPanel2.setBackground(new java.awt.Color(119, 201, 141));

        idInput.setBackground(new java.awt.Color(255, 255, 255));
        idInput.setForeground(new java.awt.Color(0, 0, 0));
        idInput.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        idInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idInputActionPerformed(evt);
            }
        });

        mostrar1.setBackground(new java.awt.Color(0, 0, 0));
        mostrar1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        mostrar1.setForeground(new java.awt.Color(255, 255, 255));
        mostrar1.setText("Mostrar");
        mostrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrar1ActionPerformed(evt);
            }
        });

        eliminar.setBackground(new java.awt.Color(0, 0, 0));
        eliminar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        eliminar.setForeground(new java.awt.Color(255, 255, 255));
        eliminar.setText("Eliminar");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });

        t1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        t1.setForeground(new java.awt.Color(0, 0, 0));
        t1.setText("ID:");

        modificar.setBackground(new java.awt.Color(0, 0, 0));
        modificar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        modificar.setForeground(new java.awt.Color(255, 255, 255));
        modificar.setText("Modificar");
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });

        mostrarTodo.setBackground(new java.awt.Color(0, 0, 0));
        mostrarTodo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        mostrarTodo.setForeground(new java.awt.Color(255, 255, 255));
        mostrarTodo.setText("Mostrar todos");
        mostrarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarTodoActionPerformed(evt);
            }
        });

        crear.setBackground(new java.awt.Color(0, 0, 0));
        crear.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        crear.setForeground(new java.awt.Color(255, 255, 255));
        crear.setText("Crear");
        crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearActionPerformed(evt);
            }
        });

        tituloInput.setBackground(new java.awt.Color(255, 255, 255));
        tituloInput.setForeground(new java.awt.Color(0, 0, 0));
        tituloInput.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        tituloInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tituloInputActionPerformed(evt);
            }
        });

        autorInput.setBackground(new java.awt.Color(255, 255, 255));
        autorInput.setForeground(new java.awt.Color(0, 0, 0));
        autorInput.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        autorInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autorInputActionPerformed(evt);
            }
        });

        anhoInput.setBackground(new java.awt.Color(255, 255, 255));
        anhoInput.setForeground(new java.awt.Color(0, 0, 0));
        anhoInput.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        anhoInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anhoInputActionPerformed(evt);
            }
        });

        t4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        t4.setForeground(new java.awt.Color(0, 0, 0));
        t4.setText("Título");

        t5.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        t5.setForeground(new java.awt.Color(0, 0, 0));
        t5.setText("Autor");

        t6.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        t6.setForeground(new java.awt.Color(0, 0, 0));
        t6.setText("Año");

        t2.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        t2.setForeground(new java.awt.Color(0, 0, 0));
        t2.setText("Nuevo:");

        cambiar.setBackground(new java.awt.Color(0, 0, 0));
        cambiar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        cambiar.setForeground(new java.awt.Color(255, 255, 255));
        cambiar.setText("Cambiar XML");
        cambiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiarActionPerformed(evt);
            }
        });

        cancelar.setBackground(new java.awt.Color(0, 0, 0));
        cancelar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        cancelar.setForeground(new java.awt.Color(255, 255, 255));
        cancelar.setText("Cancelar");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });

        nuevoXML.setBackground(new java.awt.Color(0, 0, 0));
        nuevoXML.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        nuevoXML.setForeground(new java.awt.Color(255, 255, 255));
        nuevoXML.setText("Nuevo XML");
        nuevoXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoXMLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cambiar)
                    .addComponent(nuevoXML)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(crear)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cancelar))
                        .addComponent(t2)
                        .addComponent(t6)
                        .addComponent(mostrarTodo)
                        .addComponent(t5)
                        .addComponent(t4)
                        .addComponent(t1)
                        .addComponent(idInput)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(mostrar1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(eliminar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(modificar))
                        .addComponent(tituloInput)
                        .addComponent(autorInput)
                        .addComponent(anhoInput)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(cambiar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nuevoXML)
                .addGap(27, 27, 27)
                .addComponent(t1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eliminar)
                    .addComponent(mostrar1)
                    .addComponent(modificar))
                .addGap(18, 18, 18)
                .addComponent(mostrarTodo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(t2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(t4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tituloInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(t5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(autorInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(t6)
                .addGap(3, 3, 3)
                .addComponent(anhoInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(crear)
                    .addComponent(cancelar))
                .addGap(36, 36, 36))
        );

        panel1.setBackground(new java.awt.Color(119, 201, 141));

        t3.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        t3.setForeground(new java.awt.Color(0, 0, 0));
        t3.setText("XML Seleccionado:");
        t3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        seleccionado.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        seleccionado.setForeground(new java.awt.Color(0, 0, 0));
        seleccionado.setText("ninguno");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addGap(235, 235, 235)
                .addComponent(t3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(seleccionado)
                .addContainerGap(290, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(t3)
                    .addComponent(seleccionado))
                .addGap(36, 36, 36))
        );

        panel2.setBackground(new java.awt.Color(0, 0, 0));
        panel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        vista.setBackground(new java.awt.Color(255, 255, 255));
        vista.setColumns(20);
        vista.setRows(5);
        jScrollPane2.setViewportView(vista);

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                .addContainerGap())
        );

        seleccionado1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        seleccionado1.setForeground(new java.awt.Color(255, 0, 0));

        errores.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        errores.setForeground(new java.awt.Color(255, 0, 0));
        errores.setText("no errors yet");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(376, 376, 376)
                        .addComponent(seleccionado1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(errores)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(27, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(errores)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(seleccionado1)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eliminarActionPerformed

    private void mostrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mostrar1ActionPerformed

    private void idInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idInputActionPerformed

    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_modificarActionPerformed

    private void mostrarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarTodoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mostrarTodoActionPerformed

    private void crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_crearActionPerformed

    private void tituloInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tituloInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tituloInputActionPerformed

    private void autorInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autorInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_autorInputActionPerformed

    private void anhoInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anhoInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_anhoInputActionPerformed

    private void cambiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambiarActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
        chooser.setDialogTitle("Open schedule file");
        chooser.setFileFilter(xmlfilter);
        
        int resultado = chooser.showOpenDialog(null);
        
        if(resultado!=JFileChooser.CANCEL_OPTION){
            fichero=chooser.getSelectedFile();
            seleccionado.setText(fichero.getName());
        }
    }//GEN-LAST:event_cambiarActionPerformed

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cancelarActionPerformed

    private void nuevoXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoXMLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevoXMLActionPerformed

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
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    private File fichero=null;
    private boolean todoOk=false;
    private LibrosController lc=null;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField anhoInput;
    private javax.swing.JTextField autorInput;
    private javax.swing.JButton cambiar;
    private javax.swing.JButton cancelar;
    private javax.swing.JButton crear;
    private javax.swing.JButton eliminar;
    private javax.swing.JLabel errores;
    private javax.swing.JTextField idInput;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton modificar;
    private javax.swing.JButton mostrar1;
    private javax.swing.JButton mostrarTodo;
    private javax.swing.JButton nuevoXML;
    private java.awt.Panel panel1;
    private java.awt.Panel panel2;
    private javax.swing.JLabel seleccionado;
    private javax.swing.JLabel seleccionado1;
    private javax.swing.JLabel t1;
    private javax.swing.JLabel t2;
    private javax.swing.JLabel t3;
    private javax.swing.JLabel t4;
    private javax.swing.JLabel t5;
    private javax.swing.JLabel t6;
    private javax.swing.JTextField tituloInput;
    private javax.swing.JTextArea vista;
    // End of variables declaration//GEN-END:variables
}
