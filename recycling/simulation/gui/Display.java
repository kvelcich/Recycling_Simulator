package recycling.simulation.gui;

import recycling.simulation.helper.Money;
import recycling.simulation.helper.Recyclable;
import recycling.simulation.rcm.RCM;
import recycling.simulation.rmos.RMOS;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Display extends JFrame {
    private RMOS rmos;
    private RCM rcm1, rcm2;
    private JPanel rmosRCM, rmosRCM1Panel, rmosRCM2Panel, addRecyclable, removeRecyclable;
    private RCMPanel rcmPanel1, rcmPanel2;
    private JButton restock1, restock2, empty1, empty2, addBtn, removeBtn;
    private JTextField addField, priceField, removeField;
    private JLabel mostUsed;
    private JLabel rmosRCM1Label, rmosRCM2Label, rmosMoney1, rmosMoney2, rmosCapacity1, rmosCapacity2, lastEmptied1, lastEmptied2;
    RCMGraphFrame rcmGraphFrame;

    public Display() {
        /* Setting up window */
        super("RMOS Controller");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        setLocationRelativeTo(null);
        setSize(1200, 600);

        /* Setting up variables */
        rcm1 = new RCM(1, "Benson", 50, new Money(20, 0));
        rcm2 = new RCM(2, "Bannon", 50, new Money(20, 0));
        rmos = new RMOS();
        rmos.addRCM(rcm1);
        rmos.addRCM(rcm2);
        rmos.addRecyclable(new Recyclable("Paper", 50, 0.5));
        rmos.addRecyclable(new Recyclable("Plastic", 20, 0.1));
        rmos.addRecyclable(new Recyclable("Glass", 100, 1));

        /* RCM PANEL */
        JPanel rcmPanel = new JPanel();
        rcmPanel.setLayout(new BoxLayout(rcmPanel, BoxLayout.X_AXIS));
        rcmPanel1 = new RCMPanel(rcm1);
        rcmPanel.add(rcmPanel1);
        rcmPanel2 = new RCMPanel(rcm2);
        rcmPanel.add(rcmPanel2);

        container.add(rcmPanel);

        /* ----RMOS PANEL---- */
        RMOSPanel rmosPanel = new RMOSPanel(rmos);

        container.add(rmosPanel);

        setVisible(true);
    }

    private void mostUsed() {
        mostUsed.setFont(new Font("Arial", Font.BOLD, 18));
        if (rcm1.stats().getNumItems() >= rcm2.stats().getNumItems())
            mostUsed.setText("\n\n\nMost Used: RCM " + rcm1.getId() + ", location: " + rcm1.getLocation());
        else
            mostUsed.setText("\n\n\nMost Used: RCM " + rcm2.getId() + ", location: " + rcm2.getLocation());
    }

    private void rmosFillRCMText(RCM rcm, int id) {
        switch (id) {
            case 1:
                rmosMoney1.setText("Money: " + rcm.getMoney());
                rmosCapacity1.setText("Capacity: " + rcm.getCapacity() + " / " + rcm.getMAX_CAPACITY());
                lastEmptied1.setText("Last Emptied: " + rcm.stats().getLastEmpty());
                break;
            case 2:
                rmosMoney2.setText("Money: " + rcm.getMoney());
                rmosCapacity2.setText("Capacity: " + rcm.getCapacity() + " / " + rcm.getMAX_CAPACITY());
                lastEmptied2.setText("Last Emptied: " + rcm.stats().getLastEmpty());
                break;
        }
    }

    class RMOSPanel extends JPanel {
        RMOS rmos;

        RMOSPanel(RMOS rmos) {
            this.rmos = rmos;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new TitledBorder(new LineBorder(Color.DARK_GRAY, 1), "RMOS", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 22)));


            rmosRCM = new JPanel();
            rmosRCM.setLayout(new GridLayout(1, 2));
            rmosRCM.setBorder(new EmptyBorder(0, 0, 50, 0));

            rmosMoney1 = new JLabel();
            rmosCapacity1 = new JLabel();
            lastEmptied1 = new JLabel();
            rmosMoney2 = new JLabel();
            rmosCapacity2 = new JLabel();
            lastEmptied2 = new JLabel();

            /* RCM 1 info */
            rmosRCM1Panel = new JPanel();
            rmosRCM1Panel.setLayout(new GridLayout(0, 1));
            rmosRCM1Label = new JLabel( "RCM " + rcm1.getId() + ":");
            rmosFillRCMText(rcm1, rcm1.getId());
            rmosRCM1Panel.add(rmosRCM1Label);
            rmosRCM1Panel.add(rmosMoney1);
            rmosRCM1Panel.add(rmosCapacity1);
            rmosRCM1Panel.add(lastEmptied1);

            /* RCM 2 info */
            rmosRCM2Panel = new JPanel();
            rmosRCM2Panel.setLayout(new GridLayout(0, 1));
            rmosRCM2Label = new JLabel("RCM " + rcm2.getId());
            rmosFillRCMText(rcm2, rcm2.getId());
            rmosRCM2Panel.add(rmosRCM2Label);
            rmosRCM2Panel.add(rmosMoney2);
            rmosRCM2Panel.add(rmosCapacity2);
            rmosRCM2Panel.add(lastEmptied2);

            restock1 = new JButton("Restock Money");
            restock1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rcm1.restockMoney();
                    rmosFillRCMText(rcm1, rcm1.getId());
                }
            });
            restock2 = new JButton("Restock Money");
            restock2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rcm2.restockMoney();
                    rmosFillRCMText(rcm2, rcm2.getId());
                }
            });
            empty1 = new JButton("Empty");
            empty1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rcm1.empty();
                    rmosFillRCMText(rcm1, rcm1.getId());
                    rcmGraphFrame.update();
                }
            });
            empty2 = new JButton("Empty");
            empty2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rcm2.empty();
                    rmosFillRCMText(rcm2, rcm2.getId());
                    rcmGraphFrame.update();
                }
            });
            rmosRCM1Panel.add(restock1);
            rmosRCM1Panel.add(empty1);
            rmosRCM2Panel.add(restock2);
            rmosRCM2Panel.add(empty2);


            rmosRCM.add(rmosRCM1Panel);
            rmosRCM.add(rmosRCM2Panel);
            add(rmosRCM);


            /* Edit Recyclables */
            JPanel editRecyclables = new JPanel();
            editRecyclables.setLayout(new BoxLayout(editRecyclables, BoxLayout.Y_AXIS));
            editRecyclables.setBorder(new BevelBorder(BevelBorder.RAISED, Color.GRAY, Color.GRAY.darker(), Color.GRAY, Color.GRAY.brighter()));

            addRecyclable = new JPanel();
            addRecyclable.setLayout(new BoxLayout(addRecyclable, BoxLayout.X_AXIS));
            JLabel addlabel = new JLabel("Add a Recyclable: ");
            JLabel ppplabel = new JLabel("Cents per pound: ");
            addField = new JTextField("", 10);
            addField.setMaximumSize(new Dimension(Integer.MAX_VALUE, addField.getPreferredSize().height) );
            priceField = new JTextField("", 5);
            priceField.setMaximumSize(new Dimension(Integer.MAX_VALUE, priceField.getPreferredSize().height) );
            addBtn = new JButton("Add");
            addBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!addField.getText().equals("") && !priceField.getText().equals("")) {
                        try {
                            rmos.addRecyclable(new Recyclable(addField.getText(), Integer.parseInt(priceField.getText())));
                            rcmPanel1.setRecyclables(rmos.getRecyclableList());
                            rcmPanel2.setRecyclables(rmos.getRecyclableList());
                            addField.setText("");
                            priceField.setText("");
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null, "Please enter a valid input");
                        }
                    }
                }
            });
            addRecyclable.add(addlabel);
            addRecyclable.add(addField);
            addRecyclable.add(ppplabel);
            addRecyclable.add(priceField);
            addRecyclable.add(addBtn);

            removeRecyclable = new JPanel();
            removeRecyclable.setLayout(new BoxLayout(removeRecyclable, BoxLayout.X_AXIS));
            JLabel removeLabel = new JLabel("Remove a recyclable: ");
            removeField = new JTextField();
            removeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, priceField.getPreferredSize().height) );
            removeBtn = new JButton("Remove");
            removeBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!removeField.getText().equals("")) {
                        rmos.deleteRecyclable(removeField.getText());
                        rcmPanel1.setRecyclables(rmos.getRecyclableList());
                        rcmPanel2.setRecyclables(rmos.getRecyclableList());
                        removeField.setText("");
                    }
                }
            });
            removeRecyclable.add(removeLabel);
            removeRecyclable.add(removeField);
            removeRecyclable.add(removeBtn);

            editRecyclables.add(addRecyclable);
            editRecyclables.add(removeRecyclable);

            JPanel changeRecyclable = new JPanel();
            changeRecyclable.setLayout(new BoxLayout(changeRecyclable, BoxLayout.X_AXIS));
            JLabel changeLabel = new JLabel("Change a recyclable price: ");
            JTextField changeField = new JTextField();
            changeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, priceField.getPreferredSize().height) );
            JLabel changePriceLabel = new JLabel("Desired price: ");
            JTextField changePriceField = new JTextField();
            changePriceField.setMaximumSize(new Dimension(Integer.MAX_VALUE, priceField.getPreferredSize().height) );
            JButton changeBtn = new JButton("Change");
            changeBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if (!changeField.getText().equals("") && !changePriceField.getText().equals(""))
                            rmos.setRecyclablePrice(changeField.getText(), Integer.parseInt(changePriceField.getText()));
                        rcmPanel1.setRecyclables(rmos.getRecyclableList());
                        rcmPanel2.setRecyclables(rmos.getRecyclableList());
                        changeField.setText("");
                        changePriceField.setText("");
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid input");
                    }
                }
            });
            changeRecyclable.add(changeLabel);
            changeRecyclable.add(changeField);
            changeRecyclable.add(changePriceLabel);
            changeRecyclable.add(changePriceField);
            changeRecyclable.add(changeBtn);

            editRecyclables.add(changeRecyclable);

            add(editRecyclables);

            mostUsed = new JLabel();
            mostUsed();
            mostUsed.setBorder(new EmptyBorder(25, 0, 25, 0));
            add(mostUsed);


            rcmGraphFrame = new RCMGraphFrame(rmos.getRCMList());
            add(rcmGraphFrame);

            setVisible(true);
        }
    }

    class RCMPanel extends JPanel implements ActionListener {
        RCM rcm;

        JPanel buttonPanel, buttons;
        CardLayout cardLayout;
        ArrayList<JButton> buttonList;
        JLabel typeLabel, weightLabel, moneyLabel, totalLabel, receiptLabel;

        RCMPanel(RCM rcm) {
            this.rcm = rcm;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


            JLabel idLabel = new JLabel("RCM " + rcm.getId());
            idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            idLabel.setFont(new Font("Arial", Font.BOLD, 22));
            JLabel locationLabel = new JLabel("Location: " + rcm.getLocation());
            locationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(idLabel);
            add(locationLabel);

            buttonPanel = new JPanel();
            cardLayout = new CardLayout();
            buttonPanel.setLayout(cardLayout);
            buttonPanel.setBorder(new EmptyBorder(25, 0, 50, 0));
            buttonList = new ArrayList<JButton>();
            setButtonPanel();
            add(buttonPanel);

            JButton checkout = new JButton("Checkout");
            checkout.setAlignmentX(Component.CENTER_ALIGNMENT);
            checkout.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    checkout(false);
                }
            });
            add(checkout);

            JPanel receiptPanel = new JPanel();
            receiptPanel.setLayout(new GridLayout(0, 1));
            typeLabel = new JLabel(" ");
            weightLabel = new JLabel(" ");
            moneyLabel = new JLabel(" ");
            totalLabel = new JLabel(" ");
            receiptLabel = new JLabel(" ");
            receiptPanel.add(typeLabel);
            receiptPanel.add(weightLabel);
            receiptPanel.add(moneyLabel);
            receiptPanel.add(totalLabel);
            receiptPanel.add(receiptLabel);
            add(receiptPanel);
        }

        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            if (rcm.recycleItem(rcm.getRecyclable(action)))
                update();
            else
                checkout(true);
        }

        public void setRecyclables(ArrayList<Recyclable> recyclables) {
            rcm.setRecyclableList(recyclables);
            setButtonPanel();
        }

        private void update() {
            receiptLabel.setText("");
            rmosFillRCMText(rcm, rcm.getId());
            typeLabel.setText("Type: " + rcm.getLastType());
            weightLabel.setText("Weight: " + rcm.getLastWeight() + " lbs");
            moneyLabel.setText("Money: " + rcm.getLastPrice());
            totalLabel.setText("Total: " + rcm.getTotalOwed());
            rcmGraphFrame.update();
            mostUsed();
        }

        private void setButtonPanel() {
            buttons = new JPanel();
            buttons.setLayout(new GridLayout(0, 1));
            for (Recyclable recyclable: rcm.getRecyclableList()) {
                buttonList.clear();
                buttonList.add(new JButton(recyclable.getType() + " - " + (int)recyclable.getPricePerPound() + " cents per pound"));
                buttonList.get(buttonList.size() - 1).setActionCommand(recyclable.getType());
                buttonList.get(buttonList.size() - 1).addActionListener(this);
                buttons.add(buttonList.get(buttonList.size()-1));
            }
            buttonPanel.add(buttons, "1");
            cardLayout.show(buttonPanel, "1");
        }

        private void checkout(boolean capacity) {
            typeLabel.setText("");
            weightLabel.setText("");
            moneyLabel.setText("");
            totalLabel.setText("");
            if (rcm.getMoney().sufficientFunds(rcm.getTotalOwed()))
                receiptLabel.setText("MONEY ISSUED: " + rcm.getTotalOwed());
            else
                receiptLabel.setText("RECEIPT ISSUED: " + rcm.getTotalOwed());
            rcm.pay();
            if (capacity)
                JOptionPane.showMessageDialog(null, "Sorry, the RCM is full");
            //TODO:
            rmosFillRCMText(rcm, rcm.getId());
        }
    }
}