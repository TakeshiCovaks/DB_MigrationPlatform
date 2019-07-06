package database.loaders.mysql;

import database.annotations.load.Load;
import structures.ChildList;
import structures.TreeNode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for loading STORED_PROCEDURE metadata into tree.
 */
@Load(typeMeta = TypeMeta.STORED_PROCEDURE)
public class ProcedureLoader extends AbstructLoaders {

    @Override
    public TreeNode details(TreeNode node) throws SQLException {

        String nameDB = node.getRootTree().getNameElement();

        //Attr
        PreparedStatement psAttr = connection.prepareStatement(Queries.SHOW_META_PROCEDURE_ATTR);
        psAttr.setString(1, nameDB);
        psAttr.setString(2, node.getNameElement());
        ResultSet resultSetAttr = psAttr.executeQuery();
        createAttrForDbElements(node, resultSetAttr);

        // Procedure Body in ATTR
        PreparedStatement psBodyProcedure = connection.prepareStatement(Queries.SHOW_META_PROCEDURE_BODY);
        psBodyProcedure.setString(1, node.getNameElement());
        ResultSet resultSetBody = psBodyProcedure.executeQuery();
        if (resultSetBody.next()) {
            node.getAttributes().put("BODY_PROCEDURE", resultSetBody.getString(1));
        }

        //Parameters as a child
        PreparedStatement ps = connection.prepareStatement(Queries.SHOW_META_PROCEDURE_PARAMETERS);
        ps.setString(1, nameDB);
        ps.setString(2, node.getNameElement());
        ResultSet resultSetParam = ps.executeQuery();
        node.getListChild().clear();
        while (resultSetParam.next()) {
            TreeNode parameter = fillParameter(resultSetParam, TypeMeta.STORED_PROCEDURE);
            node.addChild(parameter);
        }
        return node;
    }

    @Override
    public TreeNode full(TreeNode node) throws SQLException {
        details(node);
        return node;
    }

    @Override
    public TreeNode lazy(TreeNode node) throws SQLException {
        return node;
    }
}
