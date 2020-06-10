/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.view;

import br.com.controller.ControleCliente;
import br.com.model.Cliente;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author marco
 */
public class ConsultarClientesInadimplentes extends javax.swing.JFrame {

    /**
     * Creates new form ConsultarClientesInadimplentes
     */
    public ConsultarClientesInadimplentes() {
        initComponents();
        preencherTabela();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelBuscarCliente = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaClientesInadimplentes = new javax.swing.JTable();
        botaoVoltar = new javax.swing.JButton();
        botaoBuscarClienteInadimplentes = new javax.swing.JButton();
        jTextBuscarData = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Clientes inadimplentes");

        labelBuscarCliente.setText("Data prevista de pagamento");

        tabelaClientesInadimplentes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "RG", "CPF", "Endereço", "Status Pagamento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaClientesInadimplentes.setToolTipText("");
        jScrollPane1.setViewportView(tabelaClientesInadimplentes);

        botaoVoltar.setText("Voltar");
        botaoVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoVoltarActionPerformed(evt);
            }
        });

        botaoBuscarClienteInadimplentes.setText("Buscar");
        botaoBuscarClienteInadimplentes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoBuscarClienteInadimplentesActionPerformed(evt);
            }
        });

        jTextBuscarData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextBuscarDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(botaoVoltar)))
                        .addGap(30, 30, 30))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelBuscarCliente)
                        .addGap(18, 18, 18)
                        .addComponent(jTextBuscarData, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(botaoBuscarClienteInadimplentes)
                        .addContainerGap(201, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBuscarCliente)
                    .addComponent(botaoBuscarClienteInadimplentes)
                    .addComponent(jTextBuscarData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botaoVoltar)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(744, 441));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoVoltarActionPerformed
        this.toBack();
        setVisible(false);
        new ConsultarClientes().toFront();
        new ConsultarClientes().setVisible(true);
        new ConsultarClientes().setState(java.awt.Frame.NORMAL);
    }//GEN-LAST:event_botaoVoltarActionPerformed

    private void botaoBuscarClienteInadimplentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoBuscarClienteInadimplentesActionPerformed
        DefaultTableModel dtm = ((DefaultTableModel) tabelaClientesInadimplentes.getModel());
        dtm.setRowCount(0);
        tabelaClientesInadimplentes.setModel(dtm);
        
        ControleCliente cc = new ControleCliente();
        List<Cliente> clientesEncontrados = cc.buscarClientesInadimplentes(formataData(jTextBuscarData.getText()));
        clientesEncontrados.forEach(cliente -> {
            dtm.addRow(new Object[] {cliente.getId(),
                cliente.getName(), cliente.getRg(), cliente.getCpf(), cliente.getEndereco(), cliente.getStatusPagamento()});
        }); 
    }//GEN-LAST:event_botaoBuscarClienteInadimplentesActionPerformed

    private void jTextBuscarDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextBuscarDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextBuscarDataActionPerformed

    private void preencherTabela() {
        DefaultTableModel dtm = ((DefaultTableModel) tabelaClientesInadimplentes.getModel());
        dtm.setRowCount(0);
        tabelaClientesInadimplentes.setModel(dtm);

        List<Cliente> clientesEncontrados = new ControleCliente().buscarClientesInadimplentes(formataData(""));
        
        clientesEncontrados.forEach((Cliente cliente) -> {
            ((DefaultTableModel) tabelaClientesInadimplentes.getModel()).addRow(new Object[] {
                cliente.getId(), cliente.getName(), cliente.getRg(), cliente.getCpf(), cliente.getEndereco(), cliente.getStatusPagamento()
            });
        });
    }
    
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConsultarClientesInadimplentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ConsultarClientesInadimplentes().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoBuscarClienteInadimplentes;
    private javax.swing.JButton botaoVoltar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextBuscarData;
    private javax.swing.JLabel labelBuscarCliente;
    private javax.swing.JTable tabelaClientesInadimplentes;
    // End of variables declaration//GEN-END:variables

    private java.sql.Date formataData(String data) {
        if(data.isEmpty()) {
            String dataInicial = "2020-01-01";
            return Date.valueOf(dataInicial);  
        } else {
            
            String dia = data.substring(0, 2);
            String mes = data.substring(data.indexOf("/")+1, 5);
            String ano = data.substring(data.lastIndexOf("/")+1, data.length());

            return Date.valueOf(ano+ "-" + mes + "-" + dia);
        }
    }
}
