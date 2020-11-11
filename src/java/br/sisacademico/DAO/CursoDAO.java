package br.sisacademico.DAO;

import br.sisacademico.pojo.Curso;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CursoDAO {

    private static Statement stm = null;

    public Map<Curso, Integer> getTodosCursos() throws SQLException {

        Map<Curso, Integer> relatorio = new HashMap<Curso, Integer>();

        stm = ConnectionFactory.getConnection().createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        String select = "SELECT \"idCurso\","
                + "       \"nomeCurso\","
                + "       \"tipoCurso\","
                + "       (SELECT count(\"idAluno\")"
                + "          FROM \"tb_aluno\""
                + "         WHERE \"idCurso\" = cursos.\"idCurso\") as alunos"
                + " FROM \"tb_curso\" as cursos";

        ResultSet resultados = stm.executeQuery(select);

        while (resultados.next()) {
            Curso c = new Curso();
            c.setIdCurso(resultados.getInt("idCurso"));
            c.setNomeCurso(resultados.getString("nomeCurso"));
            c.setTipoCurso(resultados.getString("tipoCurso"));

            int qtdAlunos = resultados.getInt("alunos");

            relatorio.put(c, qtdAlunos);
        }

        stm.getConnection().close();

        return relatorio;
    }

    public ArrayList<Curso> getCursos(Integer... idCurso) throws SQLException {

        ArrayList<Curso> cursos = new ArrayList<>();

        stm = ConnectionFactory.getConnection().createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        String select = "SELECT \"idCurso\","
                + "       \"nomeCurso\","
                + "       \"tipoCurso\""
                + " FROM \"tb_curso\"";

        if (idCurso.length >= 1) {
            select += " WHERE \"idCurso\" = " + idCurso[0];
        }

        ResultSet resultados = stm.executeQuery(select);

        while (resultados.next()) {
            Curso c = new Curso();
            c.setIdCurso(resultados.getInt("idCurso"));
            c.setNomeCurso(resultados.getString("nomeCurso"));
            c.setTipoCurso(resultados.getString("tipoCurso"));

            cursos.add(c);
        }

        stm.getConnection().close();

        return cursos;
    }

    public boolean cadastraCurso(String curso, String tipoCurso) throws SQLException {

        try {
            String query = "INSERT INTO \"tb_curso\" (\"nomeCurso\", \"tipoCurso\") VALUES(?, ?)";

            PreparedStatement stm = ConnectionFactory.getConnection().prepareStatement(query);

            stm.setString(1, curso);
            stm.setString(2, tipoCurso);

            stm.execute();

            stm.getConnection().close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizaCurso(int idCurso, String nomeNovo, String tipoNovo) {
        try {
            String query = "UPDATE \"tb_curso\" SET \"nomeCurso\" = ?, \"tipoCurso\" = ? WHERE \"idCurso\" = ?";

            PreparedStatement stm = ConnectionFactory.getConnection().prepareStatement(query);
            
            stm.setString(1, nomeNovo);
            stm.setString(2, tipoNovo);
            stm.setInt(3, idCurso);
            
            stm.execute();
            
            stm.getConnection().close();

            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCurso(int idCurso) {
        try {
            String query = "DELETE FROM \"tb_curso\" WHERE \"idCurso\" = ?";

            PreparedStatement stm = ConnectionFactory.getConnection().prepareStatement(query);

            stm.setInt(1, idCurso);
            
            stm.execute();
            
            stm.getConnection().close();

            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
