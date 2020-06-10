/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controller;

import br.com.model.Cliente;
import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author marco
 */
public class ControleCliente {
    
    private final ConexaoBD conexaoBD = new ConexaoBD();
    private final Cliente cliente = new Cliente();
    private boolean retornoLogin = false;
    
    public void salvar(Cliente cliente) {
        try {
            conexaoBD.conexao();
            conexaoBD.conn.setAutoCommit(false);
            
            PreparedStatement pst = conexaoBD.conn.prepareStatement("INSERT INTO cliente (nome, rg, cpf, endereco) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, cliente.getName());
            pst.setString(2, cliente.getRg());
            pst.setString(3, cliente.getCpf());
            pst.setString(4, cliente.getEndereco());
            
            int linhasAfetadas = pst.executeUpdate();
            
            ResultSet rs = pst.getGeneratedKeys();
            int idUltimoClienteCadastrado;
            
            if(rs.next()) 
                idUltimoClienteCadastrado = rs.getInt(1);
            else
                throw new SQLException("Cliente não cadastrado");
            
            if(linhasAfetadas == 1) {
            
                LocalDate dataPagamento = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
                LocalDate proximoPagamento = dataPagamento.plusMonths(1);

                Date dataPagamentoConvertida = Date.valueOf(dataPagamento);
                Date dataProximoPagamento = Date.valueOf(proximoPagamento);

                pst = conexaoBD.conn.prepareStatement("INSERT INTO pagamento (idCliente, tipo_plano, data_pagamento, data_prox_pagamento, status_pagamento) "
                        + "values (?, ?, ?, ?, ?);");
                pst.setInt(1,idUltimoClienteCadastrado);
                pst.setString(2, "Mensal");
                pst.setDate(3, dataPagamentoConvertida);
                pst.setDate(4, dataProximoPagamento);
                pst.setString(5, "Pago");
                
                pst.executeUpdate();
                
                conexaoBD.conn.commit();
                JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso!");
            } else {
                conexaoBD.conn.rollback();
                pst.close();
                conexaoBD.conn.close();
            }
                
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar os dados!\n" + e.getMessage());
        } finally {
            conexaoBD.desconecta();
        }
    }
    
    public void excluir(int id) {
        try{
            conexaoBD.conexao();
            PreparedStatement pst = conexaoBD.conn.prepareStatement("DELETE FROM cliente WHERE id = ?");
            pst.setInt(1, id);
            
            pst.execute();
            JOptionPane.showMessageDialog(null, "Dados excluídos com sucesso!");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir os dados!\n" + e.getMessage());
        } finally {
            conexaoBD.desconecta();
        }
    }
    
    public void alterar(Cliente cliente) {
        try{
            conexaoBD.conexao();
            conexaoBD.conn.setAutoCommit(false);
            
            PreparedStatement pst = conexaoBD.conn.prepareStatement("UPDATE cliente SET nome = ?, rg = ?, cpf = ?, endereco = ? WHERE id = ?");
            pst.setString(1, cliente.getName());
            pst.setString(2, cliente.getRg());
            pst.setString(3, cliente.getCpf());
            pst.setString(4, cliente.getEndereco());
            pst.setInt(5, cliente.getId());
            
            int linhasAfetadas = pst.executeUpdate();
            if(linhasAfetadas == 1) {
                
                conexaoBD.conn.commit();
                JOptionPane.showMessageDialog(null, "Dados alterados com sucesso!");
            } else {
                throw new SQLException();
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar os dados!\n" + e.getMessage());
        } finally {
            conexaoBD.desconecta();
        }
    }
    
    public List<Cliente> buscarCliente(String nome) {
        List<Cliente> listaClientes = new ArrayList<>();
        try{
            conexaoBD.conexao();
            PreparedStatement pst;
            
            if(nome.isEmpty()) {
                pst = conexaoBD.conn.prepareStatement("SELECT id, nome, rg, cpf, endereco, data_prox_pagamento FROM cliente JOIN pagamento WHERE cliente.id = pagamento.idCliente AND status_pagamento = 'Pago'");
            } else {
                pst = conexaoBD.conn.prepareStatement("SELECT id, nome, rg, cpf, endereco, data_prox_pagamento FROM cliente JOIN pagamento WHERE cliente.id = pagamento.idCliente AND status_pagamento = 'Pago' AND cliente.nome LIKE '%"+nome.trim()+"%'");
            }
            
            ResultSet rs = pst.executeQuery();
           
            Cliente clienteEncontrado;
            while(rs.next()) {
                clienteEncontrado = new Cliente();
                clienteEncontrado.setId(rs.getInt("id"));
                clienteEncontrado.setName(rs.getString("nome"));
                clienteEncontrado.setRg(rs.getString("rg"));
                clienteEncontrado.setCpf(rs.getString("cpf"));
                clienteEncontrado.setEndereco(rs.getString("endereco"));
                clienteEncontrado.setDataProximoPagamento(rs.getDate("data_prox_pagamento"));

                listaClientes.add(clienteEncontrado);
            } 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar os dados!\n" + e.getMessage());
        } finally {
            conexaoBD.desconecta();
        }
        return listaClientes;
    }
    
    public List<Cliente> buscarClientesInadimplentes(Date data) {
        List<Cliente> listaClientes = new ArrayList<>();
        try{
            conexaoBD.conexao();
            PreparedStatement pst;
            
            pst = conexaoBD.conn.prepareStatement("SELECT id, nome, rg, cpf, endereco, data_prox_pagamento, status_pagamento FROM cliente "
                    + "JOIN pagamento WHERE cliente.id = pagamento.idCliente AND status_pagamento = 'Em aberto' AND data_prox_pagamento between ? AND now()");
            pst.setDate(1,data);

            
            ResultSet rs = pst.executeQuery();
           
            Cliente clienteEncontrado;
            while(rs.next()) {
                clienteEncontrado = new Cliente();
                clienteEncontrado.setId(rs.getInt("id"));
                clienteEncontrado.setName(rs.getString("nome"));
                clienteEncontrado.setRg(rs.getString("rg"));
                clienteEncontrado.setCpf(rs.getString("cpf"));
                clienteEncontrado.setEndereco(rs.getString("endereco"));
                clienteEncontrado.setStatusPagamento(rs.getString("status_pagamento"));
                clienteEncontrado.setDataProximoPagamento(rs.getDate("data_prox_pagamento"));

                listaClientes.add(clienteEncontrado);
            } 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar os dados!\n" + e.getMessage());
        } finally {
            conexaoBD.desconecta();
        }
        return listaClientes;
    }
    
    public boolean efetuarLogin(String usuario, String senha) {
        
        
        try {
            conexaoBD.conexao();
            
            PreparedStatement pst = conexaoBD.conn.prepareStatement("SELECT login, senha FROM gerente where login = ?");
            pst.setString(1, usuario);
            
            ResultSet rs = pst.executeQuery();
            
            if(rs.next() && senha.equals(rs.getString("senha"))) retornoLogin = true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao efetuar o login!\n" + e.getMessage());
        } finally {
            conexaoBD.desconecta();
        }
        
        if(!retornoLogin)
            JOptionPane.showMessageDialog(null, "Usuário e / ou senha inválido(s)");
        
        return retornoLogin;
    }
}