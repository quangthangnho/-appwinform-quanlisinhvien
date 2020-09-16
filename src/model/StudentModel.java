/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.*;
import java.util.*;
import entities.*;

/**
 *
 * @author home
 */
public class StudentModel {

    public StudentModel() {
    }

    //1. hàm getStudents() : lấy ra danh sách sinh viên
    public ArrayList<Students> getStudents() {
        ArrayList<Students> list = new ArrayList<Students>();
        Connection cn = new MyConnect().getcn();
        if (cn != null) {
            try {

                PreparedStatement ps = cn.prepareStatement("select * from STUDENTS");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Students stu = new Students();
                    // gán dữ liệu vào cho các thuộc tính
                    stu.setMaSV(rs.getString(1));
                    stu.setHoTen(rs.getString(2));
                    stu.setEmail(rs.getString(3));
                    stu.setSoDT(rs.getString(4));
                    stu.setGioiTinh(rs.getBoolean(5));
                    stu.setDiaChi(rs.getString(6));
                    stu.setHinh(rs.getString(7));
                    list.add(stu);// thêm đối tượng stu vào danh sách list                
                }
                ps.close();
                cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    //2. Hàm insertStudent(Student stu)
    // kq = 1 : thêm thành công
    // kq = 0 : thêm thất bại
    public int insertStudent(Students stu) {
        int kq = 0;
        Connection cn = new MyConnect().getcn();
        if (cn != null) {
            try {

                PreparedStatement ps = cn.prepareStatement("insert into STUDENTS values(?,?,?,?,?,?,?)");
                //1. Truyền giá trị  vào đối số 
                ps.setString(1, stu.getMaSV());
                ps.setString(2, stu.getHoTen());
                ps.setString(3, stu.getEmail());
                ps.setString(4, stu.getSoDT());
                ps.setBoolean(5, stu.isGioiTinh());
                ps.setString(6, stu.getDiaChi());
                ps.setString(7, stu.getHinh()); // 
                // thực thi : kq = 1 thành công kq = 0 thất bại.
                kq = ps.executeUpdate();
            //ps.close();
                //cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return kq;
    }
    
    //3. Hàm deleteStudent(String masv)
    // kq = 1 : delete thành công
    // kq = 0 : delete thất bại    
    
    public int deleteStudent(String masv)
    {
        int kq = 0;
        
        Connection cn = new MyConnect().getcn();
        //1. Nếu cn khác null - kết nối database được
        if (cn != null)
        {
            try {
                PreparedStatement ps = cn.prepareStatement("delete from STUDENTS where MASV=? ");
                ps.setString(1, masv);
                kq = ps.executeUpdate(); // xóa thành công 1 dòng thì kq = 1 ...kq =0 không xóa được dòng nào
            
                //xóa xong đóng lại
                ps.close();
                cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return kq;
    }
    
    
    //4. Hàm updateStudent(Student stu)
    // kq = 1 : sửa thành công
    // kq = 0 : sửa thất bại
    public int updateStudent(Students stu) {
        int kq = 0;
        Connection cn = new MyConnect().getcn();
        if (cn != null) {
            try {

                PreparedStatement ps = cn.prepareStatement("update STUDENTS set Hoten=?, Email=?, SoDT=?, Gioitinh=?, Diachi=?, hinh = ?  where MASV=?");
                //1. Truyền giá trị  vào đối số 
                ps.setString(7, stu.getMaSV());
                ps.setString(1, stu.getHoTen());
                ps.setString(2, stu.getEmail());
                ps.setString(3, stu.getSoDT());
                ps.setBoolean(4, stu.isGioiTinh());
                ps.setString(5, stu.getDiaChi());
                ps.setString(6, stu.getHinh()); // 
                // thực thi : kq = 1 thành công kq = 0 thất bại.
                kq = ps.executeUpdate();
                ps.close();
                cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return kq;
    }
    
    public int insertGrade(Grade gra) {
        int kq = 0;
        Connection cn = new MyConnect().getcn();
        if (cn != null) {
            try {
            PreparedStatement ps = cn.prepareStatement("insert into Grade values(?,?,?,?)" );            
            //4. Truyền giá trị cho bon tham số ?, ?, ?, ?            
            ps.setString(1, gra.getMASV());
            ps.setInt(2, gra.getTiengAnh());
            ps.setInt(3, gra.getTinHoc());
            ps.setInt(4, gra.getGDTC());
            //5. thi hành
             kq = ps.executeUpdate();
            ps.close();
             cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return kq;
    }
    public int updateGrade(Grade gra) {
        int kq = 0;
        Connection cn = new MyConnect().getcn();
        if (cn != null) {
            try {
            PreparedStatement ps = cn.prepareStatement("update GRADE set Tienganh =?,Tinhoc =?,GDTC =? where MASV =?");            
            //4. Truyền giá trị cho bon tham số ?, ?, ?, ?            
            
            ps.setInt(1, gra.getTiengAnh());
            ps.setInt(2, gra.getTinHoc());
            ps.setInt(3, gra.getGDTC());
            ps.setString(4, gra.getMASV());
            //5. thi hành
             kq = ps.executeUpdate();
            ps.close();
             cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return kq;
    }
     public int deleteGrade(String masv)
    {
        int kq = 0;
        
        Connection cn = new MyConnect().getcn();
        //1. Nếu cn khác null - kết nối database được
        if (cn != null)
        {
            try {
                PreparedStatement ps = cn.prepareStatement("delete from GRADE where MASV=? ");
                ps.setString(1, masv);
                kq = ps.executeUpdate(); // xóa thành công 1 dòng thì kq = 1 ...kq =0 không xóa được dòng nào
            
                //xóa xong đóng lại
                ps.close();
                cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return kq;
    }
    
}
