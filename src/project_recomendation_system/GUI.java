package project_recomendation_system;


import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.JToggleButton;
import javax.swing.JTabbedPane;




public class GUI extends JPanel{
	private static final long serialVersionUID = 1L;
	JTextField textField_T;
	JTextField textField_N;
	JTextField textField_X;
	JTextField textField_M;
	JTextField textField_K;
	
	JTextPane textPane;
	
	private JButton btnRun;
	private JRadioButton jaccard;
	private JRadioButton cosine;
	private JRadioButton pearson;
	private JButton btnNewButton_1;
	private JButton btnNewButton;
	
	private XYSeries seriesJacard,seriesCosine,seriesPearson;
	private ChartPanel chartPanel;
	private JPanel panel_4;
	private JButton btnNewButton_2;
	public JButton clear;
	private JButton btnNewButton_4;
	private JButton btnNewButton_5;
	public  JToggleButton reverse;
	JTable table_data;
	private JPanel panel_5;
	public DefaultTableModel model,model_prediction,model_error;
	
	public GUI(){
		setBorder(UIManager.getBorder("RadioButtonMenuItem.border"));
	       
    	init_components();
    	init_events();
    	
    	
    	
		seriesJacard = new XYSeries("Jacard");
		seriesCosine = new XYSeries("Cosine");
		seriesPearson= new XYSeries("Pearson");
		XYSeriesCollection datasetJacard = new XYSeriesCollection(seriesJacard);
		XYSeriesCollection datasetCosine = new XYSeriesCollection(seriesCosine);
		XYSeriesCollection datasetPearson = new XYSeriesCollection(seriesPearson);
		
		JFreeChart chart = ChartFactory.createXYLineChart("Error", "iterations", "Error plot", datasetJacard);
		chart.getXYPlot().setDataset(1,datasetCosine);
		chart.getXYPlot().setDataset(2,datasetPearson);
		chart.getXYPlot().setRenderer(1, new StandardXYItemRenderer());
		chart.getXYPlot().setRenderer(2, new StandardXYItemRenderer());


		chartPanel = new ChartPanel(chart);
		panel_4.add(chartPanel);
		
	}
	
	
	public void add_new_data(int method , float data){ // adds new data at plots
		
		
        switch(method){
        
        case 1: seriesJacard.add(seriesJacard.getItemCount()+1,data);  break ;
        case 2: seriesCosine.add(seriesCosine.getItemCount()+1,data);  break ;
        case 3: seriesPearson.add(seriesPearson.getItemCount()+1,data); break;
        
        }
		
	}
	
	
	public void add_new_data_exercise(int method , float data,int num){ // adds new data at plots
		
		
        switch(method){
        
        case 1: seriesJacard.add(num,data);  break ;
        case 2: seriesCosine.add(num,data);  break ;
        case 3: seriesPearson.add(num,data); break;
        
        }
		
	}
	
