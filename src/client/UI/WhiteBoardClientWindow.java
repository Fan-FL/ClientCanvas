package client.UI;

import client.controller.Controller;
import client.file.FileHandler;

import java.awt.*;
import java.awt.event.*;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

// Draw the main frame of the white board
public class WhiteBoardClientWindow extends JFrame implements ActionListener {

    private static final long serialVersionUID = -2551980583852173918L;
    private Controller controller;
    private JToolBar buttonpanel;
    // define the button panel

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private JMenuBar bar;
    private JMenu file, stroke;
    // four main menu of the button panel
    private JMenuItem savefile, exit;
    private JMenuItem strokeitem;
    private Icon sf, ex;
    // The icon objects of the button panel
    private JLabel startbar;
    private DrawArea drawarea;

    public DrawArea getDrawarea() {
        return drawarea;
    }

    public void setDrawarea(DrawArea drawarea) {
        this.drawarea = drawarea;
    }

    private FileHandler fileclass;

	private JPanel chat;

	private JTextArea recvArea, sendArea;
	private JPanel chatRoomPanel, recviveAreaPanel, sendAreaPanel,
			sendButtonPanel, sendButtonAreaPanel;
	private JLabel chatRoom;
	private JButton clear, send;
	private JScrollPane recviveScroll, sendScroll;

    private JLabel myUsernameLabel;

    public JLabel getMyUsernameLabel() {
        return myUsernameLabel;
    }

    public void setMyUsernameLabel(JLabel myUsernameLabel) {
        this.myUsernameLabel = myUsernameLabel;
    }

    String[] fontName;
    // Define the name of the icons in the button panel
    private String names[] = {"pen","line", "rect", "frect", "oval", "foval", "circle", "fcircle",
            "roundrect", "froundrect", "rubber", "color", "stroke", "word"};
    private Icon icons[];

    // Show instruction when the mouse moves above the button
    private String tiptext[] = {"freely draw", "draw a straight line",
            "draw a hollow rectangle", "draw a solid rectangle",
            "draw a hollow oval", "draw a solid oval", "draw a hollow circle",
            "draw a solid circle", "draw a rounded corner rectangle",
            "draw a solid rounded corner rectangle", "eraser", "color",
            "brush size", "text input"};
    JButton button[]; // define button group in toolbar
    private JCheckBox bold, italic;

    private JComboBox stytles;

    private UserTable userTable;

    public UserTable getUserTable() {
        return userTable;
    }

    public void setUserTable(UserTable userTable) {
        this.userTable = userTable;
    }

    Toolkit tool = getToolkit();
    Dimension dim = tool.getScreenSize();// Get the size of current screen

    public WhiteBoardClientWindow(String string, final Controller controller) {
        // TODO constructor of main interface
        super(string);
        this.controller = controller;
        // initial menu
        file = new JMenu("file");
        stroke = new JMenu("brush");
        bar = new JMenuBar();// initial menu

        bar.add(file);
        bar.add(stroke);
        bar.setOpaque(true);
        bar.setBackground(new Color(245,245,245));

        setJMenuBar(bar);

        // Define short cut keys for the buttons
        file.setMnemonic('F');
        stroke.setMnemonic('S');

        // File menu initialization
        try {
            Reader reader = new InputStreamReader(getClass()
                    .getResourceAsStream("/icon"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Can't read image from the disk", "error",
                    JOptionPane.ERROR_MESSAGE);
        }
        // Show the icons for the sub-buttons of File menu
        sf = new ImageIcon(getClass().getResource("/icon/savefile.jpg"));
        ex = new ImageIcon(getClass().getResource("/icon/exit.jpg"));
        savefile = new JMenuItem("save", sf);
        exit = new JMenuItem("exit", ex);

        file.add(savefile);
        file.add(exit);

        // Add short cut keys for the buttons
        savefile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                InputEvent.CTRL_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
                InputEvent.CTRL_MASK));

        savefile.addActionListener(this);
        exit.addActionListener(this);

