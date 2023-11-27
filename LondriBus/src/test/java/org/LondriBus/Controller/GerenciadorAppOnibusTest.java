import org.LondriBus.Controller.GerenciadorAppOnibus;
import org.LondriBus.Model.CartaoGeral;
import org.LondriBus.Model.Compras;
import org.LondriBus.Model.FormularioBeneficio;
import org.LondriBus.Model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GerenciadorAppOnibusTest {

    @MockBean

    private Usuario usuario;

    @MockBean
    private Model model;

    @Test
    public void testFormCriarUsuario() {
        GerenciadorAppOnibus controller = new GerenciadorAppOnibus();
        String view = controller.formCriarUsuario(usuario);
        assertEquals("form-novo-usuario", view);
    }

    @Test
    public void testCriarUsuario() {
        GerenciadorAppOnibus controller = new GerenciadorAppOnibus();
        when(usuario.getLogin()).thenReturn("teste");
        when(usuario.getSenha()).thenReturn("senha");
        when(usuario.getNome()).thenReturn("Nome Teste");
        when(usuario.getCep()).thenReturn("12345-678");
        when(usuario.getCpf()).thenReturn("123.456.789-00");
        when(usuario.getEndereco()).thenReturn("Rua Teste, 123");
        when(usuario.getCartaoGeral().getCodigoNFC()).thenReturn("NFC123");

        String view = controller.criarUsuario(usuario, null, model);
        assertEquals("usuario-logado", view);
    }

    @Test
    public void testMostrarTelaInicial() {
        GerenciadorAppOnibus controller = new GerenciadorAppOnibus();
        String view = controller.mostrarTelaInicial(usuario);
        assertEquals("index", view);
    }

    @Test
    public void testLogoutUsuario() {
        GerenciadorAppOnibus controller = new GerenciadorAppOnibus();
        String view = controller.logoutUsuario(usuario, model);
        assertEquals("index", view);
    }

    @Test
    public void testLogarUsuario() {
        GerenciadorAppOnibus controller = new GerenciadorAppOnibus();
        when(usuario.getLogin()).thenReturn("teste");
        when(usuario.getSenha()).thenReturn("senha");
        when(usuario.getNome()).thenReturn("Nome Teste");
        when(usuario.getCep()).thenReturn("12345-678");
        when(usuario.getCpf()).thenReturn("123.456.789-00");
        when(usuario.getEndereco()).thenReturn("Rua Teste, 123");
        when(usuario.getCartaoGeral().getCodigoNFC()).thenReturn("NFC123");

        String view = controller.logarUsuario(usuario, null, model);
        assertEquals("usuario-logado", view);
    }

    @Test
    public void testObterCartao() {
        GerenciadorAppOnibus controller = new GerenciadorAppOnibus();
        CartaoGeral cartaoGeral = controller.obterCartao("123.456.789-00");
        assertEquals("NFC123", cartaoGeral.getCodigoNFC());
    }

    @Test
    public void testFormComprarCredito() {
        GerenciadorAppOnibus controller = new GerenciadorAppOnibus();
        String view = controller.formComprarCredito("123.456.789-00", model);
        assertEquals("comprar-creditos", view);
    }

    @Test
    public void testCompraCredito() {
        GerenciadorAppOnibus controller = new GerenciadorAppOnibus();
        Compras compra = new Compras();
        compra.setTipo("comBeneficio");
        compra.setQntdCreditos(10);

        String view = controller.compraCredito("123.456.789-00", compra, model);
        assertEquals("usuario-logado", view);
    }

    @Test
    public void testFormBeneficio() {
        GerenciadorAppOnibus controller = new GerenciadorAppOnibus();
        String view = controller.formBeneficio("123.456.789-00", model);
        assertEquals("form-beneficio", view);
    }

    @Test
    public void testSolicitaBeneficio() {
        GerenciadorAppOnibus controller = new GerenciadorAppOnibus();
        FormularioBeneficio formBeneficio = new FormularioBeneficio();
        String view = controller.solicitaBeneficio("123.456.789-00", formBeneficio, model);
        assertEquals("usuario-logado", view);
    }

    @Test
    public void testGotoSac() {
        GerenciadorAppOnibus controller = new GerenciadorAppOnibus();
        String view = controller.gotoSac("123.456.789-00", model);
        assertEquals("sac", view);
    }

}
