package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {
	private static String url = "jdbc:postgresql://localhost:5432/curso-jsp-servlet?autoReconnect=true";
	private static String usuario = "postgres";
	private static String senha = "root";
	private static Connection conexao = null;

	SingleConnection() {
		conectar();
	}

	static {
		conectar();
	}

	private static void conectar() {
		try {
			if (conexao == null) {
				Class.forName("org.postgresql.Driver");
				conexao = DriverManager.getConnection(url, usuario, senha);
				conexao.setAutoCommit(false);
				System.out.println("conectou!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConexao() {
		return conexao;
	}
}
