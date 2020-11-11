package br.sisacademico.DAO;

import br.sisacademico.security.TipoUsuario;
import br.sisacademico.security.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UsuarioDAO {

    private static Statement stm = null;

    public Usuario autentica(String email, String senha) {
        try {
            Usuario u = null;

            String query = "SELECT \"idUsuario\", \"email\", \"senha\", \"idTipoUsuario\" \n"
                    + "FROM \"tb_usuario\" "
                    + "WHERE \"email\" = ? "
                    + "  AND \"senha\" = ?";

            PreparedStatement stm = ConnectionFactory.getConnection().prepareStatement(query);

            stm.setString(1, email);
            stm.setString(2, senha);

            ResultSet resultados = stm.executeQuery();

            while (resultados.next()) {
                u = new Usuario(
                        resultados.getInt("idUsuario"),
                        email,
                        senha,
                        resultados.getInt("idTipoUsuario") == 1 ? 
                                TipoUsuario.admin : TipoUsuario.usuario);
            }
            
            stm.getConnection().close();

            return u;
        } catch (Exception e) {
            return null;
        }
    }
}
