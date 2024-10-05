package super_prog3;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;


public class VentanaInicio {
	private JFrame fLogin;
	private JLabel lNombreDeUsuario;
	private JTextField tfNombreDeUsuario;
	private JLabel lContraseña;
	private JTextField tfContraseña;
	private JLabel lErrores;
	private JButton bIniciarSesion;
	private JButton bCrearCuenta;
	
public VentanaInicio(ServicioPersistenciaBD servicioPersistencia) {
		
		lNombreDeUsuario = new JLabel();
		lNombreDeUsuario.setText("Nombre de usuario:");
		lNombreDeUsuario.setBounds(10, 10, 160, 30);
		tfNombreDeUsuario = new JTextField();
		tfNombreDeUsuario.setBounds(180, 11, 226, 30);
		
		lContraseña = new JLabel();
		lContraseña.setText("Contraseña:");
		lContraseña.setBounds(10, 50, 160, 30);
		tfContraseña = new JTextField();
		tfContraseña.setBounds(180, 51, 226, 30);
		
		lErrores = new JLabel();
		lErrores.setForeground(new Color(255, 0, 0));
		lErrores.setBounds(10, 104, 366, 30);
		
		bIniciarSesion = new JButton("Inciar Sesión");
		bIniciarSesion.setBounds(125, 158, 180, 30);
		bCrearCuenta = new JButton("Crear Cuenta");
		bCrearCuenta.setBounds(125, 198, 180, 30);
		
		fLogin = new JFrame("Login");
		fLogin.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		fLogin.setSize(430, 275);
		fLogin.setVisible(true);
		fLogin.getContentPane().setLayout(null);
		fLogin.getContentPane().add(lNombreDeUsuario);
		fLogin.getContentPane().add(tfNombreDeUsuario);
		fLogin.getContentPane().add(lContraseña);
		fLogin.getContentPane().add(tfContraseña);
		fLogin.getContentPane().add(lErrores);
		fLogin.getContentPane().add(bIniciarSesion);
		fLogin.getContentPane().add(bCrearCuenta);
		
		// Listeners
		bCrearCuenta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaCrearUsuario(servicioPersistencia);
			}
		});
		
		bIniciarSesion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Usuario usuario = null;
				Boolean isFound = false;
				ArrayList<Usuario> usuarios = servicioPersistencia.cargarTodosUsuarios();
				for(Usuario u:usuarios) {
					if(u.getNombreDeUsuario().equals(tfNombreDeUsuario.getText())) {
						isFound = true;
						usuario = u;
					}
				}
//				System.out.println(tfContraseña.getText());
//				System.out.println(usuario.getContraseña());
//				System.out.println(tfContraseña.getText().equals(usuario.getContraseña()));
				if (isFound == false) {
					lErrores.setText("Nombre de usuario incorrecto");
				} else if (!usuario.getContraseña().equals(tfContraseña.getText())) {
					lErrores.setText("Contraseña incorrecta");
				} else if (!usuario.getActivo()) {
					lErrores.setText("Este usuario esta bloqueado");
				} else {
					Tienda tienda = new Tienda(servicioPersistencia);
					if(usuario.getTipo() == TipoUsuario.USUARIO_ADMINISTRADOR) {
						new VentanaOpcionesAdmin(tienda, usuario, servicioPersistencia);
					} else {
						new VentanaTienda(tienda, usuario, servicioPersistencia);
						new VentanaAnuncio();
					}
				}
			}
		});
	}

}
