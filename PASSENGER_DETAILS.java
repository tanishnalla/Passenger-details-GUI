import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.time.*;
import java.time.format.*;

class PASSENGER_DETAILS extends JFrame implements ActionListener
{
JLabel name,dob,gender,bp;
JTextField t1,t2;
JButton b1,b2;
JRadioButton rb1,rb2;
JComboBox cb;
JTextArea ta1;
ArrayList <String> passengers;
PASSENGER_DETAILS()
{
setSize(600,600);
setLayout(new FlowLayout());
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setTitle("Train Ticket Booking System");

name = new JLabel("Enter Name");
add(name);

t1= new JTextField(20);
add(t1);

dob = new JLabel("Enter DOB (dd/mm/yyyy)");
add(dob);
t2 = new JTextField(10);
add(t2);

gender = new JLabel("Select Gender");
add(gender);

rb1 = new JRadioButton("Male");
rb2 = new JRadioButton("Female");
add(rb1);
add(rb2);
ButtonGroup gendergroup = new ButtonGroup();
 gendergroup.add(rb1);
 gendergroup.add(rb2);
 
bp = new JLabel("Select Breath Preference");
add(bp);

String [] x ={"Select Any","Upper","Middle","Lower"};
cb = new JComboBox(x);
add(cb);

b2 = new JButton("Add Passanger");
add(b2);

b1 = new JButton("Submit");
add(b1);

ta1 = new JTextArea(30,30);
add(ta1);
ta1.setEditable(false);

passengers = new ArrayList<>();

b1.addActionListener(this);
b2.addActionListener(this);
setVisible(true);
}

private int calculateAge(String dobStr) throws Exception
{
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
LocalDate dob;
try
{
dob=LocalDate.parse(dobStr,formatter);
}
catch(DateTimeParseException e)
{
throw new Exception("Invalid DOB format.Use dd/MM/yyyy");
}
LocalDate today = LocalDate.now();
if(dob.isAfter(today))
{
throw new Exception("DOB cannot be in the future");
}
return Period.between(dob,today).getYears();
}

public void actionPerformed(ActionEvent e)
{
try
{
if(e.getSource() == b2)
{
String name = t1.getText().trim();
if(name.isEmpty())
throw new Exception("Enter name");

String dobStr = t2.getText().trim();
if(dobStr.isEmpty())
throw new Exception("Enter dob");

int a =calculateAge(dobStr); 
if(a<0)
throw new InvalidAgeException("Enter valid age");
String gender="";
if(rb1.isSelected())
gender="Male";
else if(rb2.isSelected())
gender="Female";
else
throw new Exception("Select Gender");

String breth=" ";
int index = cb.getSelectedIndex();
if(index==0)
throw new Exception("Select Breth Preference");
else if(index==1)
breth="Upper";
else if(index==2)
breth="Middle";
else if(index==3)
breth="Lower";
 
String details ="Name: "+name+", Age: "+a+", Gender: "+ gender+", Berth: "+breth;
if(a>60)
details+="(Senior Citizen)";
passengers.add(details);

t1.setText("");
t2.setText("");
rb1.setSelected(false);
rb2.setSelected(false);
cb.setSelectedIndex(0);

ta1.setText("Passenger added!\n");
ta1.append("Current Passenger Count: "+passengers.size());
}
else if(e.getSource()==b1)
{
if(passengers.isEmpty())
ta1.setText("No passengers added yet!");
else
{
ta1.setText("****Ticket Booking Details**** \n");
int count=1;
for(String p:passengers){
ta1.append("Passenger "+count+":"+p+"\n");
count++;
}
}
}
}
catch(Exception ex)
{
ta1.setText("Error: "+ex.getMessage());
}
}

public static void main(String [] args)
{
new PASSENGER_DETAILS();
}
}