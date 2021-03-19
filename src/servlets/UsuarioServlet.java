package servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.codec.binary.Base64;

import beans.Usuario;
import dao.UsuarioDAO;

@WebServlet("/salvarUsuario")
@MultipartConfig
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UsuarioDAO usuarioDAO = new UsuarioDAO();

	public UsuarioServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");
		String id = request.getParameter("id");

		if (acao.equalsIgnoreCase("delete")) {
			try {
				usuarioDAO.deletarUsuario(id);
				RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", usuarioDAO.listarUsuario());
				dispatcher.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (acao.equalsIgnoreCase("editar")) {
			try {
				Usuario usuarioConsultado = usuarioDAO.consultarUsuario(id);
				RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuario", usuarioConsultado);
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (acao.equalsIgnoreCase("listar")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroUsuario.jsp");
			request.setAttribute("usuarios", usuarioDAO.listarUsuario());
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			
			String id = request.getParameter("id");
			String login = request.getParameter("cadLogin");
			String senha = request.getParameter("cadSenha");
			String nome = request.getParameter("cadNome");
			String telefone = request.getParameter("cadTelefone");
			String cep = request.getParameter("cep");
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String uf = request.getParameter("uf");
			String ibge = request.getParameter("ibge");
			
			Usuario usuario = new Usuario();

			usuario.setId(!id.isEmpty() ? Long.parseLong(id) : null);
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setNome(nome);
			usuario.setTelefone(telefone);
			usuario.setCep(cep);
			usuario.setRua(rua);
			usuario.setBairro(bairro);
			usuario.setCidade(cidade);
			usuario.setUf(uf);
			usuario.setIbge(ibge);
			
			/*******Upload de imagens/pdf*********/
			if(ServletFileUpload.isMultipartContent(request)) {
				Part foto = request.getPart("foto");
				new Base64();
				String fotoBase64 = Base64.encodeBase64String(converteStreamParaByte(foto.getInputStream()));
				usuario.setFotoBase64(fotoBase64);
				usuario.setContentType(foto.getContentType());
			}
			
			/****************/

			if (login.trim() == null || login.isEmpty()) {
				request.setAttribute("msg", "O campo 'Login' não pode ficar vazio.");
				request.setAttribute("usuario", usuario);
			} else if (senha == null || senha.isEmpty()) {
				request.setAttribute("msg", "O campo 'Chave' não pode ficar vazio.");
				request.setAttribute("usuario", usuario);
			} else if (nome == null || nome.isEmpty()) {
				request.setAttribute("msg", "O campo 'Nome' não pode ficar vazio.");
				request.setAttribute("usuario", usuario);
			} else if (telefone == null || telefone.isEmpty()) {
				request.setAttribute("msg", "O campo 'Telefone' não pode ficar vazio.");
				request.setAttribute("usuario", usuario);
			} else if (cep == null || cep.isEmpty()) {
				request.setAttribute("msg", "O campo 'CEP'não pode ficar vazio.");
				request.setAttribute("usuario", usuario);
			}
			
			else {
				if (id == null || id.isEmpty() && !usuarioDAO.validarLoginMultiplo(login)) {
					request.setAttribute("msg", "Login já existe!");
				}
				if (id == null || id.isEmpty() && usuarioDAO.validarLoginMultiplo(login)) {
					usuarioDAO.salvarUsuario(usuario);
					request.setAttribute("msg", "Cadastro realizado com sucesso!");
				} else if (id != null && !id.isEmpty()) {
					if (!usuarioDAO.validarLoginMultiploUpdate(login, id)) {
						request.setAttribute("msg", "Login duplicado! Tente outro!");
						request.setAttribute("usuario", usuario);
					} else {
						usuarioDAO.atualizar(usuario);
					}
				}
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroUsuario.jsp");
			request.setAttribute("usuarios", usuarioDAO.listarUsuario());
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private byte[] converteStreamParaByte(java.io.InputStream inputStream) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = inputStream.read();
		while (reads != -1) {
			baos.write(reads);
			reads = inputStream.read();
		}
		return baos.toByteArray();
	}
}
