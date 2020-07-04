package com.eclipse.panel.dbConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.eclipse.panel.Logs;
import com.google.gson.*;

public class DBConnect {

    //error SQL constant
    public static final int ERROR_FOREIGN_KEY = 1452;
    public static final int ERROR_NULL_ELEMENT = 1048;
    public static final int ERROR_DUPLICATE_KEY = 1062;

    private String lastQuery = null;

    public DBConnect() {
        //generateConnextion();
    }

    public boolean connectionVerification() {
        try (
                Connection conn = (new Database(Database.DB_CONTROLLER_NAME)).getConnection();
        ) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Run Select Query
     * @param table
     * @param selected
     * @return JSONArray Select content
     * @throws SQLException
     */
    public JsonArray select(String table, String[] selected) throws SQLException {
        return select(table, selected, null, null, false);
    }

    public JsonArray select(String table, String[] selected, String where, String[] whereValues) throws SQLException {
        return select(table, selected, where, whereValues, false);
    }

    public JsonArray select(String table, String[] selected, String where, String[] whereValues, boolean disableAphostro) throws SQLException {
        JsonArray result = null;
        //Prepare QUERY
        String sql = "SELECT ";
        String aphost = (disableAphostro) ? "" : "`";
        for (String v : selected) {
            sql += aphost + v + aphost + ",";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += " FROM " + aphost + table + aphost;

        if (where != null) sql += " WHERE " + where;

        //System.out.println(sql);

        //Prepare Connection and execute
        return getJsonElements(whereValues, sql, where != null, where);
    }

    private JsonArray getJsonElements(String[] whereValues, String sql, boolean b, String where) throws SQLException {
        JsonArray result;
        try (
                Connection conn = (new Database(Database.DB_CONTROLLER_NAME)).getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            if (b) for (int i = 0; i < whereValues.length; i++) pstmt.setString(i + 1, whereValues[i]);
            //System.out.println("pstms"+ pstmt);
            this.lastQuery = pstmt.toString();
            result = resultToJsonConvert(pstmt.executeQuery());
        }

        return result;
    }

    /**
     * Run query in DB!, becareful!
     * @param query
     * @return
     */
    public JsonArray selectQuery(String query) throws SQLException {
        JsonArray result = null;

        //Prepare Connection and execute
        try (
                Connection conn = (new Database(Database.DB_CONTROLLER_NAME)).getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
        ) {
            this.lastQuery = pstmt.toString();
            result = resultToJsonConvert(pstmt.executeQuery());
        }
        return result;
    }

    public JsonArray selectQuery(String query, String[] whereVal) throws SQLException {
        JsonArray result = null;

        //Prepare Connection and execute
        try (
                Connection conn = (new Database(Database.DB_CONTROLLER_NAME)).getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
        ) {
            if (whereVal != null)
                for (int i = 0; i < whereVal.length; i++)
                    pstmt.setString(i + 1, whereVal[i]);
            //System.out.println("pstms"+ pstmt);
            this.lastQuery = pstmt.toString();
            result = resultToJsonConvert(pstmt.executeQuery());
        }
        return result;
    }

    /**
     * Delete data from DB Query
     * @param table
     * @param where
     * @param whereValues
     * @throws SQLException
     */
    public void delete(String table, String where, String[] whereValues) throws Exception {
        if (where == null || where.length() < 3) throw new Exception("Where in DELETE is MANDATORY!");
        //Prepare QUERY
        String sql = "DELETE FROM " + table + " WHERE " + where;

        //Prepare Connection and excetute
        try (
                Connection conn = (new Database(Database.DB_CONTROLLER_NAME)).getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            for (int i = 0; i < whereValues.length; i++) {
                pstmt.setString(i + 1, whereValues[i]);
            }
            this.lastQuery = pstmt.toString();
            pstmt.executeQuery();
        }
    }

    /**
     * Insert SQL Query.
     * @param table
     * @param idColumn
     * @param columns
     * @param values
     * @return insert ID
     * @throws SQLException
     */
    public String insert(String table, String idColumn, String[] columns, String[] values) throws Exception {
        String id = null;
        if ((columns.length > 0 && values.length > 0) &&
                (columns.length == values.length)) {
            String columnsSQL = "";
            String valuesSQL = "";
            for (String c : columns) {
                columnsSQL += "`" + c + "`,";
                valuesSQL += "?,";
            }
            columnsSQL = columnsSQL.substring(0, columnsSQL.length() - 1);
            valuesSQL = valuesSQL.substring(0, valuesSQL.length() - 1);

            String sql = "INSERT INTO " + table + " (" + columnsSQL + ") values (" + valuesSQL + ")";
            String[] valuesWithWhereValues = values;

            //Run insert...
            String tempLastQuery = sql;
            try (
                    Connection conn = (new Database(Database.DB_CONTROLLER_NAME)).getConnection();
                    PreparedStatement pstmt = conn.prepareStatement(sql);
            ) {
                for (int i = 0; i < valuesWithWhereValues.length; i++) {
                    pstmt.setString(i + 1, valuesWithWhereValues[i]);
                }
                //Logs.saveLog("PSTMT: "+ this.pstmt);
                tempLastQuery = pstmt.toString();
                pstmt.executeUpdate();
            }
            //Get ID after insert...
            String whereID = "";
            List<String> valusSelect = new ArrayList<>();
            for (int i = 0; i < columns.length; i++) {
                if (values[i] != null) {
                    whereID += "`" + columns[i] + "`=? AND ";
                    valusSelect.add(values[i]);
                }
            }
            String[] stockArr = new String[valusSelect.size()];
            stockArr = valusSelect.toArray(stockArr);
            whereID = whereID.substring(0, whereID.length() - 5);
            JsonArray v = select(table,
                    new String[]{idColumn},
                    whereID,
                    stockArr);
            this.lastQuery = tempLastQuery + this.lastQuery; //save insert and select
            if (v.size() == 0) {
                Logs.fatalLog(this.getClass(), "FAIL (EXIT) TO GET ID! - " + sql);
                System.exit(-1);
            } else {
                id = v.get(0).getAsJsonObject().get(idColumn).getAsString();
            }
        } else {
            throw new Exception("Invalid data in SQL Insert '" + this.getClass() + "'");
        }
        return id;
    }

    public String insert(String table, String idColumn, Map<Object, Object> columns) throws Exception {
        String[] nC = new String[columns.size()];
        String[] nV = new String[columns.size()];
        // Info
        int i = 0;
        for (Map.Entry<Object, Object> entry : columns.entrySet()) {
            nC[i] = entry.getKey().toString();
            nV[i] = entry.getValue().toString();
            i++;
        }
        return insert(table, idColumn, nC, nV);
    }

    /**
     * Update Query
     * @param table
     * @param columns
     * @param values
     * @param where
     * @param whereValues
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void update(String table, String[] columns, String[] values, String where, String[] whereValues) throws Exception {
        if ((columns.length > 0 && values.length > 0) &&
                (columns.length == values.length)) {
            String columnsSQL = "";
            for (String c : columns) {
                columnsSQL += "`" + c + "` = ?,";
            }
            columnsSQL = columnsSQL.substring(0, columnsSQL.length() - 1);

            String sql = "UPDATE " + table + " SET " + columnsSQL;
            if (where != null) {
                sql += " WHERE " + where;
                String[] valInSql = new String[values.length + whereValues.length];
                int i = 0;
                for (; i < values.length; i++) valInSql[i] = values[i];
                for (int j = 0; j < whereValues.length; j++, i++) valInSql[i] = whereValues[j];
                values = valInSql;
            }

            //Run update...
            try (
                    Connection conn = (new Database(Database.DB_CONTROLLER_NAME)).getConnection();
                    PreparedStatement pstmt = conn.prepareStatement(sql);
            ) {
                for (int i = 0; i < values.length; i++) {
                    pstmt.setString(i + 1, values[i]);
                }
                //Logs.saveLog("PSTMT: "+ this.pstmt);
                //Run Update
                this.lastQuery = pstmt.toString();
                pstmt.executeUpdate();
            }
        } else {
            throw new Exception("Invalid data in SQL Insert");
        }
    }

    public void update(String table, Map<Object, Object> info, String where, String[] whereValues) throws Exception {
        String[] nC = new String[info.size()];
        String[] nV = new String[info.size()];
        // Info
        int i = 0;
        for (Map.Entry<Object, Object> entry : info.entrySet()) {
            nC[i] = entry.getKey().toString();
            nV[i] = entry.getValue().toString();
            i++;
        }
        update(table, nC, nV, where, whereValues);
    }

    /**
     * Convert SQL Result to JSONArray
     * @return
     * @throws SQLException
     */
    private static JsonArray resultToJsonConvert(ResultSet rs) throws SQLException {
        JsonArray json = new JsonArray();
        ResultSetMetaData rsmd = rs.getMetaData();

        while (rs.next()) {
            int numColumns = rsmd.getColumnCount();
            JsonObject obj = new JsonObject();

            for (int i = 1; i < numColumns + 1; i++) {
                String column_name = rsmd.getColumnName(i);
                Gson gson = new Gson();

                switch (rsmd.getColumnType(i)) {
                    case java.sql.Types.ARRAY:
                        if (rs.getArray(column_name) != null)
                            obj.addProperty(column_name, gson.toJson(rs.getArray(column_name)));
                        else
                            obj.add(column_name, null);
                        break;
                    case java.sql.Types.BIGINT:
                        obj.addProperty(column_name, rs.getLong(column_name));
                        break;
                    case java.sql.Types.BOOLEAN:
                        obj.addProperty(column_name, rs.getBoolean(column_name));
                        break;
                    case java.sql.Types.BLOB:
                        if (rs.getBlob(column_name) != null)
                            obj.addProperty(column_name, gson.toJson(rs.getBlob(column_name)));
                        else
                            obj.add(column_name, null);
                        break;
                    case java.sql.Types.DOUBLE:
                        obj.addProperty(column_name, rs.getDouble(column_name));
                        break;
                    case java.sql.Types.FLOAT:
                        obj.addProperty(column_name, rs.getFloat(column_name));
                        break;
                    case java.sql.Types.TINYINT:
                        obj.addProperty(column_name, rs.getInt(column_name) == 1);
                        break;
                    case java.sql.Types.INTEGER:
                    case java.sql.Types.SMALLINT:
                        obj.addProperty(column_name, rs.getInt(column_name));
                        break;
                    case java.sql.Types.NVARCHAR:
                        obj.addProperty(column_name, rs.getNString(column_name));
                        break;
                    case java.sql.Types.VARCHAR:
                        String elem = rs.getString(column_name);
                        if (elem != null) {
                            try {
                                JsonElement jObject = JsonParser.parseString(elem);
                                if (jObject.isJsonPrimitive()) {
                                    obj.addProperty(column_name, elem);
                                } else {
                                    obj.add(column_name, jObject);
                                }
                            } catch (JsonSyntaxException e) {
                                obj.addProperty(column_name, elem);
                            }
                        }
                        break;
                    case java.sql.Types.DATE:
                        if (rs.getDate(column_name) != null)
                            obj.addProperty(column_name, gson.toJson(rs.getDate(column_name)));
                        else
                            obj.add(column_name, null);
                        break;
                    case java.sql.Types.TIMESTAMP:
                        if (rs.getTimestamp(column_name) != null)
                            obj.addProperty(column_name, rs.getTimestamp(column_name).toString());
                        else
                            obj.add(column_name, null);
                        break;
                    default:
                        if (rs.getObject(column_name) != null)
                            obj.addProperty(column_name, gson.toJson(rs.getObject(column_name)));
                        else
                            obj.add(column_name, null);
                        break;
                }
            }
            json.add(obj);
        }
        return json;
    }

    public String getLastQuery() {
        return this.lastQuery;
    }

}
