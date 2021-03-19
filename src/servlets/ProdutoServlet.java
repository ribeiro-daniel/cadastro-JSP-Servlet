package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Produto;
import dao.ProdutoDAO;

@WebServlet("/salvarProduto")
public class ProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Produto produto = new Produto();
	ProdutoDAO produtoDAO = new ProdutoDAO();

	public ProdutoServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");
		String id = request.getParameter("id");

		if (acao.equalsIgnoreCase("listar")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroProduto.jsp");
			request.setAttribute("produtos", produtoDAO.listarProduto());
			dispatcher.forward(request, response);
		} else if (acao.equalsIgnoreCase("excluir")) {
			produtoDAO.deletarProduto(id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroProduto.jsp");
			request.setAttribute("produtos", produtoDAO.listarProduto());
			dispatcher.forward(request, response);
		} else if (acao.equalsIgnoreCase("editar")) {
			Produto produtoConsultado = produtoDAO.consultarProduto(id);
			request.setAttribute("produto", produtoConsultado);
			RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroProduto.jsp");
			dispatcher.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String quantidade = request.getParameter("quantidade");
		String preco = request.getParameter("preco");

		produto.setId(id);
		produto.setNome(nome);
		produto.setQuantidade(!quantidade.isEmpty() ? Integer.parseInt(quantidade) : null);
		produto.setPreco(!preco.isEmpty() ? Double.parseDouble(preco) : null);

		if (nome.trim() == null || nome.isEmpty()) {
			request.setAttribute("msg", "O campo 'Nome' não pode ficar vazio.");
			request.setAttribute("produto", produto);
		} else if (quantidade == null || quantidade.isEmpty()) {
			request.setAttribute("msg", "O campo 'Quantidade' não pode ficar vazio.");
			request.setAttribute("produto", produto);
		} else if (preco == null || preco.isEmpty()) {
			request.setAttribute("msg", "O campo 'Preço' não pode ficar vazio.");
			request.setAttribute("produto", produto);
		} else {
			try {
				if (id == null || id.isEmpty() && !produtoDAO.validarNomeCadastro(nome)) {
					request.setAttribute("msg", "Nome já existe!");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				if (id == null || id.isEmpty() && produtoDAO.validarNomeCadastro(nome)) {
					produtoDAO.salvarProduto(produto);
					request.setAttribute("msg", "Produto cadastrado c/ sucesso!");
				} else if (id != null && !id.isEmpty()) {
					try {
						if (!produtoDAO.validaNome(id, nome)) {
							request.setAttribute("msg", "Nome já cadastrado.");
							request.setAttribute("produto", produto);
						} else {
							produtoDAO.atualizarProduto(produto);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroProduto.jsp");
		request.setAttribute("produtos", produtoDAO.listarProduto());
		dispatcher.forward(request, response);
	}
}
