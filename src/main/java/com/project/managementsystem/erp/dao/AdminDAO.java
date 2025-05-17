package com.project.managementsystem.erp.dao;
import com.project.managementsystem.erp.models.Admin;

public interface AdminDAO {
    boolean register(Admin admin);
    Admin login(String email, String password);

}
