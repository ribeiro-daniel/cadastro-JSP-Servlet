package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Telefone;
import connection.SingleConnection;

public class TelefoneDAO {
	private Connection conexao;

	public TelefoneDAO() {
		conexao = SingleConnection.getConexao();
	}

	public void salvarTelefone(Telefone telefone) {
		String sql = "insert into tb_telefone (id_usuario, numero, tipo) values (?, ?, ?)";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, telefone.getId_usuario());
			stmt.setString(2, telefone.getNumero());
			stmt.setString(3, telefone.getTipo());

			stmt.execute();
			conexao.commit();

		} catch (Exception e) {
			try {
				conexao.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}

	}

	public List<Telefone> listarTelefones(Long id) {
		List<Telefone> lista = new ArrayList<Telefone>();
		String sql = "select * from tb_telefone where id_usuario = " + id;
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				Telefone telefone = new Telefone();
				telefone.setId(resultSet.getLong("id"));
				telefone.setId_usuario(resultSet.getLong("id_usuario"));
				telefone.setNumero(resultSet.getString("numero"));
				telefone.setTipo(resultSet.getString("tipo"));

				lista.add(telefone);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public void deletarTelefone(Long id) {
		String sql = "delete from tb_telefone where id = " + id;
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
