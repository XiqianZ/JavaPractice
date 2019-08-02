package questionnaire;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.*;

public class SevenScaleQuestionnaireUI {


	private static String emotionTag = "N/A";
	private static String touchPatternTag = "N/A";
	private static String titleString = "ERICAによる「" + emotionTag + "」感情の表現に関するアンケートにお答えください";
	
	private static String strengthTitleString = "表情と発話のみによる感情表現に比べて，今回の感情表現から伝わったERICAの感情の度合いは";
	
	private static String naturenessTitleString = "ERICAの触れ方は，感情を表現する上で";
	
	private static String friendlinessTitleString = "表情と発話のみによる感情表現に比べて，今回の感情表現から伝わる親しみの度合いは";
	
	private static String readyString = "Ready?";
	private static String continueString = "Continue";
	
	private static String fontStyle = "Serif";
	private static int titleFontSize = 24;
	private static int subTitleFontSize = 20;
	
	private static ButtonGroup strengthButtonGroup = new ButtonGroup();
	private static JRadioButton strengthN3 = createScoreButton("<html><p align=\"center\">-3 <br> とても弱い</p></html>", strengthButtonGroup);
	private static JRadioButton strengthN2 = createScoreButton("<html><p align=\"center\">-2 <br> &nbsp  </p></html>", strengthButtonGroup);
	private static JRadioButton strengthN1 = createScoreButton("<html><p align=\"center\">-1 <br> &nbsp  </p></html>", strengthButtonGroup);
	private static JRadioButton strengthZero = createScoreButton("<html><p align=\"center\">0 <br> 同じ程度</p></html>", strengthButtonGroup);
	private static JRadioButton strengthP1 = createScoreButton("<html><p align=\"center\">1 <br> &nbsp  </p></html>", strengthButtonGroup);
	private static JRadioButton strengthP2 = createScoreButton("<html><p align=\"center\">2 <br> &nbsp  </p></html>", strengthButtonGroup);
	private static JRadioButton strengthP3 = createScoreButton("<html><p align=\"center\">3 <br> とても強い</p></html>", strengthButtonGroup);
	
	private static ButtonGroup naturenessButtonGroup = new ButtonGroup();
	private static JRadioButton naturenessN3 = createScoreButton("<html><p align=\"center\">-3 <br> とても不自然</p></html>",naturenessButtonGroup);
	private static JRadioButton naturenessN2 = createScoreButton("<html><p align=\"center\">-2 <br> &nbsp  </p></html>",naturenessButtonGroup);
	private static JRadioButton naturenessN1 = createScoreButton("<html><p align=\"center\">-1 <br> &nbsp  </p></html>",naturenessButtonGroup);
	private static JRadioButton naturenessZero = createScoreButton("<html><p align=\"center\">0 <br> どちらでもない</p></html>",naturenessButtonGroup);
	private static JRadioButton naturenessP1 = createScoreButton("<html><p align=\"center\">1 <br> &nbsp  </p></html>",naturenessButtonGroup);
	private static JRadioButton naturenessP2 = createScoreButton("<html><p align=\"center\">2 <br> &nbsp  </p></html>",naturenessButtonGroup);
	private static JRadioButton naturenessP3 = createScoreButton("<html><p align=\"center\">3 <br> とても自然</p></html>",naturenessButtonGroup);
	
	private static ButtonGroup friendlinessButtonGroup = new ButtonGroup();
	private static JRadioButton friendN3 = createScoreButton("<html><p align=\"center\">-3 <br> とても弱い</p></html>",friendlinessButtonGroup);
	private static JRadioButton friendN2 = createScoreButton("<html><p align=\"center\">-2 <br> &nbsp  </p></html>",friendlinessButtonGroup);
	private static JRadioButton friendN1 = createScoreButton("<html><p align=\"center\">-1 <br> &nbsp  </p></html>",friendlinessButtonGroup);
	private static JRadioButton friendZero = createScoreButton("<html><p align=\"center\">0 <br> 同じ程度</p></html>",friendlinessButtonGroup);
	private static JRadioButton friendP1 = createScoreButton("<html><p align=\"center\">1 <br> &nbsp  </p></html>",friendlinessButtonGroup);
	private static JRadioButton friendP2 = createScoreButton("<html><p align=\"center\">2 <br> &nbsp  </p></html>",friendlinessButtonGroup);
	private static JRadioButton friendP3 = createScoreButton("<html><p align=\"center\">3 <br> とても強い</p></html>",friendlinessButtonGroup);
	