	   public void init_events(){
		   
	         reverse.addChangeListener(new ChangeListener() {
		         	public void stateChanged(ChangeEvent arg0) {
		         		recomendation_system.reverse_matrix();
		         	}
		         });
		   
	         btnNewButton_5.addActionListener(new ActionListener() {
		         	public void actionPerformed(ActionEvent e) {
		         		recomendation_system.exercise_B();

		         	}
		         });
		   
		   
	         btnNewButton_4.addActionListener(new ActionListener() {
		         	public void actionPerformed(ActionEvent e) {
		         		recomendation_system.exercise_A();
		         	}
		         });
		   
		   
	         btnNewButton_2.addActionListener(new ActionListener() {
		         	public void actionPerformed(ActionEvent arg0) {
		         		
		         		recomendation_system.compute_all_iterations();
		         	}
		         });
	         
	         clear.addActionListener(new ActionListener() {
		         	public void actionPerformed(ActionEvent e) {
		         		
		         		seriesJacard.clear();
		         		seriesCosine.clear();
		         		seriesPearson.clear();
		         		textPane.setText(" ");
		         	}
		         });
	    	
	        btnRun.addActionListener(new ActionListener() {
	         	public void actionPerformed(ActionEvent arg0) {
//	         		System.out.println("read CONFIGFILE.txt");
//	         		btnNewButton_1.doClick();
	         		

	         		if(jaccard.isSelected()){
//	         			System.out.println("running " + jaccard.getText());
	         			recomendation_system.compute_one_iterations(1);
	         		}
	         		else if(cosine.isSelected()){
//	         			System.out.println("running " + cosine.getText());
	         			recomendation_system.compute_one_iterations(2);
	         		}
	         		else if(pearson.isSelected()){
//	         			System.out.println("running " + pearson.getText());
	         			recomendation_system.compute_one_iterations(3);
	         		}
	         	}
	         });
	        
	        
	         btnNewButton.addActionListener(new ActionListener() {
		         	public void actionPerformed(ActionEvent arg0) {
		         		recomendation_system.setTxt();
		         	}
		         });
	         btnNewButton_1.addActionListener(new ActionListener() {
	         	public void actionPerformed(ActionEvent arg0) {
	         		recomendation_system.readTXT();
	         		recomendation_system.createMatrix();
	                textField_T.setText(Integer.toString(recomendation_system.T));
	                textField_N.setText(Integer.toString(recomendation_system.N));
	                textField_M.setText(Integer.toString(recomendation_system.M));
	                textField_X.setText(Integer.toString(recomendation_system.X)+"%");
	                textField_K.setText(Integer.toString(recomendation_system.K));
	         	}
	         });
	    }
	    
