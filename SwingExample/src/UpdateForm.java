import javax.swing.*;

public class UpdateForm extends JDialog {
    private JPanel root;
    private JLabel nameLabel;

    private Athlete athlete;

    public UpdateForm(Athlete athlete) {
        this.setModal(true);
        this.athlete = athlete;
        this.setContentPane(root);
        nameLabel.setText("Updating " + athlete.getFirstName());
        this.pack();
        this.setVisible(true);
    }
}
