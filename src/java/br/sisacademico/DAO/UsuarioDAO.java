package br.sisacademico.DAO;

import br.sisacademico.security.TipoUsuario;
import br.sisacademico.security.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
                        resultados.getInt("idTipoUsuario") == 1
                        ? TipoUsuario.admin : TipoUsuario.usuario);
            }

            stm.getConnection().close();

            return u;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Usuario> getUsuarios() throws SQLException {
        ArrayList<Usuario> usuarios = new ArrayList<>();

        stm = ConnectionFactory.getConnection().createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        String select = "SELECT \"idUsuario\", \"email\", \"tipo\" "
                + "FROM \"tb_usuario\""
                + "    INNER JOIN \"tb_tipoUsuario\" ON \"idTipoUsuario\" = \"idTipo\"";

        ResultSet resultados = stm.executeQuery(select);

        while (resultados.next()) {
            Usuario u = new Usuario();
            u.setIdUsuario(resultados.getInt("idUsuario"));
            u.setEmail(resultados.getString("email"));

            u.setTipo(resultados.getString("tipo").equalsIgnoreCase("admin")
                    ? TipoUsuario.admin
                    : TipoUsuario.usuario);

            usuarios.add(u);
        }

        stm.getConnection().close();

        return usuarios;
    }
}
