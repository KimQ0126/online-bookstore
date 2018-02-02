package bookshop.run;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import java.sql.*;
import java.util.Vector;
import bookshop.util.*;
import bookshop.book.*;
import javax.servlet.http.*;
import java.util.*;
public class op_buy extends DataBase {

    private javax.servlet.http.HttpServletRequest request; //����ҳ������
    private HttpSession session; //ҳ���session;
    private boolean sqlflag = true; //�Խ��յ��������Ƿ���ȷ
    private Vector purchaselist; //��ʾͼ���б���������
    private Vector allorder; //�������б�
    private Vector order_list; //�����嵥�б�
    private int booknumber = 0; //����������
    private float all_price = 0; //�����ܼ�Ǯ
    private boolean isEmpty = false; //���е��������Ƿ񹻹������
    private int leaveBook = 0; //�������
    private String orderId = ""; //�û�������
    private boolean isLogin = true; //�û��Ƿ��¼��
    private int page = 1; //��ʾ��ҳ��
    private int pageSize = 15; //ÿҳ��ʾ�Ķ�����
    private int pageCount = 0; //ҳ������
    private long recordCount = 0; //��ѯ�ļ�¼����

    Calendar date = Calendar.getInstance();
    long time=date.getTimeInMillis();



    public op_buy() {
      super();
  }
    public Vector getPurchaselist() {
      return purchaselist;
    }
    public Vector getOrder_list() {
      return order_list;
    }
    public Vector getAllorder() {
      return allorder;
    }
    public boolean getSqlflag() {
      return sqlflag;
    }
    public void setIsEmpty(boolean flag) {
      isEmpty = flag;
    }
    public boolean getIsEmpty() {
      return isEmpty;
    }
    public void setLeaveBook(int bknum) {
      leaveBook = bknum;
    }
    public int getLeaveBook() {
      return leaveBook;
    }
    public void setOrderId(String newId) {
      orderId = newId;
    }
    public String getOrderId() {
      return orderId;
    }
    public void setIsLogin(boolean flag) {
      isLogin = flag;
    }
    public boolean getIsLogin() {
      return isLogin;
    }
    public int getPage() { //��ʾ��ҳ��
      return page;
    }
    public void setPage(int newpage) {
      page = newpage;
    }
    public int getPageSize() { //ÿҳ��ʾ��ͼ����
      return pageSize;
    }
    public void setPageSize(int newpsize) {
      pageSize = newpsize;
    }
    public int getPageCount() { //ҳ������
      return pageCount;
    }
    public void setPageCount(int newpcount) {
      pageCount = newpcount;
    }
    public long getRecordCount() {
      return recordCount;
    }
    public void setRecordCount(long newrcount) {
      recordCount = newrcount;
    }
    public String getGbk(String str) {
      try {
        return new String(str.getBytes("ISO8859-1"));
      }
      catch (Exception e) {
        return str;
      }
    }

