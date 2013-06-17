package net.scravy.technetium.standalone;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import net.scravy.persistence.EntityManager;
import net.scravy.persistence.EntityManagerWrapper;
import net.scravy.persistence.PersistenceUtil;
import net.scravy.technetium.util.DatabaseUtil;
import net.scravy.technetium.util.EnhancedProperties;
import net.scravy.technetium.util.value.Either;
import net.scravy.technetium.util.value.ValueUtil;
import net.scravy.weblet.transform.CachingTransformerPool;

/**
 * The main panel.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	final static String urlMysql = "jdbc:mysql://%s/%s?zeroDateTimeBehavior=convertToNull";
	final static String urlPostgres = "jdbc:postgresql://%s/%s";
	final static String urlDerby = "jdbc:derby:%s;create=true";

	final JTextArea console;

	JButton[] buttons = { new JButton("Start Technetium"),
			new JButton("Test Connectivity"), new JButton("Create Tables"),
			new JButton("Create Test Data"), new JButton("Drop Tables"),
			new JButton("Open in Browser") };
	JSpinner port = new JSpinner(new SpinnerNumberModel(8080, 1, 65535, 1));
	JComboBox driver = new JComboBox(new DefaultComboBoxModel(ValueUtil.array(
			"Derby", "MySQL", "Postgres")));
	JTextArea host = new JTextArea("localhost");
	JTextArea database = new JTextArea("technetium");
	JTextArea user = new JTextArea("technetium");
	JTextArea password = new JTextArea("???");

	Properties getProperties() {
		Properties p = new EnhancedProperties();

		String selectedDriver = driver.getSelectedItem().toString();
		if (selectedDriver.equalsIgnoreCase("derby")) {
			p.setProperty("javax.persistence.jdbc.driver",
					"org.apache.derby.jdbc.EmbeddedDriver");
			p.setProperty("javax.persistence.jdbc.url",
					String.format(urlDerby, database.getText()));
		} else if (selectedDriver.toString().equalsIgnoreCase("mysql")) {
			p.setProperty("javax.persistence.jdbc.driver",
					"com.mysql.jdbc.Driver");
			p.setProperty("javax.persistence.jdbc.url",
					String.format(urlMysql, host.getText(), database.getText()));
		} else if (selectedDriver.toString().equalsIgnoreCase("postgres")) {
			p.setProperty("javax.persistence.jdbc.driver",
					"org.postgresql.Driver");
			p.setProperty(
					"javax.persistence.jdbc.url",
					String.format(urlPostgres, host.getText(),
							database.getText()));
		}
		p.setProperty("javax.persistence.jdbc.user", user.getText());
		p.setProperty("javax.persistence.jdbc.password", password.getText());

		return p;
	}

	class Menu extends JPanel {

		Menu() {
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			setPreferredSize(new Dimension(200, buttons.length * 26));

			JPanel panel1 = new JPanel() {
				{
					setLayout(new GridLayout(buttons.length, 1));
					setMaximumSize(new Dimension(200, buttons.length * 26));

					for (JButton b : buttons) {
						add(b);
					}
				}
			};

			port.setEditor(new JSpinner.NumberEditor(port, "#####"));

			JPanel panel2 = new JPanel() {
				{
					setLayout(new GridLayout(6, 2));
					setMaximumSize(new Dimension(200, 156));

					add(new JLabel("Webserver Port:"));
					add(port);

					add(new JLabel("Driver:"));
					add(driver);

					add(new JLabel("Host:"));
					add(host);

					add(new JLabel("Database:"));
					add(database);

					add(new JLabel("User:"));
					add(user);

					add(new JLabel("Password:"));
					add(password);
				}
			};
			add(panel1);
			add(Box.createRigidArea(new Dimension(10, 10)));
			add(panel2);
		}
	}

	MainPanel(final TechnetiumApp app) {
		String url = app.properties.getProperty("javax.persistence.jdbc.url");
		if (url != null) {
			if (url.startsWith("jdbc:mysql:")) {
				driver.setSelectedIndex(1);
			} else if (url.startsWith("jdbc:postgresql:")) {
				driver.setSelectedIndex(2);
			}
		}
		String username = app.properties
				.getProperty("javax.persistence.jdbc.username");
		if (username != null) {
			user.setText(username);
		}
		String password = app.properties
				.getProperty("javax.persistence.jdbc.password");
		if (password != null) {
			this.password.setText(password);
		}
		if (driver.getSelectedIndex() == 0) {
			database.setText("derby_db");
		}

		setLayout(new BorderLayout());

		console = new JTextArea() {
			{
				setEditable(false);
				setForeground(Color.WHITE);
				setBackground(Color.BLACK);
				setFont(Font.decode("Courier"));
				setLineWrap(true);
			}

			@Override
			public void append(String text) {
				super.append(text);
				this.setCaretPosition(this.getCaretPosition() + text.length());
			}
		};

		JComponent left = new Menu();
		left.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JComponent right = new JScrollPane(console);

		add(left, BorderLayout.WEST);
		add(right, BorderLayout.CENTER);

		buttons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				app.properties.putAll(getProperties());

				app.properties.setProperty("technetium.transformers.pool",
						CachingTransformerPool.class.getCanonicalName());

				System.out.println("\n> Starting Technetium Webserver...");

				try {
					checkTables(app.properties);
					app.startServer((Integer) port.getValue(), "/");
					System.out.println("Success!");

					if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
						try {
							Desktop.getDesktop().browse(
									new URI("http://localhost:"
											+ port.getValue() + "/"));
							return;
						} catch (Exception exc) {
						}
					}
				} catch (Exception exc) {
					System.out.printf("Failed: %s (%s)\n", exc.getMessage(),
							exc.getClass().getSimpleName());
				}
			}
		});

		buttons[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				Properties p = getProperties();

				System.out.println("\n> Test Connectivity ");
				System.out.printf("Driver:\t%s\n",
						p.getProperty("javax.persistence.jdbc.driver"));
				System.out.printf("URL:\t%s\n",
						p.getProperty("javax.persistence.jdbc.url"));

				Either<Connection, ? extends Exception> c = DatabaseUtil.connect(
						p.getProperty("javax.persistence.jdbc.driver"),
						p.getProperty("javax.persistence.jdbc.url"),
						p.getProperty("javax.persistence.jdbc.user"),
						p.getProperty("javax.persistence.jdbc.password"));

				if (c.isLeft()) {
					System.out.println("Success!");
					try {
						c.getLeft().close();

					} catch (SQLException exc) {

					}
				} else {
					System.out.printf("Failed: %s (%s)\n", c.getRight()
							.getMessage(), c.getRight().getClass()
							.getSimpleName());
				}
			}
		});

		buttons[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				Properties p = getProperties();

				System.out.println("\n> Creating Tables...");
				System.out.printf("Driver:\t%s\n",
						p.getProperty("javax.persistence.jdbc.driver"));
				System.out.printf("URL:\t%s\n",
						p.getProperty("javax.persistence.jdbc.url"));

				try {
					EntityManager em = createEntityManager(p);

					PersistenceUtil.createTables(em);

					EntityManagerFactory emf = em.getEntityManagerFactory();
					em.close();
					emf.close();

					System.out.println("Success!");
				} catch (Exception exc) {
					System.out.printf("Failed: %s (%s)\n", exc.getMessage(),
							exc.getClass().getSimpleName());
				}
			}
		});

		buttons[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				Properties p = getProperties();

				System.out.println("\n> Creating test data...");
				System.out.printf("Driver:\t%s\n",
						p.getProperty("javax.persistence.jdbc.driver"));
				System.out.printf("URL:\t%s\n",
						p.getProperty("javax.persistence.jdbc.url"));

				EntityManager em = createEntityManager(p);

				try {
					PersistenceUtil.initData(em);

					System.out
							.println("\nAdmin user `admin` with password `admin` created.");

					System.out
							.println("\nOrdinary user `john.doe` with password `password` created.");
				} finally {
					try {
						EntityManagerFactory emf = em.getEntityManagerFactory();
						em.close();
						emf.close();

						System.out.println("Success!");
					} catch (Exception exc) {
						System.out
								.println("Fail, could not write changes and close EntityManager.");
					}
				}
			}
		});

		buttons[4].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				Properties p = getProperties();

				System.out.println("\n> Drop Tables ");
				System.out.printf("Driver:\t%s\n",
						p.getProperty("javax.persistence.jdbc.driver"));
				System.out.printf("URL:\t%s\n",
						p.getProperty("javax.persistence.jdbc.url"));

				Either<Connection, ? extends Exception> c = DatabaseUtil.connect(
						p.getProperty("javax.persistence.jdbc.driver"),
						p.getProperty("javax.persistence.jdbc.url"),
						p.getProperty("javax.persistence.jdbc.user"),
						p.getProperty("javax.persistence.jdbc.password"));

				if (c.isLeft()) {
					DatabaseUtil.dropTables(c.getLeft());
					System.out.println("Success!");

					try {
						c.getLeft().close();
					} catch (SQLException exc) {

					}
				} else {
					System.out.printf("Failed: %s (%s)\n", c.getRight()
							.getMessage(), c.getRight().getClass()
							.getSimpleName());
				}
			}
		});

		buttons[5].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				if (Desktop.isDesktopSupported()) {
					if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
						try {
							Desktop.getDesktop().browse(
									new URI("http://localhost:"
											+ port.getValue() + "/"));
							return;
						} catch (Exception exc) {

						}
					}
				}
				System.out.println("Could not open a desktop browser.");
			}
		});
	}

	static EntityManager createEntityManager(Properties p) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(
				"technetium", p);
		return EntityManagerWrapper.wrap(emf.createEntityManager());

	}

	static void checkTables(Properties p) throws Exception {

		Either<Connection, ? extends Exception> ce = DatabaseUtil.connect(
				p.getProperty("javax.persistence.jdbc.driver"),
				p.getProperty("javax.persistence.jdbc.url"),
				p.getProperty("javax.persistence.jdbc.user"),
				p.getProperty("javax.persistence.jdbc.password"));

		if (ce.isRight()) {
			throw ce.getRight();
		}

		Connection c = ce.getLeft();
		EntityManager em = createEntityManager(p);
		try {
			if (p.getProperty("javax.persistence.jdbc.url").startsWith(
					"jdbc:derby:")) {
				DatabaseMetaData metaData = c.getMetaData();

				List<String> tablesInSchema = PersistenceUtil
						.getSchemaTableNames(em);
				int exists = 0;
				for (String table : tablesInSchema) {
					ResultSet result = metaData.getTables(null, null,
							table.toUpperCase(), null);
					if (result.next()) {
						exists++;
					}
				}
				if (exists == 0) {
					System.out
							.println("No known table recognized in derby database - auto-creating tables.");

					PersistenceUtil.createTables(em);
					PersistenceUtil.initData(em);

					System.out.println("Schema created.");
				} else if (exists != tablesInSchema.size()) {
					throw new SQLException(
							"Partial schema exists (maybe an old one?) - please clean your derby database.");
				}
			} else {
				List<String> tablesInDatabase = DatabaseUtil.getTableNames(c);
				c.close();

				List<String> tablesInSchema = PersistenceUtil
						.getSchemaTableNames(em);

				if (tablesInDatabase.isEmpty()) {
					System.out
							.println("No tables found in database - auto-creating tables.");

					PersistenceUtil.createTables(em);
					PersistenceUtil.initData(em);

					System.out.println("Schema created.");
				} else {
					Set<String> missingTables = new TreeSet<String>(
							tablesInSchema);
					missingTables.removeAll(tablesInDatabase);
					if (!missingTables.isEmpty()) {
						throw new SQLException(
								"Partial schema exists (maybe an old one?) - please clean your database. The following tables are missing: "
										+ missingTables.toString()
										+ " - However, these are available: "
										+ tablesInDatabase.toString());
					}
				}
			}
		} finally {
			EntityManagerFactory emf = em.getEntityManagerFactory();
			em.close();
			emf.close();

			c.close();
		}
	}
}