	private static JButton continueButton = new JButton(readyString);
	static JLabel titleLabel = new JLabel(titleString);
	
	private static JPanel container;
	
	private static boolean canSelect = false;
	private static boolean isReady = false;
	private static boolean isBaseline = true;
	
//	public static void main(String args[]) {
//		JFrame jf = new JFrame("Questionnaire");
//		jf.add(getContainerPanel());
//
//		jf.pack();
//		jf.setVisible(true);
//		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
	
	static JPanel getContainerPanel() {
		if(container == null) {
			container = new JPanel(new GridLayout(0,1,0,0));
			container.add(createTitlePanel());
			container.add(strengthSelectionPanel());
//			container.add(new JSeparator(SwingConstants.HORIZONTAL));	
			container.add(naturenessSelectionPanel());
			container.add(friendlinessSelectionPanel());
//			container.add(new JSeparator(SwingConstants.HORIZONTAL));
			container.add(createContinuePanel());			
		}
		return container;
	}
	
	static void initialize() {
		canSelect = false;
		isReady = false;
		isBaseline = true;
		continueButton.setText(readyString);
		changeSelectionAbility();
	}
	
	private static JPanel createTitlePanel() {
		JPanel titlePanel = new JPanel(new BorderLayout());
		
		titleLabel.setFont(new Font(fontStyle, Font.BOLD, titleFontSize));
		titlePanel.add(titleLabel, BorderLayout.CENTER);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		return titlePanel;
	}
	
	private static JPanel strengthSelectionPanel() {
		JPanel strengthSelectionPanel = new JPanel(new GridLayout(2,1));
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		strengthSelectionPanel.setBorder(raisedbevel);
		
		JPanel titleLabelPanel = new JPanel(new GridLayout(1,0));
		JPanel radioButtonPanel = createRadioButtonPanel();
		
		JLabel strengthTitleLabel = new JLabel(strengthTitleString);
		strengthTitleLabel.setFont(new Font(fontStyle, Font.ITALIC, subTitleFontSize));
		strengthTitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		titleLabelPanel.add(strengthTitleLabel);
		
		radioButtonPanel.add(strengthN3);
		radioButtonPanel.add(strengthN2);
		radioButtonPanel.add(strengthN1);
		radioButtonPanel.add(strengthZero);
		radioButtonPanel.add(strengthP1);
		radioButtonPanel.add(strengthP2);
		radioButtonPanel.add(strengthP3);
		
		strengthSelectionPanel.add(titleLabelPanel);
		strengthSelectionPanel.add(radioButtonPanel);
		return strengthSelectionPanel;
	}
	
	private static JPanel naturenessSelectionPanel() {
		JPanel naturenessSelectionPanel = new JPanel(new GridLayout(2, 1));
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		naturenessSelectionPanel.setBorder(raisedbevel);
		
		JPanel titleLabelPanel = new JPanel(new GridLayout(1,0));
		JPanel radioButtonPanel = createRadioButtonPanel();
		
		JLabel titleLabel = new JLabel(naturenessTitleString);
		titleLabel.setFont(new Font(fontStyle, Font.ITALIC, subTitleFontSize));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		titleLabelPanel.add(titleLabel);

		radioButtonPanel.add(naturenessN3);
		radioButtonPanel.add(naturenessN2);
		radioButtonPanel.add(naturenessN1);
		radioButtonPanel.add(naturenessZero);
		radioButtonPanel.add(naturenessP1);
		radioButtonPanel.add(naturenessP2);
		radioButtonPanel.add(naturenessP3);

		naturenessSelectionPanel.add(titleLabelPanel);
		naturenessSelectionPanel.add(radioButtonPanel);
		
		return naturenessSelectionPanel;
	}
	
