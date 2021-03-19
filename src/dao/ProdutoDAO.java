package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Produto;
import connection.SingleConnection;

public class ProdutoDAO {
	private Connection conexao;

	public ProdutoDAO() {
		conexao = SingleConnection.getConexao();
	}

	public void salvarProduto(Produto produto) {
		String sql = "insert into tb_produto (nome, quantidade, preco) values (?, ?, ?)";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, produto.getNome());
			stmt.setInt(2, produto.getQuantidade());
			stmt.setDouble(3, produto.getPreco());

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

	public List<Produto> listarProduto() {
		List<Produto> lista = new ArrayList<Produto>();
		String sql = "select * from tb_produto";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				Produto produto = new Produto();
				produto.setId(resultSet.getString("id"));
				produto.setNome(resultSet.getString("nome"));
				produto.setQuantidade(resultSet.getInt("quantidade"));
				produto.setPreco(resultSet.getDouble("preco"));

				lista.add(produto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public void deletarProduto(String id) {
		String sql = "delete from tb_produto where id = '" + id + "'";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Produto consultarProduto(String id) {
		String sql = "select * from tb_produto where id = '" + id + "'";

		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.next()) {
				Produto produto = new Produto();
				produto.setId(resultSet.getString("id"));
				produto.setNome(resultSet.getString("nome"));
				produto.setQuantidade(resultSet.getInt("quantidade"));
				produto.setPreco(resultSet.getDouble("preco"));
				return produto;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void atualizarProduto(Produto produto) {
		String sql = "update tb_produto set nome = ?, quantidade = ?, preco = ? where id = '" + produto.getId() + "'";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, produto.getNome());
			stmt.setInt(2, produto.getQuantidade());
			stmt.setDouble(3, produto.getPreco());
			stmt.executeUpdate();
			conexao.commit();
		} catch (SQLException e) {
			try {
				conexao.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}

	}

	public boolean validaNome(String id, String nome) throws SQLException {
		String sql = "select count(nome) as row from tb_produto where nome =  '" + nome + "' and id <> '" + id + "'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet resultSet = stmt.executeQuery();
		if (resultSet.next()) {
			return resultSet.getInt("row") == 0; /* true -> Pode atualizar */
		}
		return false;
	}

	public boolean validarNomeCadastro(String nome) throws SQLException {
		String sql = "select count(nome) as row from tb_produto where nome = '" + nome + "'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet resultSet = stmt.executeQuery();
		if (resultSet.next()) {
			return resultSet.getInt("row") == 0;
		}
		return false;
	}

}
