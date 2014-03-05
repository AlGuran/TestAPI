
package com.api.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.simple.*;

/**
 *
 * @author Guran
 */
@Controller
public class SimpleController extends AbstractController{
    private String viewName;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
    
    protected ModelAndView handleRequestInternal( 
         HttpServletRequest request, HttpServletResponse response) 
       throws Exception {
        ModelAndView mav = new ModelAndView();
        String a = request.getParameter("action");
        String type = request.getParameter("type");
        JSONObject resultJson = new JSONObject();
        PreparedStatement ps;
        ResultSet rs;
        String id, nick, email, result = "";
        Connection con = returnConnection();
        if (con == null)
            if (type.equals("1")){
                result+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                        + "<result>Not Connection</result>";
            }else{
                resultJson.put("result","Not Connection");
                result = resultJson.toString();
            }
        if(type==null){
            type="2";
        }
        if(a!=null){
            switch (a){
                case "getUser":
                    id = request.getParameter("id");
                    ps = con.prepareStatement("SELECT * "
                            + "FROM users "
                            + "WHERE id=?");
                    ps.setString(1, id);
                    rs = ps.executeQuery();
                    if (type.equals("1")){
                        result = getInXML(rs);
                    }else{
                        result = getInJSON(rs);
                    }
                break;
                case "findUser":
                    String search = request.getParameter("search");
                    ps = con.prepareStatement("SELECT * "
                            + "FROM users "
                            + "WHERE nick=? OR login=? OR email=?");
                    ps.setString(1, search);
                    ps.setString(2, search);
                    ps.setString(3, search);
                    rs = ps.executeQuery();
                    if (type.equals("1")){
                        result = getInXML(rs);
                    }else{
                        result = getInJSON(rs);
                    }
                break;
                case "updateUser":
                    email = request.getParameter("email");
                    nick = request.getParameter("nick");
                    id = request.getParameter("id");
                    try{
                        if(email !=null && !email.equals("")) {
                            ps = con.prepareStatement("UPDATE users SET email=? WHERE id=?");
                            ps.setString(1, email);
                            ps.setString(2, id);
                            ps.executeUpdate();
                            if (type.equals("1")){
                                result+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                                        + "<result>ok</result>";
                            }else{
                                resultJson.put("result","ok");
                                result = resultJson.toString();
                            }
                        }
                        if(nick !=null && !nick.equals("")) {
                            ps = con.prepareStatement("UPDATE users SET nick=? WHERE id=?");
                            ps.setString(1, nick);
                            ps.setString(2, id);
                            ps.executeUpdate();
                            if (type.equals("1")){
                                result+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                                        + "<result>ok</result>";
                            }else{
                                resultJson.put("result","ok");
                                result = resultJson.toString();
                            }
                        }
                    }catch(SQLException e){
                        if (type.equals("1")){
                            result+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                                        + "<result>SQLException</result>";
                        }else{
                            resultJson.put("result","SQLException");
                            result = resultJson.toString();
                        }
                    }
                break;
                case "setAchieve":
                    id = request.getParameter("user_id");
                    String achieve_id = request.getParameter("achieve_id");
                    try{
                        ps = con.prepareStatement("INSERT INTO user_achievements (user_id, achievement_id) VALUES (?, ?)");
                        ps.setString(1, id);
                        ps.setString(2, achieve_id);
                        ps.executeUpdate();
                        if (type.equals("1")){
                            result+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                                    + "<result>ok</result>";
                        }else{
                            resultJson.put("result","ok");
                            result = resultJson.toString();
                        }
                    }catch(SQLException e){
                        if (type.equals("1")){
                            result+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                                        + "<result>SQLException</result>";
                        }else{
                            resultJson.put("result","SQLException");
                            result = resultJson.toString();
                        }
                    }
                break;
                default:
            }
        }else{
            result = "OOPS!";
        }
        mav.addObject("result", result);
        return mav;
    }
    
    public String getInJSON(ResultSet rs) throws SQLException {
        String result;
        List  l1 = new LinkedList();
        
        while(rs.next()){
            Map m1 = new LinkedHashMap();
            m1.put("id",rs.getString("id"));
            m1.put("nick",rs.getString("nick"));
            m1.put("login",rs.getString("login"));
            m1.put("email",rs.getString("email"));
            l1.add(m1);
        }
        
        result = JSONValue.toJSONString(l1);
 
        return result;

    }
    
    public String getInXML(ResultSet rs) throws SQLException {
        String result;
        result="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        while(rs.next()){
            result+="<user>"
                +"<id>"+rs.getString("id")+"</id>"
                + "<nick>"+rs.getString("nick")+"</nick>"
                + "<login>"+rs.getString("login")+"</login>"
                + "<email>"+rs.getString("email")+"</email>"
                +"</user>";
        }
 
        return result;

    }
    
    public Connection returnConnection() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        Class.forName("com.mysql.jdbc.Driver").newInstance ();
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://host:port/database","login", "password");
        
            return con;
        }catch(SQLException e){
            return null;
        }
    }
 
}