    public String getSql() {
      sqlStr = "select id,classname from book order by id";
      return sqlStr;
    }
    /**
     * �����ﳵ�����ѡ����ͼ��
     * @param newrequest
     * @return
     */
    public boolean addnew(HttpServletRequest newrequest) {
      request = newrequest;
      String ID = request.getParameter("bookid");
      String Amount = request.getParameter("amount");
      long bookid = 0;
      int amount = 0;
      try {
        bookid = Long.parseLong(ID);
        amount = Integer.parseInt(Amount);
      }
      catch (Exception e) {
        return false;
      }
      if (amount < 1)
        return false;
      session = request.getSession(false);
      if (session == null) {
        return false;
      }
      purchaselist = (Vector) session.getAttribute("shopcar");
      sqlStr = "select leav_number from book where id=" + bookid;
      try {
        DataBase db = new DataBase();
                  Connection conn=db.connect();
                  stmt = conn.createStatement ();

        rs = stmt.executeQuery(sqlStr);
        if (rs.next()) {
          if (amount > rs.getInt(1)) {
            leaveBook = rs.getInt(1);
            isEmpty = true;
            return false;
          }
        }
        rs.close();
      }
      catch (SQLException e) {
        return false;
      }

      allorder iList = new allorder();
      iList.setBookNo(bookid);
      iList.setAmount(amount);
      boolean match = false; //�Ƿ������ͼ��
      if (purchaselist == null) { //��һ�ι���
        purchaselist = new Vector();
        purchaselist.addElement(iList);
      }

      else { // ���ǵ�һ�ι���
        for (int i = 0; i < purchaselist.size(); i++) {
          allorder itList = (allorder) purchaselist.elementAt(i);
          if (iList.getBookNo() == itList.getBookNo()) {
            itList.setAmount(itList.getAmount() + iList.getAmount());
            purchaselist.setElementAt(itList, i);
            match = true;
            break;
          } //if name matches����
        } // forѭ������
        if (!match)
          purchaselist.addElement(iList);
      }
      session.setAttribute("shopcar", purchaselist);
      return true;
    }
    /**
     * �޸��Ѿ��Ž����ﳵ������
     * @param newrequest
     * @return
     */
    public boolean modiShoper(HttpServletRequest newrequest) {
      request = newrequest;
      String ID = request.getParameter("bookid");
      String Amount = request.getParameter("amount");
      long bookid = 0;
      int amount = 0;
      try {
        bookid = Long.parseLong(ID);
        amount = Integer.parseInt(Amount);
      }
      catch (Exception e) {
        return false;
      }
      if (amount < 1)
        return false;
      session = request.getSession(false);
      if (session == null) {
        return false;
      }
      purchaselist = (Vector) session.getAttribute("shopcar");
      if (purchaselist == null) {
        return false;
      }
      sqlStr = "select leav_number from book where id=" + bookid;
      try {
        DataBase db = new DataBase();
                  Connection conn=db.connect();
                  stmt = conn.createStatement ();

        rs = stmt.executeQuery(sqlStr);
        if (rs.next()) {
          if (amount > rs.getInt(1)) {
            leaveBook = rs.getInt(1);
            isEmpty = true;
            return false;
          }
        }
        rs.close();
      }
      catch (SQLException e) {
        return false;
      }
      for (int i = 0; i < purchaselist.size(); i++) {
        allorder itList = (allorder) purchaselist.elementAt(i);
        if (bookid == itList.getBookNo()) {
          itList.setAmount(amount);
          purchaselist.setElementAt(itList, i);
          break;
        } //if name matches����
      } // forѭ������
      return true;
    }
    /**
     *ɾ�����ﳵ������
     * @param newrequest
     * @return
     */
    public boolean delShoper(HttpServletRequest newrequest) {
      request = newrequest;
      String ID = request.getParameter("bookid");
      long bookid = 0;
      try {
        bookid = Long.parseLong(ID);
      }
      catch (Exception e) {
        return false;
      }
      session = request.getSession(false);
      if (session == null) {
        return false;
      }
      purchaselist = (Vector) session.getAttribute("shopcar");
      if (purchaselist == null) {
        return false;
      }

      for (int i = 0; i < purchaselist.size(); i++) {
        allorder itList = (allorder) purchaselist.elementAt(i);
        if (bookid == itList.getBookNo()) {
          purchaselist.removeElementAt(i);
          break;
        } //if name matches����
      } // forѭ������
      return true;
    }
    /**
     * �ύ���ﳵ
     * @param newrequest
     * @return
     * @throws java.lang.Exception
     */
    public boolean payout(HttpServletRequest newrequest) throws Exception {
      request = newrequest;
      session = request.getSession(false);
    // System.out.print("f1");
      if (session == null) {
        return false;
      }
      String Userid = (String) session.getAttribute("userid"); //ȡ���û�ID��
      long userid = 0;
      if (Userid == null || Userid.equals("")) {
        isLogin = false;
        return false;
      }
      else {
        try {
          userid = Long.parseLong(Userid);
        }
        catch (NumberFormatException e) {
        //  System.out.print(e.getMessage());
          return false;
        }
      }
  
      purchaselist = (Vector) session.getAttribute("shopcar");
      if (purchaselist == null || purchaselist.size() < 0) {
        return false;
      }
      String Content = request.getParameter("content");
      if (Content == null) {
        Content = "";
      }
     //   System.out.print("f3");
      Content = getGbk(Content);
      String IP = request.getRemoteAddr();
      String TotalPrice = request.getParameter("totalprice");
      long timeInMillis = System.currentTimeMillis();
  //System.out.println("f4");
      sqlStr = "insert into orders (orderId,UserId,SubmitTime,ConsignmentTime,TotalPrice,content,IPAddress,IsPayoff,IsSales) values (";
      orderId=""+timeInMillis;//��ϵͳʱ�����λ�ƵĶ������
      sqlStr = sqlStr  + orderId + ",'";
      sqlStr = sqlStr + userid + "',now(),now()+7,'";
      sqlStr = sqlStr + TotalPrice + "','";
      sqlStr = sqlStr + dataFormat.toSql(Content) + "','";
      sqlStr = sqlStr + IP + "',1,1)";
    //  System.out.print(sqlStr);
    //String setIdSql="udate orders set orderid=orderid+id where UserId = " + userid;
      try {
        stmt.execute(sqlStr);
        //stmt.execute(setIdSql);
       // sqlStr = "select max(id) from orders where UserId = " + userid;
       // rs = stmt.executeQuery(sqlStr);
        //long indentid = 0;
       // while (rs.next()) {
         // indentid = rs.getLong(1);
        //}
        //rs.close();
        for (int i = 0; i < purchaselist.size(); i++) {
          allorder iList = (allorder) purchaselist.elementAt(i);
          sqlStr =
              "insert into allorder (orderId,BookNo,Amount) values (";
          sqlStr = sqlStr + orderId + ",'";
          sqlStr = sqlStr + iList.getBookNo() + "','";
          sqlStr = sqlStr + iList.getAmount() + "')";
          stmt.execute(sqlStr);
          sqlStr = "update book set leav_number=leav_number - " +
              iList.getAmount() + " where id = " + iList.getBookNo();
          stmt.execute(sqlStr);
        }
        return true;
      }
      catch (SQLException e) {
        System.out.print(e.getMessage());
        return false;
      }

    }
    /**
     * ��ѯָ���û�id�����ж���
     * @param userid
     * @return
     */
    public boolean getOrder(long userid) {
      sqlStr = "select * from orders where userid = '" + userid +
          "' order by id desc";
      try {
        DataBase db = new DataBase();
                  Connection conn=db.connect();
                  stmt = conn.createStatement ();

        rs = stmt.executeQuery(sqlStr);
        allorder = new Vector();
        while (rs.next()) {
          order ind = new order();
          ind.setId(rs.getLong("id"));
          ind.setOrderId(rs.getString("orderId"));
          ind.setUserId(rs.getLong("userid"));
          ind.setSubmitTime(rs.getString("submitTime"));
          ind.setConsignmentTime(rs.getString("ConsignmentTime"));
          ind.setTotalPrice(rs.getFloat("TotalPrice"));
          ind.setContent(rs.getString("content"));
          ind.setIPAddress(rs.getString("IpAddress"));
          if (rs.getInt("IsPayoff") == 1)
            ind.setIsPayoff(false);
          else
            ind.setIsPayoff(true);
          if (rs.getInt("IsSales") == 1)
            ind.setIsSales(false);
          else
            ind.setIsSales(true);
          allorder.addElement(ind);
        }
        rs.close();
        return true;
      }
      catch (SQLException e) {
        return false;
      }
    }
    /**
     * ��ѯָ��������ŵĶ���
     * @param iid
     * @return
     */
    public boolean getSinggleOrder(String order_id) {
      sqlStr = "select * from orders where orderId = '" + order_id +
          "' ";
      try {
        DataBase db = new DataBase();
                  Connection conn=db.connect();
                  stmt = conn.createStatement ();

        rs = stmt.executeQuery(sqlStr);
        allorder = new Vector();
        while (rs.next()) {
          order ind = new order();
          ind.setId(rs.getLong("id"));
          ind.setOrderId(rs.getString("orderId"));
          ind.setUserId(rs.getLong("userid"));
          ind.setSubmitTime(rs.getString("submitTime"));
          ind.setConsignmentTime(rs.getString("ConsignmentTime"));
          ind.setTotalPrice(rs.getFloat("TotalPrice"));
          ind.setContent(rs.getString("content"));
          ind.setIPAddress(rs.getString("IpAddress"));
          if (rs.getInt("IsPayoff") == 1)
            ind.setIsPayoff(false);
          else
            ind.setIsPayoff(true);
          if (rs.getInt("IsSales") == 1)
            ind.setIsSales(false);
          else
            ind.setIsSales(true);
          allorder.addElement(ind);
          System.out.print("allorder:"+allorder.size());
        }
        rs.close();
        return true;
      }
      catch (SQLException e) {
        System.out.print(e.getMessage());
        return false;
      }
    }
    /**
     * ��ѯ�������ж�������
     * @return
     */
    public boolean getOrder() {
      sqlStr = "select count(*) from orders"; //ȡ����¼��
      int rscount = pageSize;
      try {
        DataBase db = new DataBase();
                  Connection conn=db.connect();
                  stmt = conn.createStatement ();

        ResultSet rs1 = stmt.executeQuery(sqlStr);
        if (rs1.next())
          recordCount = rs1.getInt(1);
        rs1.close();
      }
      catch (SQLException e) {
        return false;
      }
      //�趨�ж���pageCount
      if (recordCount < 1)
        pageCount = 0;
      else
        pageCount = (int) (recordCount - 1) / pageSize + 1;
        //���鿴��ҳ�����Ƿ��ڷ�Χ��
      if (page < 1)
        page = 1;
      else if (page > pageCount)
        page = pageCount;

      rscount = (int) recordCount % pageSize; // ���һҳ��¼��

      //sqlΪ����ȡֵ
      sqlStr = "select  * from orders ";
      if (page == 1) {
        sqlStr = sqlStr + " order by Id desc";
      }
      else {
        sqlStr = sqlStr + " order by Id desc  limit "+(recordCount - pageSize * page) +","+ (recordCount - pageSize * (page - 1));

      }

      try {
        DataBase db = new DataBase();
                  Connection conn=db.connect();
                  stmt = conn.createStatement ();

        rs = stmt.executeQuery(sqlStr);
        allorder = new Vector();
        while (rs.next()) {
          order ind = new order();
          ind.setId(rs.getLong("id"));
          ind.setOrderId(rs.getString("orderid"));
          ind.setUserId(rs.getLong("userid"));
          ind.setSubmitTime(rs.getString("submitTime"));
          ind.setConsignmentTime(rs.getString("ConsignmentTime"));
          ind.setTotalPrice(rs.getFloat("TotalPrice"));
          ind.setContent(rs.getString("content"));
          ind.setIPAddress(rs.getString("IpAddress"));
          if (rs.getInt("IsPayoff") == 1)
            ind.setIsPayoff(false);
          else
            ind.setIsPayoff(true);
          if (rs.getInt("IsSales") == 1)
            ind.setIsSales(false);
          else
            ind.setIsSales(true);
          allorder.addElement(ind);
        }
        rs.close();
        return true;
      }
      catch (SQLException e) {
        System.out.println(e);
        return false;
      }
    }
    /**
     * ��ö����б�
     * @param nid
     * @return
     */
    public boolean getAllorder(String order_id) {
      sqlStr = "select * from allorder where orderId = '" + order_id + "'";
      try {
        DataBase db = new DataBase();
                  Connection conn=db.connect();
                  stmt = conn.createStatement ();

        rs = stmt.executeQuery(sqlStr);
        order_list = new Vector();
        while (rs.next()) {
          allorder identlist = new allorder();
          identlist.setId(rs.getLong("id"));
          identlist.setOrderId(rs.getLong("orderId"));
          identlist.setBookNo(rs.getLong("BookNo"));
          identlist.setAmount(rs.getInt("Amount"));
          order_list.addElement(identlist);
        }
        rs.close();
        return true;
      }
      catch (SQLException e) {
        System.out.print(e.getMessage());
        return false;
      }
    }
    /**
     * �޸Ķ������޸ĸ����־
     * @param res
     * @return
     */
    public boolean update(HttpServletRequest res) {
      request = res;
      int payoff = 1;
      int sales = 1;
      //long orderId = 0;
      try {
        System.out.println("payoff:"+request.getParameter("payoff"));
        payoff = Integer.parseInt(request.getParameter("payoff"));
        sales = Integer.parseInt(request.getParameter("sales"));
        orderId =request.getParameter("indentid");
        sqlStr = "update orders set IsPayoff = '" + payoff + "',IsSales='" +
            sales + "' where orderId =" + orderId;
        DataBase db = new DataBase();
                  Connection conn=db.connect();
                  stmt = conn.createStatement ();
                  System.out.print(sqlStr);
        stmt.execute(sqlStr);
        return true;
      }
      catch (Exception e) {
        System.out.print(e.getMessage());
        return false;
      }
    }
    /**
     * ɾ������
     * @param id
     * @return
     */
    public boolean delete(long id) {
      try {
        DataBase db = new DataBase();
                  Connection conn=db.connect();
                  stmt = conn.createStatement ();
        sqlStr = "delete from allorder where id =" + id;
        stmt.execute(sqlStr);
        sqlStr = "delete from orders where id= " + id;
        stmt.execute(sqlStr);
        return true;
      }
      catch (SQLException e) {
        return false;
      }
    }

  }
