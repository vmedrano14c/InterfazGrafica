package trabajofinal;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblTitulo;
	private JTextField textNombre;
	private JTextField textNumero;

	

	/**
	 * Create the frame.
	 */
	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 348);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblTitulo = new JLabel("AGENDA ELECTRONICA");
		lblTitulo.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
		lblTitulo.setForeground(new Color(255, 255, 255));
		lblTitulo.setBounds(132, 11, 358, 44);
		contentPane.add(lblTitulo);
		
		JLabel lblNewLabel = new JLabel("Número:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(83, 142, 64, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(83, 84, 64, 14);
		contentPane.add(lblNewLabel_1);
		
		textNombre = new JTextField();
		textNombre.setBounds(182, 83, 256, 20);
		contentPane.add(textNombre);
		textNombre.setColumns(10);
		
		textNumero = new JTextField();
		textNumero.setBounds(182, 141, 256, 20);
		contentPane.add(textNumero);
		textNumero.setColumns(10);
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Obtener el nombre del contacto a ser actualizado
		            // desde el campo de texto.
		            String newName = String.valueOf(textNombre.getText());
		 
		            // Obtener el número a ser actualizado
		            // desde el campo de texto.
		            long newNumber = Long.parseLong(textNumero.getText());
		 
		            String nameNumberString;
		            String name;
		            long number;
		            int index;
		 
		            // Creando el archivo utilizando un apuntador de archivo.
		            File file = new File("C:\\Users\\lenovo\\Pictures\\Files\\friendsContact.txt");
		            
		            if (!file.exists() == false) { 
		                // Crear un nuevo archivo si no existe.
		                file.createNewFile();
		            }
		            
		         // Abrir el archivo en modo de lectura y escritura.
		            RandomAccessFile raf = new RandomAccessFile(file, "rw");
		            boolean found = false;
		         // Verificar si el nombre del contacto ya existe.
		            // getFilePointer() da el valor actual del offset
		            // desde el inicio del archivo.
		            while (raf.getFilePointer() < raf.length()) {
		 
		                // Leer una línea del archivo.
		                nameNumberString = raf.readLine();
		 
		             // Verificar que la línea contiene el separador "!"
		                if (nameNumberString != null && nameNumberString.contains("!")) {
		                	// Separar la cadena para obtener el nombre y el número.
                            String[] lineSplit = nameNumberString.split("!");
                            
                         // Separando el nombre y el número.
                            name = lineSplit[0];
                            number = Long.parseLong(lineSplit[1]);
                            
                         // Condición para verificar la existencia del registro.
                            if (name.equals(newName) || number == newNumber) {
                                found = true;
                                break;
                            }   
		                }else {
		                	// Manejar el caso en que la línea no tiene el formato esperado.
                            System.out.println("Formato incorrecto en la línea: " + nameNumberString);
		                }   
		            }
		 
		            if (!found) {
		                // Ingresar al bloque if cuando un registro
		                // no está presente en el archivo.
		                nameNumberString = newName + "!" + newNumber;
		 
		                // writeBytes escribe una cadena como una secuencia de bytes.
		                raf.writeBytes(nameNumberString);
		 
		                // Insertar el siguiente registro en una nueva línea.
		                raf.writeBytes(System.lineSeparator());
		 
		                // Imprimir mensaje de éxito.
		                System.out.println("Amigo agregado.");
		            } else {
		            	// Imprimir mensaje de contacto ya existente.
                        System.out.println("El nombre ingresado ya existe.\n");
		            }
		         // Cerrar los recursos.
                    raf.close();
		        }catch (IOException ioe) {
		            // Manejo de excepciones de entrada/salida.
		            System.out.println(ioe);
		        }
		        catch (NumberFormatException nef) {
		            // Manejo de excepciones de formato numérico.
		            System.out.println(nef);
		        }

			}
		});
		btnCrear.setBounds(83, 199, 106, 23);
		contentPane.add(btnCrear);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Obtener los valores del nombre y número a actualizar
                    String newName = textNombre.getText();
                    long newNumber = Long.parseLong(textNumero.getText());

                    // Variables para leer el archivo
                    String nameNumberString;
                    String name;
                    long number;
                    boolean found = false;

                    // Archivo donde se almacenan los contactos
                    File file = new File("C:\\Users\\lenovo\\Pictures\\Files\\friendsContact.txt");

                    // Lista para almacenar temporalmente los contactos
                    List<String> contacts = new ArrayList<>();

                    // Verificar si el archivo existe
                    if(file.exists()) {
                    	// Abrir el archivo en modo lectura/escritura
                        RandomAccessFile raf = new RandomAccessFile(file, "rw");

                        // Leer todos los contactos y almacenarlos en la lista
                        while (raf.getFilePointer() < raf.length()) {
                        	nameNumberString = raf.readLine();
                            String[] lineSplit = nameNumberString.split("!");

                            // Validar formato
                            if(lineSplit.length==2) {
                            	name = lineSplit[0];
                            	number = Long.parseLong(lineSplit[1]);
                            	
                            	// Si el contacto coincide con el nombre, actualizarlo
                                if (name.equals(newName)) {
                                	found = true;
                                	contacts.add(newName + "!" + newNumber);
                                }else {
                                	 // Mantener los contactos que no se actualizan
                                    contacts.add(nameNumberString);
                                }
                            }
                        }
                        //cerrar el archivo
                        raf.close();
                        
                     // Si se encontró el contacto, sobrescribir el archivo
                        if (found) {
                        	 raf = new RandomAccessFile(file, "rw");
                             for (String contact : contacts) {
                                 raf.writeBytes(contact);
                                 raf.writeBytes(System.lineSeparator());
                             }
                             raf.close();
                             System.out.print("Contacto actualizado.\n");
                        } else {
                        	System.out.print("El contacto no fue encontrado.\n");
                        }
                    } else {
                    	System.out.print("El archivo no existe.\n");
                    }
				} catch (IOException ioe) {
					System.out.print(ioe);
				} catch (NumberFormatException nfe) {
                    System.out.println("Formato de número incorrecto.\n");
                }
			}
		});
		btnActualizar.setBounds(256, 199, 126, 23);
		contentPane.add(btnActualizar);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            String deleteName = textNombre.getText();
		            String nameNumberString;
		            boolean found = false;

		            File file = new File("C:\\Users\\lenovo\\Pictures\\Files\\friendsContact.txt");
		            File tempFile = new File("C:\\Users\\lenovo\\Pictures\\Files\\temp.txt");

		            RandomAccessFile raf = new RandomAccessFile(file, "rw");
		            RandomAccessFile tempRaf = new RandomAccessFile(tempFile, "rw");

		            while (raf.getFilePointer() < raf.length()) {
		                nameNumberString = raf.readLine();
		                String[] lineSplit = nameNumberString.split("!");

		                String name = lineSplit[0];

		                // Si el nombre no coincide, escribir en el archivo temporal
		                if (!name.equals(deleteName)) {
		                    tempRaf.writeBytes(nameNumberString);
		                    tempRaf.writeBytes(System.lineSeparator());
		                } else {
		                    found = true;
		                }
		            }

		            raf.close();
		            tempRaf.close();

		            // Reemplazar el archivo original con el archivo temporal
		            if (found) {
		                file.delete();
		                tempFile.renameTo(file);
		                textNumero.setText("Contacto eliminado");
		            } else {
		                tempFile.delete();
		                textNumero.setText("El contacto no existe");
		            }

		        } catch (IOException ioe) {
		            System.out.println("Error al eliminar el contacto: " + ioe);
		        }

			}
		});
		btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnBorrar.setBounds(256, 251, 126, 23);
		contentPane.add(btnBorrar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textNombre.setText("");
				textNumero.setText("");
			}
			
		});
		btnLimpiar.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLimpiar.setBounds(452, 110, 106, 23);
		contentPane.add(btnLimpiar);
		
		JButton btnLeer = new JButton("Leer");
		btnLeer.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLeer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RandomAccessFile raf = null;
		        try {
		            // Obtener el nombre que queremos buscar
		            String searchName = textNombre.getText();
		            String nameNumberString;
		            String name;
		            long number;
		            boolean found = false;

		            // Abrir el archivo en modo de solo lectura
		            File file = new File("C:\\Users\\lenovo\\Pictures\\Files\\friendsContact.txt");
		            if (!file.exists()) {
		                // Si el archivo no existe, mostrar el mensaje "El contacto no existe"
		                textNumero.setText("El contacto no existe");
		                System.out.println("El archivo no existe.");
		                return;
		            }

		            raf = new RandomAccessFile(file, "r");

		            // Leer el archivo línea por línea
		            while (raf.getFilePointer() < raf.length()) {
		                // Leer una línea del archivo
		                nameNumberString = raf.readLine();

		                // Verificar si la línea contiene el separador "!"
		                if (nameNumberString.contains("!")) {
		                    // Separar nombre y número
		                    String[] lineSplit = nameNumberString.split("!");
		                    if (lineSplit.length >= 2) {
		                        name = lineSplit[0];
		                        number = Long.parseLong(lineSplit[1]);

		                        // Si el nombre coincide con el que se está buscando
		                        if (name.equalsIgnoreCase(searchName)) {
		                            // Mostrar el número en el campo correspondiente
		                            textNumero.setText(String.valueOf(number));
		                            System.out.println("Contacto encontrado: " + name + " con número: " + number);
		                            found = true;
		                            break;
		                        }
		                    }
		                }
		            }

		            // Si no se encontró el contacto, mostrar "El contacto no existe"
		            if (!found) {
		                textNumero.setText("El contacto no existe");
		                System.out.println("El contacto " + searchName + " no existe.");
		            }

		        } catch (IOException ioe) {
		            System.out.println("Error en la lectura del archivo: " + ioe);
		        } catch (NumberFormatException nfe) {
		            System.out.println("Error en el formato del archivo: " + nfe);
		        } finally {
		            // Asegurarse de cerrar el archivo en el bloque finally para evitar errores
		            try {
		                if (raf != null) {
		                    raf.close();
		                }
		            } catch (IOException ioe) {
		                System.out.println("Error al cerrar el archivo: " + ioe);
		            }
		        }
			}
		});
		btnLeer.setBounds(83, 251, 106, 23);
		contentPane.add(btnLeer);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 64, 22);
		contentPane.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("Menú");
		mnNewMenu.setFont(new Font("Segoe UI", Font.BOLD, 13));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Salir");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mntmNewMenuItem.setBackground(new Color(0, 0, 0));
		mntmNewMenuItem.setForeground(new Color(255, 255, 255));
		mnNewMenu.add(mntmNewMenuItem);
		
		JLabel lblFondo = new JLabel("New label");
		lblFondo.setIcon(new ImageIcon("C:\\Users\\lenovo\\Downloads\\Imagen de WhatsApp 2024-09-20 a las 09.59.57_9241c437.jpg"));
		lblFondo.setBounds(0, 0, 585, 320);
		contentPane.add(lblFondo);
	}
}
