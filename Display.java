import javax.swing.*;
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
    private JTextArea receipt1, receipt2, data1, data2, rmosRCM1, rmosRCM2;
    private JTextField addField, priceField, removeField;
    private JLabel mostUsed;
    RCMFrame rcmFrame;

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
        rcm1Panel = new JPanel();
        rcm1Panel.setLayout(new BoxLayout(rcm1Panel, BoxLayout.Y_AXIS));
        JLabel idLabel1 = new JLabel("RCM - " + rcm1.getId());
        JLabel locationLabel1 = new JLabel("Location: " + rcm1.getLocation());
        rcm1Panel.add(idLabel1);
        rcm1Panel.add(locationLabel1);

        rcm1ButtonPanel = new JPanel();
        cardLayout1 = new CardLayout();
        rcm1ButtonPanel.setLayout(cardLayout1);
        buttonList1 = new ArrayList<JButton>();
        setButtonPanel(1);
        rcm1Panel.add(rcm1ButtonPanel);

        checkout1 = new JButton("Checkout");
        checkout1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkout(1,false);
            }
        });
        rcm1Panel.add(checkout1);

        data1 = new JTextArea();
        rcm1Panel.add(data1);
        receipt1 = new JTextArea();
        rcm1Panel.add(receipt1);

        rcmPanel.add(rcm1Panel);

        /* RCM 2 Panel */
        rcm2Panel = new JPanel();
        rcm2Panel.setLayout(new BoxLayout(rcm2Panel, BoxLayout.Y_AXIS));
        JLabel idLabel2 = new JLabel("RCM - " + rcm2.getId());
        JLabel locationLabel2 = new JLabel("Location: " + rcm2.getLocation());
        rcm2Panel.add(idLabel2);
        rcm2Panel.add(locationLabel2);

        rcm2ButtonPanel = new JPanel();
        cardLayout2 = new CardLayout();
        rcm2ButtonPanel.setLayout(cardLayout2);
        buttonList2 = new ArrayList<JButton>();
        setButtonPanel(2);
        rcm2Panel.add(rcm2ButtonPanel);

        checkout2 = new JButton("Checkout");
        checkout2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkout(2,false);
            }
        });
        rcm2Panel.add(checkout2);

        data2 = new JTextArea();
        rcm2Panel.add(data2);
        receipt2 = new JTextArea();
        rcm2Panel.add(receipt2);

        rcmPanel.add(rcm2Panel);

        container.add(rcmPanel);

        /* RMOS PANEL */
        rmosPanel = new JPanel();
        rmosPanel.setLayout(new BoxLayout(rmosPanel, BoxLayout.Y_AXIS));
        rmosRCM = new JPanel();
        rmosRCM.setLayout(new BoxLayout(rmosRCM, BoxLayout.X_AXIS));

        rmosRCM1Panel = new JPanel();
        rmosRCM1Panel.setLayout(new BoxLayout(rmosRCM1Panel, BoxLayout.Y_AXIS));
        rmosRCM2Panel = new JPanel();
        rmosRCM2Panel.setLayout(new BoxLayout(rmosRCM2Panel, BoxLayout.Y_AXIS));
        rmosRCM1 = new JTextArea();
        rmosRCM2 = new JTextArea();
        rmosFillRCMText(1);
        rmosFillRCMText(2);
        rmosRCM1Panel.add(rmosRCM1);
        rmosRCM2Panel.add(rmosRCM2);

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
                rcmFrame.update();
            }
        });
        empty2 = new JButton("Empty");
        empty2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rcm2.empty();
                rmosFillRCMText(2);
                rcmFrame.update();
            }
        });
        rmosRCM1Panel.add(restock1);
        rmosRCM1Panel.add(empty1);
        rmosRCM2Panel.add(restock2);
        rmosRCM2Panel.add(empty2);


        rmosRCM.add(rmosRCM1Panel);
        rmosRCM.add(rmosRCM2Panel);
        rmosPanel.add(rmosRCM);


        /* Adding editables */
        JPanel editRecyclables = new JPanel();
        editRecyclables.setLayout(new BoxLayout(editRecyclables, BoxLayout.Y_AXIS));

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
                    rmos.addRecyclable(new Recyclable(addField.getText(), Integer.parseInt(priceField.getText())));
                    setButtonPanel(1);
                    setButtonPanel(2);
                    addField.setText("");
                    priceField.setText("");
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
                if (!changeField.getText().equals("") && !changePriceField.getText().equals(""))
                    rmos.setRecyclablePrice(changeField.getText(), Integer.parseInt(changePriceField.getText()));
                    setButtonPanel(1);
                    setButtonPanel(2);
                    changeField.setText("");
                    changePriceField.setText("");
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
        rmosPanel.add(mostUsed);


        rcmFrame = new RCMFrame(rmos.getRCMList());
        rmosPanel.add(rcmFrame);

        container.add(rmosPanel);

        setVisible(true);
    }

    private void setButtonPanel (int id) {
        switch (id) {
            case 1:
                rcm1Buttons = new JPanel();
                rcm1Buttons.setLayout(new BoxLayout(rcm1Buttons, BoxLayout.Y_AXIS));
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
                rcm2Buttons.setLayout(new BoxLayout(rcm2Buttons, BoxLayout.Y_AXIS));
                for (Recyclable recyclable: rcm2.getRecyclableList()) {
                    buttonList2.clear();
                    buttonList2.add(new JButton(recyclable.getType() + " - " + (int) recyclable.getPricePerPound() + " cents per pound"));
                    buttonList2.get(buttonList2.size() - 1).setActionCommand("2-" + recyclable.getType());
                    buttonList2.get(buttonList2.size() - 1).addActionListener(this);
                    rcm2Buttons.add(buttonList2.get(buttonList2.size() - 1));
                }
                rcm2ButtonPanel.add(rcm2Buttons, "2");
                cardLayout2.show(rcm2ButtonPanel, "2");
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
                    update(1);
                } else {
                    checkout(1, true);
                }
                break;
            case 2:
                if(rcm2.recycleItem(rcm2.getRecyclable(split[1]))) {
                    //Item is recycled.
                    update(2);
                } else {
                    checkout(2, true);
                }
                break;
        }
    }

    private void update(int id) {
        switch (id) {
            case 1:
                receipt1.setText("");
                rmosFillRCMText(1);
                data1.setText("");
                data1.append("Type: " + rcm1.getLastType() + "\n");
                data1.append("Weight: " + rcm1.getLastWeight() + " lbs\n");
                data1.append("Money: " + rcm1.getLastPrice() + "\n");
                data1.append("Total: " + rcm1.getTotalOwed());
                break;
            case 2:
                receipt2.setText("");
                rmosFillRCMText(2);
                data2.setText("");
                data2.append("Type: " + rcm2.getLastType() + "\n");
                data2.append("Weight: " + rcm2.getLastWeight() + " lbs\n");
                data2.append("Money: " + rcm2.getLastPrice() + "\n");
                data2.append("Total: " + rcm2.getTotalOwed());
                break;
        }
        rcmFrame.update();
    }

    private void checkout(int id, boolean capacity) {
        switch (id) {
            case 1:
                if (rcm1.getMoney().sufficientFunds(rcm1.getTotalOwed()))
                    receipt1.setText("MONEY ISSUED: " + rcm1.getTotalOwed());
                else
                    receipt1.setText("RECEIPT ISSUED: " + rcm1.getTotalOwed());
                data1.setText("");
                rcm1.pay();
                if (capacity)
                    receipt1.append("\nCAPACITY IS FULL");
                rmosFillRCMText(1);
                break;
            case 2:
                if (rcm2.getMoney().sufficientFunds(rcm2.getTotalOwed()))
                    receipt2.setText("MONEY ISSUED: " + rcm2.getTotalOwed());
                else
                    receipt2.setText("RECEIPT ISSUED: " + rcm2.getTotalOwed());
                data2.setText("");
                rcm2.pay();
                if (capacity)
                    receipt2.append("\nCAPACITY IS FULL");
                rmosFillRCMText(2);
                break;
        }
    }

    private void rmosFillRCMText(int id) {
        switch (id) {
            case 1:
                rmosRCM1.setText("");
                rmosRCM1.append("RCM " + rcm1.getId() + ":" + "\n");
                rmosRCM1.append("Money: " + rcm1.getMoney() + "\n");
                rmosRCM1.append("Capacity: " + rcm1.getCapacity() + " / " + rcm1.getMAX_CAPACITY() + "\n");
                rmosRCM1.append("Last Emptied: " + rcm1.getLastEmpty() + "\n");
                break;
            case 2:
                rmosRCM2.setText("");
                rmosRCM2.append("RCM " + rcm2.getId() + ":" + "\n");
                rmosRCM2.append("Money: " + rcm2.getMoney() + "\n");
                rmosRCM2.append("Capacity: " + rcm2.getCapacity() + " / " + rcm2.getMAX_CAPACITY() + "\n");
                rmosRCM2.append("Last Emptied: " + rcm2.getLastEmpty() + "\n");
                break;
        }
    }

    private void mostUsed() {
        mostUsed.setFont(new Font("Arial", Font.BOLD, 18));
        if (rcm1.getNumItems() >= rcm2.getNumItems())
            mostUsed.setText("\n\n\nMost Used: RCM " + rcm1.getId() + ", location: " + rcm1.getLocation());
        else
            mostUsed.setText("\n\n\nMost Used: RCM " + rcm2.getId() + ", location: " + rcm2.getLocation());
    }

    public static void main(String args[]) {
        new Display();
    }
}
