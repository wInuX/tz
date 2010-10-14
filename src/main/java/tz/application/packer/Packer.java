package tz.application.packer;

import com.avaje.ebean.Ebean;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.GZIPInputStream;

/**
 * @author Dmitry Shyshkin
 */
public class Packer {
    public static void main(String[] args) throws ParseException, SQLException, IOException {
        FileOutputStream fos = new FileOutputStream("/tmp/battles");
        long day = 24 * 60 * 60 * 1000L;
        long now = new SimpleDateFormat("dd/MM/yyyy").parse("13/10/2010").getTime() + 12 * 60 * 60 * 1000L;
        Date start = new Date(now / day * day);
        Date end = new Date(now / day * day + day);
        Ebean.beginTransaction();
//        PreparedStatement statement = Ebean.currentTransaction().getConnection().prepareStatement("select content from battle where battle_date >= ? and battle_date <= ? ");
//        statement.setDate(1, new java.sql.Date(start.getTime()));
//        statement.setDate(2, new java.sql.Date(end.getTime()));
        PreparedStatement statement = Ebean.currentTransaction().getConnection().prepareStatement("select content from battle");
        ResultSet resultSet = statement.executeQuery();
        char[] buf = new char[0x1000];
        while (resultSet.next()) {
            byte[] content = resultSet.getBytes(1);
            StringBuilder sb = new StringBuilder(content.length * 4);
            Reader reader = new InputStreamReader(new GZIPInputStream(new ByteArrayInputStream(content)), "UTF-8");
            int read;
            while ((read = reader.read(buf)) > 0) {
                sb.append(buf, 0, read);
            }
            fos.write(sb.toString().getBytes("UTF-8"));
            fos.write('\n');
        }
        resultSet.close();
        statement.close();
        Ebean.endTransaction();
        fos.close();
    }
}
