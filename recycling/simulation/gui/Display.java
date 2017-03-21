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

public class Display extends JFrame implements ActionListener {
    private RMOS rmos;
    private RCM rcm1, rcm2;
    private JPanel rcm1Panel, rcm2Panel, rcm1ButtonPanel, rcm1Buttons, rcm2ButtonPanel, rcm2Buttons, rmosPanel, rmosRCM, rmosRCM1Panel, rmosRCM2Panel, addRecyclable, removeRecyclable;
    private CardLayout cardLayout1, cardLayout2;
    private ArrayList<JButton> buttonList1, buttonList2;
    private JButton checkout1, checkout2, restock1, restock2, empty1, empty2, addBtn, removeBtn;
    private JTextField addField, priceField, removeField;
    private JLabel mostUsed;
    private JLabel type1Label, type2Label, weight1Label, weight2Label, money1Label, money2Label, total1Label, total2Label, receipt1Label, receipt2Label;
    private JLabel rmosRCM1Label, rmosRCM2Label, rmosMoney1, rmosMoney2, rmosCapacity1, rmosCapacity2, lastEmptied1, lastEmptied2;
    RCMGraphFrame rcmGraphFrame;

    public Display() {
        /* Setting up window */
        super("RMOS Controller");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        setLocationRelativeTo(null);
        setSize(1400, 800);

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


        /* RCM 1 Panel */
        RCMPanel rcmPanel1 = new RCMPanel(rcm1);
        rcmPanel.add(rcmPanel1);

        rcm1Panel = new JPanel();
        rcm1Panel.setLayout(new BoxLayout(rcm1Panel, BoxLayout.Y_AXIS));
        JLabel idLabel1 = new JLabel("RCM " + rcm1.getId());
        idLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        idLabel1.setFont(new Font("Arial", Font.BOLD, 22));
        JLabel locationLabel1 = new JLabel("Location: " + rcm1.getLocation());
        locationLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        rcm1Panel.add(idLabel1);
        rcm1Panel.add(locationLabel1);

        rcm1ButtonPanel = new JPanel();
        cardLayout1 = new CardLayout();
        rcm1ButtonPanel.setLayout(cardLayout1);
        rcm1ButtonPanel.setBorder(new EmptyBorder(25, 0, 50, 0));
        buttonList1 = new ArrayList<JButton>();
        setButtonPanel(1);
        rcm1Panel.add(rcm1ButtonPanel);

        checkout1 = new JButton("Checkout");
        checkout1.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkout1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkout(1,false);
            }
        });
        rcm1Panel.add(checkout1);

        JPanel receipt1Panel = new JPanel();
        receipt1Panel.setLayout(new GridLayout(0, 1));
        type1Label = new JLabel(" ");
        weight1Label = new JLabel(" ");
        money1Label = new JLabel(" ");
        total1Label = new JLabel(" ");
        receipt1Label = new JLabel(" ");
        receipt1Panel.add(type1Label);
        receipt1Panel.add(weight1Label);
        receipt1Panel.add(money1Label);
        receipt1Panel.add(total1Label);
        receipt1Panel.add(receipt1Label);
        rcm1Panel.add(receipt1Panel);

        rcmPanel.add(rcm1Panel);

        /* RCM 2 Panel */
        rcm2Panel = new JPanel();
        rcm2Panel.setLayout(new BoxLayout(rcm2Panel, BoxLayout.Y_AXIS));
        JLabel idLabel2 = new JLabel("RCM " + rcm2.getId());
        idLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        idLabel2.setFont(new Font("Arial", Font.BOLD, 22));
        JLabel locationLabel2 = new JLabel("Location: " + rcm2.getLocation());
        locationLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        rcm2Panel.add(idLabel2);
        rcm2Panel.add(locationLabel2);

        rcm2ButtonPanel = new JPanel();
        cardLayout2 = new CardLayout();
        rcm2ButtonPanel.setLayout(cardLayout2);
        rcm2ButtonPanel.setBorder(new EmptyBorder(25, 0, 50, 0));
        buttonList2 = new ArrayList<JButton>();
        setButtonPanel(2);
        rcm2Panel.add(rcm2ButtonPanel);

        checkout2 = new JButton("Checkout");
        checkout2.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkout2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkout(2,false);
            }
        });
        rcm2Panel.add(checkout2);

        JPanel receipt2Panel = new JPanel();
        receipt2Panel.setLayout(new GridLayout(0, 1));
        type2Label = new JLabel(" ");
        weight2Label = new JLabel(" ");
        money2Label = new JLabel(" ");
        total2Label = new JLabel(" ");
        receipt2Label = new JLabel(" ");
        receipt2Panel.add(type2Label);
        receipt2Panel.add(weight2Label);
        receipt2Panel.add(money2Label);
        receipt2Panel.add(total2Label);
        receipt2Panel.add(receipt2Label);
        rcm2Panel.add(receipt2Panel);

        rcmPanel.add(rcm2Panel);

        container.add(rcmPanel);

        /* ----RMOS PANEL---- */
        rmosPanel = new JPanel();
        rmosPanel.setLayout(new BoxLayout(rmosPanel, BoxLayout.Y_AXIS));
        rmosPanel.setBorder(new TitledBorder(new LineBorder(Color.DARK_GRAY, 1), "RMOS", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 22)));


        rmosRCM = new JPanel();
        rmosRCM.setLayout(new GridLayout(1, 2));
        rmosRCM.setBorder(new EmptyBorder(0, 0, 50, 0));

        /* RCM 1 info */
        rmosRCM1Panel = new JPanel();
        rmosRCM1Panel.setLayout(new GridLayout(0, 1));
        rmosRCM1Label = new JLabel( "RCM " + rcm1.getId() + ":");
        rmosMoney1 = new JLabel();
        rmosCapacity1 = new JLabel();
        lastEmptied1 = new JLabel();
        rmosFillRCMText(1);
        rmosRCM1Panel.add(rmosRCM1Label);
        rmosRCM1Panel.add(rmosMoney1);
        rmosRCM1Panel.add(rmosCapacity1);
        rmosRCM1Panel.add(lastEmptied1);

        /* RCM 2 info */
        rmosRCM2Panel = new JPanel();
        rmosRCM2Panel.setLayout(new GridLayout(0, 1));
        rmosRCM2Label = new JLabel("RCM " + rcm2.getId());
        rmosMoney2 = new JLabel();
        rmosCapacity2 = new JLabel();
        lastEmptied2 = new JLabel();
        rmosFillRCMText(2);
        rmosRCM2Panel.add(rmosRCM2Label);
        rmosRCM2Panel.add(rmosMoney2);
        rmosRCM2Panel.add(rmosCapacity2);
        rmosRCM2Panel.add(lastEmptied2);

        restock1 = new JButton("Restock Money");
        restock1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rcm1.restockMoney();
                rmosFillRCMText(1);
            }
        });
        restock2 = new JButton("Restock Money");
        restock2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rcm2.restockMoney();
                rmosFillRCMText(2);
            }
        });
        empty1 = new JButton("Empty");
        empty1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rcm1.empty();
                rmosFillRCMText(1);
                rcmGraphFrame.update();
            }
        });
        empty2 = new JButton("Empty");
        empty2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rcm2.empty();
                rmosFillRCMText(2);
                rcmGraphFrame.update();
            }
        });
        rmosRCM1Panel.add(restock1);
        rmosRCM1Panel.add(empty1);
        rmosRCM2Panel.add(restock2);
        rmosRCM2Panel.add(empty2);


        rmosRCM.add(rmosRCM1Panel);
        rmosRCM.add(rmosRCM2Panel);
        rmosPanel.add(rmosRCM);


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
                        setButtonPanel(1);
                        setButtonPanel(2);
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
                    setButtonPanel(1);
                    setButtonPanel(2);
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
                    setButtonPanel(1);
                    setButtonPanel(2);
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

        rmosPanel.add(editRecyclables);

        mostUsed = new JLabel();
        mostUsed();
        mostUsed.setBorder(new EmptyBorder(25, 0, 25, 0));
        rmosPanel.add(mostUsed);


        rcmGraphFrame = new RCMGraphFrame(rmos.getRCMList());
        rmosPanel.add(rcmGraphFrame);

        container.add(rmosPanel);

        setVisible(true);
    }

    private void setButtonPanel (int id) {
        switch (id) {
            case 1:
                rcm1Buttons = new JPanel();
                rcm1Buttons.setLayout(new GridLayout(0, 1));
                for (Recyclable recyclable: rcm1.getRecyclableList()) {
                    buttonList1.clear();
                    buttonList1.add(new JButton(recyclable.getType() + " - " + (int)recyclable.getPricePerPound() + " cents per pound"));
                    buttonList1.get(buttonList1.size() - 1).setActionCommand("1-"+recyclable.getType());
                    buttonList1.get(buttonList1.size() - 1).addActionListener(this);
                    rcm1Buttons.add(buttonList1.get(buttonList1.size()-1));
                }
                rcm1ButtonPanel.add(rcm1Buttons, "1");
                cardLayout1.show(rcm1ButtonPanel, "1");
                break;
            case 2:
                rcm2Buttons = new JPanel();
                rcm2Buttons.setLayout(new GridLayout(0, 1));
                for (Recyclable recyclable: rcm2.getRecyclableList()) {
                    buttonList2.clear();
                    buttonList2.add(new JButton(recyclable.getType() + " - " + (int)recyclable.getPricePerPound() + " cents per pound"));
                    buttonList2.get(buttonList2.size() - 1).setActionCommand("2-"+recyclable.getType());
                    buttonList2.get(buttonList2.size() - 1).addActionListener(this);
                    rcm2Buttons.add(buttonList2.get(buttonList2.size()-1));
                }
                rcm2ButtonPanel.add(rcm2Buttons, "1");
                cardLayout2.show(rcm2ButtonPanel, "1");
                break;
        }
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        String[] split = action.split("-");

        switch (Integer.parseInt(split[0])) {
            case 1:
                if(rcm1.recycleItem(rcm1.getRecyclable(split[1]))) {
                    //Item is recycled.
                    //update(1);
                } else {
                    checkout(1, true);
                }
                break;
            case 2:
                if(rcm2.recycleItem(rcm2.getRecyclable(split[1]))) {
                    //Item is recycled.
                    //update(2);
                } else {
                    checkout(2, true);
                }
                break;
        }
    }

    private void checkout(int id, boolean capacity) {
        switch (id) {
            case 1:
                if (rcm1.getMoney().sufficientFunds(rcm1.getTotalOwed()))
                    receipt1Label.setText("MONEY ISSUED: " + rcm1.getTotalOwed());
                else
                    receipt1Label.setText("RECEIPT ISSUED: " + rcm1.getTotalOwed());
                resetLabel(1);
                rcm1.pay();
                if (capacity)
                    JOptionPane.showMessageDialog(null, "Sorry, the RCM is full");
                rmosFillRCMText(1);
                break;
            case 2:
                if (rcm2.getMoney().sufficientFunds(rcm2.getTotalOwed()))
                    receipt2Label.setText("MONEY ISSUED: " + rcm2.getTotalOwed());
                else
                    receipt2Label.setText("RECEIPT ISSUED: " + rcm2.getTotalOwed());
                resetLabel(2);
                rcm2.pay();
                receipt2Label.setBackground(Color.green);
                if (capacity)
                    JOptionPane.showMessageDialog(null, "Sorry, the RCM is full");
                rmosFillRCMText(2);
                break;
        }
    }

    private void resetLabel(int id) {
        switch(id) {
            case 1:
                type1Label.setText("");
                weight1Label.setText("");
                money1Label.setText("");
                total1Label.setText("");
                break;
            case 2:
                type2Label.setText("");
                weight2Label.setText("");
                money2Label.setText("");
                total2Label.setText("");
                break;
        }
    }

    private void rmosFillRCMText(int id) {
        switch (id) {
            case 1:
                rmosMoney1.setText("Money: " + rcm1.getMoney());
                rmosCapacity1.setText("Capacity: " + rcm1.getCapacity() + " / " + rcm1.getMAX_CAPACITY());
                lastEmptied1.setText("Last Emptied: " + rcm1.stats().getLastEmpty());
                break;
            case 2:
                rmosMoney2.setText("Money: " + rcm2.getMoney());
                rmosCapacity2.setText("Capacity: " + rcm2.getCapacity() + " / " + rcm2.getMAX_CAPACITY());
                lastEmptied2.setText("Last Emptied: " + rcm2.stats().getLastEmpty());
                break;
        }
    }

    private void mostUsed() {
        mostUsed.setFont(new Font("Arial", Font.BOLD, 18));
        if (rcm1.stats().getNumItems() >= rcm2.stats().getNumItems())
            mostUsed.setText("\n\n\nMost Used: RCM " + rcm1.getId() + ", location: " + rcm1.getLocation());
        else
            mostUsed.setText("\n\n\nMost Used: RCM " + rcm2.getId() + ", location: " + rcm2.getLocation());
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
        }

        private void update() {
            receiptLabel.setText("");
            rmosFillRCMText(1);
            typeLabel.setText("Type: " + rcm1.getLastType());
            weightLabel.setText("Weight: " + rcm1.getLastWeight() + " lbs");
            moneyLabel.setText("Money: " + rcm1.getLastPrice());
            totalLabel.setText("Total: " + rcm1.getTotalOwed());
            rcmGraphFrame.update();
            mostUsed();
        }

        private void setButtonPanel() {
            buttons = new JPanel();
            buttons.setLayout(new GridLayout(0, 1));
            for (Recyclable recyclable: rcm.getRecyclableList()) {
                buttonList.clear();
                buttonList.add(new JButton(recyclable.getType() + " - " + (int)recyclable.getPricePerPound() + " cents per pound"));
                buttonList.get(buttonList.size() - 1).setActionCommand("1-"+recyclable.getType());
                buttonList.get(buttonList.size() - 1).addActionListener(this);
                buttons.add(buttonList.get(buttonList.size()-1));
            }
            buttonPanel.add(buttons, "1");
            cardLayout.show(buttonPanel, "1");
        }

        private void checkout(boolean capacity) {
            if (rcm.getMoney().sufficientFunds(rcm.getTotalOwed()))
                receiptLabel.setText("MONEY ISSUED: " + rcm1.getTotalOwed());
            else
                receipt1Label.setText("RECEIPT ISSUED: " + rcm1.getTotalOwed());
            resetLabel(1);
            rcm1.pay();
            if (capacity)
                JOptionPane.showMessageDialog(null, "Sorry, the RCM is full");
            rmosFillRCMText(1);
        }
    }

    public static void main(String args[]) {
        new Display();
    }
}