        // Initialization for stroke menu
        strokeitem = new JMenuItem("set brush");
        strokeitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
                InputEvent.CTRL_MASK));
        stroke.add(strokeitem);
        strokeitem.addActionListener(this);

        // Initialization for the toolbar
        buttonpanel = new JToolBar(JToolBar.HORIZONTAL);
        icons = new ImageIcon[names.length];
        button = new JButton[names.length];
        for (int i = 0; i < names.length; i++) {
            // read icons for the button to the buffer
            icons[i] = new ImageIcon(getClass().getResource(
                    "/icon/" + names[i] + ".jpg"));
            // Create the buttons
            button[i] = new JButton("", icons[i]);
            // Show instructions when the mouse moves over the icon
            button[i].setToolTipText(tiptext[i]);
            buttonpanel.add(button[i]);
            button[i].setBackground(Color.white);
            if (i < 3) {
                button[i].addActionListener(this);
            } else if (i <= 17) {
                button[i].addActionListener(this);
            }

        }
        // Handles the styles of the characters
        CheckBoxHandler CHandler = new CheckBoxHandler();
        bold = new JCheckBox("bold");
        bold.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
        // Set font for the character "bold"
        bold.addItemListener(CHandler);
        italic = new JCheckBox("italic");
        italic.addItemListener(CHandler);
        // Set font for the character "italic"
        italic.setFont(new Font(Font.DIALOG, Font.ITALIC, 30));
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        // Get available fonts from the computer
        fontName = ge.getAvailableFontFamilyNames();
        stytles = new JComboBox(fontName); // Initialization for the font lists
        stytles.addItemListener(CHandler);
        stytles.setMaximumSize(new Dimension(400, 50));
        stytles.setMinimumSize(new Dimension(250, 40));
        stytles.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        // Set font for the lists

        buttonpanel.add(bold);
        buttonpanel.add(italic);
        buttonpanel.add(stytles);
        buttonpanel.setBackground(new Color(245,245,245));

        // Initialization for the start bar
        startbar = new JLabel("White Board");

        userTable = new UserTable(this);
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BorderLayout());
        myUsernameLabel = new JLabel();
        userInfoPanel.add(myUsernameLabel, BorderLayout.NORTH);
        userInfoPanel.add(userTable, BorderLayout.CENTER);

		chat = new JPanel();
		chat.setLayout(new BorderLayout(0, 10));
		chat.setPreferredSize(new Dimension(400, dim.height - 130));
		chat.add(recvWindow(), BorderLayout.NORTH);
		chat.add(sendWindow(), BorderLayout.CENTER);
		chat.add(buttons(), BorderLayout.SOUTH);

        // Initialization for the canvas
        drawarea = new DrawArea(this);
        fileclass = new FileHandler(this, drawarea);
        validate();

        Container con = getContentPane(); // Get the canvas implemented
        con.add(buttonpanel, BorderLayout.NORTH);
        con.add(drawarea, BorderLayout.CENTER);
        con.add(startbar, BorderLayout.SOUTH);
        con.add(userInfoPanel, BorderLayout.WEST);
        con.add(chat, BorderLayout.EAST);


        setBounds(0, 0, dim.width, dim.height - 40);
        validate();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                if(controller != null && controller.getTcpClient().isConnected()){
                    controller.sendToServerData("{\"cmd\":\"disconnect\"}");
                    controller.getTcpClient().stop(null);
                }
                System.exit(0);
            }

        });
        setVisible(true);
    }
    

	public JPanel recvWindow() {
		recvArea = new JTextArea(400, (dim.height - 130) * 2 / 3 - 50);

		chatRoom = new JLabel("  The Chat Room");
		chatRoom.setOpaque(true);
		chatRoom.setBackground(new Color(245,245,245));
		chatRoomPanel = new JPanel();
		chatRoomPanel.setLayout(new GridLayout());
		chatRoomPanel.add(chatRoom);
		recviveScroll = new JScrollPane(recvArea);

		recviveScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		recvArea.setEditable(false);
		recvArea.setLineWrap(true);

		recviveAreaPanel = new JPanel();
		recviveAreaPanel.setLayout(new BorderLayout());
		recviveAreaPanel.add(chatRoomPanel, BorderLayout.NORTH);
		recviveAreaPanel.add(recviveScroll, BorderLayout.CENTER);
		// recviveAreaPanel.setSize(400,(dim.height-130)*2/3 - 50);
		recviveAreaPanel.setPreferredSize(new Dimension(400,
				(dim.height - 130) * 2 / 3 - 50));
		recviveAreaPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return recviveAreaPanel;
	}

	public JPanel sendWindow() {
		sendArea = new JTextArea(400, (dim.height - 130) / 3 - 50);
		sendAreaPanel = new JPanel();
		sendScroll = new JScrollPane(sendArea);

		sendScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sendArea.setLineWrap(true);
		sendAreaPanel.setLayout(new BorderLayout());

		sendAreaPanel.add(sendScroll , BorderLayout.CENTER);
		sendAreaPanel.setSize(400, (dim.height - 130) / 3 - 50);
		sendAreaPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return sendAreaPanel;
	}

	public JPanel buttons() {
		clear = new JButton("Clear");
		send = new JButton("Send");
		//send.setMnemonic(/n);

		clear.addActionListener(this);
		clear.setActionCommand("clear");
		send.addActionListener(this);
		send.setActionCommand("send");

		sendButtonPanel = new JPanel();
		sendButtonPanel.add(clear);
		sendButtonPanel.add(send);
		sendButtonAreaPanel = new JPanel();
		sendButtonAreaPanel.add(sendButtonPanel, BorderLayout.EAST);
		return sendButtonAreaPanel;
	}

	public void addChatMessage(String content) {
        recvArea.append(content);
	}

    public String generateChatMessage() {
        String userName = this.userTable.getMyUsername();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        String message = userName + "   (" + time + ("):") + System.getProperty("line.separator")
                + sendArea.getText() + System.getProperty("line.separator") + System.getProperty("line.separator");
        return message;
    }


    // The characters shown in the start bar
    public void setStratBar(String s) {
        startbar.setText(s);
    }

    /*
      The functions of the button
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i <= 10; i++) {
            if (e.getSource() == button[i]) {
                drawarea.setCurrentShapeType(DrawArea.ShapeType.values()[i]);
                drawarea.repaint();
            }

        }if (e.getSource() == savefile){
            // save file
            fileclass.saveFile();
        } else if (e.getSource() == exit){
            // exit
            this.controller.sendToServerData("{\"cmd\":\"disconnect\"}");
            System.exit(0);
        } else if (e.getSource() == button[11]){
            // color plate
            drawarea.chooseColor();// Choose your color
        } else if (e.getSource() == button[12] || e.getSource() == strokeitem){
            // Brush size
            drawarea.setStroke();
        } else if (e.getSource() == button[13]){
            // add text
            JOptionPane.showMessageDialog(null,
                    "please click on canvs to confirm position of textinput",
                    "hints", JOptionPane.INFORMATION_MESSAGE);
            drawarea.setCurrentShapeType(DrawArea.ShapeType.WORD);
            drawarea.repaint();
        } else if (e.getActionCommand().equals("clear")) {
			sendArea.setText("");
		} else if (e.getActionCommand().equals("send")) {
            this.controller.sendChatMessage(generateChatMessage());
            sendArea.setText("");
		}

    }
    
    // About the font of the character
    public class CheckBoxHandler implements ItemListener {

        public void itemStateChanged(ItemEvent ie) {
            // TODO the font of the character
            if (ie.getSource() == bold) // Bold font
            {
                if (ie.getStateChange() == ItemEvent.SELECTED)
                    drawarea.setFont(1, Font.BOLD);
                else
                    drawarea.setFont(1, Font.PLAIN);
            } else if (ie.getSource() == italic) // Italic font
            {
                if (ie.getStateChange() == ItemEvent.SELECTED)
                    drawarea.setFont(2, Font.ITALIC);
                else
                    drawarea.setFont(2, Font.PLAIN);

            } else if (ie.getSource() == stytles) // System font
            {
                drawarea.setStytle(fontName[stytles.getSelectedIndex()]);
            }
        }

    }
}