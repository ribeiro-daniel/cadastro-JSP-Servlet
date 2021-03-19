package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.SingleConnection;

public class LoginDAO {
	private Connection conexao;
	
	public LoginDAO() {
		conexao = SingleConnection.getConexao();
	}
	
	public boolean validarLogin(String login, String senha) throws SQLException {
		String sql = "select * from tb_usuario where login = '" + login +"' and senha  = '" + senha + "'";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet resultado = stmt.executeQuery();
		
		if(resultado.next()) {
			return true; //usuário válido
		} else {
			return false; //usuário inválido
		}		
	}
}
