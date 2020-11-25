/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sisacademico.servlets;

import br.sisacademico.DAO.UsuarioDAO;
import br.sisacademico.security.TipoUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author thiagograzianitraue
 */
public class usuarioServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String tipoAcao = request.getParameter("tipoAcao");

            if (tipoAcao.equals("delete")) {
                UsuarioDAO uDAO = new UsuarioDAO();
                int idUsuarioDeletado = Integer.parseInt(request.getParameter("idUsuario"));
                //out.print("Vou deletar o usuário " + idUsuarioDeletado);
                if (uDAO.deletarUsuario(idUsuarioDeletado)) {
                    response.sendRedirect("gestaousuarios.jsp?acao=true");
                } else {
                    response.sendRedirect("gestaousuarios.jsp?acao=true");
                }
            }

            if (tipoAcao.equals("insere")) {
                String email = request.getParameter("email");
                String senha = request.getParameter("senha");

                //criptografia da senha 
                MessageDigest m = MessageDigest.getInstance("SHA-256");
                m.update(senha.getBytes(), 0, senha.length());

                int idTipo = Integer.parseInt(request.getParameter("idTipoUsuario"));
                TipoUsuario tipo = idTipo == 1 ? TipoUsuario.admin : TipoUsuario.usuario;
                UsuarioDAO uDAO = new UsuarioDAO();
                if (uDAO.cadastrarUsuario(email, new BigInteger(1, m.digest()).toString(16), tipo)) {
                    response.sendRedirect("gestaousuarios.jsp?acao=true");
                } else {
                    response.sendRedirect("gestaousuarios.jsp?acao=false");
                }
            }

            if (tipoAcao.equals("edicao")) {
                String email = request.getParameter("email");
                String senha = request.getParameter("senha");
                String checkSenha = request.getParameter("alteraSenha");

                int idTipoNovo = Integer.parseInt(request.getParameter("idTipoUsuario"));
                TipoUsuario t = idTipoNovo == 1 ? TipoUsuario.admin : TipoUsuario.usuario;

                int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                boolean alteraSenha = false;
                if (checkSenha != null) {
                    alteraSenha = checkSenha.equals("on");
                }

                //criptografia da senha 
                String senhaCripto = "";
                if (alteraSenha) {
                    MessageDigest m = MessageDigest.getInstance("SHA-256");
                    m.update(senha.getBytes(), 0, senha.length());
                    senhaCripto = new BigInteger(1, m.digest()).toString(16);
                }

                UsuarioDAO uDAO = new UsuarioDAO();
                if (uDAO.atualizaUsuario(idUsuario, email, senhaCripto, t, alteraSenha)) {
                    response.sendRedirect("gestaousuarios.jsp?acao=true");
                } else {
                    response.sendRedirect("gestaousuarios.jsp?acao=false");
                }
            }

            if (tipoAcao.equals("alteraSenha")) {
                HttpSession session = request.getSession();

                String email = (String) session.getAttribute("emailUsuario");

                String senhaDigitada = request.getParameter("senhaAntiga");
                MessageDigest m = MessageDigest.getInstance("SHA-256");
                m.update(senhaDigitada.getBytes(), 0, senhaDigitada.length());
                String senhaAntigaCripto = new BigInteger(1, m.digest()).toString(16);
                
                String senhaNova = request.getParameter("senhaNova_1");
                m.update(senhaNova.getBytes(), 0, senhaNova.length());
                String senhaNovaCripto = new BigInteger(1, m.digest()).toString(16);

                UsuarioDAO uDAO = new UsuarioDAO();
                if (uDAO.autentica(email, senhaAntigaCripto) != null) {
                    int idUsuario = (Integer) session.getAttribute("idUsuario");
                    TipoUsuario tipo = (TipoUsuario) session.getAttribute("tipoUsuario");
                    if (uDAO.atualizaUsuario(idUsuario, email, senhaNovaCripto, tipo, true)) {
                        response.sendRedirect("cadastros/alterarsenha.jsp?acao=true");
                    } else {
                        response.sendRedirect("cadastros/alterarsenha.jsp?acao=false");
                    }
                } else {
                    response.sendRedirect("cadastros/alterarsenha.jsp?acao=false");
                }
            }

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(usuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
