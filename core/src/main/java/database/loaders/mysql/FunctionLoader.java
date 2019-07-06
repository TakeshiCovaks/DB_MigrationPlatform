package database.loaders.mysql;

import database.annotations.load.Load;
import structures.ChildList;
import structures.TreeNode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for loading Function metadata into tree.
 */
@Load(typeMeta = TypeMeta.FUNCTION)
public class FunctionLoader extends AbstructLoaders {

    @Override
    public TreeNode lazy(TreeNode node) throws SQLException {
        return node;
    }

    @Override
    public TreeNode details(TreeNode node) throws SQLException {
        String nameDB = node.getRootTree().getNameElement();

        PreparedStatement psMetaFunction = connection.prepareStatement(Queries.SHOW_META_FUNCTION);
        psMetaFunction.setString(1, nameDB);
        psMetaFunction.setString(2, node.getNameElement());
        ResultSet resultSet = psMetaFunction.executeQuery();

        createAttrForDbElements(node, resultSet);
        fullParameterFunction(node, nameDB);

        return node;
    }

    @Override
    public TreeNode full(TreeNode node) throws SQLException {
        details(node);
        return node;
    }

    private TreeNode fullParameterFunction(TreeNode node, String nameDB) throws SQLException {
        PreparedStatement psParametersFunction = connection.prepareStatement(Queries.SHOW_META_FUNCTION_PARAM);
        psParametersFunction.setString(1, nameDB);
        psParametersFunction.setString(2, node.getNameElement());

        ResultSet resultSetParam = psParametersFunction.executeQuery();

        node.getListChild().clear();
        while (resultSetParam.next()) {
            TreeNode parameter = fillParameter(resultSetParam, TypeMeta.FUNCTION);
            node.addChild(parameter);
        }
        return node;
    }
}
