package tz.application.packer;

import com.avaje.ebean.Ebean;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Dmitry Shyshkin
 */
public class Packer {
    public static void main(String[] args) throws ParseException, SQLException, IOException, UnsupportedEncodingException {
        FileOutputStream fos = new FileOutputStream("/tmp/battles");
        long day = 24 * 60 * 60 * 1000L;
        long now = new SimpleDateFormat("dd/MM/yyyy").parse("13/10/2010").getTime() + 12 * 60 * 60 * 1000L;
        Date start = new Date(now / day * day);
        Date end = new Date(now / day * day + day);
        Ebean.beginTransaction();
        PreparedStatement statement = Ebean.currentTransaction().getConnection().prepareStatement("select xml from battle where battle_date >= ? and battle_date <= ? ");
        statement.setDate(1, new java.sql.Date(start.getTime()));
        statement.setDate(2, new java.sql.Date(end.getTime()));
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String xml = resultSet.getString(1);
            fos.write(xml.getBytes("UTF-8"));
        }
        resultSet.close();
        statement.close();
        Ebean.endTransaction();
        fos.close();
    }
}
