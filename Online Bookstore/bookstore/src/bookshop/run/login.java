package bookshop.run;
/**
 * <p>�����û���¼���� </p>
 */
import bookshop.util.*;
import java.sql.Connection;
import java.sql.Statement;

public class login extends DataBase {
        private String username;	//��¼�û���
        private String passwd;		//��¼����
        private boolean isadmin;	//�Ƿ����Ա��¼
        private long userid=0;		//�û�ID��
        public login() throws Exception{
                super();
                username = "";
                passwd = "";
                isadmin = false;
        }
        public String getUsername() {
                return username;
        }
        public void setUsername(String newusername) {
                username = newusername;
        }
        public String getPasswd() {
                return passwd;
        }
        public void setPasswd(String newpasswd) {
                passwd = newpasswd;
        }
        public boolean getIsadmin() {
                return isadmin;
        }
        public void setIsadmin(boolean newIsadmin) {
                isadmin = newIsadmin;
        }
        public long getUserid() {
                return userid;
        }
        public void setUserid (long uid) {
                userid = uid;
        }
        /**
         * ��ò�ѯ�û���Ϣ��sql���
         * @return
         */
        public String getSql() {
                if (isadmin) {
                        sqlStr = "select * from BookAdmin where adminuser = '" +
                            dataFormat.toSql(username) + "' and adminpass = '" +
                            dataFormat.toSql(passwd) + "'";
                }else {
                        sqlStr = "select * from shop_user where username = '" +
                            username + "' and password = '" + passwd + "'";
                }
                return sqlStr;
        }
        /**
         * ִ�в�ѯ
         * @return
         * @throws java.lang.Exception
         */
        public boolean excute() throws Exception 
		{
		      
                boolean flag = false;
                DataBase db = new DataBase();
        
                Connection conn=db.connect();
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(getSql());
                if (rs.next()){
                        if (!isadmin)
                        {
                                userid = rs.getLong("id");
                        }
                        flag = true;
                }
                rs.close();
                return flag;
				
		        }
}

