package database.loaders.mysql;

import database.loaders.Loaders;
import structures.TreeNode;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstructLoaders implements Loaders {
    public Connection connection;
    protected Map<String, Loaders> mapInstance = new HashMap<>();

    /**
     * Adding attributes from query result to tree node
     *
     * @param node node for adding attribute
     * @param rs   table of data representing a database result set.
     * @throws SQLException
     */
    public TreeNode createAttrForDbElements(TreeNode node, ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();

        while (rs.next()) {
            HashMap<String, String> attr = fillAttr(rsmd, rs);
            node.setAttributes(attr);
        }
        return node;
    }

    public HashMap<String, String> fillAttr(ResultSetMetaData rsmd, ResultSet rs) throws SQLException {
        HashMap<String, String> attr = new HashMap<>();
        for (int i = 1; i < rsmd.getColumnCount(); i++) {
            String nameData = rsmd.getColumnLabel(i);
            if (rs.getString(nameData) != null && !rs.getString(nameData).isEmpty()) {
                attr.put(nameData, rs.getString(nameData));
            }
        }
        return attr;
    }

    /**
     * Full load for kids node.
     *
     * @param node        children of this node will be loaded
     * @throws SQLException
     */
    public void fullLoadChild(TreeNode node) throws SQLException {

        for (TreeNode childNode : node.getListChild()) {
            Loaders clazz = mapInstance.get(childNode.getData());
            clazz.full(childNode);
        }
    }

    public TreeNode fillParameter(ResultSet resultSetParam, String typeMeta) throws SQLException {

        String parameterName = resultSetParam.getString("PARAMETER_NAME");
        String parameterType = resultSetParam.getString("DTD_IDENTIFIER");
        String parameterPosition = resultSetParam.getString("ORDINAL_POSITION");

        TreeNode parameter = new TreeNode(parameterPosition, typeMeta);
        parameter.getAttributes().put("PARAMETER_NAME", parameterName);
        parameter.getAttributes().put("DTD_IDENTIFIER", parameterType);
        parameter.getAttributes().put("ORDINAL_POSITION", parameterPosition);

        return parameter;
    }

    @Override
    public TreeNode lazy(TreeNode node) throws SQLException {
        return node;
    }

    @Override
    public TreeNode details(TreeNode node) throws SQLException {
        return node;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void loadIntermediateMeta(TreeNode node) throws SQLException {
        for (TreeNode currentNode : node.getListChild()) {
            Loaders clazz = mapInstance.get(currentNode.getData());
            clazz.lazy(currentNode);
        }
    }

    @Override
    public void setMapInstance(Map<String, Loaders> mapInstance) {
        this.mapInstance = mapInstance;
    }
}
