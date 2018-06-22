package br.unicamp.laricaco;

import br.unicamp.laricaco.usuario.Usuario;

import javax.swing.*;
import java.awt.event.*;

public class JanelaLogin extends JFrame {

    private final Main main;

    private JTextField campoLogin;
    private JPasswordField campoSenha;

    public JanelaLogin(Main main) {
        super("Login");

        this.main = main;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //Nao queremos que os usuarios fechem o programa


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel login = new JLabel("RA: ");
        campoLogin = new JTextField(8);
        login.setLabelFor(campoLogin);

        JLabel senha = new JLabel("Senha: ");
        campoSenha = new JPasswordField(8);
        senha.setLabelFor(campoSenha);

        JButton entrar = new JButton("Entrar");
        entrar.addActionListener(new Entrar());
        entrar.setMnemonic(KeyEvent.VK_ENTER);

        panel.add(login);
        panel.add(campoLogin);
        panel.add(senha);
        panel.add(campoSenha);
        panel.add(entrar);
        getContentPane().add(panel);
        this.setVisible(true);
    }

    public class Entrar implements ActionListener {

        int ra, pin;
        Usuario usuario;

        @Override
        public void actionPerformed(ActionEvent e) {
            ra = Integer.parseInt(campoLogin.getText());
            pin = Integer.parseInt(String.valueOf(campoSenha.getPassword()));

            usuario = main.getGerenciadorUsuario().getUsuario(ra);
            if (usuario != null && usuario.getPin() == pin) {
                criarJanelaPrincipal();
            }
        }

        public void criarJanelaPrincipal() {
            JanelaPrincipal janela = new JanelaPrincipal(main, usuario, this);
            JanelaLogin.this.setVisible(false);
            janela.pack();
            janela.setVisible(true);
            janela.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    janela.dispose();
                    campoLogin.setText("");
                    campoSenha.setText("");
                    JanelaLogin.this.setVisible(true);
                }
            });
        }
    }
}
