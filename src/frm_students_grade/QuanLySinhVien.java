/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frm_students_grade;
import Login_main.Login;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import entities.*;
import model.*;


/**
 *
 * @author Quang
 */
public class QuanLySinhVien extends javax.swing.JFrame {
       //1. khai báo biến toàn cục
   ArrayList<Students> list = new ArrayList<>();
   int current = 0; // vị trí  hiện hành
 
    public QuanLySinhVien() {
        initComponents();
        // gọi hàm load_data khi chạy form
        load_data();
    }

    //2. hàm load_data() ; load dữ liệu từ bảng Students lên
    public void load_data()
    {
        try 
        {
            // gọi hàm Myconnect để kết nối
            Connection cn = new MyConnect().getcn();
          //  PreparedStatement ps = cn.prepareStatement("exec  sp_STUDENTS_get");
            PreparedStatement ps = cn.prepareStatement("select * from STUDENTS");
            ResultSet rs = ps.executeQuery();
            list.clear();// xoá list để lấy dữ liệu từ bảng
            while(rs.next())
            {
                // tạo đối tượng students
                Students stu = new Students(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getString(6), rs.getString(7));
                // thêm vào ArrayLisst
                list.add(stu);
            }
            cn.close();
            ps.close();
            
            ////////// load dữ liêif từ list lên table
            // lấy mô hình dữ liệu và xoá sạch các hàng
            DefaultTableModel model = (DefaultTableModel) tbl_Sinhvien.getModel();
        model.setRowCount(0);
        for(Students sv: list){
            Object[] row = new Object[]{sv.getMaSV(),sv.getHoTen(),sv.getEmail(),sv.getSoDT(),sv.isGioiTinh(),sv.getDiaChi(),sv.getHinh()};
            model.addRow(row);
        }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    // hiển thị thông tin sinh viên lên form
    public void show_Detail(){
        // lấy ra sv hiện hành
        Students stu = list.get(current);
        // gán thông tin sv lên form
        txt_maSV.setText(stu.getMaSV());
        txt_hoTen.setText(stu.getHoTen());
        txt_email.setText(stu.getEmail());
        txt_phone.setText(stu.getSoDT());
        if(stu.isGioiTinh() == true){
            rdoNam.setSelected(true);
        }else {
             rdoNu.setSelected(true);
        }
        txtA_diachi.setText(stu.getDiaChi());
        ImageIcon image = new ImageIcon(getClass().getResource(stu.getHinh()));
        ImageIcon resizedImage = resize(image, 100, 150);
        lblHinh.setIcon(resizedImage);
    }
    
     public static ImageIcon resize(ImageIcon imageIcon, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(imageIcon.getImage(), 0, 0, width, height, null);
        g2d.dispose();
        return new ImageIcon(bi);
    }
       public void insert() {
        try {
            //1. Tạo 1 đối tượng sinh viên
            Students stu = new Students();
            //2. gán giá trị cho đối tượng sinh viên stu
            stu.setMaSV(txt_maSV.getText());
            stu.setHoTen(txt_hoTen.getText());
            stu.setEmail(txt_email.getText());
            stu.setSoDT(txt_phone.getText());
            if (rdoNam.isSelected() == true) {
                stu.setGioiTinh(true);
            } else {
                stu.setGioiTinh(false);
            }
            stu.setDiaChi(txtA_diachi.getText());
            stu.setHinh("ngoctrinh1.jpg");

            StudentModel m = new StudentModel();
            //3. Thêm đối tượng sinh viên stu bằng cách gọi hàm insertStudent trong StudentModel
            int kq = m.insertStudent(stu);

            if (kq == 1) {
                JOptionPane.showMessageDialog(this, "Thêm thành công.");
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại.");
            }

            //4. nhớ gọi lại load_data() để load dữ liệu mới lên jtable
            this.load_data();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm Không Thành Công.");
            e.printStackTrace();
        }
    }
       public void delete() {

        try {

            //1. Tạo đối tượng StudentModel và gọi hàm xóa
            StudentModel m = new StudentModel();
            int kq = m.deleteStudent(txt_maSV.getText());

            if (kq == 1) {
                JOptionPane.showMessageDialog(this, "Xóa thành công.");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại.");
            }

            //4. nhớ gọi lại load_data() để load dữ liệu mới lên jtable
            this.load_data();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Xóa Không Thành Công.");
            e.printStackTrace();
        }
    }
       public void update() {

        try {
            //1. Tạo 1 đối tượng sinh viên
            Students stu = new Students();
            //2. gán giá trị cho đối tượng sinh viên stu
            stu.setMaSV(txt_maSV.getText());
            stu.setHoTen(txt_hoTen.getText());
            stu.setEmail(txt_email.getText());
            stu.setSoDT(txt_phone.getText());
            if (rdoNam.isSelected() == true) {
                stu.setGioiTinh(true);
            } else {
                stu.setGioiTinh(false);
            }
            stu.setDiaChi(txtA_diachi.getText());
            stu.setHinh("ngoctrinh1.jpg");

            StudentModel m = new StudentModel();
            //3. gọi hàm updateStudent
            int kq = m.updateStudent(stu);

            if (kq == 1) {
                JOptionPane.showMessageDialog(this, "Sửa thành công.");
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại.");
            }

            //4. nhớ gọi lại load_data() để load dữ liệu mới lên jtable
            this.load_data();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sửa Không Thành Công.");
            e.printStackTrace();
        }

    }
            
            
            
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_maSV = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_hoTen = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_phone = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtA_diachi = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        lblHinh = new javax.swing.JLabel();
        btn_new = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_Sinhvien = new javax.swing.JTable();
        btn_First = new javax.swing.JButton();
        btn_previous = new javax.swing.JButton();
        btn_next = new javax.swing.JButton();
        btn_last = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("Quản Lý Sinh Viên");

        jLabel2.setText("Mã SV:");

        jLabel3.setText("Họ tên:");

        jLabel4.setText("Email:");

        jLabel5.setText("Số điện thoại:");

        jLabel6.setText("Giới tính:");

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        jLabel7.setText("Địa chỉ:");

        txtA_diachi.setColumns(20);
        txtA_diachi.setRows(5);
        jScrollPane1.setViewportView(txtA_diachi);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHinh, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHinh, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_new.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/new.png"))); // NOI18N
        btn_new.setText("New");
        btn_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newActionPerformed(evt);
            }
        });

        btn_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/save.png"))); // NOI18N
        btn_save.setText("Save");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/delete.png"))); // NOI18N
        btn_delete.setText("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/update.png"))); // NOI18N
        btn_update.setText("Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        tbl_Sinhvien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã SV", "Họ tên", "Email", "Số điện thoại", "Giới tính", "Địa chỉ", "hình"
            }
        ));
        tbl_Sinhvien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_SinhvienMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_Sinhvien);

        btn_First.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/left_2.png"))); // NOI18N
        btn_First.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_FirstActionPerformed(evt);
            }
        });

        btn_previous.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/left_1.png"))); // NOI18N
        btn_previous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_previousActionPerformed(evt);
            }
        });

        btn_next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/right_1_1.png"))); // NOI18N
        btn_next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nextActionPerformed(evt);
            }
        });

        btn_last.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/right.png"))); // NOI18N
        btn_last.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lastActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/user.png"))); // NOI18N
        jButton1.setText("Đăng xuất");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(184, 184, 184))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(btn_First))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_maSV, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                            .addComponent(txt_hoTen)
                            .addComponent(txt_email)
                            .addComponent(txt_phone)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rdoNam)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdoNu)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_new, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_previous)
                                .addGap(18, 18, 18)
                                .addComponent(btn_next)
                                .addGap(18, 18, 18)
                                .addComponent(btn_last)
                                .addGap(39, 39, 39)
                                .addComponent(jButton1)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_maSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(txt_hoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(txt_phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btn_new)
                                        .addComponent(btn_save))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btn_delete)
                                        .addComponent(btn_update)))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_previous)
                            .addComponent(btn_First)
                            .addComponent(btn_next)
                            .addComponent(btn_last)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
         int kq = JOptionPane.showConfirmDialog(this, "Bạn có chắc không?", "Xoá", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(kq == JOptionPane.YES_OPTION) {
          //Xử lý trường hợp người dùng chọn Yes
           delete();
        } else {

        }
        
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newActionPerformed
        // TODO add your handling code here:
        txt_maSV.setText("");
        txt_hoTen.setText("");
        txt_email.setText("");
        txt_phone.setText("");       
        rdoNam.setSelected(true);
        txtA_diachi.setText("");
        txt_maSV.requestFocus();
        lblHinh.setIcon(null);
    }//GEN-LAST:event_btn_newActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        // TODO add your handling code here:
       insert();
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        // TODO add your handling code here:
       update();
    }//GEN-LAST:event_btn_updateActionPerformed

    private void tbl_SinhvienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_SinhvienMouseClicked
        // TODO add your handling code here:
         current = tbl_Sinhvien.getSelectedRow();
         show_Detail();
    }//GEN-LAST:event_tbl_SinhvienMouseClicked

    private void btn_FirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_FirstActionPerformed
        // TODO add your handling code here:
        current = 0;
        show_Detail();
    }//GEN-LAST:event_btn_FirstActionPerformed

    private void btn_lastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lastActionPerformed
        // TODO add your handling code here:
        current = list.size()-1;
        show_Detail();
    }//GEN-LAST:event_btn_lastActionPerformed

    private void btn_previousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_previousActionPerformed
        // TODO add your handling code here:
        try {
            current -= 1;
            if(current < 0){
                 current = 0;
            }
         show_Detail();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btn_previousActionPerformed

    private void btn_nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nextActionPerformed
        // TODO add your handling code here:
        try {
             current += 1;
             int a = list.size()-1;
             if(current > a){
                 current = list.size()-1;
            }
         show_Detail();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btn_nextActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose(); // form grade biến mất
        Login lg = new Login();
        lg.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuanLySinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLySinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLySinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLySinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLySinhVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_First;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_last;
    private javax.swing.JButton btn_new;
    private javax.swing.JButton btn_next;
    private javax.swing.JButton btn_previous;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_update;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tbl_Sinhvien;
    private javax.swing.JTextArea txtA_diachi;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_hoTen;
    private javax.swing.JTextField txt_maSV;
    private javax.swing.JTextField txt_phone;
    // End of variables declaration//GEN-END:variables
}
