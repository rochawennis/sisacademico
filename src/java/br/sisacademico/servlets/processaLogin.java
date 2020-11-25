package br.sisacademico.servlets;

import br.sisacademico.DAO.UsuarioDAO;
import br.sisacademico.security.Usuario;
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

public class processaLogin extends HttpServlet {
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

            String email, senha;
            email = request.getParameter("email");
            senha = request.getParameter("senha");

            UsuarioDAO uDAO = new UsuarioDAO();
            
            //criptografia da senha 
            MessageDigest m = MessageDigest.getInstance("SHA-256");
            m.update(senha.getBytes(), 0, senha.length());

            Usuario u = uDAO.autentica(email, new BigInteger(1,m.digest()).toString(16));

            HttpSession session = request.getSession();

            if (u != null) { //autenticou
                session.setAttribute("autenticado", true);
                session.setAttribute("idUsuario", u.getIdUsuario());
                session.setAttribute("emailUsuario", u.getEmail());
                session.setAttribute("tipoUsuario", u.getTipo());
                response.sendRedirect("home.jsp");
                
            } else {
                session.setAttribute("autenticado", false);
                response.sendRedirect("index.jsp");
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(processaLogin.class.getName()).log(Level.SEVERE, null, ex);
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
