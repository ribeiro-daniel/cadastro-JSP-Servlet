package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import beans.Usuario;
import connection.SingleConnection;

public class UsuarioDAO {
	private Connection conexao;

	public UsuarioDAO() {
		conexao = SingleConnection.getConexao();
	}

	public void salvarUsuario(Usuario usuario) {
		try {
			String sql = "insert into tb_usuario (login, senha, nome, telefone, cep, rua, bairro, cidade, uf, ibge, fotoBase64, contentType) values (?, ?, ?, ?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getSenha());
			stmt.setString(3, usuario.getNome());
			stmt.setString(4, usuario.getTelefone());
			stmt.setString(5, usuario.getCep());
			stmt.setString(6, usuario.getRua());
			stmt.setString(7, usuario.getBairro());
			stmt.setString(8, usuario.getCidade());
			stmt.setString(9, usuario.getUf());
			stmt.setString(10, usuario.getIbge());
			stmt.setString(11, usuario.getFotoBase64());
			stmt.setString(12, usuario.getContentType());
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

	public List<Usuario> listarUsuario() {
		List<Usuario> lista = new ArrayList<>();

		try {
			String sql = "select * from tb_usuario order by id";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			ResultSet resultado = stmt.executeQuery();

			while (resultado.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(resultado.getLong("id"));
				usuario.setLogin(resultado.getString("login"));
				usuario.setSenha(resultado.getString("senha"));
				usuario.setNome(resultado.getString("nome"));
				usuario.setTelefone(resultado.getString("telefone"));
				usuario.setCep(resultado.getString("cep"));
				usuario.setRua(resultado.getString("rua"));
				usuario.setBairro(resultado.getString("bairro"));
				usuario.setCidade(resultado.getString("cidade"));
				usuario.setUf(resultado.getString("uf"));
				usuario.setIbge(resultado.getString("ibge"));
				lista.add(usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public void deletarUsuario(String id) {
		try {
			String sql = "delete from tb_usuario where id = '" + id + "'";
			PreparedStatement stmt = conexao.prepareStatement(sql);
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

	public Usuario consultarUsuario(String id) throws Exception {
		String sql = "select * from tb_usuario where id = '" + id + "'";

		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet resultado = stmt.executeQuery();
		if (resultado.next()) {
			Usuario usuario = new Usuario();
			usuario.setId(resultado.getLong("id"));
			usuario.setLogin(resultado.getString("login"));
			usuario.setSenha(resultado.getString("senha"));
			usuario.setNome(resultado.getString("nome"));
			usuario.setTelefone(resultado.getString("telefone"));
			usuario.setCep(resultado.getString("cep"));
			usuario.setRua(resultado.getString("rua"));
			usuario.setBairro(resultado.getString("bairro"));
			usuario.setCidade(resultado.getString("cidade"));
			usuario.setUf(resultado.getString("uf"));
			usuario.setIbge(resultado.getString("ibge"));

			return usuario;
		}
		return null;
	}

	public boolean validarLoginMultiplo(String login) throws Exception {
		String sql = "select count(login) as row from tb_usuario where login = '" + login + "'";

		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet resultado = stmt.executeQuery();
		if (resultado.next()) {

			return resultado.getInt("row") == 0; /* Retorna true */
		}
		return false;
	}

	public boolean validarLoginMultiploUpdate(String login, String id) throws Exception {
		String sql = "select count(login) as row from tb_usuario where login = '" + login + "' and id <> '" + id + "'";

		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet resultado = stmt.executeQuery();
		if (resultado.next()) {

			return resultado.getInt("row") == 0;
		}
		return false;
	}

	public void atualizar(Usuario usuario) {
		String sql = "update tb_usuario set login = ?, senha = ?, nome = ?, telefone = ?, cep = ?, rua = ?, bairro = ?, cidade = ?, uf = ?, ibge = ? where id = " + usuario.getId();
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getSenha());
			stmt.setString(3, usuario.getNome());
			stmt.setString(4, usuario.getTelefone());
			stmt.setString(5, usuario.getCep());
			stmt.setString(6, usuario.getRua());
			stmt.setString(7, usuario.getBairro());
			stmt.setString(8, usuario.getCidade());
			stmt.setString(9, usuario.getUf());
			stmt.setString(10, usuario.getIbge());
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

}