	private static JPanel friendlinessSelectionPanel() {
		JPanel friendlinessSelectionPanel = new JPanel(new GridLayout(2,1));
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		friendlinessSelectionPanel.setBorder(raisedbevel);
		
		JPanel titleLabelPanel = new JPanel(new GridLayout(1,0));
		JPanel radioButtonPanel = createRadioButtonPanel();

		
		JLabel titleLabel = new JLabel(friendlinessTitleString);
		titleLabel.setFont(new Font(fontStyle, Font.ITALIC, subTitleFontSize));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		titleLabelPanel.add(titleLabel);

		radioButtonPanel.add(friendN3);
		radioButtonPanel.add(friendN2);
		radioButtonPanel.add(friendN1);
		radioButtonPanel.add(friendZero);
		radioButtonPanel.add(friendP1);
		radioButtonPanel.add(friendP2);
		radioButtonPanel.add(friendP3);

		friendlinessSelectionPanel.add(titleLabelPanel);
		friendlinessSelectionPanel.add(radioButtonPanel);
		
		return friendlinessSelectionPanel;
	}
	
	private static JPanel createRadioButtonPanel() {
		JPanel jp = new JPanel(new GridLayout(1,7));
		jp.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		return jp;
	}
	
	private static JRadioButton createScoreButton(String txt, ButtonGroup group) {
		JRadioButton jrb = new JRadioButton(txt);
		jrb.setFont(new Font(fontStyle, Font.BOLD, subTitleFontSize));
		group.add(jrb);
		jrb.setVerticalTextPosition(JRadioButton.BOTTOM);
		jrb.setHorizontalTextPosition(JRadioButton.CENTER);
		jrb.setHorizontalAlignment(JRadioButton.CENTER);
		return jrb;
	}
	
