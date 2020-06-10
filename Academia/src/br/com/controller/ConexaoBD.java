/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author marco
 */
public class ConexaoBD {
    public Statement stmt;
    public ResultSet rs;
    public Connection conn;
    
    private final String driver = "com.mysql.jdbc.Driver";
    private final String url = "jdbc:mysql://localhost:3306/academia";
    private final String usuario = "root";
    private final String senha = "CarlosDaniel2020";
    
    public void conexao() {
        try {
            System.setProperty("jdbc.Drivers", driver);
            conn = DriverManager.getConnection(url, usuario, senha);
//            JOptionPane.showMessageDialog(null, "Conexão efetuada com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao se conectar ao banco:\n"+ex.getMessage());
        }
    }
    
    public void desconecta() {
        try {
            conn.close();
//            JOptionPane.showMessageDialog(null, "Desconectado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao se desconectar do banco:\n"+ex.getMessage());
        }
    }
}