/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sisacademico.servlets;

import br.sisacademico.DAO.CursoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author thiagograzianitraue
 */
public class cursoServlet extends HttpServlet {

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
            String acao = request.getParameter("tipoAcao");
            //String paramEncoding = getInitParameter("PARAMETER_ENCODING");

            if (acao.equalsIgnoreCase("cadastro")) {
                //a ação que veio é de cadastrar um curso novo
                String curso = java.net.URLDecoder.decode(request.getParameter("nomeCurso"), "utf-8");

                //String curso = request.getParameter("nomeCurso");
                String tipoCurso = request.getParameter("tipoCurso");

                //paramos aqui. Fazer o redirect
                CursoDAO cDAO = new CursoDAO();
                try {
                    if (cDAO.cadastraCurso(curso, tipoCurso)) {
                        response.sendRedirect(request.getContextPath() + "/mensagem.jsp?op=cadastro&entity=curso&result=true");
                    }

                } catch (SQLException ex) {
                    //Logger.getLogger(cursoServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (acao.equalsIgnoreCase("edicao")) {
                int idCurso = Integer.parseInt(request.getParameter("idCurso"));
                String cursoNovo = request.getParameter("nomeCurso");
                String tipoCurso = request.getParameter("tipoCurso");
                CursoDAO cDAO = new CursoDAO();
                try {
                    if (cDAO.atualizaCurso(idCurso, cursoNovo, tipoCurso)) {
                        //conseguiu atualiar o curso
                        response.sendRedirect(request.getContextPath() + "/mensagem.jsp?op=edicao&entity=curso&result=true");
                    } else {
                        //não conseguiu atualizar o curso
                        response.sendRedirect(request.getContextPath() + "/mensagem.jsp?op=edicao&entity=curso&result=false");
                    }
                } catch (Exception e) {

                }
            } else if (acao.equalsIgnoreCase("delete")) {
                int idCurso = Integer.parseInt(request.getParameter("idCurso"));
                CursoDAO cDAO = new CursoDAO();
                if (cDAO.deleteCurso(idCurso)) {
                    //deletou o curso:
                    response.sendRedirect(request.getContextPath() + "/mensagem.jsp?op=apagar&entity=curso&result=true");
                } else {
                    //não deletou o curso:
                    response.sendRedirect(request.getContextPath() + "/mensagem.jsp?op=apagar&entity=curso&result=false");
                }

            }
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