	private static JPanel createContinuePanel() {
		JPanel continuePanel = new JPanel();
		continuePanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
		continueButton.setFont(new Font(fontStyle, Font.BOLD, titleFontSize));		
		continueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isReady) {
					if(isBaseline) {
						onBaselineConditionButtonAction();
					} else {
						onTouchConditionButtonAction();						
					}
				} else {
					getReadyButtonAction();
				}
			}
			
		});
		
		continuePanel.add(continueButton);
		
		return continuePanel;
	}
	
	static void setTags() {
//		emotionTag = InteractionManager_ForExperiment.getEmotionTag();
//		touchPatternTag = InteractionManager_ForExperiment.getGestureTag();
		titleString = "ERICAによる「" + emotionTag + "」感情の表現に関するアンケートにお答えください";
		titleLabel.setText(titleString);
	}
	
	private static void resetSelection() {
		strengthButtonGroup.clearSelection();
		naturenessButtonGroup.clearSelection();
		friendlinessButtonGroup.clearSelection();
	}
	
	private static void saveSelection() {
		String emotion = emotionTag;
		String touchPattern = touchPatternTag;
		
		String strength = "N/A";
		if(strengthN3.isSelected()) strength = "-3";
		if(strengthN2.isSelected()) strength = "-2";
		if(strengthN1.isSelected()) strength = "-1";
		if(strengthZero.isSelected()) strength = "0";
		if(strengthP1.isSelected()) strength = "1";
		if(strengthP2.isSelected()) strength = "2";
		if(strengthP3.isSelected()) strength = "3";
		
		String natureness = "N/A";
		if(naturenessN3.isSelected()) natureness = "-3";
		if(naturenessN2.isSelected()) natureness = "-2";
		if(naturenessN1.isSelected()) natureness = "-1";
		if(naturenessZero.isSelected()) natureness = "0";
		if(naturenessP1.isSelected()) natureness = "1";
		if(naturenessP2.isSelected()) natureness = "2";
		if(naturenessP3.isSelected()) natureness = "3";
		
		String friendliness = "N/A";
		if(friendN3.isSelected()) friendliness = "-3";
		if(friendN2.isSelected()) friendliness = "-2";
		if(friendN1.isSelected()) friendliness = "-1";
		if(friendZero.isSelected()) friendliness = "0";
		if(friendP1.isSelected()) friendliness = "1";
		if(friendP2.isSelected()) friendliness = "2";
		if(friendP3.isSelected()) friendliness = "3";
		
		SevenScaleData item = new SevenScaleData(emotion, touchPattern, strength, natureness, friendliness);
		DataManager.addItem(item);
	}
	
	static void asBaseLineCondition() {
		isBaseline = true;
		disableSelection();
	}
	
	static void asTouchCondition() {
		isBaseline = false;
		enableSelection();
	}
	
	static void enableContinueButton() {
		continueButton.setEnabled(true);
	}
	
	static void disableContinueButton() {
		continueButton.setEnabled(false);
	}
	
	private static void onTouchConditionButtonAction() {
		boolean isAllSelected = isButtonSelected();
		if(isAllSelected) {
			disableContinueButton();
			continueButton.setText(continueString);
			saveSelection();
			resetSelection();
			try {
				DataManager.writeData();
			} catch (IOException e) {
				e.printStackTrace();
			}
//			InteractionManager_ForExperiment.increaseStep();	
//			InteractionManager_ForFun.increaseStep();
		} else {
			continueButton.setText("Please select all");
		}
	}
	
	private static boolean isButtonSelected() {
		boolean isStrengthSelected = isButtonGroupSelected(strengthButtonGroup);
		boolean isNaturenessSelected = isButtonGroupSelected(naturenessButtonGroup);
		boolean isFriendlinessSelected = isButtonGroupSelected(friendlinessButtonGroup);
		return (isStrengthSelected && isNaturenessSelected &&isFriendlinessSelected);
	}
	
	private static boolean isButtonGroupSelected(ButtonGroup bg) {
		boolean isSelected = false;
		Enumeration<AbstractButton> buttons=bg.getElements();  
		while(buttons.hasMoreElements())  
		{  
			JRadioButton temp=(JRadioButton)buttons.nextElement();  
			if(temp.isSelected()) {
				isSelected = true;
				break;
			}
		}
		return isSelected;
	}
	
	private static void onBaselineConditionButtonAction() {
		disableContinueButton();
//		InteractionManager_ForExperiment.increaseStep();
//		InteractionManager_ForFun.increaseStep();
	}
	
	private static void getReadyButtonAction() {
		disableContinueButton();
		isReady = true;
		continueButton.setText(continueString);
//		InteractionManager_ForExperiment.increaseStep();
//		InteractionManager_ForFun.increaseStep();
	}
	
	private static void disableSelection() {
		canSelect = false;
		changeSelectionAbility();
	}
	
	private static void enableSelection() {
		canSelect = true;
		changeSelectionAbility();
	}
	
	private static void changeSelectionAbility() {
		strengthN3.setEnabled(canSelect);
		strengthN2.setEnabled(canSelect);
		strengthN1.setEnabled(canSelect);
		strengthZero.setEnabled(canSelect);
		strengthP1.setEnabled(canSelect);
		strengthP2.setEnabled(canSelect);
		strengthP3.setEnabled(canSelect);
		
		naturenessN3.setEnabled(canSelect);
		naturenessN2.setEnabled(canSelect);
		naturenessN1.setEnabled(canSelect);
		naturenessZero.setEnabled(canSelect);
		naturenessP1.setEnabled(canSelect);
		naturenessP2.setEnabled(canSelect);
		naturenessP3.setEnabled(canSelect);
		
		changeSelectAbilityOfButtonGroup(friendlinessButtonGroup, canSelect);
	}
	
	private static void changeSelectAbilityOfButtonGroup(ButtonGroup bg, boolean enableOrNot) {
		for(Enumeration<AbstractButton> buttons = bg.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			button.setEnabled(enableOrNot);
		}
	}

}
