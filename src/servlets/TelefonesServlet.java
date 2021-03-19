package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Telefone;
import beans.Usuario;
import dao.TelefoneDAO;
import dao.UsuarioDAO;

@WebServlet("/salvarTelefones")
public class TelefonesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TelefoneDAO telefoneDAO = new TelefoneDAO();
	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	public TelefonesServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String acao = req.getParameter("acao");
			if (acao.equalsIgnoreCase("addTel")) {
				String id = req.getParameter("id");
				Usuario usuario = usuarioDAO.consultarUsuario(id);
				req.getSession().setAttribute("usuarioTeste", usuario); /* Passar o usuario CHEIO */
				req.setAttribute("usuarioTeste", usuario);
				RequestDispatcher dispatcher = req.getRequestDispatcher("cadastroTelefones.jsp");
				req.setAttribute("telefones", telefoneDAO.listarTelefones(usuario.getId()));
				dispatcher.forward(req, resp);
			} else if (acao.equalsIgnoreCase("deletarTel")) {
				String tel = req.getParameter("tel");
				telefoneDAO.deletarTelefone(Long.parseLong(tel));
				Usuario usuario_sessao = (Usuario) req.getSession().getAttribute("usuarioTeste");
				RequestDispatcher dispatcher = req.getRequestDispatcher("cadastroTelefones.jsp");
				req.setAttribute("telefones", telefoneDAO.listarTelefones(usuario_sessao.getId()));
				dispatcher.forward(req, resp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Usuario usuario_sessao = (Usuario) req.getSession().getAttribute("usuarioTeste");
		String numero = req.getParameter("numero");
		String tipo = req.getParameter("tipo");
		Telefone telefone = new Telefone();
		telefone.setId_usuario(usuario_sessao.getId());
		telefone.setNumero(numero);
		telefone.setTipo(tipo);

		telefoneDAO.salvarTelefone(telefone);

		req.getSession().setAttribute("usuarioTeste", usuario_sessao);
		req.setAttribute("usuarioTeste", usuario_sessao);
		RequestDispatcher dispatcher = req.getRequestDispatcher("cadastroTelefones.jsp");

		req.setAttribute("telefones", telefoneDAO.listarTelefones(usuario_sessao.getId()));
		dispatcher.forward(req, resp);

	}
}
