package br.sisacademico.DAO;

import br.sisacademico.pojo.Aluno;
import br.sisacademico.pojo.Curso;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AlunoDAO {

    private static Statement stm = null;

    public ArrayList<Aluno> getTodosAluno(Class classe, Integer... id) throws SQLException {
        ArrayList<Aluno> alunos = new ArrayList<>();

        String and = "";
        if (classe != null) {
            if (classe == Aluno.class) {
                and = (id.length >= 1) ? "AND alunos.\"idAluno\" = " + id[0] : "";
            } else if (classe == Curso.class) {
                and = (id.length >= 1) ? "AND alunos.\"idCurso\" = " + id[0] : "";
            }
        } else {
            and = (id.length >= 1) ? "AND alunos.\"idAluno\" = " + id[0] : "";
        }

        stm = ConnectionFactory.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        String select = "SELECT alunos.\"idAluno\", "
                + "       alunos.\"ra\", "
                + "       alunos.\"nomeAluno\", "
                + "       cursos.\"idCurso\","
                + "       cursos.\"nomeCurso\","
                + "       cursos.\"tipoCurso\" "
                + "FROM \"tb_aluno\" as alunos, "
                + "     \"tb_curso\" as cursos "
                + "WHERE alunos.\"idCurso\" = cursos.\"idCurso\" "
                + and
                + " ORDER BY alunos.\"nomeAluno\"";

        ResultSet resultados = stm.executeQuery(select);

        while (resultados.next()) {
            Aluno a = new Aluno();
            Curso c = new Curso();

            a.setIdAluno(resultados.getInt("idAluno"));
            a.setRa(resultados.getInt("ra"));
            a.setNomeAluno(resultados.getString("nomeAluno"));

            c.setIdCurso(resultados.getInt("idCurso"));
            c.setNomeCurso(resultados.getString("nomeCurso"));
            c.setTipoCurso(resultados.getString("tipoCurso"));

            a.setCurso(c);

            alunos.add(a);
        }
        stm.getConnection().close();

        return alunos;
    }

    public boolean insereAluno(int ra, String nome, int idCurso) {
        try {

            String query = "INSERT INTO \"tb_aluno\" (\"ra\", \"nomeAluno\", \"idCurso\") VALUES (?, ?, ?)";

            PreparedStatement stm = ConnectionFactory.getConnection().prepareCall(query);

            stm.setInt(1, ra);
            stm.setString(2, nome);
            stm.setInt(3, idCurso);

            stm.execute();

            stm.getConnection().close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean atualizaAluno(int idAluno, int raNovo, String nomeNovo, int idCursoNovo) {
        try {

            String query = "UPDATE \"tb_aluno\" SET \"ra\" = ?, \"nomeAluno\" = ?, \"idCurso\" = ? WHERE \"idAluno\" = ?";

            PreparedStatement stm = ConnectionFactory.getConnection().prepareCall(query);

            stm.setInt(1, raNovo);
            stm.setString(2, nomeNovo);
            stm.setInt(3, idCursoNovo);
            stm.setInt(4, idAluno);

            stm.execute();

            stm.getConnection().close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteAluno(int idAluno) {
            try {

            String query = "DELETE FROM \"tb_aluno\" WHERE \"idAluno\" = ?";

            PreparedStatement stm = ConnectionFactory.getConnection().prepareCall(query);

            stm.setInt(1, idAluno);

            stm.execute();

            stm.getConnection().close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
