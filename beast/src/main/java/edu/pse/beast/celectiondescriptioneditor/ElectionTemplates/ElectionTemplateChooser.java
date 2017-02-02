/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.ElectionTemplates;

import edu.pse.beast.celectiondescriptioneditor.UserActions.NewElectionUserAction;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;

import javax.swing.*;
import java.util.ArrayList;

/**
 *
 * @author Holger-Desktop
 */
public class ElectionTemplateChooser extends javax.swing.JFrame{
    private NewElectionUserAction action;
    private ElectionTemplateHandler electionTemplateHandler;
    private StringResourceLoader loader;
    private ArrayList<String> inputIds = new ArrayList<>();
    private ArrayList<String> resIds = new ArrayList<>();
    private String emptyNameTextFieldError;

    /**
     * Creates new form ElectionTemplateChooser
     */
    public ElectionTemplateChooser(
            NewElectionUserAction action,
            ElectionTemplateHandler electionTemplateHandler,
            StringResourceLoader loader, String emptyNameTextFieldError) {
        this.emptyNameTextFieldError = emptyNameTextFieldError;
        initComponents();
        this.action = action;
        this.loader = loader;
        
        createButton.setText(loader.getStringFromID("create"));
        
        createButton.addActionListener((ae) -> {
            if (nameField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, emptyNameTextFieldError, "", JOptionPane.OK_OPTION);
            } else {
                action.create(inputIds.get(inputList.getSelectedIndex()),
                        resIds.get(resultList.getSelectedIndex()),
                        nameField.getText());
                this.dispose();
            }
        });
            for(int i = 0; i < electionTemplateHandler.getInputIds().length; ++i) {
                inputIds.add(electionTemplateHandler.getInputIds()[i]);
                inputList.addItem(loader.getStringFromID(electionTemplateHandler.getInputIds()[i]));
        }
        
        for(int i = 0; i < electionTemplateHandler.getOutputTypes().length; ++i) {
                resIds.add(electionTemplateHandler.getOutputTypes()[i]);
                resultList.addItem(loader.getStringFromID(electionTemplateHandler.getOutputTypes()[i]));
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        createButton = new javax.swing.JButton();
        inputList = new javax.swing.JComboBox<>();
        resultList = new javax.swing.JComboBox<>();
        name = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Input");

        jLabel2.setText("Result");

        createButton.setText("create");

        name.setText("name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resultList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(inputList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 252, Short.MAX_VALUE)
                        .addComponent(createButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(name)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameField)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(inputList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(name)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(createButton)
                    .addComponent(resultList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createButton;
    private javax.swing.JComboBox<String> inputList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel name;
    private javax.swing.JTextField nameField;
    private javax.swing.JComboBox<String> resultList;

    // End of variables declaration//GEN-END:variables
}