	    public void init_components(){
	         
	         JPanel panel_1 = new JPanel();
	         
	         JPanel panel_2 = new JPanel();
	         
	         JScrollPane scrollPane = new JScrollPane();
//	         scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
//	         scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	         JPanel panel_3 = new JPanel();
	         
	         panel_4 = new JPanel();
	         
	         clear = new JButton("clear");
	         
	         panel_5 = new JPanel();



	         GroupLayout groupLayout = new GroupLayout(this);
	         groupLayout.setHorizontalGroup(
	         	groupLayout.createParallelGroup(Alignment.LEADING)
	         		.addGroup(groupLayout.createSequentialGroup()
	         			.addContainerGap()
	         			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
	         				.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 157, Short.MAX_VALUE)
	         				.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
	         			.addGap(38)
	         			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	         			.addPreferredGap(ComponentPlacement.RELATED)
	         			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
	         				.addComponent(clear)
	         				.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
	         				.addGroup(groupLayout.createSequentialGroup()
	         					.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
	         					.addGap(18)
	         					.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)))
	         			.addContainerGap())
	         );
	         groupLayout.setVerticalGroup(
	         	groupLayout.createParallelGroup(Alignment.LEADING)
	         		.addGroup(groupLayout.createSequentialGroup()
	         			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
	         				.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 219, Short.MAX_VALUE)
	         				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	         				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
	         					.addContainerGap()
	         					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
	         						.addComponent(panel_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
	         						.addComponent(panel_5, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
	         					.addPreferredGap(ComponentPlacement.RELATED)))
	         			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
	         				.addGroup(groupLayout.createSequentialGroup()
	         					.addGap(27)
	         					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
	         					.addPreferredGap(ComponentPlacement.RELATED)
	         					.addComponent(clear))
	         				.addGroup(groupLayout.createSequentialGroup()
	         					.addGap(4)
	         					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)))
	         			.addGap(11))
	         );
	         
	         JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	         panel_5.setLayout(new GridLayout(0, 1, 0, 0));
	         

	         
	         panel_4.setLayout(new GridLayout(1, 0, 0, 0));

	         
	           model = new DefaultTableModel(20, 20);

	           table_data = new JTable(model);
	           table_data.setFillsViewportHeight(true);

	           
		         JScrollPane scrollPane_2 = new JScrollPane(table_data);
		         scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		         scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		         tabbedPane.add("data", scrollPane_2);
		         
		         model_prediction = new DefaultTableModel(20, 20);
		         JTable table_prediction = new JTable(model_prediction);
		         JScrollPane scrollPane_prediction = new JScrollPane(table_prediction);
		         scrollPane_prediction.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		         scrollPane_prediction.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		         tabbedPane.add("prediction", scrollPane_prediction);
		         
		         
		         model_error = new DefaultTableModel(20, 20);
		         JTable table_error = new JTable(model_error);
		         JScrollPane scrollPane_error = new JScrollPane(table_error);
		         scrollPane_error.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		         scrollPane_error.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		         tabbedPane.add("error", scrollPane_error);
		         
		         
		         panel_5.add(tabbedPane);

            panel_3.setLayout(new GridLayout(0, 1, 0, 0));
	                  
	        JScrollPane scrollPane_1 = new JScrollPane();
	        scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	        scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
          	panel_3.add(scrollPane_1);
	                  
           textPane = new JTextPane();
           textPane.setForeground(new Color(102, 0, 255));
           textPane.setFont(new Font("Calibri", Font.PLAIN, 11));
           scrollPane_1.setViewportView(textPane);
           textPane.setBackground(new Color(51, 204, 102));
	         panel_2.setLayout(new GridLayout(0, 1, 0, 0));
	         
	         btnNewButton_1 = new JButton("read values");
	         panel_2.add(btnNewButton_1);
	         
	         
	                  
	                  JPanel panel = new JPanel();
	                  panel_2.add(panel);
	                  panel.setLayout(new GridLayout(0, 2, 0, 0));
	                  
	                  JLabel lblInputData = new JLabel("input data");
	                  panel.add(lblInputData);
	                  
	                  JLabel lblNewLabel_1 = new JLabel("New label");
	                  panel.add(lblNewLabel_1);
	                  
	                  JLabel label = new JLabel("T:");
	                  panel.add(label);
	                  
	                  textField_T = new JTextField();
	                  panel.add(textField_T);
	                  textField_T.setColumns(10);
	                  
	                  JLabel lblNewLabel = new JLabel("N:");
	                  panel.add(lblNewLabel);
	                  
	                  textField_N = new JTextField();
	                  panel.add(textField_N);
	                  textField_N.setColumns(10);
	                  
	                  JLabel lblM = new JLabel("M");
	                  panel.add(lblM);
	                  
	                  textField_M = new JTextField();
	                  panel.add(textField_M);
	                  textField_M.setColumns(10);
	                  
	                  JLabel lblX = new JLabel("X:");
	                  panel.add(lblX);
	                  
	                  textField_X = new JTextField();
	                  panel.add(textField_X);
	                  textField_X.setColumns(10);
	                  
	                  JLabel lblK = new JLabel("K:");
	                  panel.add(lblK);
	                  
	                  textField_K = new JTextField();
	                  panel.add(textField_K);
	                  textField_K.setColumns(10);
	         
	         btnNewButton = new JButton("set values");
	         panel_2.add(btnNewButton);

	         panel_1.setLayout(new GridLayout(4, 2, 0, 0));
	         ButtonGroup group = new ButtonGroup();
	         jaccard = new JRadioButton("Jaccard");
	         panel_1.add(jaccard);
	         group.add(jaccard);
	         
	         btnRun = new JButton("run 1 iteration");
	         panel_1.add(btnRun);
	         cosine = new JRadioButton("Cosine");
	         panel_1.add(cosine);
	         group.add(cosine);
	         
	         btnNewButton_2 = new JButton("run all iterations");
	         panel_1.add(btnNewButton_2);
	         pearson = new JRadioButton("Pearson");
	         panel_1.add(pearson);
	         group.add(pearson);
	         
	         btnNewButton_4 = new JButton("exercise A");

	         panel_1.add(btnNewButton_4);
	         
	         reverse = new JToggleButton("reverse");

	         panel_1.add(reverse);
	         
	         btnNewButton_5 = new JButton("exercise B");

	         panel_1.add(btnNewButton_5);
	         setLayout(groupLayout);
	    	
	    	
	         
	    	
	    }
}
