import com.learnwy.db.mysql.MySQL;
import com.learnwy.model.Role;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class RoleDB {
    public static List<Role> getAllRoles() {
        LinkedList<Role> ret = new LinkedList<Role>();
        String sql = " select display_name,role_id  from `role`";
        ResultSet rs = MySQL.excuteSQL(sql);
        Role role = null;
        try {
            while (rs.next()) {
                role = new Role(rs.getString(1), rs.getLong(2));
                ret.add(role);
            }
        } catch (Exception ex) {
        }
        return ret;
    }

    public static int deleteRole(Role role) {
        if (role == null) {
            return -1;
        }
        String sql = "delete from `role` where role_id = " + role.getRoleId();
        return MySQL.updateSQL(sql);
    }

    public static int updateOrAddRole(Role role) {
        if (role == null) {
            return -1;
        } else if (role.getRoleId() == -1) {
            return addRole(role);
        } else {
            return updateRole(role);
        }
    }

    public static int addRole(Role role) {
        String sql = "insert into `role`(display_name)values(" + "'" + role.getDisplayName() + "'" + ")";
        int ret = MySQL.updateSQL(sql);
        return ret;
    }

    public static int updateRole(Role role) {
        if (role == null) {
            return -1;
        } else if (role.getRoleId() == -1) {
            return addRole(role);
        }
        String sql = "update `role` set " + "display_name = '" + role.getDisplayName() + "'" + " where role_id = " + role.getRoleId();
        return MySQL.updateSQL(sql);
    }
}
