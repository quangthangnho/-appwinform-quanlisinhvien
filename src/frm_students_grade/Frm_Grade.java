/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frm_students_grade;

import Login_main.Login;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import entities.*;
import javax.swing.table.DefaultTableModel;
import model.*;




/**
 *
 * @author Quang
 */
public class Frm_Grade extends javax.swing.JFrame {

 ArrayList<Grade> list = new ArrayList<>();
   int current = 0; // vị trí  hiện hành
    public Frm_Grade() {
        initComponents();
        load_data();
    }

    public void load_data()
    {
        try 
        {
            // gọi hàm Myconnect để kết nối
            Connection cn = new MyConnect().getcn();
            PreparedStatement ps = cn.prepareStatement("select STUDENTS.MASV, Hoten, Tienganh, Tinhoc, GDTC, (Tienganh + Tinhoc + GDTC)/3 as diemTB from STUDENTS, GRADE where STUDENTS.MASV = GRADE.MASV");
            ResultSet rs = ps.executeQuery();
            list.clear();// xoá list để lấy dữ liệu từ bảng
            while(rs.next())
            {
                // tạo đối tượng students
                Grade gra = new Grade(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
                // thêm vào ArrayLisst
                list.add(gra);
            }
            cn.close();
            ps.close();
            
            ////////// load dữ liêif từ list lên table
            // lấy mô hình dữ liệu và xoá sạch các hàng
            DefaultTableModel model = (DefaultTableModel) tbl_table.getModel();
        model.setRowCount(0);
        for(Grade gra: list){
            Object[] row = new Object[]{gra.getMASV(),gra.getHoTen(),gra.getTiengAnh(),gra.getTinHoc(),gra.getGDTC(),gra.getDiemTB()};
            model.addRow(row);
        }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    public void show_Detail(){
        // lấy ra sv hiện hành
        Grade gra = list.get(current);
        // gán thông tin sv lên form
        lbl_hovaten.setText(gra.getHoTen());
        txt_maSV2.setText(gra.getMASV());
        txt_tienganh.setText(String.valueOf(gra.getTiengAnh()));
        txt_tinhoc.setText(String.valueOf(gra.getTinHoc()));
        txt_GDTC.setText(String.valueOf(gra.getGDTC()));
        lbl_diemTrungbinh.setText(String.valueOf(gra.getDiemTB()));
    }
    public void search()
    {
        try 
        {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Connection cn = DriverManager.getConnection(url,username,password);
            // gọi hàm Myconnect để kết nối
            Connection cn = new MyConnect().getcn();
            PreparedStatement ps = cn.prepareStatement("select STUDENTS.MASV, Hoten, Tienganh, Tinhoc, GDTC, (Tienganh + Tinhoc + GDTC)/3 as diemTB from STUDENTS, GRADE where STUDENTS.MASV = GRADE.MASV and STUDENTS.MASV=?");
           ps.setString(1, txt_maSV1.getText());
            ResultSet rs = ps.executeQuery();
            list.clear();// xoá list để lấy dữ liệu từ bảng
            while(rs.next())
            {
                // tạo đối tượng students
                Grade gra = new Grade(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
                // thêm vào ArrayLisst
                list.add(gra);
            }
            cn.close();
            ps.close();
            
            ////////// load dữ liêif từ list lên table
            // lấy mô hình dữ liệu và xoá sạch các hàng
            DefaultTableModel model = (DefaultTableModel) tbl_table.getModel();
        model.setRowCount(0);
        for(Grade gra: list){
            Object[] row = new Object[]{gra.getMASV(),gra.getHoTen(),gra.getTiengAnh(),gra.getTinHoc(),gra.getGDTC(),gra.getDiemTB()};
            model.addRow(row);
        }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    public void insert(){
        try {
            Grade gra = new Grade();
        gra.setMASV(txt_maSV2.getText());
        gra.setTiengAnh(Integer.parseInt(txt_tienganh.getText()));
        gra.setTinHoc(Integer.parseInt(txt_tinhoc.getText()));
        gra.setGDTC(Integer.parseInt(txt_GDTC.getText()));
        StudentModel m = new StudentModel();
            //3. Thêm đối tượng sinh viên stu bằng cách gọi hàm insertStudent trong StudentModel
            int kq = m.insertGrade(gra);

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
    public void update(){
        try {
            Grade gra = new Grade();
        gra.setMASV(txt_maSV2.getText());
        gra.setTiengAnh(Integer.parseInt(txt_tienganh.getText()));
        gra.setTinHoc(Integer.parseInt(txt_tinhoc.getText()));
        gra.setGDTC(Integer.parseInt(txt_GDTC.getText()));
        StudentModel m = new StudentModel();
            //3. Thêm đối tượng sinh viên stu bằng cách gọi hàm insertStudent trong StudentModel
            int kq = m.updateGrade(gra);

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
            int kq = m.deleteGrade(txt_maSV2.getText());

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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txt_maSV1 = new javax.swing.JTextField();
        btn_search = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lbl_hovaten = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_maSV2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_tienganh = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_tinhoc = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_GDTC = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        lbl_diemTrungbinh = new javax.swing.JLabel();
        btn_new = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        bnt_left_left = new javax.swing.JButton();
        bnt_left = new javax.swing.JButton();
        bnt_right = new javax.swing.JButton();
        bnt_right_right = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_table = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("QUẢN LÝ ĐIỂM SINH VIÊN");

        jLabel2.setText("Tìm kiếm");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText("Mã SV:");

        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/search.png"))); // NOI18N
        btn_search.setText("  Search");
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_maSV1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_maSV1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("Họ và tên SV:");

        lbl_hovaten.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel6.setText("Mã SV:");

        jLabel7.setText("Tiếng anh:");

        jLabel8.setText("Tin học:");

        jLabel9.setText("Giáo dục TC:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Điểm trung bình:");

        lbl_diemTrungbinh.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_diemTrungbinh.setForeground(new java.awt.Color(51, 51, 255));
        lbl_diemTrungbinh.setText("0.0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_tienganh, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_maSV2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_tinhoc, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_GDTC, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(lbl_hovaten, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_diemTrungbinh, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lbl_hovaten))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_maSV2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_tienganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_tinhoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_GDTC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_diemTrungbinh, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                    .addComponent(jLabel10))
                .addContainerGap())
        );

        btn_new.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/new_1.png"))); // NOI18N
        btn_new.setText("  New");
        btn_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newActionPerformed(evt);
            }
        });

        btn_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/save.png"))); // NOI18N
        btn_save.setText("  Save");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/delete.png"))); // NOI18N
        btn_delete.setText("  Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/update.png"))); // NOI18N
        btn_update.setText("  Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        bnt_left_left.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/left.png"))); // NOI18N
        bnt_left_left.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnt_left_leftActionPerformed(evt);
            }
        });

        bnt_left.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/left_1.png"))); // NOI18N
        bnt_left.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnt_leftActionPerformed(evt);
            }
        });

        bnt_right.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/right_1.png"))); // NOI18N
        bnt_right.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnt_rightActionPerformed(evt);
            }
        });

        bnt_right_right.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/right.png"))); // NOI18N
        bnt_right_right.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnt_right_rightActionPerformed(evt);
            }
        });

        tbl_table.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tbl_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã SV", "Họ tên", "Tiếng anh", "Tin học", "Giáo dục TC", "Điểm TB"
            }
        ));
        tbl_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_table);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(346, 346, 346))
        );

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
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(111, 111, 111)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(37, 37, 37)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(btn_update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btn_new, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btn_save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addComponent(bnt_left_left, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(bnt_left, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(31, 31, 31)
                                        .addComponent(bnt_right, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(bnt_right_right, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(btn_new)
                        .addGap(18, 18, 18)
                        .addComponent(btn_save)
                        .addGap(18, 18, 18)
                        .addComponent(btn_delete)
                        .addGap(18, 18, 18)
                        .addComponent(btn_update)
                        .addGap(34, 34, 34)
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bnt_right, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bnt_left, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bnt_right_right, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bnt_left_left, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        insert();
        
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
       int kq = JOptionPane.showConfirmDialog(this, "Bạn có chắc không?", "Xoá", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(kq == JOptionPane.YES_OPTION) {
          //Xử lý trường hợp người dùng chọn Yes
           delete();
        } else {

        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        // TODO add your handling code here:
        try {
            search();
        show_Detail();
        } catch (Exception e) {
        }
        
    }//GEN-LAST:event_btn_searchActionPerformed

    private void tbl_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_tableMouseClicked
        // TODO add your handling code here:
        try {
            current = tbl_table.getSelectedRow();
        show_Detail();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tbl_tableMouseClicked

    private void bnt_left_leftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnt_left_leftActionPerformed
        // TODO add your handling code here:
        try {
            current = 0;
        show_Detail();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_bnt_left_leftActionPerformed

    private void bnt_right_rightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnt_right_rightActionPerformed
        // TODO add your handling code here:
        try {
            current = list.size()-1;
        show_Detail();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_bnt_right_rightActionPerformed

    private void bnt_leftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnt_leftActionPerformed
        // TODO add your handling code here:
        try {
            current -= 1;
            if(current < 0){
                 current = 0;
            }
         show_Detail();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_bnt_leftActionPerformed

    private void bnt_rightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnt_rightActionPerformed
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
    }//GEN-LAST:event_bnt_rightActionPerformed

    private void btn_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newActionPerformed
        // TODO add your handling code here:
        txt_maSV1.setText("");
        txt_maSV2.setText("");
        lbl_hovaten.setText("");
        txt_tienganh.setText("");
        txt_tinhoc.setText("");
        txt_GDTC.setText("");
        lbl_diemTrungbinh.setText("0.0");
    }//GEN-LAST:event_btn_newActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btn_updateActionPerformed

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
            java.util.logging.Logger.getLogger(Frm_Grade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_Grade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_Grade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_Grade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm_Grade().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bnt_left;
    private javax.swing.JButton bnt_left_left;
    private javax.swing.JButton bnt_right;
    private javax.swing.JButton bnt_right_right;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_new;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btn_update;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_diemTrungbinh;
    private javax.swing.JLabel lbl_hovaten;
    private javax.swing.JTable tbl_table;
    private javax.swing.JTextField txt_GDTC;
    private javax.swing.JTextField txt_maSV1;
    private javax.swing.JTextField txt_maSV2;
    private javax.swing.JTextField txt_tienganh;
    private javax.swing.JTextField txt_tinhoc;
    // End of variables declaration//GEN-END:variables
}
