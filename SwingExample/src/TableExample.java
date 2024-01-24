/**
 * Credit: This file was inspired by the example on
 * https://alvinalexander.com/source-code/java/java-jtable-example-abstracttablemodel-jscrollpane-iscelleditable-getcolumnname-get/
 **/

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

//By making it a subclass of JFrame,
//We can reuse all the methods.
public class TableExample extends JFrame {
    private JTable mainTable;
    // The table needs to be inside a ScrollPane, which shows a scrollbar automatically.
    private JPanel rootPanel;
    private JButton delButton;
    private JButton refreshButton;
    //The rootPanel of the Frame, which contains the scroll pane.

    public TableExample() {
        // Add the panel to the
        setContentPane(rootPanel);
        // MyTableDataModel contains the data for the table.
        // MyTableDataModel is a subclass of AbstractTableModel that is defined below.
        AthleteTableDataModel dataModel = new AthleteTableDataModel();
        // Set the model for the table.
        mainTable.setModel(dataModel);
        // Set the row height to 40. All the image icons are resized to 40 * 40.
        mainTable.setRowHeight(40);
        // The size of the frame is automatically adjusted based on its content.
        this.pack();
        //Show the frame.
        this.setVisible(true);

        // Double click to update a record.
        mainTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                // If the user just clicked 2 times and has selected a row.1
                if (mouseEvent.getClickCount() == 2 && TableExample.this.mainTable.getSelectedRow() != -1) {
                    // You should replace this one with a form.
                    new UpdateForm(dataModel.getValue(TableExample.this.mainTable.getSelectedRow()));
                }
            }
        });

        // Add a listener for the Delete Button.
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Make sure only one row has been selected.
                if(TableExample.this.mainTable.getSelectedRowCount()==1) {
                    // Call the model method to remove the row.
                    dataModel.removeRow(TableExample.this.mainTable.getSelectedRow());
                } else {
                    JOptionPane.showInternalMessageDialog(null, "You can only select one record to delete.");
                }
            }
        });

        // refresh the data.
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AthleteTableDataModel m = (AthleteTableDataModel) TableExample.this.mainTable.getModel();
                m.refresh();
            }
        });
    }

    // The model for our table.

    class AthleteTableDataModel extends AbstractTableModel {
        private String[] columnNames;
        private ArrayList<Athlete> data;
        public AthleteTableDataModel() {
            columnNames = new String[]{"First Name", "Last Name", "Sport"};
            // Retrieve the data from the database.
            data = AtheleteDB.findAll();
        }

        /**
         * remove a row from the UI and the database.
         * @param rowIndex
         */
        public void removeRow(int rowIndex) {
            // Remove the data from the database.
            AtheleteDB.delete(this.data.get(rowIndex));
            // Update the UI.
            this.fireTableRowsDeleted(rowIndex, rowIndex);
        }

        /**
         * re-fetch the data from the database and update the UI.
         */
        public void refresh() {
            // Retrieve the data.
            this.data = AtheleteDB.findAll();
            // Update the UI.
            this.fireTableDataChanged();
        }

        /**
         * Get the athlete on a particular row.
         * @param rowIndex
         * @return
         */
        public Athlete getValue(int rowIndex) {
            return this.data.get(rowIndex);
        }

        // The following methods overrides the ones in the parent class.
        // These methods are called when the table is being drawn
        // even though you don't need to call them explicitly.

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            // The first column is first name.
            // The second column is last name.
            // ...
            return switch (columnIndex) {
                case 0 -> data.get(rowIndex).getFirstName();
                case 1 -> data.get(rowIndex).getLastName();
                case 2 -> data.get(rowIndex).getSport();
                default -> null;
            };
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }
        @Override
        public Class getColumnClass(int c) {
            // This method is required for the fields to be drawn correctly.
            // In this case, a boolean field is drawn with a checkbox automatically.
            return getValueAt(0, c).getClass();
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            // By returning true, you allow the user to update
            // the records directly.
                return false;

        }
        // This method is useless in this example as editing is disabled,
        // but it shows you how you can allow the user to edit the cells directly.
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0 -> data.get(rowIndex).setFirstName((String)aValue);
                case 1 -> data.get(rowIndex).setLastName((String)aValue);
                case 2 -> data.get(rowIndex).setSport((String)aValue);
                default -> {
                    return;
                }
            };
            // Update the database
            AtheleteDB.update(rowIndex, data.get(rowIndex));
            // Update the UI.
            fireTableCellUpdated(rowIndex, columnIndex);// notify listeners
        }
    }
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TableExample te = new TableExample();
            }
        });
    }
}
