import java.awt.BorderLayout;

import java.util.List;

import javax.swing.JFrame;


public class OuterContainer extends JFrame {
	
	private ContPane cp;
	
	public OuterContainer() {
		super("Painter");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 1200);
		add(cp = new ContPane());
		pack();
		setResizable(false);
		setVisible(true);
		
	}
	
	public ContPane getContPane() {
		return cp;
	}

}
