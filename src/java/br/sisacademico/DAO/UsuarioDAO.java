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

    public boolean deletarUsuario(int idUsuario) {
        try {
            String query = "DELETE FROM \"tb_usuario\" WHERE \"idUsuario\" = ?";

            PreparedStatement stm = ConnectionFactory.getConnection().prepareStatement(query);

            stm.setInt(1, idUsuario);

            stm.execute();

            stm.getConnection().close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean cadastrarUsuario(String email, String senha, TipoUsuario tipo){
        try {
            String query = "INSERT INTO \"tb_usuario\" (\"email\", \"senha\", \"idTipoUsuario\") VALUES(?, ?, ?)";

            PreparedStatement stm = ConnectionFactory.getConnection().prepareStatement(query);

            stm.setString(1, email);
            stm.setString(2, senha);
            stm.setInt(3, tipo == TipoUsuario.admin ? 1 : 2);

            stm.execute();

            stm.getConnection().close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
